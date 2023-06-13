package com.dante.designmodel.chain.handler;

public abstract class GatewayHandler {
	
	protected GatewayHandler nextHandler;
	
	public void setNext(GatewayHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public abstract void service();
	
}
