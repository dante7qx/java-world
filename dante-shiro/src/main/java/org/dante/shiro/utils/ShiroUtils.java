package org.dante.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroUtils {
	public static Subject login(String configPath, String username,
			String password) {
		// 读取配置文件
		Factory<SecurityManager> shiroFactory = new IniSecurityManagerFactory(
				configPath);
		SecurityManager securityManager = shiroFactory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		// 创建token令牌
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		currentUser.login(token);
		return currentUser;
	}
}
