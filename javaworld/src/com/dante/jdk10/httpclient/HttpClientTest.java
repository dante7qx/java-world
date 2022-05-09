package com.dante.jdk10.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * 1. (jdk 9~10)jdk.incubator.http => (jdk11)java.net.http
 * 2. (jdk 9~10)HttpResponse.BodyHandler.asString() => (jdk11)HttpResponse.BodyHandlers.ofString()
 * 3. (jdk 9~10)asXXX() => (jdk11)ofXXX()
 **/
//import jdk.incubator.http.HttpClient;
//import jdk.incubator.http.HttpRequest;
//import jdk.incubator.http.HttpResponse;

/**
 * JRE运行时添加模块：--add-modules jdk.incubator.httpclient
 * 
 * @author dante
 *
 */
public class HttpClientTest {

	private static final String URI = "http://spiritdev-caas-consumer-api.air.haihangyun.com/msg";
//	private static final String URI = "http://localhost:8082/111111";
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
	
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().uri(new URI(URI)).build();
		
		send(httpClient, request);
		asyncSend(httpClient, request);
	}
	
	private static void send(HttpClient httpClient, HttpRequest request) throws IOException, InterruptedException {
		for (int i = 0; i < 10; i++) {
			String returnStr = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
			System.out.println("同步返回结果 ====> " + returnStr);
		}
	}
	
	private static void asyncSend(HttpClient httpClient, HttpRequest request) {
		for (int i = 0; i < 10; i++) {
			fixedThreadPool.execute(() -> {
				String returnStr = "";
				try {
					returnStr = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				System.out.println("异步返回结果 ====> " + returnStr);
			});
		}
	}

}
