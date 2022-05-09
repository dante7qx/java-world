package com.epolleo.pub.att.bean;

import java.util.Date;

public class AttBean {
	// 编号
    private Long id;
    // 附件批号，一个批次号对用多个附件，生成规则使用UUID，参考公共方法UUID.randomUUID()
    private String lotId;
    // 类型
    private String type;
    // 附件描述，用户未填入描述则取带后缀的文件全名，在附件下载中链接显示的名称。
    private String description;
    // 附件路径，附件在文件服务器的相对路径
    private String path;
    // 更新人
    private String updateBy;
    //更新人姓名
    public String updateByUserName;
    // 更新时间
    private Date updateDate;

    /**
     * 获取属性id - "编号"的值.
     * 
     * @return 返回值是{@link Long }
     * @see setId
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置属性id - "编号"的值.
     * 
     * @param id 允许传入的值 是{@link Long }
     * @see getId
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取属性lotId - "附件批号"的值.
     * 
     * @return 返回值是{@link String }
     * @see setLotId
     */
    public String getLotId() {
        return this.lotId;
    }

    /**
     * 设置属性lotId - "附件批号"的值.
     * 
     * @param lotId 允许传入的值 是{@link String }
     * @see getLotId
     */
    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    /**
     * 获取属性type - "类型"的值.
     * 
     * @return 返回值是{@link String }
     * @see setType
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置属性type - "类型"的值.
     * 
     * @param type 允许传入的值 是{@link String }
     * @see getType
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取属性description - "附件描述"的值.
     * 
     * @return 返回值是{@link String }
     * @see setDescription
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置属性description - "附件描述"的值.
     * 
     * @param description 允许传入的值 是{@link String }
     * @see getDescription
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取属性path - "附件路径"的值.
     * 
     * @return 返回值是{@link String }
     * @see setPath
     */
    public String getPath() {
        return this.path;
    }

    /**
     * 设置属性path - "附件路径"的值.
     * 
     * @param path 允许传入的值 是{@link String }
     * @see getPath
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取属性updateBy - "更新人"的值.
     * 
     * @return 返回值是{@link String }
     * @see setUpdateBy
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 设置属性updateBy - "更新人"的值.
     * 
     * @param updateBy 允许传入的值 是{@link String }
     * @see getUpdateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取属性updateDate - "更新时间"的值.
     * 
     * @return 返回值是{@link Date }
     * @see setUpdateDate
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置属性updateDate - "更新时间"的值.
     * 
     * @param updateDate 允许传入的值 是{@link Date }
     * @see getUpdateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateByUserName() {
        return this.updateByUserName;
    }

    public void setUpdateByUserName(String updateByUserName) {
        this.updateByUserName = updateByUserName;
    }
}