package org.dante.springmvc.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.dante.springmvc.mybatis.domain.User;
import org.dante.springmvc.mybatis.service.UserService;
import org.dante.springmvc.spring.exception.DanteException;
import org.dante.springmvc.spring.utils.CryptographyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login(Model model, User user) throws DanteException {
		String returnView = "redirect:/";
		String username = user.getUserName();
		String password = user.getPassword();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, CryptographyUtils.sha256Hash(password, CryptographyUtils.SALT));
			currentUser.login(token);
		} catch (AuthenticationException e) {
			returnView = "login";
			model.addAttribute("errorMsg", "用户名、密码错误，请输入正确的用户名、密码。");
			logger.error(username + "登录失败", e);
		} catch (Exception e) {
			throw new DanteException("用户名查询错误", e);
		}
		return returnView;
	}
	
	@RequestMapping("/register")
	public String register(Model model, User user) throws DanteException {
		try {
			userService.insertUser(user.getUserName(), user.getPassword());
		} catch (Exception e) {
			logger.error(user.getUserName() + "注册失败.", e);
			throw new DanteException("注册失败", e);
		}
		return "index";
	}

	@RequiresRoles("superadmin")
	@RequestMapping(value = "/login/err", produces = "text/plain;chartset=UTF-8")
	@ResponseBody
	public String tryErr() throws DanteException {
		throw new DanteException("哎呀，出错了，被你发现了！");
	}
	
	@RequestMapping(value = "/login/test", produces = "text/plain;chartset=UTF-8")
	@ResponseBody
	public String test() throws DanteException {
		return "👏，欢迎登录，✌️";
	}
}
