package org.dante.shiro.realm;

import java.sql.Connection;
import java.util.HashSet;
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
import org.dante.shiro.dao.UserDao;
import org.dante.shiro.domain.Permission;
import org.dante.shiro.domain.Role;
import org.dante.shiro.domain.User;
import org.dante.shiro.utils.CryptographyUtils;
import org.dante.shiro.utils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义realm
 * 
 * @author dante
 */
public class MyAuthenticateRealm extends AuthorizingRealm {
	private final static Logger logger = LoggerFactory.getLogger(MyAuthenticateRealm.class);
	private UserDao userDao = new UserDao();
	
	/**
	 * 用户认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = null;
		Connection conn = DbUtils.getConn();
		String userName = (String) token.getPrincipal();
		try {
			logger.info("[user]->"+userName+" 认证！");
			User user = userDao.findByUserName(conn, userName);
			if(user != null) {
//				authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), CryptographyUtils.sha256Hash(user.getPassword(), CryptographyUtils.SALT), "");
				authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "");
				authenticationInfo.setPrincipals(new SimplePrincipalCollection(user, ""));
			} else {
				throw new Exception(userName + "在DB不存在。");
			}
		} catch (Exception e) {
			logger.error(userName + "从DB获取失败。", e);
		} finally {
			DbUtils.closeConn(conn);
		}
		return authenticationInfo;
	}

	/**
	 * 用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Connection conn = DbUtils.getConn();
		SimpleAuthorizationInfo authorizationInfo = null;
		User user = (User) principals.getPrimaryPrincipal();
		String userName = user.getUserName();
		try {
			logger.info("[user]->"+userName+" 授权！");
			authorizationInfo = new SimpleAuthorizationInfo();
			Set<Role> roles = userDao.findUserRolesByUserName(conn, userName);
			if(roles != null && !roles.isEmpty()) {
				Set<String> roleIds = new HashSet<String>();
				for (Role role : roles) {
					roleIds.add("ROLE[" + role.getId() + "]");
				}
				authorizationInfo.setRoles(roleIds);
			}
			Set<Permission> permissions = userDao.findUserPermissionByUserName(conn, userName);
			if(permissions != null && !permissions.isEmpty()) {
				Set<String> permissionCodes = new HashSet<String>();
				for (Permission permission : permissions) {
					permissionCodes.add(permission.getCode());
				}
				authorizationInfo.setStringPermissions(permissionCodes);
			}
			
		} catch (Exception e) {
			logger.error(userName + "role、permission从DB获取失败。", e);
		} finally {
			DbUtils.closeConn(conn);
		}
		
		return authorizationInfo;
	}

	

}
