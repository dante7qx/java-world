package org.java.world.dante.factory;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;


/**
 * JBoss的Marshalling序列化框架，它是JBoss内部使用的序列化框架，Netty提供了Marshalling编码和解码器，方便用户在Netty中使用Marshalling。
 * 
 * JBoss
 * Marshalling是一个Java对象序列化包，对JDK默认的序列化框架进行了优化，但又保持跟java.io.Serializable接口的兼容，
 * 同时增加了一些可调的参数和附加的特性，这些参数和特性可通过工厂类进行配置。
 * 
 * @author dante
 *
 */
public final class MarshallingCodeFactory {

	/**
	 * Marshalling 解码器
	 * 
	 * @return
	 */
	public static MarshallingDecoder buildMarshallingDecoder() {
		// serial 表示创建的是 java 序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		// 构建 Netty 的 MarshallingDecoder 对象，俩个参数分别为provider和单个消息序列化后的最大长度
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
		return decoder;
	}
	
	/**
	 * Marshalling 编码器
	 * 
	 * @return
	 */
	public static MarshallingEncoder buildMarshallingEncoder() {
		// serial 表示创建的是 java 序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        // 构建 Netty 的 MarshallingEncoder 对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
	

}
