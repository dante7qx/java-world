package com.dante.rpc.server.provider.service.impl;

import com.dante.rpc.server.provider.service.EchoService;

public class EchoServiceImpl implements EchoService {

	@Override
	public String echo(String ping) {
		return ping != null ? ping + " --> I am ok!" : "I am ok!";
	}

}
