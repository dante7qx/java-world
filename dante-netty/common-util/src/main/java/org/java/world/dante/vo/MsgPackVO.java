package org.java.world.dante.vo;

import java.util.Date;

import org.msgpack.annotation.Message;

import lombok.Data;

/**
 * 使用 MsgPack，pojo对象一定要加上@Message注解
 * MessagePack将对象编码后，解码返回的对象是一个List集合
 * 
 * @author dante
 *
 */
@Message
@Data
public class MsgPackVO {
	private String uid;
	private String msg;
	private Integer code;
	private Date sendDate;
	
	public MsgPackVO() {}
}
