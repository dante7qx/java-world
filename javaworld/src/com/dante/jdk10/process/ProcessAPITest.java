package com.dante.jdk10.process;

import java.io.IOException;

public class ProcessAPITest {
	
	/**
	 * 平台日志 API 
	 */
	private static final System.Logger LOGGER = System.getLogger("Slf4j"); 

	/**
	 * 对原生进程进行管理，尤其适合于管理长时间运行的进程
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception  {
		handleProcess("ps");
	}
	
	private static void handleProcess(String command) throws IOException {
		final ProcessBuilder processBuilder = new ProcessBuilder(command) 
			    .inheritIO();
		final ProcessHandle processHandle = processBuilder.start().toHandle(); 
		processHandle.onExit().whenCompleteAsync((handle, throwable) -> { 
		    if (throwable == null) { 
		    	LOGGER.log(System.Logger.Level.INFO, handle.pid() + ""); 
		    } else { 
		        throwable.printStackTrace(); 
		    } 
		});
	}
	
}
