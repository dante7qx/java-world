package org.dante.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestIniRealm {
	private final static Logger logger = LoggerFactory.getLogger(TestIniRealm.class);
	
	public static void main(String[] args) {
		// 读取配置文件
		Factory<SecurityManager> shiroFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = shiroFactory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		// 创建token令牌
		UsernamePasswordToken token = new UsernamePasswordToken("superadmin", "1");
		// 身份认证
		try {
			if(!currentUser.isAuthenticated()) {
				currentUser.login(token);
				logger.info(currentUser.getPrincipal() + "登录成功");
			}
		} catch (AuthenticationException e) {
			logger.error("登录失败", e);
		} 
		
		currentUser.logout();
		
	}
	
}
