package org.java.world.distributeid;

import org.java.world.distributeid.zookeeper.SequenceEnum;
import org.java.world.distributeid.zookeeper.ZKCachedSequenceHandler;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKCachedSequenceHandlerTest {
	
	private static final String ZK_ADDR = "127.0.0.1:2181";
	private static final String PATH = "/spirit";
	
	@Test
	public void nextCachedSequence() {
		long startTime = System.currentTimeMillis();   //获取开始时间
        final ZKCachedSequenceHandler sequenceHandler = ZKCachedSequenceHandler.getInstance(ZK_ADDR, PATH, "seq-");
        sequenceHandler.loadZK();
        new Thread() {
            public void run() {
                long startTime2 = System.currentTimeMillis();   //获取开始时间
                for (int i = 0; i < 5000; i++) {
                    System.out.println("线程1 " + sequenceHandler.nextId(SequenceEnum.ORDER));
                }
                long endTime2 = System.currentTimeMillis(); //获取结束时间
                System.out.println("程序运行时间1： " + (endTime2 - startTime2) + "ms");
            }
        }.start();
        for (int i = 0; i < 5000; i++) {
            System.out.println("线程2 " + sequenceHandler.nextId(SequenceEnum.ORDER));
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        log.info("程序运行时间2： " + (endTime - startTime) + "ms");
	}
	
}
