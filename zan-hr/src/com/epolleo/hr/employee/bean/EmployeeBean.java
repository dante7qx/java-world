package com.epolleo.hr.employee.bean;

import java.util.Date;

/**
 * @Description: 员工的Bean
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:53
 *
 */
public class EmployeeBean {

	/**
	 * @Fields id : 员工Id
	 */
	private Long id;

	/**
	 * @Fields code : 员工编号
	 */
	private String code;

	/**
	 * @Fields chnName : 员工名称
	 */
	private String chnName;

	/**
	 * @Fields engName : 员工英文名称
	 */
	private String engName;

	/**
	 * @Fields usedName : 员工曾用名
	 */
	private String usedName;

	/**
	 * @Fields idNum : 身份证号
	 */
	private String idNum;

	/**
	 * @Fields birthday : 出生日期
	 */
	private Date birthday;

	/**
	 * @Fields gen : 性别
	 */
	private Integer gen;

	/**
	 * @Fields recruitment : 籍贯
	 */
	private String recruitment;

	/**
	 * @Fields horoscope : 星座
	 */
	private Integer horoscope;

	/**
	 * @Fields household : 户口性质
	 */
	private Integer household;

	/**
	 * @Fields politics : 政治面貌
	 */
	private Integer politics;

	/**
	 * @Fields marriage : 婚姻状况
	 */
	private Integer marriage;

	/**
	 * @Fields phone : 联系电话
	 */
	private String phone;

	/**
	 * @Fields email : 电子邮箱
	 */
	private String email;

	/**
	 * @Fields healthValid : 健康证明有效期
	 */
	private Date healthValid;

	/**
	 * @Fields education : 学历
	 */
	private Integer education;

	/**
	 * @Fields major : 专业
	 */
	private String major;

	/**
	 * @Fields graduateInstitution : 毕业院校
	 */
	private String graduateInstitution;

	/**
	 * @Fields graduateDate : 毕业时间
	 */
	private Date graduateDate;

	/**
	 * @Fields languageSkill : 语种
	 */
	private Integer languageSkill;

	/**
	 * @Fields urgencyLinkman : 紧急联系人/电话
	 */
	private String urgencyLinkman;

	/**
	 * @Fields fileLocation : 档案存放地
	 */
	private String fileLocation;

	/**
	 * @Fields householdLocation : 户口所在地
	 */
	private String householdLocation;

	/**
	 * @Fields dwell : 居住地址
	 */
	private String dwell;

	/**
	 * @Fields attLotId : 附件号
	 */
	private String attLotId;
	
	/**
	 * @Fields isActive : 是否生效
	 */
	private Boolean isActive;

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

	/**
	 * @Field deptId : 部门Id
	 */
	private Integer deptId;
	
	/**
	 * @Field dept : 部门
	 */
	private String dept;

	/**
	 * @Fields workType : 用工类型
	 */
	private String workType;

	/**
	 * @Fields post : 职位
	 */
	private String post;
	
	/**
	 * @Fields orgId : 组织机构
	 */
	private Long orgId;
	
	/**
	 * @Fields orgName : 组织机构
	 */
	private String orgName;
	
	/**
	 * @Fields workPlaceId : 工作地点
	 */
	private Long workPlaceId;
	
	/**
	 * @Fields workPlaceName : 工作地点
	 */
	private String workPlaceName;
	
	/**
	 * @Fields entrantDate : 入职日期
	 */
	private Date entrantDate;
	
	/**
	 * @Fields entrantStartDate : 入职开始日期
	 */
	private Date entrantStartDate;
	
	/**
	 * @Fields entrantEndDate : 入职结束日期
	 */
	private Date entrantEndDate;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChnName() {
		return this.chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getEngName() {
		return this.engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getUsedName() {
		return this.usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getIdNum() {
		return this.idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGen() {
		return this.gen;
	}

	public void setGen(Integer gen) {
		this.gen = gen;
	}

	public String getRecruitment() {
		return this.recruitment;
	}

	public void setRecruitment(String recruitment) {
		this.recruitment = recruitment;
	}

	public Integer getHoroscope() {
		return this.horoscope;
	}

	public void setHoroscope(Integer horoscope) {
		this.horoscope = horoscope;
	}

	public Integer getHousehold() {
		return this.household;
	}

	public void setHousehold(Integer household) {
		this.household = household;
	}

	public Integer getPolitics() {
		return this.politics;
	}

	public void setPolitics(Integer politics) {
		this.politics = politics;
	}

	public Integer getMarriage() {
		return this.marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHealthValid() {
		return this.healthValid;
	}

	public void setHealthValid(Date healthValid) {
		this.healthValid = healthValid;
	}

	public Integer getEducation() {
		return this.education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGraduateInstitution() {
		return this.graduateInstitution;
	}

	public void setGraduateInstitution(String graduateInstitution) {
		this.graduateInstitution = graduateInstitution;
	}

	public Date getGraduateDate() {
		return this.graduateDate;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	public Integer getLanguageSkill() {
		return this.languageSkill;
	}

	public void setLanguageSkill(Integer languageSkill) {
		this.languageSkill = languageSkill;
	}

	public String getUrgencyLinkman() {
		return this.urgencyLinkman;
	}

	public void setUrgencyLinkman(String urgencyLinkman) {
		this.urgencyLinkman = urgencyLinkman;
	}

	public String getFileLocation() {
		return this.fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getHouseholdLocation() {
		return this.householdLocation;
	}

	public void setHouseholdLocation(String householdLocation) {
		this.householdLocation = householdLocation;
	}

	public String getDwell() {
		return this.dwell;
	}

	public void setDwell(String dwell) {
		this.dwell = dwell;
	}

	public String getAttLotId() {
		return this.attLotId;
	}

	public void setAttLotId(String attLotId) {
		this.attLotId = attLotId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getEntrantDate() {
		return entrantDate;
	}

	public void setEntrantDate(Date entrantDate) {
		this.entrantDate = entrantDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getEntrantStartDate() {
		return entrantStartDate;
	}

	public void setEntrantStartDate(Date entrantStartDate) {
		this.entrantStartDate = entrantStartDate;
	}

	public Date getEntrantEndDate() {
		return entrantEndDate;
	}

	public void setEntrantEndDate(Date entrantEndDate) {
		this.entrantEndDate = entrantEndDate;
	}

	public Long getWorkPlaceId() {
		return workPlaceId;
	}

	public void setWorkPlaceId(Long workPlaceId) {
		this.workPlaceId = workPlaceId;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}

}
