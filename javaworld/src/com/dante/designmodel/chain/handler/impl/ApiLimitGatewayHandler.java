package com.dante.designmodel.chain.handler.impl;

import com.dante.designmodel.chain.handler.GatewayHandler;

import cn.hutool.core.lang.Console;

public class ApiLimitGatewayHandler extends GatewayHandler {

	int API_LIMIT = 2;
	
	@Override
	public void service() {
		Console.log("API Limit 开始处理。。。");
		if(this.nextHandler != null) {
			this.nextHandler.service();
		}
	}

}
