/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:03:47 +0800 
 */
package com.epolleo.bp.role.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.role.bean.FunctionRoleTree;
import com.epolleo.bp.role.bean.RoleBean;
import com.epolleo.bp.role.bean.RoleNodeBean;
import com.epolleo.bp.role.service.RoleFunctionService;
import com.epolleo.bp.role.service.RoleService;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.user.service.LoginUserService;
import com.epolleo.bp.util.DateUtils;

/**
 * RoleAction.java
 */
@Function()
@FunctionDir(id="bp.role", name="角色")
public class RoleAction extends AbstractAction {

	@Resource
	private RoleService roleService;
	@Resource
	private RoleFunctionService roleFunctionService;
	@Resource
	private LoginUserService loginUserService;
	@Resource
	private SeqService seqService;

	/**
	 * 
	 * 分页查询角色的基本信息
	 * 
	 * @param context
	 * @param form
	 *            表单分页的基本信息
	 * @param roleBean
	 */
	@Function("bp.role.query")
    @FunctionName("角色查询")
	public void doQuery(Context context, @Params PagingForm form,
			@Params RoleBean roleBean) {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		form.setFilterMap(filter);
		PagingResult<RoleBean> result = roleService.findPaging(form);
		context.put("json", result);
	}

	/**
	 * 通过角色的编号查询角色的基本信息
	 */
	@Function("bp.role.query")
	public void doQueryRole(Context context, @Param(name = "id") Integer id) {
		RoleBean roleBean = roleService.findRoleBeanById(id);
		context.put("json", roleBean);
	}

	@Function("public")
	public void doQueryRoleTree(Context context, @Param(name = "id") Integer id) {
		List<RoleNodeBean> roles = roleService.getAllRole();
		RoleNodeBean root = new RoleNodeBean();
		root.setState("open");
		root.setId(-1);
		root.setText("全部角色");
		root.setChildren(roles);
		context.put("json", Arrays.asList(root));
	}

	/**
	 * 保存角色的基本信息
	 */
	@Function("bp.role.insert")
    @FunctionName("角色保存")
	public void doSaveRoleBean(HttpServletRequest request, Context context,
			@Params RoleBean roleBean, @Param("funcs") String funcs) {
		String userId = LoginUser.getCurrentUser().getUserId();
		if (roleBean.getId() == 0) {
			roleBean.setId((int) seqService.getNewId(IdKind.RoleId));
			roleBean.setCreateTime(DateUtils.getCurrentDate());
			roleBean.setCreateUser(userId);
			roleBean.setUpdateTime(DateUtils.getCurrentDate());
			roleBean.setUpdateUser(userId);
			Set<String> fset = new HashSet<String>();
			if (funcs != null) {
				StringTokenizer st = new StringTokenizer(funcs, ",");
				while (st.hasMoreTokens()) {
					fset.add(st.nextToken());
				}
			}
			roleService.saveRoleBean(roleBean);
			roleFunctionService.updateRoleFunction(roleBean.getId(), fset);
		} else {
			roleBean.setUpdateTime(DateUtils.getCurrentDate());
			roleBean.setUpdateUser(userId);
			Set<String> fset = new HashSet<String>();
			if (funcs != null) {
				StringTokenizer st = new StringTokenizer(funcs, ",");
				while (st.hasMoreTokens()) {
					fset.add(st.nextToken());
				}
			}
			roleService.updateRoleBean(roleBean);
			roleFunctionService.updateRoleFunction(roleBean.getId(), fset);
		}
		context.put("json", roleBean);
	}

	/**
	 * 查询角色功能关系表，展示所有的功能，并根据FunctionRole的selected字段显示该角色是否选择该功能。
	 */
	@Function("bp.role.query")
	public void doQueryRoleFunction(Context context,
			@Param("roleId") Integer roleId) {
		List<FunctionRoleTree> functionRoles = roleService
				.findRoleAndFunction(roleId);
		context.put("json", functionRoles);
	}

	/**
	 * 通过角色编号删除角色的基本信息
	 */
	@Function("bp.role.delete")
	@FunctionName("角色删除")
	public void doDeleteRoleBean(Context context, @Param("id") Integer id) {
		boolean existUserRole = loginUserService.isExistUserRole(id);
		if (!existUserRole) {
			roleFunctionService.deleteRoleFunctionByRoleId(id);
			roleService.deleteRoleBean(id);
		}
		context.put("json", !existUserRole);
	}
}
