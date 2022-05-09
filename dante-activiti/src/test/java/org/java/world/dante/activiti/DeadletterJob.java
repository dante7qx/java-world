package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class DeadletterJob implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("DeadletterJob 开始执行......");

	}

}
