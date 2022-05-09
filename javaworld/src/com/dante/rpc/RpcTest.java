package com.dante.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.dante.rpc.client.importer.RpcImporter;
import com.dante.rpc.server.exporter.RpcExporter;
import com.dante.rpc.server.provider.service.EchoService;
import com.dante.rpc.server.provider.service.impl.EchoServiceImpl;

public class RpcTest {

	public static void main(String[] args) {
		// 服务端服务启动
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RpcExporter.exporter("localhost", 8000);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		// 服务调用者
		RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
		EchoService echoService = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8000));
		System.out.println(echoService.echo("Are you ok?"));
	}
	
}
