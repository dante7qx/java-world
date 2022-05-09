package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工工作信息的Bean
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:02
 *
 */
public class EmployeeWorkBean {

    /**
     * @Fields id : 编号
     */
    private Long id;
       
    /**
     * @Fields employeeId : 员工Id
     */
    private Long employeeId;
    
    /**
     * @Fields workPlaceId : 工作地点Id
     */
    private Integer workPlaceId;
       
    /**
     * @Fields company : 入职公司
     */
    private Integer company;
       
    /**
     * @Fields dept : 入职部门
     */
    private Integer dept;
       
    /**
     * @Fields post : 入职岗位
     */
    private Integer post;
       
    /**
     * @Fields capacityLevel : 能力级别
     */
    private Integer capacityLevel;
       
    /**
     * @Fields entrantDate : 入职日期
     */
    private Date entrantDate;
       
    /**
     * @Fields type : 员工类型
     */
    private Integer type;
       
    /**
     * @Fields trialPeriod : 试用期
     */
    private Integer trialPeriod;
       
    /**
     * @Fields contractDeadline : 合同期限
     */
    private Integer contractDeadline;
       
    /**
     * @Fields contractType : 合同类型
     */
    private Integer contractType;
       
    /**
     * @Fields contractStartDate : 合同期限开始时间
     */
    private Date contractStartDate;
       
    /**
     * @Fields contractEndDate : 合同期限结束时间
     */
    private Date contractEndDate;
       
    /**
     * @Fields trialDeadline : 试用期结束日期
     */
    private Date trialDeadline;
       
    /**
     * @Fields practiceStartDate : 实习期限开始时间
     */
    private Date practiceStartDate;
       
    /**
     * @Fields practiceEndDate : 实习期限结束时间
     */
    private Date practiceEndDate;
       
    /**
     * @Fields parttimeStartDate : 兼职期限开始时间
     */
    private Date parttimeStartDate;
       
    /**
     * @Fields parttimeEndDate : 兼职期限结束时间
     */
    private Date parttimeEndDate;
       
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
    
    public Integer getCompany() {
        return this.company;
    }
    
	public void setCompany(Integer company) {
	    this.company = company;
	}
    
    public Integer getDept() {
        return this.dept;
    }
    
	public void setDept(Integer dept) {
	    this.dept = dept;
	}
    
    public Integer getPost() {
        return this.post;
    }
    
	public void setPost(Integer post) {
	    this.post = post;
	}
    
    public Integer getCapacityLevel() {
        return this.capacityLevel;
    }
    
	public void setCapacityLevel(Integer capacityLevel) {
	    this.capacityLevel = capacityLevel;
	}
    
    public Date getEntrantDate() {
        return this.entrantDate;
    }
    
	public void setEntrantDate(Date entrantDate) {
	    this.entrantDate = entrantDate;
	}
    
    public Integer getType() {
        return this.type;
    }
    
	public void setType(Integer type) {
	    this.type = type;
	}
    
    public Integer getTrialPeriod() {
        return this.trialPeriod;
    }
    
	public void setTrialPeriod(Integer trialPeriod) {
	    this.trialPeriod = trialPeriod;
	}
    
    public Integer getContractDeadline() {
        return this.contractDeadline;
    }
    
	public void setContractDeadline(Integer contractDeadline) {
	    this.contractDeadline = contractDeadline;
	}
    
    public Integer getContractType() {
        return this.contractType;
    }
    
	public void setContractType(Integer contractType) {
	    this.contractType = contractType;
	}
    
    public Date getContractStartDate() {
        return this.contractStartDate;
    }
    
	public void setContractStartDate(Date contractStartDate) {
	    this.contractStartDate = contractStartDate;
	}
    
    public Date getContractEndDate() {
        return this.contractEndDate;
    }
    
	public void setContractEndDate(Date contractEndDate) {
	    this.contractEndDate = contractEndDate;
	}
    
    public Date getTrialDeadline() {
        return this.trialDeadline;
    }
    
	public void setTrialDeadline(Date trialDeadline) {
	    this.trialDeadline = trialDeadline;
	}
    
    public Date getPracticeStartDate() {
        return this.practiceStartDate;
    }
    
	public void setPracticeStartDate(Date practiceStartDate) {
	    this.practiceStartDate = practiceStartDate;
	}
    
    public Date getPracticeEndDate() {
        return this.practiceEndDate;
    }
    
	public void setPracticeEndDate(Date practiceEndDate) {
	    this.practiceEndDate = practiceEndDate;
	}
    
    public Date getParttimeStartDate() {
        return this.parttimeStartDate;
    }
    
	public void setParttimeStartDate(Date parttimeStartDate) {
	    this.parttimeStartDate = parttimeStartDate;
	}
    
    public Date getParttimeEndDate() {
        return this.parttimeEndDate;
    }
    
	public void setParttimeEndDate(Date parttimeEndDate) {
	    this.parttimeEndDate = parttimeEndDate;
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

	public Integer getWorkPlaceId() {
		return workPlaceId;
	}

	public void setWorkPlaceId(Integer workPlaceId) {
		this.workPlaceId = workPlaceId;
	}

}
