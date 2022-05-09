package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompensationTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(CompensationTask.class);
	
	@Override
	public void execute(DelegateExecution execution) {
		logger.info("取消补偿事件任务开始执行......");

	}

}
