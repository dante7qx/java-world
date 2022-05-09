package com.epolleo.pub.att.service;

public enum AttServiceEnum {
	UPLOAD_SUCCEED(1, "UPLOAD_SUCCEED", "Upload file succeed!"),
    UPLOAD_FAIL(2, "UPLOAD_FAILED", "Upload file failed!"),
    UPLOAD_FILE_ISNULL(3, "UPLOAD_FILE_ISNULL", "Upload failed,file is null!"),
    UPLOAD_SIZE_LIMIT_EXCEEDED(4, "UPLOAD_SIZE_LIMIT_EXCEEDED", "Upload failed, file size limit exceeded!"),
    UPLOAD_TYPE_LIMIT_EXCEEDED(5, "UPLOAD_TYPE_LIMIT_EXCEEDED", "Upload failed, file type is not in uploaded-file-whitelist!"),
    UPLOAD_UNKNOWN_EXCEPTION(6, "UPLOAD_UNKNOWN_EXCEPTION", "Upload failed, upload unknow exception!"),
    UPLOAD_FILENAME_NOTALLOW_EXCEPTION(7, "UPLOAD_UNKNOWN_EXCEPTION", "Upload failed, upload file name is not allow!");
    public final int code;
    public final String codeName;
    public final String desc;

    private AttServiceEnum(int code, String codeName, String desc) {
        this.code = code;
        this.codeName = codeName;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
