package com.epolleo.util.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.bp.util.LoginConstant;
import com.epolleo.pub.att.bean.AttBean;
import com.epolleo.pub.att.service.AttService;
import com.epolleo.util.common.bean.FileIOConfig;

public class FileIOService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * webx 缓存
	 */
	@Resource
	private BufferedRequestContext buffered;

	@Resource
	private AttService attService;

	@Resource
	private FileIOConfig fileIOConfig;

	/**
	 * 文件保存在文件服务器上的完整路径
	 */
	private String attAbsoultePath = null;
	/**
	 * 文件保存在文件服务器上的相对路径
	 */
	private String attRelativePath = null;

	public FileIOConfig getFileIOConfig() {
		return fileIOConfig;
	}

	public String getFilePathByAttBean(AttBean attbean) throws IOException {
		String filePath = fileIOConfig.getFileRepoHome() + attbean.getPath();
		return filePath;
	}

	/**
	 * 将服务器上的文件根据上传规则和AttBean的路径信息，拷贝到指定的modelFilePath下
	 * 
	 * @param attbean
	 * @param modelFilePath
	 * @param attLotId
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public AttBean copyAttEntity(AttBean attbean, String modelFilePath,
			String attLotId, String type) throws IOException {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		String path = null;
		String name = null;
		try {
			name = attbean.getDescription()
					+ attbean.getPath().substring(
							attbean.getPath().lastIndexOf("."));
			String fileName = fileIOConfig.getFileRepoHome() + File.separator
					+ attbean.getPath();
			File remodeFile = new File(fileName);

			path = modelFilePath + File.separator
					+ DateUtils.getDate2SStr1(new Date()) + File.separator
					+ formatFileName(name);
			File uploadFile = new File(fileIOConfig.getFileRepoHome()
					+ File.separator + path);
			if (!uploadFile.getParentFile().exists()) {
				uploadFile.getParentFile().mkdirs();
			}

			fos = new FileOutputStream(uploadFile);
			fis = new FileInputStream(remodeFile);
			byte[] buff = new byte[1024];
			while (fis.read(buff) != -1) {
				fos.write(buff);
			}

			AttBean attBean = new AttBean();
			attBean.setLotId(attLotId);
			attBean.setType(type);
			if (LoginUser.getCurrentUser() != null) {
				attBean.setUpdateBy(LoginUser.getCurrentUser().getUserName());
			} else {
				attBean.setUpdateBy("SysThread");
			}
			attBean.setUpdateDate(DateUtils.getCurrentDate());
			attBean.setPath(path);
			attBean.setDescription(attbean.getDescription());
			return attBean;
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param fileItem
	 * @param modelFilePath
	 * @return
	 */
	private boolean upload(FileItem fileItem, String modelFilePath) {
		if (fileItem == null) {
			return false;
		}
		if (StringUtils.isBlank(modelFilePath)) {
			throw new RuntimeException("子模块路径非法，请检查配置!");
		}
		try {
			String fileName = fileItem.getName();
			String formatFileName = formatFileName(fileName);
			String fileStoreRelativePath = mkDirByMonth(modelFilePath);
			String fileStoreAbsoultePath = fileIOConfig.getFileRepoHome()
					+ fileStoreRelativePath;
			File storeSubDir = new File(fileStoreAbsoultePath);
			if (!storeSubDir.exists()) {// 按规则创建存储文件夹
				storeSubDir.mkdirs();
			}
			attRelativePath = fileStoreRelativePath + formatFileName;
			attAbsoultePath = fileStoreAbsoultePath + formatFileName;
			File uploadFile = new File(attAbsoultePath);
			fileItem.write(uploadFile);
			fileItem.delete();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param inputStream
	 *            :need to saved inputStream
	 * @param fileName
	 *            :need to saved as fileName
	 * @param modelFilePath
	 * @return
	 */
	private boolean upload(final InputStream inputStream, String fileName,
			String modelFilePath) {
		if (inputStream == null || fileName == null) {
			return false;
		}
		if (StringUtils.isBlank(modelFilePath)) {
			throw new RuntimeException("子模块路径非法，请检查配置!");
		}
		try {
			String formatFileName = formatFileName(fileName);
			String fileStoreRelativePath = mkDirByMonth(modelFilePath);
			String fileStoreAbsoultePath = fileIOConfig.getFileRepoHome()
					+ fileStoreRelativePath;
			File storeSubDir = new File(fileStoreAbsoultePath);
			if (!storeSubDir.exists()) {// 按规则创建存储文件夹
				storeSubDir.mkdirs();
			}
			attRelativePath = fileStoreRelativePath + formatFileName;
			attAbsoultePath = fileStoreAbsoultePath + formatFileName;
			File uploadFile = new File(attAbsoultePath);
			try {// save the inputStream to file named as fileName
				OutputStream os = new FileOutputStream(uploadFile);
				int bytesRead = 0;
				byte[] buffer = new byte[1024];
				while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * fileItem.write(uploadFile); fileItem.delete();
			 */
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	private static String mkDirByMonth(String parentPath) {
		String subDirPath = parentPath
				+ DateUtils.getDate2SStr1(DateUtils.getCurrentDate()) + "/";
		File subDirByMonth = new File(subDirPath);
		if (!subDirByMonth.exists()) {
			subDirByMonth.mkdir();
		}
		return subDirPath;
	}

	private String formatFileName(String originalFileName) {
		String formatedFilename = System.currentTimeMillis() + originalFileName;
		return formatedFilename;
	}

	/**
	 * 上传附件到文件服务器，并返回附件在文件服务器上的相对路径
	 * 
	 * @param fileItem
	 * @param modelFilePath
	 * @return
	 * @throws Exception
	 */

	public String getAttRelativePath(FileItem fileItem, String modelFilePath) {
		if (upload(fileItem, modelFilePath)) {
			logger.info("!!!Att upload success!!!");
		} else {
			// logger.warn("!!!Att upload fail!!!");
			throw new RuntimeException("!!!Att upload fail!!!");
		}
		return attRelativePath;
	}

	/**
	 * @param inputStream
	 * @param fileName
	 * @param modelFilePath
	 * @return
	 */
	public String getAttRelativePath(InputStream inputStream, String fileName,
			String modelFilePath) {
		if (upload(inputStream, fileName, modelFilePath)) {
			logger.info("!!!Att upload success!!!");
		} else {
			// logger.warn("!!!Att upload fail!!!");
			throw new RuntimeException("!!!Att upload fail!!!");
		}
		return attRelativePath;
	}

	/**
	 * 下载附件
	 * 
	 * @param req
	 * @param resp
	 * @param attId
	 * @param type
	 *            1.attachment;2.inline
	 */
	public void download(HttpServletRequest req, HttpServletResponse resp,
			Long attId, Boolean isPrview) {
		LoginUser loginUser = (LoginUser) req.getSession().getAttribute(
				LoginConstant.LOGIN_USER_SESSION_KEY);
		if (loginUser != null) {
			if (attId == null) {
				logger.warn("!!!AttId error!!!");
				return;
			}
			AttBean attBean = attService.find(Long.valueOf(attId));
			if (attBean == null) {
				logger.warn("!!!Req att is not exists!!!");
				return;
			}
			isPrview = isPrview == null ? false : isPrview;// 默认对附件做下载处理
			File remoteFile = new File(fileIOConfig.getFileRepoHome()
					+ attBean.getPath());
			pushAtt(attBean.getDescription(), req, resp, remoteFile, isPrview);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @param attBean
	 */
	public void downloadAtts(HttpServletRequest req, HttpServletResponse resp,
			AttBean attBean) {
		LoginUser loginUser = (LoginUser) req.getSession().getAttribute(
				LoginConstant.LOGIN_USER_SESSION_KEY);
		if (loginUser != null) {
			if (attBean == null) {
				logger.warn("!!!Req AttBean is null!!!");
				return;
			}
			File remoteFile = new File(fileIOConfig.getFileRepoHome()
					+ attBean.getPath());
			pushAtt(attBean.getDescription(), req, resp, remoteFile, false);
		}
	}

	/**
	 * public method transfer the att/atts
	 * 
	 * @param resp
	 * @param attBean
	 */
	private void pushAtt(String attName, HttpServletRequest req,
			HttpServletResponse resp, File remoteFile, Boolean isPrview) {

		buffered.setBuffering(false);// 附件下载/预览时关闭Buffering
		OutputStream localOus = null;
		FileInputStream fis = null;
		String fileName = null;
		if (remoteFile != null & remoteFile.exists()) {
			try {
				// 如果没有后缀名，则使用path中的文件后缀名
				if (!attName.contains(".")) {
					String tmpName = remoteFile.getName();
					int index = tmpName.lastIndexOf(".");
					attName += tmpName.substring(index, tmpName.length());
				}
				fileName = encodingFileName(attName, req);
				localOus = resp.getOutputStream();
				// resp.setContentType("image/jpeg;charset=utf-8");
				/*
				 * setHeader "Content-disposition":1.attachment 作为附件下载
				 * 2.inline在线打开
				 */
				String contentDisposition = isPrview ? "inline" : "attachment";

				resp.setHeader("Content-disposition", contentDisposition
						+ ";filename=" + fileName);
				fis = new FileInputStream(remoteFile);

				byte[] b = new byte[1024];
				int len = 0;
				while ((len = fis.read(b)) != -1) {
					try {

						localOus.write(b, 0, len);

					} catch (IOException e) {
						logger.warn(e.getClass() + " - " + e.getMessage());
						break;
					}
				}
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			} finally {
				try {
					fis.close();
				} catch (Exception e) {
				}
				try {
					localOus.close();
				} catch (Exception e) {
				}
			}
		} else {
			logger.info("!!!Req resource is not avivable now!!!");
			return;
		}
	}

	/**
	 * 处理跨浏览器附件中文名乱码、空格
	 * 
	 * @param fileName
	 * @return 编码的fileName
	 * 
	 */
	private String encodingFileName(String fileName, HttpServletRequest req) {
		String userAgent = req.getHeader("user-agent");
		logger.info(userAgent);
		boolean isMSIE = userAgent.contains("MSIE");
		if (!isMSIE) {
			// IE 11 识别问题 : Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0;
			// Touch; rv:11.0) like Gecko
			isMSIE = userAgent.contains("like Gecko");
		}
		boolean isMSIE6 = userAgent.contains("MSIE 6.0");
		boolean isFF = false;
		if (!isMSIE) {
			isFF = userAgent.contains("Mozilla");
		}
		String returnFileName = "";
		try {
			if (isMSIE) {// IE
				returnFileName = URLEncoder.encode(fileName, "UTF-8");
				if (returnFileName.length() > 150 && isMSIE6) {// 处理IE6 无法下载问题
																// "KB816868"
					/*
					 * returnFileName = new String(fileName.getBytes("GB2312"),
					 * "ISO8859-1");//默认设置简体中文GB2312,暂未解决决动态获取编码问题（如繁体中文会出现问题）
					 * returnFileName = StringUtils.replace(returnFileName, " ",
					 * "%20");
					 */
					returnFileName = returnFileName.substring(0, 149);// 截取前150位
				} else {
					returnFileName = StringUtils.replace(returnFileName, "+",
							"%20");
				}
			} else if (isFF) {// FF
				returnFileName = "=?UTF-8?B?"
						+ (new String(Base64.encodeBase64(fileName
								.getBytes("UTF-8")))) + "?=";
			} else {
				returnFileName = fileName;
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage(), e);
		}
		return returnFileName;
	}

	/**
	 * 删除文件服务器上的文件
	 * 
	 * @param attEntity
	 * @return
	 * @throws FileExistsException
	 */
	public boolean deleteAttEntity(AttBean attEntity) {
		File remoteFile = new File(fileIOConfig.getFileRepoHome()
				+ attEntity.getPath());
		if (remoteFile.exists()) {// 请求删除的文件是否存在于文件服务器
			boolean isDelete = remoteFile.delete();
			if (isDelete) {// 实际删除附件文件
				logger.info("!!!Att delete success from the file server!!!");
			} else {
				logger.warn("!!!Att delete fail from the file server!!!");
			}
			return isDelete;
		} else {
			logger.warn("!!!Att is not exist in the file server!!!");
			return true;
		}

	}

	/**
	 * 文件下载方法
	 *
	 * @param req
	 * @param resp
	 * @param remoteStaticFile
	 */
	public void downloadStaticFile(HttpServletRequest req,
			HttpServletResponse resp, File remoteStaticFile) {
		this.downloadStaticFile(req, resp, remoteStaticFile, null);
	}

	/**
	 * 文件下载方法
	 *
	 * @param req
	 * @param resp
	 * @param remoteStaticFile
	 */
	public void downloadStaticFile(HttpServletRequest req,
			HttpServletResponse resp, File remoteStaticFile, String downloadName) {
		LoginUser loginUser = (LoginUser) req.getSession().getAttribute(
				LoginConstant.LOGIN_USER_SESSION_KEY);
		if (loginUser != null) {
			if (remoteStaticFile == null) {
				logger.warn("!!!AttId error!!!");
				return;
			}
			if (StringUtils.isEmpty(downloadName)) {
				downloadName = "Test_download";
			}
			pushAtt(downloadName, req, resp, remoteStaticFile, false);
		}
	}
}
