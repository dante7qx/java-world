package org.java.world.dante.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class SelectorTests {

	@Test
	public void testSelectorOperator() {
		Selector selector = null;
		try {
			// 1. 打开一个 Selector
			selector = Selector.open();
			// 2. 有一个新的Socket客户端 SocketChannel (Channel 必须是非阻塞的)
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			// 3. SocketChannel 注册到 Selector 上, 需要处理已被接收事件(Accept) 和 可读事件(Read)
			socketChannel.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);
			while(!Thread.currentThread().isInterrupted()) {
				// 4. Selector 询问已就绪事件
				int readyEvt = selector.select();
				if(readyEvt == 0) continue;
				// 5. 对于不通的注册事件，进行相关的业务操作
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> itors = selectedKeys.iterator();
				while(itors.hasNext()) {
					SelectionKey key = itors.next();
					if(key.isAcceptable()) {
				        // a connection was accepted by a ServerSocketChannel.
				    } else if (key.isConnectable()) {
				        // a connection was established with a remote server.
				    } else if (key.isReadable()) {
				        // a channel is ready for reading
				    } else if (key.isWritable()) {
				        // a channel is ready for writing
				    }
					// 6. 事件被处理后，清除 key
					itors.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
