package org.dante.springmvc.web.upload.service;

import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	
	@Value("${file.upload.base.directory}")
	private String baseDirectory;

	public boolean upload(MultipartFile file) throws Exception {
		boolean flag = false;
		if (file != null) {
			flag = this.upload(new MultipartFile[] { file });
		} else {
			throw new Exception("文件上传失败！");
		}

		return flag;
	}

	public boolean upload(MultipartFile[] files) throws Exception {
		boolean flag = false;
		try {
			if(files == null || files.length == 0) {
				throw new Exception("文件为空，请先选择要上传的附件！");
			}
			// 检查根目录是否存在
			File baseFile = new File(baseDirectory);
			if(!baseFile.exists()) {
				baseFile.mkdirs();
			}
			for (MultipartFile file : files) {
				FileUtils.writeByteArrayToFile(new File(baseDirectory + File.separator + this.formatFileName(file.getOriginalFilename())), file.getBytes());
			}
			flag = true;
		} catch(Exception e) {
			throw new Exception("文件上传失败！", e);
		}
		return flag;
	}

	
	private String formatFileName(String fileName) {
		return UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
	}
	
}
