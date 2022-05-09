package org.java.world.dante.nio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import lombok.extern.slf4j.Slf4j;

/**
 * Channel 不存储数据，而是将数据写入 Buffer，或者从 Buffer 读取数据
 * 
 * @author dante
 *
 */
@Slf4j
public class EchoServerHandler implements Runnable {

	// 客户端连接
	private SocketChannel socketChannel;
	private boolean flag = true;

	public EchoServerHandler(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	@Override
	public void run() {
		// 数据缓存区
		ByteBuffer buffer = ByteBuffer.allocate(50);
		try {
			while (flag) {
				// 清空buffer
				buffer.clear();
				// 1. 将客户端数据从 Channel 写入 Buffer

				int writeCount = socketChannel.read(buffer);
				String clientMsg = new String(buffer.array(), 0, writeCount);
				log.info("客户 {} 发送数据：{}", socketChannel.getLocalAddress(), clientMsg);
				// 2. 设置回复信息，写入到 buffer 中
				String replyMsg = "【Echo】: " + clientMsg + "\n";
				if ("bye".equalsIgnoreCase(clientMsg)) {
					replyMsg = "服务端结束通话。";
					flag = false;
				}
				buffer.clear();
				buffer.put(replyMsg.getBytes());
				buffer.flip(); // 切换到读模式
				this.socketChannel.write(buffer);
			}
			this.socketChannel.close();

		} catch (IOException e) {
			log.error("Channel 写入 Buffer 失败。", e);
		}
	}

}
