package org.java.world.dante.activiti;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SwimTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("开始游泳比赛🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️🏊‍♀️");
		throw new BpmnError("1000", "有人溺水了，快救人！");
	}

}
