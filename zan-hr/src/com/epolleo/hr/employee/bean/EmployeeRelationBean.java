package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工家属信息的Bean
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:06
 *
 */
public class EmployeeRelationBean {

    /**
     * @Fields id : 编号
     */
    private Long id;
       
    /**
     * @Fields employeeId : 员工Id
     */
    private Long employeeId;
       
    /**
     * @Fields name : 名称
     */
    private String name;
       
    /**
     * @Fields relation : 与本人关系
     */
    private Integer relation;
       
    /**
     * @Fields phone : 联系电话
     */
    private String phone;
       
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
    
    public Integer getRelation() {
        return this.relation;
    }
    
	public void setRelation(Integer relation) {
	    this.relation = relation;
	}
    
    public String getPhone() {
        return this.phone;
    }
    
	public void setPhone(String phone) {
	    this.phone = phone;
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
