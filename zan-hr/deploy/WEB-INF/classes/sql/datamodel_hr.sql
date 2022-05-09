create table `P_Att`
(
 `Id` int  comment '编号' 
 ,`LotId` varchar(64)  comment '附件批号' 
 ,`Type` varchar(128)  comment '类型' 
 ,`Description` varchar(512)  comment '附件描述' 
 ,`Path` varchar(512)  comment '附件路径' 
 ,`UpdateBy` varchar(32)  comment '更新人' 
 ,`UpdateDate` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table P_Att comment '附件表';

create table `hr_code_dict`
(
 `Id` int  comment 'Id' 
 ,`Type` varchar(16)  comment '类型' 
 ,`TypeName` varchar(8)  comment '类型名称' 
 ,`Code` varchar(16)  comment '编码' 
 ,`Value` varchar(16)  comment '名称' 
 ,`ShowOrder` int  comment '显示顺序' 
 ,`UpdateBy` varchar(32)  comment '更新人' 
 ,`UpdateDate` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table hr_code_dict comment '基础数据字典表';


create table `hr_employee`
(
 `Id` int  comment '员工Id' 
 ,`Code` varchar(64)  comment '员工编号' 
 ,`ChnName` varchar(64)  comment '员工名称' 
 ,`EngName` varchar(64)  comment '员工英文名称' 
 ,`UsedName` varchar(64)  comment '员工曾用名' 
 ,`IdNum` varchar(18)  comment '身份证号' 
 ,`Birthday` datetime  comment '出生日期' 
 ,`Gen` int  comment '性别' 
 ,`Recruitment` varchar(32)  comment '籍贯' 
 ,`Horoscope` int  comment '星座' 
 ,`Household` int  comment '户口性质' 
 ,`Politics` int  comment '政治面貌' 
 ,`Marriage` int  comment '婚姻状况' 
 ,`Phone` varchar(32)  comment '联系电话' 
 ,`Email` varchar(64)  comment '电子邮箱' 
 ,`HealthValid` datetime  comment '健康证明有效期' 
 ,`Education` int  comment '学历' 
 ,`Major` varchar(64)  comment '专业' 
 ,`GraduateInstitution` varchar(128)  comment '毕业院校' 
 ,`GraduateDate` datetime  comment '毕业时间' 
 ,`LanguageSkill` int  comment '语种' 
 ,`UrgencyLinkman` varchar(512)  comment '紧急联系人/电话' 
 ,`FileLocation` varchar(1024)  comment '档案存放地' 
 ,`HouseholdLocation` varchar(1024)  comment '户口所在地' 
 ,`Dwell` varchar(1024)  comment '居住地址' 
 ,`AttLotId` varchar(64)  comment '附件号' 
 ,`IsActive` int  comment '是否生效' 
 ,`IsDelete` int  comment '是否删除' 
 ,`CreateUser` varchar(32)  comment '创建人' 
 ,`CreateTime` datetime  comment '创建时间' 
 ,`UpdateUser` varchar(32)  comment '更新人' 
 ,`UpdateTime` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table hr_employee comment '员工';


create table `hr_employee_certificate`
(
 `Id` int  comment '编号' 
 ,`EmployeeId` int  comment '员工Id' 
 ,`Name` varchar(128)  comment '证书名称' 
 ,`SignDate` datetime  comment '签发日期' 
 ,`ExpDate` datetime  comment '失效日期' 
 ,`AttLotId` varchar(64)  comment '附件号' 
 ,`IsDelete` int  comment '是否删除' 
 ,`CreateUser` varchar(32)  comment '创建人' 
 ,`CreateTime` datetime  comment '创建时间' 
 ,`UpdateUser` varchar(32)  comment '更新人' 
 ,`UpdateTime` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table hr_employee_certificate comment '员工资格、职称证书';


create table `hr_employee_work`
(
 `Id` int  comment '编号' 
 ,`EmployeeId` int  comment '员工Id'
 ,`WorkPlaceId` int comment '工作地点Id'
 ,`Company` int  comment '入职公司' 
 ,`Dept` int  comment '入职部门' 
 ,`Post` int  comment '入职岗位' 
 ,`CapacityLevel` int  comment '能力级别' 
 ,`EntrantDate` datetime  comment '入职日期' 
 ,`Type` int  comment '员工类型' 
 ,`TrialPeriod` int  comment '试用期' 
 ,`ContractDeadline` int  comment '合同期限' 
 ,`ContractType` int  comment '合同类型' 
 ,`ContractStartDate` datetime  comment '合同期限开始时间' 
 ,`ContractEndDate` datetime  comment '合同期限结束时间' 
 ,`TrialDeadline` datetime  comment '试用期结束日期' 
 ,`PracticeStartDate` datetime  comment '实习期限开始时间' 
 ,`PracticeEndDate` datetime  comment '实习期限结束时间' 
 ,`ParttimeStartDate` datetime  comment '实习期限开始时间' 
 ,`ParttimeEndDate` datetime  comment '实习期限结束时间' 
 ,`CreateUser` varchar(32)  comment '创建人' 
 ,`CreateTime` datetime  comment '创建时间' 
 ,`UpdateUser` varchar(32)  comment '更新人' 
 ,`UpdateTime` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table hr_employee_work comment '员工工作信息';


create table `hr_employee_relation`
(
 `Id` int  comment '编号' 
 ,`EmployeeId` int  comment '员工Id' 
 ,`Name` varchar(64)  comment '名称' 
 ,`Relation` int  comment '与本人关系' 
 ,`Phone` varchar(32)  comment '联系电话' 
 ,`IsDelete` int  comment '是否删除' 
 ,`CreateUser` varchar(32)  comment '创建人' 
 ,`CreateTime` datetime  comment '创建时间' 
 ,`UpdateUser` varchar(32)  comment '更新人' 
 ,`UpdateTime` datetime  comment '更新时间' 
,primary key (Id) 
);

alter table hr_employee_relation comment '员工家属信息';


create table `hr_employee_other`
(
`Id` int  comment '编号'
 ,`EmployeeId` int  comment '员工Id'
 ,`AttLotId` varchar(64)  comment '附件号'
 ,`Remark` varchar(1024)  comment '备注'
 ,`createUser` varchar(32)  comment '创建人'
 ,`createTime` datetime  comment '创建时间'
 ,`updateUser` varchar(32)  comment '更新人'
 ,`updateTime` datetime  comment '更新时间'
,primary key (Id)
);
 
alter table hr_employee_other comment '员工补充信息';
 
 
create table `hr_employee_benefit`
(
`Id` int  comment '编号'
 ,`EmployeeId` int  comment '员工Id'
 ,`PreTaxSalary` double(20,2)  comment '税前工资'
 ,`TrialPeriodSalary` double(20,2)  comment '试用期工资'
 ,`BankAccount` varchar(64)  comment '工资卡开户行'
 ,`SalaryCardNo` varchar(64)  comment '工资卡号'
 ,`ProvidentFundDepositBase` double(20,2)  comment '住房公积金缴存基数'
 ,`InsureDepositBase` double(20,2)  comment '保险缴存基数'
 ,`PayDate` datetime  comment '起缴日期'
 ,`createUser` varchar(32)  comment '创建人'
 ,`createTime` datetime  comment '创建时间'
 ,`updateUser` varchar(32)  comment '更新人'
 ,`updateTime` datetime  comment '更新时间'
,primary key (Id)
);
 
alter table hr_employee_benefit comment '员工薪资福利';
