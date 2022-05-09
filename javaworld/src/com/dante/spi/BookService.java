package com.dante.spi;

/**
 * SPI 全称为 (Service Provider Interface) ,是JDK内置的一种服务提供发现机制。 
 * 目前有不少框架用它来做服务的扩展发现， 简单来说，它就是一种动态替换发现的机制
 * 
 * 有个接口，想运行时动态的给它添加实现，你只需要添加一个实现
 * 
 * 具体是在JAR包的"src/META-INF/services/"目录下建立一个文件，文件名是接口的全限定名，
 * 文件的内容可以有多行，每行都是该接口对应的具体实现类的全限定名
 * 
 * @author dante
 *
 */
public interface BookService {
	void read();
}
