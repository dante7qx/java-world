package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderGenTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(OrderGenTask.class);
	
	@Override
	public void execute(DelegateExecution execution) {
		logger.info("系统开始生成订单......");
	}

}
