package org.dante.springmvc.web.json.controller;

import org.dante.springmvc.web.json.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class UserRestController {

	// http://localhost:8080/dante-springmvc/rest/getjson?id=7&name=dante
	@RequestMapping(value = "/getjson", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public User getUserJson(User user) {
		user.setName("但丁");
		return user;
	}

	// http://localhost:8080/dante-springmvc/rest/getxml?id=7&name=dante
	@RequestMapping(value = "/getxml", method = RequestMethod.GET, produces = "application/xml;charset=UTF-8")
	public User getUserXml(User user) {
		user.setName("但丁");
		return user;
	}
}
