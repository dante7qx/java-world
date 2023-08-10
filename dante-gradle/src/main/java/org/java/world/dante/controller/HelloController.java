package org.java.world.dante.controller;

import org.java.world.dante.util.IPUtil;
import org.java.world.dante.vo.MsgVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.Console;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String hello(HttpServletRequest request) {
		String ip = IPUtil.getIpAddr(request);
		String addr = IPUtil.getAddr(ip);
		Console.log("当前请求IP --> {} <==> {}", ip, addr);
		return "你好，Springboot + Gradle!";
	}
	
	@PostMapping("/msg")
	public String msg(@RequestBody MsgVO msg) {
		return msg.toString();
	}
	
}
