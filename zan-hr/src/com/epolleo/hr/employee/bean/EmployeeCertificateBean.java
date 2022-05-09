package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工资格、职称证书的Bean
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:58
 *
 */
public class EmployeeCertificateBean {

    /**
     * @Fields id : 编号
     */
    private Long id;
       
    /**
     * @Fields employeeId : 员工Id
     */
    private Long employeeId;
       
    /**
     * @Fields name : 证书名称
     */
    private String name;
       
    /**
     * @Fields signDate : 签发日期
     */
    private Date signDate;
       
    /**
     * @Fields expDate : 失效日期
     */
    private Date expDate;
       
    /**
     * @Fields attLotId : 附件号
     */
    private String attLotId;
       
    /**
     * @Fields isDelete : 是否删除
     */
    private Boolean isDelete;
       
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
    
    public String getName() {
        return this.name;
    }
    
	public void setName(String name) {
	    this.name = name;
	}
    
    public Date getSignDate() {
        return this.signDate;
    }
    
	public void setSignDate(Date signDate) {
	    this.signDate = signDate;
	}
    
    public Date getExpDate() {
        return this.expDate;
    }
    
	public void setExpDate(Date expDate) {
	    this.expDate = expDate;
	}
    
    public String getAttLotId() {
        return this.attLotId;
    }
    
	public void setAttLotId(String attLotId) {
	    this.attLotId = attLotId;
	}
    
    public Boolean getIsDelete() {
        return this.isDelete;
    }
    
	public void setIsDelete(Boolean isDelete) {
	    this.isDelete = isDelete;
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
    
}
