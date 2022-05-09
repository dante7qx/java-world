package org.dante.springmvc.web.json.controller;

import javax.servlet.http.HttpServletRequest;

import org.dante.springmvc.web.json.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	// http://localhost:8080/dante-springmvc/user
	@RequestMapping(produces = "text/plain;chartset=UTF-8")
	@ResponseBody
	public String index(HttpServletRequest request) {
		return "url:" + request.getRequestURL() + " can access";
	}

	// http://localhost:8080/dante-springmvc/user/pathvar/userId=2
	@RequestMapping(value = "/pathvar/{str}", produces = "text/plain;chartset=UTF-8")
	public @ResponseBody String pathVar(@PathVariable String str,
			HttpServletRequest request) {
		return "url:" + request.getRequestURL()
				+ " can access, pathVar str is " + str;
	}

	// http://localhost:8080/dante-springmvc/user/reqparam?id=7
	@RequestMapping(value = "/reqparam", produces = "text/plain;chartset=UTF-8")
	public @ResponseBody String reqParam(HttpServletRequest request, Integer id) {
		return "url:" + request.getRequestURL()
				+ " can access, request param id is " + id;
	}

	// http://localhost:8080/dante-springmvc/user/json?id=7&name=dante
	@RequestMapping(value = "/json", produces = "application/json;chartset=UTF-8")
	@ResponseBody
	public String json(HttpServletRequest request, User user) {
		return "url:" + request.getRequestURL() + " can access, user is "
				+ user;
	}

	// 不同路径映射到相同的方法
	// http://localhost:8080/dante-springmvc/user/reg1
	// http://localhost:8080/dante-springmvc/user/reg2
	@RequestMapping(value = { "/reg1", "/reg2" })
	public @ResponseBody String register(HttpServletRequest request) {
		logger.info("url:" + request.getRequestURL());
		return "url:" + request.getRequestURL() + " can access";
	}
}
