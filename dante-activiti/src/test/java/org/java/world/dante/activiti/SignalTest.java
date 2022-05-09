package org.java.world.dante.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 信号触发流程
 * 
 * @author dante
 *
 */
public class SignalTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SignalTest.class);
	
	@Test
	public void testSingnal() {
		repositoryService.createDeployment().addClasspathResource("diagrams/signal.bpmn").deploy();
		
		// 开始流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("SignalProcess");
		// 查找执行流
		Execution exec = runtimeService.createExecutionQuery().activityId("writeVacation").singleResult();
		if(exec != null) {
			logger.info("当前执行流程 {}.", exec.getName());
		}
		
		// 发送信号，让执行流向前执行
		runtimeService.signalEventReceived("sigX", exec.getId());
		
		// 向下执行后，会出现流程分支，根据流程节点ID查询两个执行流
		Execution managerExec = runtimeService.createExecutionQuery().activityId("managerAudit").singleResult();
		Execution hrExec = runtimeService.createExecutionQuery().activityId("hrAudit").singleResult();
		if(managerExec != null && hrExec != null) {
			logger.info("当前执行流程 {} 和 {}.", managerExec.getName(), hrExec.getName());
		}
		
		// 设置参数
		Map<String, Object> args = new HashMap<>();
		args.put("days", 5);
		
		// 经理 和 Hr 审批通过
		runtimeService.signalEventReceived("sigX", managerExec.getId());
		runtimeService.signalEventReceived("sigX", hrExec.getId(), args);
		
		// Boss开始审批
		Execution bossExec = runtimeService.createExecutionQuery().activityId("bossAudit").singleResult();
		if(bossExec != null) {
			logger.info("当前执行流程 {}, 参数 {}.", bossExec.getName(), runtimeService.getVariable(bossExec.getId(), "days"));
		}
		
		// 完成流程
		runtimeService.signalEventReceived("sigX", bossExec.getId());
		long execCount = runtimeService.createExecutionQuery().processDefinitionId(pi.getId()).count();
		logger.info("流程完成，执行流数量{}.", execCount);
	}
	
}
