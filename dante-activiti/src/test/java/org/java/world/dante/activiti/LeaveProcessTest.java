package org.java.world.dante.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveProcessTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(LeaveProcessTest.class);

	@Test
	public void testLeaveProcess() {
		// 存储服务
		RepositoryService rs = engine.getRepositoryService();
		// 运行时服务
		RuntimeService runService = engine.getRuntimeService();
		// 任务服务
		TaskService taskService = engine.getTaskService();

		// 部署流程
		rs.createDeployment().addClasspathResource("diagrams/leave.bpmn").deploy();
		
		// 获取流程实例
		ProcessInstance processInstance = runService.startProcessInstanceByKey("myProcess");
		String instanceId = processInstance.getId();
		
		// 员工申请
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		logger.info("当前流程节点 {}。", task.getName());
		taskService.complete(task.getId());
		
		// 领导审批
		task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		logger.info("当前流程节点 {}。", task.getName());
		taskService.complete(task.getId());
		
		// 流程结束
		task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		logger.info("当前流程节点 {}。", task);
	}
}
