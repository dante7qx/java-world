package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ExceptionJob implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		throw new RuntimeException("总是失败的任务......");
	}

}
