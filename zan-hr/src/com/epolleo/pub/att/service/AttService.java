package com.epolleo.pub.att.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.service.upload.UploadParameters;
import com.alibaba.citrus.service.upload.UploadService;
import com.alibaba.citrus.turbine.Context;
import com.epolleo.bp.pub.AbstractService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.pub.att.bean.AttBean;
import com.epolleo.pub.att.dao.ibatis.AttDao;
import com.epolleo.util.common.service.FileIOService;

public class AttService extends AbstractService<AttBean, AttDao> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	public void setDao(AttDao dao) {
		this.dao = dao;
	}

	@Resource
	private SeqService seqService;

	@Resource
	private FileIOService fileIOService;

	@Resource
	UploadService uploadService;

	/**
	 * @param entity
	 *            新增附件表对象
	 * @return 新增成功的附件表
	 * @see AttBean
	 */
	/*
	 * public AttBean save(AttBean entity) { return dao.save(entity); }
	 */

	/**
	 * @param id
	 *            待删除附件表的关键值（比如主键Key）
	 * @return 如果删除成功，返回1
	 */
	public int delete(Serializable id) {
		int deleteFlag = 0;
		if (id != null) {
			AttBean attEntity = dao.find(id);
			if (fileIOService.deleteAttEntity(attEntity)) {// 从文件服务器删除对应的附件文件
				deleteFlag = dao.delete(id);
			}
		} else {
			logger.warn("!!!Delete Failed,Att Id is null!!!");
		}
		return deleteFlag;
	}

	/**
	 * 遍历删除一个批次的所有附件
	 * 
	 * @param lotId
	 * @return
	 */
	public int deleteByLot(String lotId) {
		int deleteFlag = 0;
		List<Long> attList = dao.getIdByLotId(lotId);
		for (Long id : attList) {
			deleteFlag *= delete(id);
		}
		return deleteFlag;
	}

	/**
	 * @param entity
	 *            修改附件表对象
	 * @return 如果修改成功，返回1
	 * @see AttBean
	 */
	public int update(AttBean entity) {
		return dao.update(entity);
	}

	public int updateForName(AttBean entity) {
		return dao.updateForName(entity);
	}

	/**
	 * @param pagingForm
	 *            分页及查询过滤参数
	 * @return 指定页及条件的查询结果
	 * @see PagingForm
	 */
	public List<AttBean> queryByLotId(String lotId) {
		return dao.queryByLotId(lotId);
	}

	/**
	 * @return 查询附件表的所有记录
	 * @see AttBean
	 */
	public List<AttBean> findAll() {
		return dao.findAll();
	}

	/**
	 * @return 返回指定关键值（比如主键Key）的附件表
	 * @see AttBean
	 */
	public AttBean find(Serializable id) {
		return dao.find(id);
	}

	/**
	 * @param fileItem
	 * @param initAttBean
	 * @param modelFilePath
	 * @return 保存后附件的编号
	 */
	public Long getSaveAttBeanId(FileItem fileItem, AttBean initAttBean,
			String modelFilePath) {
		return saveAttBean(fileItem, initAttBean, modelFilePath).getId();
	}

	/**
	 * @param fileItem
	 * @param initAttBean
	 * @param modelFilePath
	 * @return 附件是否保存成功
	 */
	public boolean isAttBeanSave(FileItem fileItem, AttBean initAttBean,
			String modelFilePath) {
		return (saveAttBean(fileItem, initAttBean, modelFilePath) != null);
	}

	/**
	 * @param fileItem
	 * @param initAttBean
	 * @param modelFilePath
	 * @return 返回保存的AttBean
	 */
	public AttBean saveAttBean(final FileItem initFileItem,
			AttBean initAttBean, String modelFilePath) {

		if (initFileItem == null) {
			throw new RuntimeException("!!!Upload fail,FileItem is null!!!");
		}

		// 重新封装FileItem对象
		FileItem fileItem = getFileItemExtend(initFileItem);
		// 保存文件实体到文件服务器，并获取存储的上传附件的相对路径
		String relativePath = fileIOService.getAttRelativePath(fileItem,
				(String) fileIOService.getFileIOConfig().getModuleFilePathMap()
						.get(modelFilePath));
		initAttBean.setPath(relativePath);

		// 设置上传附件默认描述
		if (StringUtils.isBlank(initAttBean.getDescription())) {
			String fileName = fileItem.getName();
			initAttBean.setDescription(fileName == null ? null : fileName);
		}
		initAttBean.setId(seqService.getNewId(IdKind.AttId));
		AttBean saveBean = dao.save(initAttBean);
		if (saveBean != null) {
			logger.info("!!!Att save in db success!!!");
		} else {
			logger.error("!!!Att save in db fail!!!");
		}
		return saveBean;
	}

	/**
	 * @param fileItem
	 * @param initAttBean
	 * @param modelFilePath
	 * @return 返回保存的AttBean
	 */
	public AttBean saveAttBean(final InputStream inputStream, String fileName,
			AttBean initAttBean, String modelFilePath) {

		if (inputStream == null) {
			throw new RuntimeException("!!!Upload fail,InputStream is null!!!");
		}
		if (fileName == null) {
			throw new RuntimeException(
					"!!!Upload fail,fileName is not allow null!!!");
		}

		// 保存文件实体到文件服务器，并获取存储的上传附件的相对路径
		String relativePath = fileIOService.getAttRelativePath(inputStream,
				fileName, (String) fileIOService.getFileIOConfig()
						.getModuleFilePathMap().get(modelFilePath));
		initAttBean.setPath(relativePath);

		// 设置上传附件默认描述
		if (StringUtils.isBlank(initAttBean.getDescription())) {
			initAttBean.setDescription(fileName == null ? null : fileName
					.substring(0, fileName.lastIndexOf(".")));// 设置不带后缀名的文件名
		}
		initAttBean.setId(seqService.getNewId(IdKind.AttId));
		AttBean saveBean = dao.save(initAttBean);
		if (saveBean != null) {
			logger.info("!!!Att save in db success!!!");
		} else {
			logger.error("!!!Att save in db fail!!!");
		}
		return saveBean;
	}

	/**
	 * @param initAttBeanMap
	 * @param modelFilePath
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<AttBean> saveAttBeans(Map<AttBean, FileItem> initAttBeanMap,
			String modelFilePath) {
		if (initAttBeanMap == null || StringUtils.isBlank(modelFilePath)) {
			logger.warn("!!!Upload parameters invalid!!!");
			return null;
		}
		List<AttBean> saveAttBeanList = new ArrayList<AttBean>();
		Iterator iter = initAttBeanMap.entrySet().iterator();
		FileItem fileItem = null;
		AttBean initAttBean = null;
		while (iter.hasNext()) {
			Entry<AttBean, FileItem> entry = (Entry<AttBean, FileItem>) iter
					.next();
			fileItem = getFileItemExtend(entry.getValue());
			initAttBean = entry.getKey();
			saveAttBean(fileItem, initAttBean, modelFilePath);
		}
		return saveAttBeanList;
	}

	/**
	 * @param initFileItem
	 * @return 重新封装的FileItem对象
	 */
	@SuppressWarnings("serial")
	private FileItem getFileItemExtend(final FileItem initFileItem) {
		FileItem fileItem = null;
		if (initFileItem != null)
			fileItem = new FileItem() {
				@Override
				public void write(File arg0) throws Exception {
					initFileItem.write(arg0);
				}

				@Override
				public void setFormField(boolean arg0) {
					initFileItem.setFormField(arg0);
				}

				@Override
				public void setFieldName(String arg0) {
					initFileItem.setFieldName(arg0);
				}

				@Override
				public boolean isInMemory() {
					return initFileItem.isInMemory();
				}

				@Override
				public boolean isFormField() {
					return initFileItem.isFormField();
				}

				@Override
				public String getString(String arg0)
						throws UnsupportedEncodingException {
					return initFileItem.getString(arg0);
				}

				@Override
				public String getString() {
					return initFileItem.getString();
				}

				@Override
				public long getSize() {
					return initFileItem.getSize();
				}

				@Override
				public OutputStream getOutputStream() throws IOException {
					return initFileItem.getOutputStream();
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.apache.commons.fileupload.FileItem#getName() /*
				 * FileItem Returns the original filename in the client's
				 * filesystem, as provided by the browser (or other client
				 * software). In most cases, this will be the base file name,
				 * without path information. However, some clients, such as the
				 * Opera browser, do include path information.
				 */
				@Override
				public String getName() {
					String fileName = initFileItem.getName();
					return fileName.contains(File.separator) ? fileName
							.substring(fileName.lastIndexOf(File.separator) + 1)
							: fileName;
				}

				@Override
				public InputStream getInputStream() throws IOException {
					return initFileItem.getInputStream();
				}

				@Override
				public String getFieldName() {
					return initFileItem.getFieldName();
				}

				@Override
				public String getContentType() {
					return initFileItem.getContentType();
				}

				@Override
				public byte[] get() {
					return initFileItem.get();
				}

				@Override
				public void delete() {
					initFileItem.delete();
				}
			};
		return fileItem;
	}

	/**
	 * @param lotId
	 * @return 获得一个批次lotId的所有附件Id编号
	 */
	public List<Long> getIdByLotId(String lotId) {
		return dao.getIdByLotId(lotId);
	}

	/**
	 * @param req
	 * @param parser
	 * @param uploadParams
	 * @param attItemStr
	 * @return error codes
	 */
	public Map<AttServiceEnum, String> attVerify(HttpServletRequest req,
			ParserRequestContext parser, UploadParameters uploadParams,
			String attItemStr) {

		// 上传失败&上传文件过大校验
		Map<AttServiceEnum, String> attVerfiyResult = new HashMap<AttServiceEnum, String>();
		boolean isVaild = true;
		if (Boolean.parseBoolean(req
				.getParameter(ParserRequestContext.UPLOAD_FAILED))) {// 这里表示upload_failed
			isVaild = false;// 上传附件失败
			// Object obj =
			// parser.getParameters().get(ParserRequestContext.UPLOAD_SIZE_LIMIT_EXCEEDED+".cause");//上传附件失败的异常
			if (Boolean
					.parseBoolean(req
							.getParameter(ParserRequestContext.UPLOAD_SIZE_LIMIT_EXCEEDED))) {
				// 超过最大附件限制
				attVerfiyResult.put(AttServiceEnum.UPLOAD_SIZE_LIMIT_EXCEEDED,
						null);
			} else {
				// 其它的异常,简单抛出
				attVerfiyResult.put(AttServiceEnum.UPLOAD_UNKNOWN_EXCEPTION,
						null);
			}
		}

		// 是否违反白名单&上传文件是否为空校验
		ParameterParser paramParser = parser.getParameters();
		String[] localFileNames = paramParser.getStrings(attItemStr);
		FileItem[] fileItems = paramParser.getFileItems(attItemStr);
		if (localFileNames != null && localFileNames != null) {
			if (localFileNames.length != fileItems.length) {// 违反白名单被过滤
				StringBuffer sb = new StringBuffer();
				sb.append("[");
				for (String name : localFileNames) {
					if ("".equals(name))
						continue;
					boolean exists = false;// 默认不存在
					int i = 0;
					for (FileItem item : fileItems) {
						if (this.getFileItemExtend(item).getName().equalsIgnoreCase(name)) {
							exists = true;
						}
					}
					if (!exists) {
						isVaild = false;
						sb.append(name.substring(name.lastIndexOf(".") + 1));
						if (i != (localFileNames.length - 1)) {
							sb.append(",");
						}
					}
					i++;
				}
				sb.append("]");
				attVerfiyResult.put(AttServiceEnum.UPLOAD_TYPE_LIMIT_EXCEEDED,
						sb.toString());
			} else if (localFileNames.length == 0
					&& (localFileNames.length == fileItems.length)) {// 上传附件为空处理
				if (isVaild) {// 排除UPLOAD_SIZE_LIMIT_EXCEEDED，UPLOAD_UNKNOWN_EXCEPTION，UPLOAD_FAILED，UPLOAD_TYPE_LIMIT_EXCEEDED
								// 附件为空的情况
					isVaild = false;
					attVerfiyResult
							.put(AttServiceEnum.UPLOAD_FILE_ISNULL, null);
				}
			}
			// 附件名包含 % ;
			for (String name : localFileNames) {
				if (!verifyFileName(name)) {// 附件名包含 % ;
					isVaild = false;
					attVerfiyResult.put(
							AttServiceEnum.UPLOAD_FILENAME_NOTALLOW_EXCEPTION,
							null);
					break;
				}
			}
		}

		// 附件上传成功标识
		if (isVaild) {
			attVerfiyResult.put(AttServiceEnum.UPLOAD_SUCCEED, null);
		}

		return attVerfiyResult;
	}

	public String attVerfiyResultWapper(
			Map<AttServiceEnum, String> attVerfiyResult,
			UploadParameters uploadParams) {
		if (attVerfiyResult == null) {
			return null;
		}
		StringBuffer resutSb = new StringBuffer();
		Iterator<AttServiceEnum> keyIter = attVerfiyResult.keySet().iterator();
		boolean isVaild = false;
		while (keyIter.hasNext()) {
			AttServiceEnum ae = keyIter.next();
			String result = attVerfiyResult.get(ae);
			result = (result == null ? "" : result);
			if (ae.equals(AttServiceEnum.UPLOAD_SUCCEED)) {
				resutSb.append(result);
				resutSb.append("上传文件成功!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_FAIL)) {
				resutSb.append(result);
				resutSb.append("上传文件失败!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_FILE_ISNULL)) {

				resutSb.append(result);
				resutSb.append("上传文件不能为空!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_TYPE_LIMIT_EXCEEDED)) {
				resutSb.append(result);
				resutSb.append("为限制上传类型!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_SIZE_LIMIT_EXCEEDED)) {
				resutSb.append(result);
				resutSb.append("文件大小已超过上传文件大小限制!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_UNKNOWN_EXCEPTION)) {
				resutSb.append(result);
				resutSb.append("文件上传未知错误!<br>");
			} else if (ae
					.equals(AttServiceEnum.UPLOAD_FILENAME_NOTALLOW_EXCEPTION)) {
				resutSb.append(result);
				resutSb.append("附件名称中含有特殊字符，不允许上传!<br>");
			}
		}
		// Upload tip
		if (!isVaild) {// Upload failed tips
			resutSb.append("请选择格式为："
					+ fileIOService.getFileIOConfig().getUploadConfigMap()
							.get("fileWhiteList")
					+ "的文件，单文件且大小不超过"
					+ (uploadParams == null ? uploadService.getFileSizeMax()
							: uploadParams.getFileSizeMax())
					+ "，总大小不超过"
					+ (uploadParams == null ? uploadService.getSizeMax()
							: uploadParams.getSizeMax()) + "的文件上传！<br>");
		}

		return resutSb.toString();
	}

	public boolean attVerify(HttpServletRequest req, Context context,
			ParserRequestContext parser, UploadParameters uploadParams,
			String attItemStr) {
		boolean isVaild = false;
		Map<AttServiceEnum, String> attVerfiyResult = this.attVerify(req,
				parser, uploadParams, attItemStr);
		StringBuffer resutSb = new StringBuffer();
		Iterator<AttServiceEnum> keyIter = attVerfiyResult.keySet().iterator();
		boolean isNotPubTip = false;
		while (keyIter.hasNext()) {
			AttServiceEnum ae = keyIter.next();
			String result = attVerfiyResult.get(ae);
			result = (result == null ? "" : result);
			if (ae.equals(AttServiceEnum.UPLOAD_SUCCEED)) {
				isVaild = true;
			} else if (ae.equals(AttServiceEnum.UPLOAD_FAIL)) {
				resutSb.append(result);
				resutSb.append("上传文件失败!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_FILE_ISNULL)) {
				isVaild = true;// 兼容历史API处理，对于有非空要求的附件需要另外封装方法
				resutSb.append(result);
				resutSb.append("上传文件不能为空!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_TYPE_LIMIT_EXCEEDED)) {
				resutSb.append(result);
				resutSb.append("为限制上传类型!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_SIZE_LIMIT_EXCEEDED)) {
				resutSb.append(result);
				resutSb.append("文件大小已超过上传文件大小限制!<br>");
			} else if (ae.equals(AttServiceEnum.UPLOAD_UNKNOWN_EXCEPTION)) {
				resutSb.append(result);
				resutSb.append("文件上传未知错误!<br>");
			} else if (ae
					.equals(AttServiceEnum.UPLOAD_FILENAME_NOTALLOW_EXCEPTION)) {
				isNotPubTip = true;// 文件名过滤，不提供公共错误提示
				resutSb.append(result);
				resutSb.append("附件名称中含有特殊字符，不允许上传!<br>");
			}
		}
		// Upload tip
		if (!isVaild && !isNotPubTip) {// Upload failed tips
			resutSb.append("请选择格式为："
					+ fileIOService.getFileIOConfig().getUploadConfigMap()
							.get("fileWhiteList")
					+ "的文件，单文件大小不超过"
					+ (uploadParams == null ? uploadService.getFileSizeMax()
							: uploadParams.getFileSizeMax())
					+ "，且总大小不超过"
					+ (uploadParams == null ? uploadService.getSizeMax()
							: uploadParams.getSizeMax()) + "的文件上传！<br>");
		}
		context.put("json", resutSb.toString());
		return isVaild;
	}

	/**
	 * 上传附件
	 *
	 * @param attLotId
	 * @param fileItems
	 * @param type
	 * @param folderPath
	 * @return boolean
	 */
	public boolean upload(String attLotId, FileItem[] fileItems, String type,
			String folderPath) {
		boolean flag = false;
		if (fileItems != null && fileItems.length > 0) {
			for (FileItem file : fileItems) {
				AttBean attBean = new AttBean();
				attBean.setLotId(attLotId);
				attBean.setType(type);
				attBean.setUpdateBy(LoginUser.getCurrentUser().getUserId());
				attBean.setUpdateDate(DateUtils.getCurrentDate());
				flag = isAttBeanSave(file, attBean, folderPath);
			}
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除已被删除的附件
	 *
	 * @param ids
	 */
	public void deleteExistAttachment(String ids) {
		if (StringUtils.isNotEmpty(ids)) {
			String[] idArr = ids.substring(0, ids.length() - 1).split(",");
			for (String id : idArr) {
				this.delete(Long.valueOf(id));
			}
		}
	}

	/**
	 * 检查附件名是否合法 过滤文件名包含 % ; 的附件
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean verifyFileName(String fileName) {
		boolean isValid = true;
		if (fileName == null || fileName.contains("%")
				|| fileName.contains(";") || fileName.contains("；")) {
			isValid = false;
		}
		return isValid;
	}

	public List<AttBean> queryByLotIds(String lotId) {
		List<String> lotids = null;
		if (StringUtils.isNotEmpty(lotId)) {
			String[] idStrArr = lotId.split(",");
			lotids = new ArrayList<String>();
			for (int i = 0; i < idStrArr.length; i++) {
				lotids.add(idStrArr[i]);
			}
		}
		return dao.queryByLotIds(lotids);
	}

}