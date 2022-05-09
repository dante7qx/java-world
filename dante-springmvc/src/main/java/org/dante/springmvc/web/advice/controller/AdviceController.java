package org.dante.springmvc.web.advice.controller;

import org.dante.springmvc.web.json.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdviceController {
	
	// http://localhost:8080/dante-springmvc/advice?id=1&name=dante
	@RequestMapping("/advice")
	public String getSometing(@ModelAttribute("msg") String msg, User user) {
		throw new IllegalArgumentException("非常抱歉，参数有误／"+"来自@ModelAttribute:" + msg);
	}
	
}
