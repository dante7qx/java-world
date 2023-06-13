package com.dante.designmodel.chain.handler.impl;

import com.dante.designmodel.chain.handler.GatewayHandler;

import cn.hutool.core.lang.Console;

public class SessionGatewayHandler extends GatewayHandler {

	@Override
	public void service() {
		Console.log("Session 开始处理。。。");
		if(this.nextHandler != null) {
			this.nextHandler.service();
		}
	}

}
