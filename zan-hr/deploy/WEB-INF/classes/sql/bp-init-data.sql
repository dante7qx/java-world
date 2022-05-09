insert into BP_FUNCTION(FUNC_CODE,FUNC_NAME,FUNC_ORDER,PARENT) VALUES('public','公有',1,'-1');

insert into BP_MENU(PARENT_ID,MENU_ID,MENU_ORDER,TITLE,PATH,FUNC_CODE,MENU_KIND,MENU_INFO,WIDTH,HEIGHT,BACK_COLOR,SHOW_IN, TARGET) values
	(-1, 1, 1, '基础数据', 'jsp/bp/organ/', 'public', 0, 'ux/goa/leftall_ico_2.png', 0, 0, NULL,'default,hnaimp','innerTab'),
	(-1, 2, 6, '系统管理', 'jsp/bp/sys/', 'public', 0, 'ux/goa/leftall_ico_6.png', 0, 0, NULL,'default,hnaimp','innerTab'),
	(1, 3, 0, '*win8首页', 'jsp/bp/win8UI/win8Forward.jsp', 'public', 0, 'ux/win8UI/images/settings.png', 1, 1, '#c6df9c','default','innerTab'),
	(1, 4, 1, '组织机构类型', 'jsp/bp/organ/organizationType.jsp', 'bp.organType.query', 1, 'ux/win8UI/images/settings.png', 1, 1, '#00bff3','default,win8,hnaimp','innerTab'),
	(1, 5, 2, '组织机构', 'jsp/bp/organ/organization.jsp', 'bp.organ.query', 1, 'ux/win8UI/images/settings.png', 1, 1, '#7dc473','default,win8,hnaimp','innerTab'),
	(1, 6, 3, '员工管理', 'jsp/bp/employee/employee.jsp', 'bp.employee.query', 1, 'ux/win8UI/images/settings.png', 1, 1, '#8293ca','default,win8,hnaimp','innerTab'),
	(1, 7, 3, '资料文档分类', 'jsp/soc/kb/docType.jsp', 'soc.doc.queryDocTypeTree', 1, 'ux/win8UI/images/settings.png', 1, 1, '#f49bc1','default,win8,hnaimp','innerTab'),
	(1, 8, 4, '组织员工', 'jsp/bp/organ/orguser.jsp', 'bp.employee.orgquery', 1, 'ux/win8UI/images/settings.png', 1, 3, '#0ff','default,win8,hnaimp','innerTab'),
	(1, 9, 11, '地理区域', 'jsp/bp/location/location.jsp', 'bp.loc.query', 1, 'ux/win8UI/images/settings.png', 1, 1, '#f5999d','default,win8,hnaimp','innerTab'),
	(1, 10, 11, '论坛', 'jsp/bp/forum/forumGroup.jsp', 'public', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 11, 0, '数据库工具', 'jsp/dbhelper.jsp', 'bp.sys.dbhelper', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 12, 1, '用户管理', 'jsp/bp/user/user.jsp', 'bp.user.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 13, 2, '功能管理', 'jsp/bp/func/function.jsp', 'bp.function.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 14, 3, '角色管理', 'jsp/bp/role/roleManage.jsp', 'bp.role.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 15, 4, '菜单管理', 'jsp/bp/menu/menu.jsp', 'bp.menu.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 16, 5, '全局参数', 'jsp/bp/sys/globalParam.jsp', 'bp.sysparam.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 17, 6, '号码管理', 'jsp/bp/sys/idquery.jsp', 'bp.seq.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 18, 7, '定时任务', 'jsp/bp/sys/taskDef.jsp', 'bp.task.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 19, 8, '在线管理', 'jsp/bp/sys/onlinemgr.jsp', 'bp.online.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 20, 9, '审计日志', 'jsp/bp/sys/auditLog.jsp', 'bp.audit.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 21, 10, '报表记录管理', 'jsp/bp/sys/reportList.jsp', 'bp.report.query', 0, NULL, 0, 0, NULL,'default,hnaimp','innerTab'),
	(2, 22, 11, '百度1', 'http://www.baidu.com', 'public', 0, NULL, 0, 0, '#c6df9c','default,hnaimp','outerTab'),
	(2, 23, 12, '百度2', 'http://www.baidu.com', 'public', 0, NULL, 0, 0, '#00a99e','default,hnaimp','outerNew'),
	(2, 24, 13, '假日管理','jsp/bp/sys/holiday.jsp','public', 0,NULL, 0, 0,NULL,'default,hnaimp','innerTab');



insert into BP_SEQ(SEQ_KEY,NAME,VALUE,FORMAT_STR,FETCH_SIZE) VALUES('bp.MenuId','菜单编码',1000,null,5);

insert into BP_SYS_PARAM(PARAM_KEY,PARAM_NAME,PARAM_VALUE) VALUES('BP.TabCount','Tab页数量','6');
insert into BP_SYS_PARAM(PARAM_KEY,PARAM_NAME,PARAM_VALUE) VALUES('BP.PageSize','列表每页显示行数','10');
insert into BP_SYS_PARAM(PARAM_KEY,PARAM_NAME,PARAM_VALUE) VALUES('BP.PageList','每页显示行数选择列表','10,20,50');
insert into BP_SYS_PARAM(PARAM_KEY,PARAM_NAME,PARAM_VALUE) VALUES('BP.SingleLogin','用户单一登录','false');
insert into BP_SYS_PARAM(PARAM_KEY,PARAM_NAME,PARAM_VALUE) VALUES('BP.LoadChildrenOrgEmployees','显示下级组织所有员工','true');


insert into BP_USER(USER_ID,USER_NAME,PASSWD,STATE,IS_SYS) values ('superadmin','超级管理员','sGEVSsxMEu2Y3KtBG1tZX1',1,1);
