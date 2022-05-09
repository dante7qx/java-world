package org.dante.springmvc.web.serverpush.controller;

import java.util.Random;

import org.dante.springmvc.web.serverpush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping("/serverpush")
public class ServerPushController {

	@Autowired
	private PushService pushService;
	
	/**
	 * SSE（Server Send Event 服务端发送事件）
	 * 
	 * @return
	 */
	// http://localhost:8080/dante-springmvc/sse
	@RequestMapping(value = "/sse", produces = "text/event-stream")
	@ResponseBody
	public String push() {
		Random r = new Random();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "服务器SSE数据：" + r.nextInt();
	}
	
	// http://localhost:8080/dante-springmvc/async
	@RequestMapping("/defer")
	@ResponseBody
	public DeferredResult<String> deferredCall() {
		return pushService.getAsyncUpdate();
	}

}
