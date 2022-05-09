package com.dante.rpc.server.provider.service;

/**
 * 服务提供者，运行在服务端。负责提供服务接口定义和服务实现类
 * 
 * @author dante
 *
 */
public interface EchoService {
	public String echo(String ping);
}
