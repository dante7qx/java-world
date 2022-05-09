drop table if exists BP_SEQ;
drop table if exists BP_SYS_PARAM;
drop table if exists BP_AUDIT_LOG;
drop table if exists BP_ORGAN_TYPE;
drop table if exists BP_ORGAN;
drop table if exists BP_USER;
drop table if exists BP_EMPLOYEE;
drop table if exists BP_EMPLOYEE_ORGAN;
drop table if exists BP_ROLE;
drop table if exists BP_USER_ROLE;
drop table if exists BP_ROLE_FUNC;
drop table if exists BP_FUNCTION;
drop table if exists BP_MENU;
drop table if exists BP_ONLINE_USER;
drop table if exists BP_FORUM_GROUP;
drop table if exists BP_FORUM_TOPIC;
drop table if exists BP_TASK_DEF;
drop table if exists BP_TASK_RUN;
drop table if exists BP_ANNOUNCEINFO;
drop table if exists BP_RPT_RECORD;
drop table if exists BP_LOCATION;
drop table if exists BP_HOLIDAY;

create table BP_SEQ
(
 `SEQ_KEY` varchar(64)  comment '号码编码', 
 `NAME` varchar(128) not null comment '号码名称', 
 `VALUE` decimal(20) not null comment '当前值', 
 `FORMAT_STR` varchar(32)  comment '格式化串', 
 `FETCH_SIZE` int not null comment '缓存大小', 
  primary key (SEQ_KEY) 
);

alter table BP_SEQ comment '号码表';

create table BP_SYS_PARAM
(
 `PARAM_KEY` varchar(128)  comment '参数ID', 
 `PARAM_NAME` varchar(128)  comment '参数名称', 
 `PARAM_VALUE` varchar(512)  comment '参数值', 
 `PARAM_TYPE` int  comment '参数类型', 
 `DEFAULT_VALUE` varchar(512)  comment '默认值', 
 `SYNC_FLAG` int  comment '是否同步', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (PARAM_KEY) 
);

alter table BP_SYS_PARAM comment '全局参数';

create table BP_AUDIT_LOG
(
 `LOG_ID` decimal(20)  comment '日志ID', 
 `USER_ID` varchar(32)  comment '用户ID', 
 `REMOTE_ADDR` varchar(64)  comment '远程地址', 
 `ACTION_NAME` varchar(128)  comment '动作名称', 
 `MODULE_NAME` varchar(64)  comment '模块名称', 
 `FUNC_CODE` varchar(64)  comment '功能编号', 
 `OPERATION_URL` varchar(512)  comment 'URL', 
 `OPERATION_TIME` datetime  comment '操作时间', 
  primary key (LOG_ID) 
);

alter table BP_AUDIT_LOG comment '审计日志';

create table BP_ORGAN_TYPE
(
 `ORG_TYPE_ID` int  comment '组织机构类型ID', 
 `ORG_TYPE` varchar(128) not null comment '组织机构类型', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (ORG_TYPE_ID) 
);

alter table BP_ORGAN_TYPE comment '组织机构类型';

create table BP_ORGAN
(
 `ORG_ID` int  comment '组织机构ID', 
 `ORG_CODE` varchar(16)  comment '机构标识码', 
 `FULL_ID` varchar(128)  comment '机构全号', 
 `FULL_CODE` varchar(128)  comment '机构全码', 
 `ORG_NAME` varchar(128) not null comment '组织机构名称', 
 `SHORTNAME` varchar(64)  comment '简称', 
 `FULLNAME` varchar(256)  comment '全称', 
 `ENABLE` int  comment '是否有效', 
 `ORG_PINYIN` varchar(10)  comment '拼音码', 
 `ORG_TYPE_ID` int  comment '组织机构类型ID', 
 `LOC_ID` int  comment '地理区域ID', 
 `PARENT_ID` int  comment '上级ID', 
 `SHOW_ORDER` int  comment '显示顺序', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (ORG_ID) 
);

alter table BP_ORGAN comment '组织机构';

create table BP_USER
(
 `USER_ID` varchar(32)  comment '登录ID', 
 `USER_NAME` varchar(128) not null comment '姓名', 
 `PASSWD` varchar(32) not null comment '密码', 
 `PASSWD_TIME` datetime  comment '密码设定时间', 
 `STATE` int not null comment '状态', 
 `EMP_ID` int  comment '员工ID', 
 `IS_SYS` int not null comment '系统用户标志', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (USER_ID) 
);

alter table BP_USER comment '用户';

create table BP_EMPLOYEE
(
 `EMP_ID` int  comment '员工ID', 
 `EMP_NAME` varchar(45) not null comment '姓名', 
 `EMP_PINYIN` varchar(10) not null comment '拼音码', 
 `ALIAS_NAME` varchar(45)  comment '别名', 
 `NICK_NAME` varchar(45)  comment '昵称', 
 `PHONE` varchar(32)  comment '电话', 
 `MOBILE` varchar(32)  comment '手机号码', 
 `ADDRESS` varchar(256)  comment '地址', 
 `EMAIL` varchar(128)  comment '邮箱', 
 `STATE` int not null comment '状态', 
 `DEFAULT_ORG_ID` int not null comment '部门编号', 
 `CREATE_USER` varchar(45)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(45)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (EMP_ID) 
);

alter table BP_EMPLOYEE comment '员工';

create table BP_EMPLOYEE_ORGAN
(
 `EMP_ID` int  comment '员工ID', 
 `ORG_ID` int  comment '机构ID' 
);

alter table BP_EMPLOYEE_ORGAN comment '员工组织机构关系';

create table BP_ROLE
(
 `ROLE_ID` int  comment '角色编号', 
 `ROLE_NAME` varchar(64) not null comment '角色名称', 
 `ROLE_DESC` varchar(256) not null comment '角色描述', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (ROLE_ID) 
);

alter table BP_ROLE comment '角色';

create table BP_USER_ROLE
(
 `USER_ID` varchar(32)  comment '用户ID', 
 `ROLE_ID` int  comment '角色Id' 
);

alter table BP_USER_ROLE comment '用户角色关系';

create table BP_ROLE_FUNC
(
 `ROLE_ID` int  comment '角色编号', 
 `FUNC_CODE` varchar(64)  comment '功能编码' 
);

alter table BP_ROLE_FUNC comment '角色功能关系';

create table BP_FUNCTION
(
 `FUNC_CODE` varchar(64)  comment '功能ID', 
 `FUNC_NAME` varchar(128) not null comment '功能名称', 
 `STATE` int  comment '有效标志', 
 `FUNC_ORDER` int not null comment '功能顺序', 
 `PARENT` varchar(64)  comment '上级功能', 
 `FUNC_NOTE` varchar(256)  comment '功能备注', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (FUNC_CODE) 
);

alter table BP_FUNCTION comment '系统功能';

create table BP_MENU
(
 `MENU_ID` int  comment '菜单编号', 
 `TITLE` varchar(32) not null comment '菜单名称', 
 `PATH` varchar(128)  comment '菜单URL', 
 `FUNC_CODE` varchar(64)  comment '功能编码', 
 `MENU_ORDER` int not null comment '菜单顺序', 
 `PARENT_ID` int  comment '父菜单', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
 `MENU_KIND` int  comment '菜单类型', 
 `MENU_INFO` varchar(256)  comment '菜单信息', 
 `WIDTH` int  comment '宽度', 
 `HEIGHT` int  comment '高度', 
 `BACK_COLOR` varchar(32)  comment '背景色', 
 `ICON_PATH` varchar(128)  comment '图标', 
 `SHOW_IN` varchar(128)  comment '需要菜单显示的地方', 
 `TARGET` varchar(10)  comment '链接打开方式组合', 
  primary key (MENU_ID) 
);

alter table BP_MENU comment '菜单';

create table BP_ONLINE_USER
(
 `SESSIONID` varchar(128)  comment '会话ID', 
 `REMOTE_ADDR` varchar(64) not null comment '远程地址', 
 `BIRTH_TIME` datetime not null comment '产生时间', 
 `LOGIN_STATE` int not null comment '在线状态', 
 `USER_ID` varchar(32)  comment '用户ID', 
 `USER_AGENT` varchar(128)  comment '浏览器', 
  primary key (SESSIONID) 
);

alter table BP_ONLINE_USER comment '在线用户';

create table BP_FORUM_GROUP
(
 `GROUP_ID` int  comment '版块ID', 
 `GROUP_NAME` varchar(255)  comment '版块名称', 
 `GROUP_DESC` varchar(255)  comment '板块描述', 
 `CREATER_ID` varchar(32)  comment '创建者ID', 
 `CREATER_NAME` varchar(255)  comment '创建者姓名', 
 `DISABLE_FLAG` varchar(255)  comment '禁用标志', 
 `UPDATE_TIME` datetime  comment '更新时间', 
 `CREATE_TIME` datetime  comment '创建时间', 
  primary key (GROUP_ID) 
);

alter table BP_FORUM_GROUP comment '论坛版块';

create table BP_FORUM_TOPIC
(
 `GROUP_ID` int  comment '版块ID', 
 `TOPIC_ID` int  comment '主题ID', 
 `PARENT_ID` int  comment '父主题ID', 
 `ROOT_ID` int  comment '根主题ID', 
 `TOPIC_NAME` varchar(255)  comment '主题名称', 
 `TOPIC_CONTEXT` varchar(255)  comment '主题内容', 
 `CREATER_ID` varchar(32)  comment '创建者ID', 
 `CREATER_NAME` varchar(255)  comment '创建者姓名', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `ISLEAF` int  comment '叶标志', 
 `REPLYCOUNT` int  comment '回复数', 
  primary key (TOPIC_ID) 
);

alter table BP_FORUM_TOPIC comment '论坛主题';

create table BP_TASK_DEF
(
 `TASK_ID` int  comment '任务ID', 
 `TASK_REF_ID` varchar(128) not null comment '任务标识', 
 `TASK_ENABLE` int not null comment '启停状态', 
 `TASK_NAME` varchar(128) not null comment '任务名称', 
 `START_TIME` datetime  comment '启用时间', 
 `END_TIME` datetime  comment '终止时间', 
 `SHED_TYPE` int  comment '定时方式', 
 `EXEC_ID` varchar(128)  comment '执行标识', 
 `SHED_EXPR` varchar(128)  comment '定时设置', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (TASK_ID) 
);

alter table BP_TASK_DEF comment '定时任务';

create table BP_TASK_RUN
(
 `TASK_ID` int  comment '任务ID', 
 `EXEC_ID` varchar(128)  comment '执行标识', 
 `LAST_TIME` datetime  comment '上次执行时间', 
 `LAST_INFO` varchar(512)  comment '上次执行情况', 
 `NEXT_TIME` datetime  comment '下次执行时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (TASK_ID, EXEC_ID) 
);

alter table BP_TASK_RUN comment '定时执行';

create table BP_ANNOUNCEINFO
(
 `Id` int  comment '编号', 
 `Content` varchar(512)  comment '通告内容', 
 `EffDate` datetime not null comment '生效日期', 
 `ExpDate` datetime  comment '失效日期', 
 `UpdateBy` varchar(32)  comment '更新人', 
 `UpdateDate` datetime  comment '更新时间', 
 `State` int not null comment '通知状态', 
 `FuncIds` varchar(256)  comment '功能ID', 
 `Url` varchar(512)  comment '页面URL', 
 `OrgCodes` varchar(128)  comment '机构标识码', 
  primary key (Id) 
);

alter table BP_ANNOUNCEINFO comment '通告信息';

create table BP_RPT_RECORD
(
 `RPT_SEQ_ID` int  comment '流水号', 
 `RPT_ID` varchar(128) not null comment '报表ID', 
 `RPT_KEY` varchar(1024)  comment '报表参数', 
 `RPT_TITLE` varchar(128) not null comment '报表名称', 
 `RPT_OBJECT` blob  comment '报表对象', 
 `CREATE_ORG_ID` varchar(32)  comment '创建机构', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime not null comment '创建时间', 
  primary key (RPT_SEQ_ID) 
);

alter table BP_RPT_RECORD comment '报表记录';

create table BP_LOCATION
(
 `LOC_ID` int  comment '地理区域ID', 
 `LOC_NAME` varchar(256)  comment '地理区域名称', 
 `LOC_NOTE` varchar(512)  comment '地理区域备注', 
 `LOC_X` int  comment 'X坐标', 
 `LOC_Y` int  comment 'Y坐标', 
 `LOC_CODE` varchar(64)  comment '标识码', 
 `ENABLE` int  comment '状态', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (LOC_ID) 
);

alter table BP_LOCATION comment '地理区域';

create table BP_HOLIDAY
(
 `H_ID` int  comment '假日ID', 
 `H_DATE` datetime  comment '假日日期', 
 `H_CONTENT` varchar(256)  comment '假日内容', 
 `DATE_TYPE` int  comment '日期类型', 
 `CREATE_USER` varchar(32)  comment '创建人', 
 `CREATE_TIME` datetime  comment '创建时间', 
 `UPDATE_USER` varchar(32)  comment '更新人', 
 `UPDATE_TIME` datetime  comment '更新时间', 
  primary key (H_ID) 
);

alter table BP_HOLIDAY comment '节假日管理';


alter table BP_ORGAN add constraint FK_BP_ORGAN_ORG_TYPE_ID
    foreign key (ORG_TYPE_ID) references BP_ORGAN_TYPE (ORG_TYPE_ID);

alter table BP_ORGAN add constraint FK_BP_ORGAN_PARENT_ID
    foreign key (PARENT_ID) references BP_ORGAN (ORG_ID);

alter table BP_USER add constraint FK_BP_USER_EMP_ID
    foreign key (EMP_ID) references BP_EMPLOYEE (EMP_ID);

alter table BP_EMPLOYEE_ORGAN add constraint FK_BP_EMPLOYEE_ORGAN_EMP_ID
    foreign key (EMP_ID) references BP_EMPLOYEE (EMP_ID);

alter table BP_EMPLOYEE_ORGAN add constraint FK_BP_EMPLOYEE_ORGAN_ORG_ID
    foreign key (ORG_ID) references BP_ORGAN (ORG_ID);

alter table BP_USER_ROLE add constraint FK_BP_USER_ROLE_USER
    foreign key (ROLE_ID) references BP_ROLE (ROLE_ID);

alter table BP_USER_ROLE add constraint FK_BP_USER_ROLE_ROLE
    foreign key (USER_ID) references BP_USER (USER_ID);

alter table BP_ROLE_FUNC add constraint FK_BP_ROLE_FUNC_FUNC_CODE
    foreign key (FUNC_CODE) references BP_FUNCTION (FUNC_CODE);

alter table BP_ROLE_FUNC add constraint FK_BP_ROLE_FUNC_FUNC_ROLE
    foreign key (ROLE_ID) references BP_ROLE (ROLE_ID);


