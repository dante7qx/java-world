package org.java.world.distributeid.zookeeper;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用zk的永久sequence策略创建节点，并获取返回值，然后删除前一个节点，这样既防止zk服务器存在过多的节点，又提高了效率；
 * 节点删除采用线程池来统一处理，提高响应速度。
 *
 *	优点：能创建连续递增的ID。
 * 
 * @author dante
 *
 */
@Slf4j
public class ZKIncreaseSequenceHandler extends SequenceHandler implements PooledObjectFactory<CuratorFramework> {

	private static ZKIncreaseSequenceHandler instance = new ZKIncreaseSequenceHandler();
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
	private GenericObjectPool<CuratorFramework> genericObjectPool;
	private Queue<Long> preNodes = new ConcurrentLinkedQueue<>();
	private static String ZK_ADDRESS = ""; // 127.0.0.1:2181
	private static String PATH = "";// /spirit
	private static String SEQ = "";// seq-;

	/**
	 * 私有化构造方法,单例模式
	 */
	private ZKIncreaseSequenceHandler() {
		GenericObjectPoolConfig<CuratorFramework> config = new GenericObjectPoolConfig<>();
		config.setMaxTotal(4);
		genericObjectPool = new GenericObjectPool<CuratorFramework>(this, config);
	}

	/**
	 * 获取sequence工具对象的唯一方法
	 *
	 * @return
	 */
	public static ZKIncreaseSequenceHandler getInstance(String zkAddress, String path, String seq) {
		ZK_ADDRESS = zkAddress;
		PATH = path;
		SEQ = seq;
		return instance;
	}

	@Override
	public long nextId(SequenceEnum sequenceEnum) {
		String result = createNode(sequenceEnum.getCode());
        final String idstr = result.substring((PATH + "/" + sequenceEnum.getCode() + "/" + SEQ).length());
        final long id = Long.parseLong(idstr);
        preNodes.add(id);
        //删除上一个节点
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Iterator<Long> iterator = preNodes.iterator();
                if (iterator.hasNext()) {
                    long preNode = iterator.next();
                    if (preNode < id) {
                        final String format = "%0" + idstr.length() + "d";
                        String preIdstr = String.format(format, preNode);
                        final String prePath = PATH + "/" + sequenceEnum.getCode() + "/" + SEQ + preIdstr;
                        CuratorFramework client = null;
                        try {
                            client = (CuratorFramework) genericObjectPool.borrowObject();
                            client.delete().forPath(prePath);
                            log.info("删除节点==> {}", prePath);
                            preNodes.remove(preNode);
                        } catch (Exception e) {
                            log.error("delete preNode error", e);
                        } finally {
                            if (client != null)
                                genericObjectPool.returnObject(client);
                        }
                    }
                }
            }
        });
        return id;
	}

	private String createNode(String prefixName) {
		CuratorFramework client = null;
		try {
			client = (CuratorFramework) genericObjectPool.borrowObject();
			String result = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
					.forPath(PATH + "/" + prefixName + "/" + SEQ, String.valueOf(0).getBytes());
			return result;
		} catch (Exception e) {
			throw new RuntimeException("create zookeeper node error", e);
		} finally {
			if (client != null)
				genericObjectPool.returnObject(client);
		}
	}

	@Override
	public PooledObject<CuratorFramework> makeObject() throws Exception {
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new ExponentialBackoffRetry(1000, 3));
		client.start();
		return new DefaultPooledObject<>(client);
	}

	@Override
	public void destroyObject(PooledObject<CuratorFramework> p) throws Exception {
	}

	@Override
	public boolean validateObject(PooledObject<CuratorFramework> p) {
		return false;
	}

	@Override
	public void activateObject(PooledObject<CuratorFramework> p) throws Exception {
	}

	@Override
	public void passivateObject(PooledObject<CuratorFramework> p) throws Exception {
	}

}
