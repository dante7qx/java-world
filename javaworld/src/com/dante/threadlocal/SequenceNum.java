package com.dante.threadlocal;

/**
 * 序号生成器
 * 
 * @author dante
 *
 */
public class SequenceNum {
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};
	
	public int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}
}
