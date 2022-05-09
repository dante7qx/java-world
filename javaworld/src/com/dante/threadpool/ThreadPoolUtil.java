package com.dante.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	private static ExecutorService pool = null;
	
	static {
		pool = Executors.newFixedThreadPool(3);
	}
	
	public static void run(Runnable thread) {
		pool.execute(thread);
	}
	
}
