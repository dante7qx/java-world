package org.java.world.dante.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程实例测试
 * 
 * @author dante
 *
 */
public class ProcessInstanceTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceTest.class);
	
	@Test
	public void startSingleProcess() {
		// 部署流程描述文件
		Deployment deploy = repositoryService.createDeployment().addClasspathResource("diagrams/single.bpmn").deploy();
		// 查找流程定义
		ProcessDefinition processDefine = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
		String processDefineId = processDefine.getId();
		
		// 流程参数
		Map<String, Object> vars = new HashMap<>();
		vars.put("days", 5);
		
		// 启动流程
		runtimeService.startProcessInstanceById(processDefineId);
		runtimeService.startProcessInstanceById(processDefineId, vars);
		runtimeService.startProcessInstanceById(processDefineId, "Single_Bussiness_Key_By_Id");
		runtimeService.startProcessInstanceByKey("singleProcess", "Single_Bussiness_Key_By_Key");
	}

	@Test
	public void startMultiProcess() {
		// 部署流程描述文件
		repositoryService.createDeployment().addClasspathResource("diagrams/multi.bpmn").deploy();
		
		// 启动流程
		runtimeService.startProcessInstanceByKey("MultiProcess");
		
		long count = runtimeService.createProcessInstanceQuery().count();
		logger.info("流程实例数量 {}。", count);
	}
	
	/**
	 * 调用式子流程
	 */
	@Test
	public void callSubProcess() {
		repositoryService.createDeployment()
			.addClasspathResource("diagrams/subProcess.bpmn")
			.addClasspathResource("diagrams/callSubProcess.bpmn")
			.deploy();
		Map<String, Object> vars = new HashMap<>();
		vars.put("days", 5);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("callSubProcess");
		logger.info("当前流程实例 {}.", pi.getId());
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务: {}.", task.getName());
		taskService.complete(task.getId(), vars);
		
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务: {}.", task.getName());
		taskService.complete(task.getId());
		
		// 主流程中获取参数
		Integer days = (Integer) runtimeService.getVariable(pi.getId(), "days");
		logger.info("主流程参数 days {}。", days);
		
		ProcessInstance subPi = runtimeService.createProcessInstanceQuery().superProcessInstanceId(pi.getId()).singleResult();
		// 子流程中获取参数 <activiti:in source="days" target="newDays"></activiti:in>
		Integer newDays = (Integer) runtimeService.getVariable(subPi.getId(), "newDays");
		logger.info("当前流程实例 {}，参数 newDays {}.", subPi.getId(), newDays);
		task = taskService.createTaskQuery().processInstanceId(subPi.getId()).singleResult();
		logger.info("当前任务: {}.", task.getName());
		taskService.complete(task.getId());
		task = taskService.createTaskQuery().processInstanceId(subPi.getId()).singleResult();
		logger.info("当前任务: {}.", task.getName());
		// 子流程中设置参数 <activiti:out source="myDays" target="resultDays"></activiti:out>
		runtimeService.setVariable(subPi.getId(), "myDays", newDays - 2);
		taskService.complete(task.getId());
		
		// 在主流程中获取参数
		Integer resultDays = (Integer) runtimeService.getVariable(pi.getId(), "resultDays");
		logger.info("主流程参数 resultDays {}。", resultDays);
	}
	
}
