package com.epolleo.util.common.bean;

import java.util.Map;

public class FileIOConfig {
	/**
	 * 模块路径在template-data.xml中的moduleFilePathMap中配置 各自模块的sub folder
	 */
	private Map<String, String> moduleFilePathMap;
	/**
	 * 上传配置参数
	 */

	/**
	 * uploadConfigMap keys:[sizeMax,fileSizeMax,sizeThreshold,fileWhiteList]
	 * 
	 */
	private Map<String, String> uploadConfigMap;
	private String fileRepoHome;
	private String fileRepoTmp;

	public String getFileRepoHome() {
		return fileRepoHome;
	}

	public void setFileRepoHome(String fileRepoHome) {
		this.fileRepoHome = fileRepoHome;
	}

	public String getFileRepoTmp() {
		return fileRepoTmp;
	}

	public void setFileRepoTmp(String fileRepoTmp) {
		this.fileRepoTmp = fileRepoTmp;
	}

	public Map<String, String> getModuleFilePathMap() {
		return moduleFilePathMap;
	}

	public void setModuleFilePathMap(Map<String, String> moduleFilePathMap) {
		this.moduleFilePathMap = moduleFilePathMap;
	}

	public Map<String, String> getUploadConfigMap() {
		return uploadConfigMap;
	}

	public void setUploadConfigMap(Map<String, String> uploadConfigMap) {
		this.uploadConfigMap = uploadConfigMap;
	}
}
