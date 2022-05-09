package org.dante.springmvc.web.upload.controller;

import javax.annotation.Resource;

import org.dante.springmvc.web.upload.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	private final static Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource
	private UploadService uploadService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartFile[] files) {
		String result = "ok";
		try {
			uploadService.upload(files);
		} catch (Exception e) {
			logger.error("文件上传失败！", e);
			result = "wrong";
		}
		return result;
	}
	
}
