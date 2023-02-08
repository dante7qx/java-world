package org.java.world.dante.nexus3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

public class Nexus3Client {

	// https://www.baeldung.com/java-9-http-client
	private static final HttpClient httpClient = HttpClient.newBuilder().build();

	public static void main(String[] args) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("http://www.baidu.com"))
				.headers("Content-Type", "text/plain;charset=UTF-8")
				.GET()
				.build();
		HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString(Charset.forName("GB2312")));
		String result = response.body();
		System.out.println(response);
		System.out.println(result);
	}

}
