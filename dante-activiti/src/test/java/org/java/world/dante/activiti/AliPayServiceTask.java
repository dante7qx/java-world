package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliPayServiceTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(AliPayServiceTask.class);
	
	@Override
	public void execute(DelegateExecution execution) {
		execution.setVariableLocal("account", "但丁");
		logger.info("从支付宝中扣款......");
	}

}
