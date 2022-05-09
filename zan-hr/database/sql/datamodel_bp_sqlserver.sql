if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_ORGAN') and o.name = 'FK_BP_ORGAN_ORG_TYPE_ID')
alter table BP_ORGAN
   drop constraint FK_BP_ORGAN_ORG_TYPE_ID
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_ORGAN') and o.name = 'FK_BP_ORGAN_PARENT_ID')
alter table BP_ORGAN
   drop constraint FK_BP_ORGAN_PARENT_ID
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_USER') and o.name = 'FK_BP_USER_EMP_ID')
alter table BP_USER
   drop constraint FK_BP_USER_EMP_ID
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_EMPLOYEE_ORGAN') and o.name = 'FK_BP_EMPLOYEE_ORGAN_EMP_ID')
alter table BP_EMPLOYEE_ORGAN
   drop constraint FK_BP_EMPLOYEE_ORGAN_EMP_ID
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_EMPLOYEE_ORGAN') and o.name = 'FK_BP_EMPLOYEE_ORGAN_ORG_ID')
alter table BP_EMPLOYEE_ORGAN
   drop constraint FK_BP_EMPLOYEE_ORGAN_ORG_ID
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_USER_ROLE') and o.name = 'FK_BP_USER_ROLE_USER')
alter table BP_USER_ROLE
   drop constraint FK_BP_USER_ROLE_USER
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_USER_ROLE') and o.name = 'FK_BP_USER_ROLE_ROLE')
alter table BP_USER_ROLE
   drop constraint FK_BP_USER_ROLE_ROLE
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_ROLE_FUNC') and o.name = 'FK_BP_ROLE_FUNC_FUNC_CODE')
alter table BP_ROLE_FUNC
   drop constraint FK_BP_ROLE_FUNC_FUNC_CODE
go

if exists (select 1
   from dbo.sysreferences r join dbo.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('BP_ROLE_FUNC') and o.name = 'FK_BP_ROLE_FUNC_FUNC_ROLE')
alter table BP_ROLE_FUNC
   drop constraint FK_BP_ROLE_FUNC_FUNC_ROLE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_SEQ')
            and   type = 'U')
   drop table BP_SEQ
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_SYS_PARAM')
            and   type = 'U')
   drop table BP_SYS_PARAM
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_AUDIT_LOG')
            and   type = 'U')
   drop table BP_AUDIT_LOG
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ORGAN_TYPE')
            and   type = 'U')
   drop table BP_ORGAN_TYPE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ORGAN')
            and   type = 'U')
   drop table BP_ORGAN
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_USER')
            and   type = 'U')
   drop table BP_USER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_EMPLOYEE')
            and   type = 'U')
   drop table BP_EMPLOYEE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_EMPLOYEE_ORGAN')
            and   type = 'U')
   drop table BP_EMPLOYEE_ORGAN
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ROLE')
            and   type = 'U')
   drop table BP_ROLE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_USER_ROLE')
            and   type = 'U')
   drop table BP_USER_ROLE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ROLE_FUNC')
            and   type = 'U')
   drop table BP_ROLE_FUNC
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_FUNCTION')
            and   type = 'U')
   drop table BP_FUNCTION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_MENU')
            and   type = 'U')
   drop table BP_MENU
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ONLINE_USER')
            and   type = 'U')
   drop table BP_ONLINE_USER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_FORUM_GROUP')
            and   type = 'U')
   drop table BP_FORUM_GROUP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_FORUM_TOPIC')
            and   type = 'U')
   drop table BP_FORUM_TOPIC
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_TASK_DEF')
            and   type = 'U')
   drop table BP_TASK_DEF
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_ANNOUNCEINFO')
            and   type = 'U')
   drop table BP_ANNOUNCEINFO
go

if exists (select 1
            from  sysobjects
           where  id = object_id('BP_RPT_RECORD')
            and   type = 'U')
   drop table BP_RPT_RECORD
go


create table BP_SEQ (
 SEQ_KEY varchar(64) 
 ,NAME varchar(128) not null
 ,VALUE numeric(20,0) not null
 ,FORMAT_STR varchar(32) 
 ,FETCH_SIZE int not null
 ,constraint PK_BP_SEQ primary key nonclustered (SEQ_KEY) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '号码表',
   'user', @CurrentUser, 'table', 'BP_SEQ'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '号码编码',
   'user', @CurrentUser, 'table', 'BP_SEQ', 'column', 'SEQ_KEY'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '号码名称',
   'user', @CurrentUser, 'table', 'BP_SEQ', 'column', 'NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前值',
   'user', @CurrentUser, 'table', 'BP_SEQ', 'column', 'VALUE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '格式化串',
   'user', @CurrentUser, 'table', 'BP_SEQ', 'column', 'FORMAT_STR'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '缓存大小',
   'user', @CurrentUser, 'table', 'BP_SEQ', 'column', 'FETCH_SIZE'
go


create table BP_SYS_PARAM (
 PARAM_KEY varchar(128) 
 ,PARAM_NAME varchar(128) 
 ,PARAM_VALUE varchar(256) 
 ,PARAM_TYPE int 
 ,DEFAULT_VALUE varchar(256) 
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_SYS_PARAM primary key nonclustered (PARAM_KEY) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '全局参数',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数ID',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'PARAM_KEY'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数名称',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'PARAM_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数值',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'PARAM_VALUE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'PARAM_TYPE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '默认值',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'DEFAULT_VALUE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_SYS_PARAM', 'column', 'UPDATE_TIME'
go


create table BP_AUDIT_LOG (
 LOG_ID numeric(20,0) 
 ,USER_ID varchar(32) 
 ,REMOTE_ADDR varchar(64) 
 ,ACTION_NAME varchar(128) 
 ,MODULE_NAME varchar(64) 
 ,FUNC_CODE varchar(64) 
 ,OPERATION_URL varchar(512) 
 ,OPERATION_TIME datetime 
 ,constraint PK_BP_AUDIT_LOG primary key nonclustered (LOG_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '审计日志',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志ID',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'LOG_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'USER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '远程地址',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'REMOTE_ADDR'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '动作名称',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'ACTION_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '模块名称',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'MODULE_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编号',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'FUNC_CODE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'URL',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'OPERATION_URL'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作时间',
   'user', @CurrentUser, 'table', 'BP_AUDIT_LOG', 'column', 'OPERATION_TIME'
go


create table BP_ORGAN_TYPE (
 ORG_TYPE_ID int 
 ,ORG_TYPE varchar(128) not null
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_ORGAN_TYPE primary key nonclustered (ORG_TYPE_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构类型',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构类型ID',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'ORG_TYPE_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构类型',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'ORG_TYPE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_ORGAN_TYPE', 'column', 'UPDATE_TIME'
go


create table BP_ORGAN (
 ORG_ID int 
 ,ORG_CODE varchar(16) 
 ,FULL_ID varchar(128) 
 ,FULL_CODE varchar(128) 
 ,ORG_NAME varchar(128) not null
 ,SHORTNAME varchar(64) 
 ,FULLNAME varchar(256) 
 ,ENABLE int 
 ,ORG_PINYIN varchar(10) 
 ,ORG_TYPE_ID int 
 ,PARENT_ID int 
 ,SHOW_ORDER int 
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_ORGAN primary key nonclustered (ORG_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构',
   'user', @CurrentUser, 'table', 'BP_ORGAN'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构ID',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ORG_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构标识码',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ORG_CODE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构全号',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'FULL_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构全码',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'FULL_CODE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构名称',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ORG_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '简称',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'SHORTNAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '全称',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'FULLNAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '是否有效',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ENABLE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '拼音码',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ORG_PINYIN'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构类型ID',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'ORG_TYPE_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上级ID',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'PARENT_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '显示顺序',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'SHOW_ORDER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_ORGAN', 'column', 'UPDATE_TIME'
go


create table BP_USER (
 USER_ID varchar(32) 
 ,USER_NAME varchar(128) not null
 ,PASSWD varchar(32) not null
 ,PASSWD_TIME datetime 
 ,STATE int not null
 ,EMP_ID int 
 ,IS_SYS int not null
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_USER primary key nonclustered (USER_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户',
   'user', @CurrentUser, 'table', 'BP_USER'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登录ID',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'USER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '姓名',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'USER_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '密码',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'PASSWD'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '密码设定时间',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'PASSWD_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '状态',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'STATE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '员工ID',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'EMP_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统用户标志',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'IS_SYS'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_USER', 'column', 'UPDATE_TIME'
go


create table BP_EMPLOYEE (
 EMP_ID int 
 ,EMP_NAME varchar(45) not null
 ,EMP_PINYIN varchar(10) not null
 ,ALIAS_NAME varchar(45) 
 ,NICK_NAME varchar(45) 
 ,PHONE varchar(32) 
 ,MOBILE varchar(32) 
 ,ADDRESS varchar(256) 
 ,EMAIL varchar(128) 
 ,STATE int not null
 ,DEFAULT_ORG_ID int not null
 ,CREATE_USER varchar(45) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(45) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_EMPLOYEE primary key nonclustered (EMP_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '员工',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '员工ID',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'EMP_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '姓名',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'EMP_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '拼音码',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'EMP_PINYIN'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '别名',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'ALIAS_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '昵称',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'NICK_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电话',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'PHONE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '手机号码',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'MOBILE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '地址',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'ADDRESS'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮箱',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'EMAIL'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '状态',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'STATE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '部门编号',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'DEFAULT_ORG_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE', 'column', 'UPDATE_TIME'
go


create table BP_EMPLOYEE_ORGAN (
 EMP_ID int 
 ,ORG_ID int 
 )
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '员工组织机构关系',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE_ORGAN'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '员工ID',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE_ORGAN', 'column', 'EMP_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '机构ID',
   'user', @CurrentUser, 'table', 'BP_EMPLOYEE_ORGAN', 'column', 'ORG_ID'
go


create table BP_ROLE (
 ROLE_ID int 
 ,ROLE_NAME varchar(64) not null
 ,ROLE_DESC varchar(256) not null
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_ROLE primary key nonclustered (ROLE_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色',
   'user', @CurrentUser, 'table', 'BP_ROLE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'ROLE_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色名称',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'ROLE_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色描述',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'ROLE_DESC'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_ROLE', 'column', 'UPDATE_TIME'
go


create table BP_USER_ROLE (
 USER_ID varchar(32) 
 ,ROLE_ID int 
 )
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户角色关系',
   'user', @CurrentUser, 'table', 'BP_USER_ROLE'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @CurrentUser, 'table', 'BP_USER_ROLE', 'column', 'USER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色Id',
   'user', @CurrentUser, 'table', 'BP_USER_ROLE', 'column', 'ROLE_ID'
go


create table BP_ROLE_FUNC (
 ROLE_ID int 
 ,FUNC_CODE varchar(64) 
 )
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色功能关系',
   'user', @CurrentUser, 'table', 'BP_ROLE_FUNC'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'BP_ROLE_FUNC', 'column', 'ROLE_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编码',
   'user', @CurrentUser, 'table', 'BP_ROLE_FUNC', 'column', 'FUNC_CODE'
go


create table BP_FUNCTION (
 FUNC_CODE varchar(64) 
 ,FUNC_NAME varchar(128) not null
 ,STATE int 
 ,FUNC_ORDER int not null
 ,PARENT varchar(64) 
 ,FUNC_NOTE varchar(256) 
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_FUNCTION primary key nonclustered (FUNC_CODE) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统功能',
   'user', @CurrentUser, 'table', 'BP_FUNCTION'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能ID',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'FUNC_CODE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能名称',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'FUNC_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '有效标志',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'STATE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能顺序',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'FUNC_ORDER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上级功能',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'PARENT'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能备注',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'FUNC_NOTE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_FUNCTION', 'column', 'UPDATE_TIME'
go


create table BP_MENU (
 MENU_ID int 
 ,TITLE varchar(32) not null
 ,PATH varchar(128) 
 ,FUNC_CODE varchar(64) 
 ,MENU_ORDER int not null
 ,PARENT_ID int 
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_MENU primary key nonclustered (MENU_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单',
   'user', @CurrentUser, 'table', 'BP_MENU'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单编号',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'MENU_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单名称',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'TITLE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单URL',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'PATH'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编码',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'FUNC_CODE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单顺序',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'MENU_ORDER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父菜单',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'PARENT_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_MENU', 'column', 'UPDATE_TIME'
go


create table BP_ONLINE_USER (
 SESSIONID varchar(128) 
 ,REMOTE_ADDR varchar(64) not null
 ,BIRTH_TIME datetime not null
 ,USER_ID varchar(32) 
 ,USER_AGENT varchar(128) 
 ,constraint PK_BP_ONLINE_USER primary key nonclustered (SESSIONID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '在线用户',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '会话ID',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER', 'column', 'SESSIONID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '远程地址',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER', 'column', 'REMOTE_ADDR'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '产生时间',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER', 'column', 'BIRTH_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户ID',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER', 'column', 'USER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '浏览器',
   'user', @CurrentUser, 'table', 'BP_ONLINE_USER', 'column', 'USER_AGENT'
go


create table BP_FORUM_GROUP (
 GROUP_ID int 
 ,GROUP_NAME varchar(255) 
 ,GROUP_DESC varchar(255) 
 ,CREATER_ID varchar(32) 
 ,CREATER_NAME varchar(255) 
 ,DISABLE_FLAG varchar(255) 
 ,UPDATE_TIME datetime 
 ,CREATE_TIME datetime 
 ,constraint PK_BP_FORUM_GROUP primary key nonclustered (GROUP_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '论坛版块',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '版块ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'GROUP_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '版块名称',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'GROUP_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '板块描述',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'GROUP_DESC'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建者ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'CREATER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建者姓名',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'CREATER_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '禁用标志',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'DISABLE_FLAG'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'UPDATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_FORUM_GROUP', 'column', 'CREATE_TIME'
go


create table BP_FORUM_TOPIC (
 GROUP_ID int 
 ,TOPIC_ID int 
 ,PARENT_ID int 
 ,ROOT_ID int 
 ,TOPIC_NAME varchar(255) 
 ,TOPIC_CONTEXT varchar(255) 
 ,CREATER_ID varchar(32) 
 ,CREATER_NAME varchar(255) 
 ,CREATE_TIME datetime 
 ,ISLEAF int 
 ,REPLYCOUNT int 
 ,constraint PK_BP_FORUM_TOPIC primary key nonclustered (TOPIC_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '论坛主题',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '版块ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'GROUP_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主题ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'TOPIC_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '父主题ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'PARENT_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '根主题ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'ROOT_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主题名称',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'TOPIC_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主题内容',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'TOPIC_CONTEXT'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建者ID',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'CREATER_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建者姓名',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'CREATER_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'CREATE_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '叶标志',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'ISLEAF'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '回复数',
   'user', @CurrentUser, 'table', 'BP_FORUM_TOPIC', 'column', 'REPLYCOUNT'
go


create table BP_TASK_DEF (
 TASK_ID int 
 ,TASK_REF_ID varchar(128) not null
 ,TASK_ENABLE int not null
 ,TASK_NAME varchar(128) not null
 ,START_TIME datetime 
 ,END_TIME datetime 
 ,SHED_TYPE int 
 ,SHED_EXPR varchar(128) 
 ,LAST_TIME datetime 
 ,LAST_INFO varchar(512) 
 ,NEXT_TIME datetime 
 ,UPDATE_USER varchar(32) 
 ,UPDATE_TIME datetime 
 ,constraint PK_BP_TASK_DEF primary key nonclustered (TASK_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '定时任务',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任务ID',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'TASK_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任务标识',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'TASK_REF_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '启停状态',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'TASK_ENABLE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任务名称',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'TASK_NAME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '启用时间',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'START_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '终止时间',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'END_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '定时方式',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'SHED_TYPE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '定时设置',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'SHED_EXPR'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上次执行时间',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'LAST_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上次执行情况',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'LAST_INFO'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '下次执行时间',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'NEXT_TIME'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'UPDATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_TASK_DEF', 'column', 'UPDATE_TIME'
go


create table BP_ANNOUNCEINFO (
 Id int 
 ,Content varchar(512) 
 ,EffDate datetime not null
 ,ExpDate datetime 
 ,UpdateBy varchar(32) 
 ,UpdateDate datetime 
 ,constraint PK_BP_ANNOUNCEINFO primary key nonclustered (Id) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '通告信息',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '编号',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'Id'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '通告内容',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'Content'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '生效日期',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'EffDate'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '失效日期',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'ExpDate'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'UpdateBy'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'user', @CurrentUser, 'table', 'BP_ANNOUNCEINFO', 'column', 'UpdateDate'
go


create table BP_RPT_RECORD (
 RPT_SEQ_ID int 
 ,RPT_ID varchar(128) not null
 ,RPT_KEY varchar(1024) 
 ,RPT_TITLE varchar(128) not null
 ,RPT_OBJECT text 
 ,CREATE_ORG_ID varchar(32) 
 ,CREATE_USER varchar(32) 
 ,CREATE_TIME datetime not null
 ,constraint PK_BP_RPT_RECORD primary key nonclustered (RPT_SEQ_ID) 
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表记录',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'RPT_SEQ_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表ID',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'RPT_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表参数',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'RPT_KEY'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表名称',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'RPT_TITLE'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表对象',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'RPT_OBJECT'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建机构',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'CREATE_ORG_ID'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'CREATE_USER'
go
declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'BP_RPT_RECORD', 'column', 'CREATE_TIME'
go





alter table BP_ORGAN add constraint FK_BP_ORGAN_ORG_TYPE_ID foreign key 
(ORG_TYPE_ID ) references BP_ORGAN_TYPE 
(ORG_TYPE_ID )
go

alter table BP_ORGAN add constraint FK_BP_ORGAN_PARENT_ID foreign key 
(PARENT_ID ) references BP_ORGAN 
(ORG_ID )
go

alter table BP_USER add constraint FK_BP_USER_EMP_ID foreign key 
(EMP_ID ) references BP_EMPLOYEE 
(EMP_ID )
go

alter table BP_EMPLOYEE_ORGAN add constraint FK_BP_EMPLOYEE_ORGAN_EMP_ID foreign key 
(EMP_ID ) references BP_EMPLOYEE 
(EMP_ID )
go

alter table BP_EMPLOYEE_ORGAN add constraint FK_BP_EMPLOYEE_ORGAN_ORG_ID foreign key 
(ORG_ID ) references BP_ORGAN 
(ORG_ID )
go

alter table BP_USER_ROLE add constraint FK_BP_USER_ROLE_USER foreign key 
(ROLE_ID ) references BP_ROLE 
(ROLE_ID )
go

alter table BP_USER_ROLE add constraint FK_BP_USER_ROLE_ROLE foreign key 
(USER_ID ) references BP_USER 
(USER_ID )
go

alter table BP_ROLE_FUNC add constraint FK_BP_ROLE_FUNC_FUNC_CODE foreign key 
(FUNC_CODE ) references BP_FUNCTION 
(FUNC_CODE )
go

alter table BP_ROLE_FUNC add constraint FK_BP_ROLE_FUNC_FUNC_ROLE foreign key 
(ROLE_ID ) references BP_ROLE 
(ROLE_ID )
go

