package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliTuiServiceTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(AliTuiServiceTask.class);
	
	@Override
	public void execute(DelegateExecution execution) {
		String account = execution.getVariable("account").toString();
		logger.info("退款到 {} 支付宝中......", account);
	}

}
