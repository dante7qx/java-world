package org.dante.springmvc.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.dante.springmvc.mybatis.domain.Permission;
import org.dante.springmvc.mybatis.domain.Role;
import org.dante.springmvc.mybatis.domain.User;
import org.dante.springmvc.mybatis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm
 * 
 * @author dante
 */
public class MyAuthenticateRealm extends AuthorizingRealm {
	private final static Logger logger = LoggerFactory.getLogger(MyAuthenticateRealm.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = null;
		String userName = (String) token.getPrincipal();
		try {
			logger.info("[user]->"+userName+" 认证！");
			User user = userService.findByUserName( userName);
			if(user != null) {
				authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "");
				authenticationInfo.setPrincipals(new SimplePrincipalCollection(user, ""));
			} else {
				throw new Exception(userName + "在DB不存在。");
			}
		} catch (Exception e) {
			logger.error(userName + "从DB获取失败。", e);
		} 
		return authenticationInfo;
	}

	/**
	 * 用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = null;
		User user = (User) principals.getPrimaryPrincipal();
		String userName = user.getUserName();
		try {
			logger.info("[user]->"+userName+" 授权！");
			authorizationInfo = new SimpleAuthorizationInfo();
			List<Role> roles = userService.findUserRolesByUserName(userName);
			if(roles != null && !roles.isEmpty()) {
				Set<String> roleIds = new HashSet<String>();
				for (Role role : roles) {
					roleIds.add("ROLE[" + role.getId() + "]");
				}
				authorizationInfo.setRoles(roleIds);
			}
			List<Permission> permissions = userService.findUserPermissionByUserName(userName);
			if(permissions != null && !permissions.isEmpty()) {
				Set<String> permissionCodes = new HashSet<String>();
				for (Permission permission : permissions) {
					permissionCodes.add(permission.getCode());
				}
				authorizationInfo.setStringPermissions(permissionCodes);
			}
			
		} catch (Exception e) {
			logger.error(userName + "role、permission从DB获取失败。", e);
		}
		return authorizationInfo;
	}

}
