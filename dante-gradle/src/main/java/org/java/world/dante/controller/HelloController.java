package org.java.world.dante.controller;

import org.java.world.dante.vo.MsgVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String hello() {
		return "你好，Springboot + Gradle!";
	}
	
	@PostMapping("/msg")
	public String msg(@RequestBody MsgVO msg) {
		return msg.toString();
	}
	
}
