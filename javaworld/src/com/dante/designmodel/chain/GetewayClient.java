package com.dante.designmodel.chain;

import com.dante.designmodel.chain.factory.GatewayHandlerEnumFactory;
import com.dante.designmodel.chain.handler.GatewayHandler;

public class GetewayClient {
	
	public static void main(String[] args) {
		GatewayHandler firstGatewayHandler = GatewayHandlerEnumFactory.getFirstGatewayHandler();
		firstGatewayHandler.service();
	}
	
}
