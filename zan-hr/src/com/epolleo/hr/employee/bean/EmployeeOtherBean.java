package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工补充信息的Bean
 * 
 * @author dante
 * @date 2015-09-26 下午03:56:07
 *
 */
public class EmployeeOtherBean {

    /**
     * @Fields id : 编号
     */
    private Long id;
       
    /**
     * @Fields employeeId : 员工Id
     */
    private Long employeeId;
       
    /**
     * @Fields attLotId : 附件号
     */
    private String attLotId;
       
    /**
     * @Fields remark : 备注
     */
    private String remark;
       
    /**
     * @Fields createUser : 创建人
     */
    private String createUser;
       
    /**
     * @Fields createTime : 创建时间
     */
    private Date createTime;
       
    /**
     * @Fields updateUser : 更新人
     */
    private String updateUser;
       
    /**
     * @Fields updateTime : 更新时间
     */
    private Date updateTime;
    
    /**
     * @Fields deletedFileIds : 被删除的附件
     */
    private String deletedFileIds;
       
    public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
	    this.id = id;
	}
    
    public Long getEmployeeId() {
        return this.employeeId;
    }
    
	public void setEmployeeId(Long employeeId) {
	    this.employeeId = employeeId;
	}
    
    public String getAttLotId() {
        return this.attLotId;
    }
    
	public void setAttLotId(String attLotId) {
	    this.attLotId = attLotId;
	}
    
    public String getRemark() {
        return this.remark;
    }
    
	public void setRemark(String remark) {
	    this.remark = remark;
	}
    
    public String getCreateUser() {
        return this.createUser;
    }
    
	public void setCreateUser(String createUser) {
	    this.createUser = createUser;
	}
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
	public void setCreateTime(Date createTime) {
	    this.createTime = createTime;
	}
    
    public String getUpdateUser() {
        return this.updateUser;
    }
    
	public void setUpdateUser(String updateUser) {
	    this.updateUser = updateUser;
	}
    
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
	public void setUpdateTime(Date updateTime) {
	    this.updateTime = updateTime;
	}

	public String getDeletedFileIds() {
		return deletedFileIds;
	}

	public void setDeletedFileIds(String deletedFileIds) {
		this.deletedFileIds = deletedFileIds;
	}
    
}
