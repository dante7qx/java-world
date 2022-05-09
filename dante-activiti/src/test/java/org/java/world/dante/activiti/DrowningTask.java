package org.java.world.dante.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class DrowningTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("有人溺水了，快救人！");
	}

}
