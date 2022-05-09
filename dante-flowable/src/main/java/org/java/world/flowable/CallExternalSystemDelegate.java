package org.java.world.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 服务任务(service task) 的处理类
 * 
 * @author dante
 *
 */
public class CallExternalSystemDelegate implements JavaDelegate {

	/**
	 * 审核通过，系统自动注册到某个外部系统
	 * 
	 */
	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("已经自动为员工" + execution.getVariable("employee") + "注册至某个外部系统。");
	}

}
