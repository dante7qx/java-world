package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JianHangTuiServiceTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(JianHangTuiServiceTask.class);
	
	int i = 1;
	@Override
	public void execute(DelegateExecution execution) {
		String account = execution.getVariable("account").toString();
		logger.info("[{}] 退款到 {} 建行中......", i, account);
		i++;
	}

}
