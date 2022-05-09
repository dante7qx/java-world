package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工薪资福利的Bean
 * 
 * @author dante
 * @date 2015-09-26 下午03:55:39
 *
 */
public class EmployeeBenefitBean {

    /**
     * @Fields id : 编号
     */
    private Long id;
       
    /**
     * @Fields employeeId : 员工Id
     */
    private Long employeeId;
       
    /**
     * @Fields preTaxSalary : 税前工资
     */
    private Double preTaxSalary;
       
    /**
     * @Fields trialPeriodSalary : 试用期工资
     */
    private Double trialPeriodSalary;
       
    /**
     * @Fields bankAccount : 工资卡开户行
     */
    private String bankAccount;
       
    /**
     * @Fields salaryCardNo : 工资卡号
     */
    private String salaryCardNo;
       
    /**
     * @Fields providentFundDepositBase : 住房公积金缴存基数
     */
    private Double providentFundDepositBase;
       
    /**
     * @Fields insureDepositBase : 保险缴存基数
     */
    private Double insureDepositBase;
       
    /**
     * @Fields payDate : 起缴日期
     */
    private Date payDate;
       
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
    
    public Double getPreTaxSalary() {
        return this.preTaxSalary;
    }
    
	public void setPreTaxSalary(Double preTaxSalary) {
	    this.preTaxSalary = preTaxSalary;
	}
    
    public Double getTrialPeriodSalary() {
        return this.trialPeriodSalary;
    }
    
	public void setTrialPeriodSalary(Double trialPeriodSalary) {
	    this.trialPeriodSalary = trialPeriodSalary;
	}
    
    public String getBankAccount() {
        return this.bankAccount;
    }
    
	public void setBankAccount(String bankAccount) {
	    this.bankAccount = bankAccount;
	}
    
    public String getSalaryCardNo() {
        return this.salaryCardNo;
    }
    
	public void setSalaryCardNo(String salaryCardNo) {
	    this.salaryCardNo = salaryCardNo;
	}
    
    public Double getProvidentFundDepositBase() {
        return this.providentFundDepositBase;
    }
    
	public void setProvidentFundDepositBase(Double providentFundDepositBase) {
	    this.providentFundDepositBase = providentFundDepositBase;
	}
    
    public Double getInsureDepositBase() {
        return this.insureDepositBase;
    }
    
	public void setInsureDepositBase(Double insureDepositBase) {
	    this.insureDepositBase = insureDepositBase;
	}
    
    public Date getPayDate() {
        return this.payDate;
    }
    
	public void setPayDate(Date payDate) {
	    this.payDate = payDate;
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
