package org.java.world.dante.netty;

import org.java.world.dante.netty.server.EchoServer;
import org.java.world.dante.util.HostInfo;

public class NettyServerApplication {
	

	public static void main(String[] args) throws Exception {
		
		// 启动运行 DiscardServer
//		new DiscardServer().run(HostInfo.PORT);
		
		// 启动运行 EchoServer
		new EchoServer().run(HostInfo.PORT);
//		new EchoPackageServer().run(HostInfo.PORT);
//		new EchoServerWithString().run(HostInfo.PORT);
//		new EchoServerCustomerSeparator().run(HostInfo.PORT);
//		new EchoServerJdkSerializable().run(HostInfo.PORT);
//		new EchoServerMsgPackSerializable().run(HostInfo.PORT);
//		new EchoServerMarshallingSerializable().run(HostInfo.PORT);
//		new EchoServerFastJsonSerializable().run(HostInfo.PORT);
		
		// 启动运行 HttpServer
//		new HttpServer().run(HostInfo.PORT);
	}
}
