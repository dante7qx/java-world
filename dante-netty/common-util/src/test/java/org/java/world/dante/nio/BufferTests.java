package org.java.world.dante.nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

public class BufferTests {

	private ByteBuffer buffer = null;

	@Before
	public void init() {
		// 分配内存空间，最大容量 1024byte
		buffer = ByteBuffer.allocate(1024);
	}

	@Test
	public void byteBuffer() throws UnsupportedEncodingException {
		// 写数据
		writeData();
		// 写、读模式切换，position 重置为 0，limit = 11
		buffer.flip();
		// position = 0，limit = 11
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());
		// 将数据读到 byte[] 中（共11字节）
		byte[] dataByte = new byte[buffer.limit()];
		ByteBuffer byteBuffer = buffer.get(dataByte);
		System.out.println(new String(dataByte).trim());

		// 读完数据后，清空缓存区
		// clear() 方法并不会将 Buffer 中的数据清空，只不过后续的写入会覆盖掉原来的数据，也就相当于清空了数据了
		byteBuffer.clear();
		// position = 0，limit = 1024
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());

		// 因为数据还在，所以可以读取到数据
		byte[] dataByteAfterClear = new byte[buffer.limit()];
		buffer.get(dataByteAfterClear);
		System.out.println(new String(dataByteAfterClear).trim());
	}

	/**
	 * mark, mark 用于临时保存 position 的值，每次调用 mark() 方法都会将 mark 设值为当前的 position reset,
	 * reset, 将position回置到 mark 的位置（没有mark过，reset 后 position = 0）
	 * rewind, 会重置 position 为 0，通常用于重新从头读写 Buffer。
	 * 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void markAndResetAndRewind() throws UnsupportedEncodingException {
		// 写数据
		writeData();
		buffer.flip();
		// 读5个字节
		readData(5);
		
		// 保存临时 position, 当前 position = 5
		buffer.mark();
		// 再读3个字节
		readData(3);
		
		// reset，从 position=5 的位置重新读取
		buffer.reset();
		readData(2);
		
		// rewind，从 position=5 的位置重新读取
		buffer.rewind();
		readData(5);
	}

	private void writeData() throws UnsupportedEncodingException {
		// 写数据，position = 0，limit = 1024
		System.out.println("开始写数据......");
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());
		buffer.put("hello-world".getBytes("UTF-8"));
		// position = 11，limit = 1024
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());
		System.out.println("写数据完成......");
	}
	
	private void readData(int readSize) {
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());
		byte[] rByte = new byte[readSize];
		buffer.get(rByte);
		System.out.println(new String(rByte));
		System.out.println("Position ==> " + buffer.position() + "，Limit ==> " + buffer.limit());
	}

}
