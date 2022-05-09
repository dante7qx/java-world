package org.java.world.dante.netty;

import org.java.world.dante.netty.client.EchoFastJsonSerializableClient;
import org.java.world.dante.util.HostInfo;

public class NettyClientApplication {
	
	public static void main(String[] args) throws InterruptedException {
		
		// Discard Client
//		new DiscardClient().run(HostInfo.HOST, HostInfo.PORT);
		
		// Echo Client
//		new EchoClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoPackageClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoWithStringClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoCustomerSeparatorClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoJdkSerializableClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoMsgPackSerializableClient().run(HostInfo.HOST, HostInfo.PORT);
//		new EchoMarshallingSerializableClient().run(HostInfo.HOST, HostInfo.PORT);
		new EchoFastJsonSerializableClient().run(HostInfo.HOST, HostInfo.PORT);
	}
	
}
