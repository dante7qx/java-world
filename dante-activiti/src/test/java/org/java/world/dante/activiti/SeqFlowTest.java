package org.java.world.dante.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeqFlowTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SeqFlowTest.class);
	
	/**
	 * 条件顺序流
	 * 
	 * 1. 单向网关  - 相当于道路分叉口，由流的条件决定流程的走向
	 * 2. 默认顺序流 - 部门经理审批
	 * 
	 */
	@Test
	public void conditiionSeq() {
		repositoryService.createDeployment().addClasspathResource("diagrams/conditionSequence.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("conditionSeqProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务 {}。", task.getName());
		Map<String, Object> vars = new HashMap<>();
		vars.put("days", 1);
		taskService.complete(task.getId(), vars);
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务 {}。", task.getName());
	}
	
	/**
	 * 并行网关
	 */
	@Test
	public void parallelGetway() {
		repositoryService.createDeployment().addClasspathResource("diagrams/parallelGetway.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("parallelGetwayProcess");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务 {}。", task.getName());
		taskService.complete(task.getId());
		List<Execution> execus = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
		logger.info("当前执行流数量 {}。", execus.size());
		
		Task hrTask = taskService.createTaskQuery().taskName("人事审批").singleResult();
		taskService.complete(hrTask.getId());
		execus = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
		logger.info("人事审批结束，当前执行流数量 {}。", execus.size());
		Task mgrTask = taskService.createTaskQuery().taskName("经理审批").singleResult();
		taskService.complete(mgrTask.getId());
		execus = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
		logger.info("经理审批结束，当前执行流数量 {}。", execus.size());
		
		task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		logger.info("当前任务 {}。", task.getName());
		execus = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
		logger.info("当前执行流数量 {}。", execus.size());
		taskService.complete(task.getId());
	}
	
	/**
	 * 事件网关
	 * 1. 当流程到达事件网关后，Activiti 会为全部的中间 Catching 事件创建相应的数据，
	 * 如果某一事件先触发，流程会往该事件所处的方向执行。
	 * 2. 事件网关只会选择最先触发的事件所在的分支向前执行。
	 * 
	 */
	@Test
	public void eventGetway() {
		repositoryService.createDeployment().addClasspathResource("diagrams/eventGetway.bpmn").deploy();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("eventGetwayProcess");
		runtimeService.signalEventReceived("eventCatchingSignal");
		
//		try {
//			Thread.sleep(30000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		List<Execution> execus = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
		/**
		 * 1. 顺序执行，流程数 3
		 * 2. 异步执行，流程数 5
		 */
		logger.info("执行流数量 {}。", execus.size());
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
		tasks.forEach(t -> logger.info("当前任务 {}。", t.getName()));
	}
}
