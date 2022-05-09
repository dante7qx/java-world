package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JianHangPayServiceTask implements JavaDelegate {

	private static final Logger logger = LoggerFactory.getLogger(JianHangPayServiceTask.class);
	
	int i = 1;
	@Override
	public void execute(DelegateExecution execution) {
		execution.setVariableLocal("account", "性格但丁");
		logger.info("[{}] 从建行中扣款......", i);
		i++;
	}

}
