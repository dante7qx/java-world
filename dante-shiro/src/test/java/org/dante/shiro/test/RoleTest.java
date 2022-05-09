package org.dante.shiro.test;

import java.util.Arrays;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.dante.shiro.utils.ShiroUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleTest {
	private final static Logger logger = LoggerFactory.getLogger(RoleTest.class);
	
	@Test
	public void testUserRole() {
		Subject currentUser = ShiroUtils.login("classpath:user-role.ini", "superadmin", "123456");
		logger.info(currentUser.hasRole("role1") ? "superadmin有role1" : "superadmin没有role1");
		boolean[] hasRoles = currentUser.hasRoles(Arrays.asList("role1","role2"));
		for (boolean hasRole : hasRoles) {
			logger.info(hasRole ? "有" : "没有");
		}
		try {
			currentUser.checkRoles("role1", "role2");
			logger.info("superadmin有role1、role2角色");
			currentUser.checkRole("role3");
			logger.info("superadmin有role3角色");
		} catch (AuthorizationException aex) {
			logger.error("superadmin没有role3角色", aex);
		}
		currentUser.logout();
	}
	
	@Test
	public void testUserPermission() {
		Subject currentUser = ShiroUtils.login("classpath:user-role.ini", "garsp", "garsp123");
		logger.info(currentUser.isPermitted("plane:delete") ? "有plane删除权限" : "没有plane删除权限");
		try {
			currentUser.checkPermissions("user:*", "plane:query");
			logger.info(currentUser.getPrincipal() + "有[user:*, plane:query]权限");
		} catch(AuthorizationException e) {
			logger.error("没有权限", e);
		}
		currentUser.logout();
	}

}
