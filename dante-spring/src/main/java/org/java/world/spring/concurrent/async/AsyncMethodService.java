package org.java.world.spring.concurrent.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 1、通过@Async表明方法是异步方法，若注解到类上，则表明类的所有方法都是异步方法
 * 2、@Async标注的方法都会自动注入到ThreadPoolTaskExecutor
 * 
 * @author dante
 *
 */
@Service
public class AsyncMethodService {
	
	@Async
	public void executeAsyncTask(int i) {
		System.out.println("executeAsyncTask：" + i);
	}
	
	@Async
	public void executeAsyncPlus(int i) {
		System.out.println("executeAsyncPlus：" + (i+1));
	}
}
