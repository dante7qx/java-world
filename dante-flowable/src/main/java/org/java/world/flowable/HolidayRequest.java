package org.java.world.flowable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


public class HolidayRequest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HolidayRequest.class);
	
	public static void main(String[] args) {
		 /**
		  *  1. 初始化ProcessEngine流程引擎实例。这是一个线程安全的对象，因此通常只需要在一个应用中初始化一次。
		  *  	ProcessEngine由ProcessEngineConfiguration实例创建。该实例可以配置与调整流程引擎的设置。 
		  *  	通常使用一个配置XML文件创建ProcessEngineConfiguration。
		  */
		ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
			      .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
			      .setJdbcUsername("sa")
			      .setJdbcPassword("")
			      .setJdbcDriver("org.h2.Driver")
			      .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		ProcessEngine processEngine = cfg.buildProcessEngine();
		
		/**
		 * 2. 部署流程定义(process definition)。Flowable引擎需要流程定义为BPMN 2.0格式，这是一个业界广泛接受的XML标准。
		 * 	  一个流程定义可以启动多个流程实例(process instance)。
		 * 	  
		 *    本例：流程定义定义了请假的各个步骤，而一个流程实例对应某个雇员提出的一个请假申请。启动流程需要提供一些信息，例如雇员名字、请假时长以及说明。
		 *    
		      左侧的圆圈叫做启动事件(start event)。这是一个流程实例的起点。

			  第一个矩形是一个用户任务(user task)。这是流程中人类用户操作的步骤。在这个例子中，经理需要批准或驳回申请。
				
			  取决于经理的决定，排他网关(exclusive gateway) (带叉的菱形)会将流程实例路由至批准或驳回路径。（自动逻辑）
				
		      如果批准，则需要将申请注册至某个外部系统，并跟着另一个用户任务，将经理的决定通知给申请人。当然也可以改为发送邮件。
				
			  如果驳回，则为雇员发送一封邮件通知他。
		 */
		// 这里我们直接撰写XML，以熟悉BPMN 2.0及其概念。保存在src/main/resources文件夹下。
		/**
		 * （1）每一个步骤（在BPMN 2.0术语中称作活动(activity)）都有一个id属性。
		 * （2）活动之间通过顺序流(sequence flow)连接，在流程图中是一个有向箭头。在执行流程实例时，执行(execution)会从启动事件沿着顺序流流向下一个活动。
		 * （3）离开排他网关(带有X的菱形)的顺序流很特别：都以表达式(expression)的形式定义了条件(condition) 。
		 * 	   当流程实例的执行到达这个网关时，会计算条件，并使用第一个计算为true的顺序流。这就是排他的含义：只选择一个。
		 *     当然如果需要不同的路由策略，可以使用其他类型的网关。
		 * （3--） 计算条件会有表达式，表达式中的变量，称为流程变量(process variable)。流程变量是持久化的数据，与流程实例存储在一起，并可以在流程实例的生命周期中使用。
		 *     该变量需要在特定的地方（当经理用户任务提交时）设置这个流程变量，因为这不是流程实例启动时就能获取的数据。
		 *     
		 * （4）有了流程定义XML文件，下来需要将它部署(deploy)到引擎中，部署一个流程定义意味着：
		 * 		4-1 流程引擎会将XML文件存储在数据库中，这样可以在需要的时候获取它。
		 *      4-2 流程定义转换为内部的、可执行的对象模型，这样使用它就可以启动流程实例。

		 */
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("holiday-request.bpmn20.xml")
				.deploy();
		// 可以通过API查询验证流程定义是否已经部署在引擎中。
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				  .deploymentId(deployment.getId())
				  .singleResult();
		LOGGER.info("\n=============================================================================");
		System.out.println("发现流程定义 : " + processDefinition.getId() + " <==> " + processDefinition.getName());
		
		/**
		 * 3. 启动流程实例。
		 * 	  启动流程实例，需要提供一些初始化流程变量。一般来说，可以通过呈现给用户的表单。
		 *    本例中：我们简化为使用java.util.Scanner类在命令行输入一些数据
		 * 
		 */
		// 模拟用户申请信息
		Scanner scanner = new Scanner(System.in);
		System.out.println("你是谁？");
		
		String employee = scanner.nextLine();
		System.out.println("你要请几天年假？");
		
		Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());
		System.out.println("你的请假原因是什么？");
		String description = scanner.nextLine();
		
		/**
		 *  使用RuntimeService启动一个流程实例。
		 *  收集的数据作为一个java.util.Map实例传递，其中的键就是之后用于获取变量的标识符。
		 *  这个流程实例使用key启动。这个key就是BPMN 2.0 XML文件中设置的id属性。
		 */
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		Map<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("employee", employee);
		varMap.put("nrOfHolidays", nrOfHolidays);
		varMap.put("description", description);
		// 启动流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
				.businessKey("100")
				.processDefinitionKey("holidayRequest")
				.variables(varMap)
				.start();
		
		// 在流程实例启动后，会创建一个执行(execution)，并将其放在启动事件上。
		// 本例：这个执行沿着顺序流移动到经理审批的用户任务，并执行用户任务行为。这个行为将在数据库中创建一个任务，该任务可以之后使用查询找到。
		//		用户任务是一个等待状态(wait state)，引擎会停止执行，返回API调用处。
		
		/**
		 * 4. 查询与完成任务
		 * 
		 * 	  实际应用中，会为雇员及经理提供用户界面，让他们可以登录并查看任务列表。其中可以看到作为流程变量存储的流程实例数据，并决定如何操作任务。
		 * 	  任务列表，通常由UI驱动的服务在后台调用API来获取的。
		 * 	  每个用户任务需要配置办理人（组），assignee、candidateGroups，可使用流程变量动态指派，在流程实例启动时传递。
		 * 
		 * 本例：将第一个任务指派给"经理(managers)"组，而第二个用户任务指派给请假申请的提交人${employee}
		 * 
		 */
		TaskService taskService = processEngine.getTaskService();
		// 获取经理(managers)组的待办任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
		System.out.println("你有" + tasks.size() + "个任务需要处理，待办任务：");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i+1) + ") " + tasks.get(i).getName());
		}
			
		// 模拟处理
		System.out.println("你想要处理那个任务？");
		int taskIndex = Integer.valueOf(scanner.nextLine());
		Task task = tasks.get(taskIndex - 1);
		// 通过任务Id，获取特定流程实例的变量，即申请人请假申请的信息
		Map<String, Object> variables = taskService.getVariables(task.getId());
		System.out.println("员工" + variables.get("employee") + "想要请" + variables.get("nrOfHolidays") + "天年假，请假原因 [" + variables.get("description") + "]。你同意吗（y/n）？");
		
		// 经理现在就可以完成任务了。现实中，通常意味着由用户提交一个表单。表单中的数据作为流程变量传递。
		// 本例：在完成任务时传递带有 ’approved’ 变量（变量名很重要，因为之后会在顺序流的条件中使用！）
		boolean approved = scanner.nextLine().toLowerCase().equals("y");
		Map<String, Object> approvalMap = new HashMap<>();
		approvalMap.put("approved", approved);
		approvalMap.put("approveDescription", approved ? "同意" : "不同意");
		// 经理审批任务完成
		taskService.complete(task.getId(), approvalMap);
		
		// 现在任务完成，并会在离开排他网关的两条路径中，基于’approved’流程变量选择一条。
		
		/**
		 * 5. 实现自动委托任务（JavaDelegate），即服务任务(service task)。
		 *    在现实中，这个逻辑可以做任何事情：向某个系统发起一个HTTP REST服务调用，或调用某个使用了好几十年的系统中的遗留代码。
		 * 	 
		 *    本例：服务任务是申请注册至某个外部系统 <serviceTask id="externalSystemCall" name="申请注册至某个外部系统" />
		 */
		
		// 审批通过后，模拟员工自动结束任务
		List<Task> task2s = taskService.createTaskQuery().taskAssignee(employee).list();
		if(!CollectionUtils.isEmpty(task2s)) {
			taskService.complete(task2s.get(0).getId());
		}
		
		scanner.close();
		
		/**
		 * 6. 使用历史数据
		 * 	  Flowable流程引擎，可以自动存储所有流程实例的审计数据或历史数据。
		 *    
		 *    本例：只选择一个特定流程实例的活动，只选择已完成的活动，结果按照结束时间排序，代表其执行顺序。
		 */
		/*
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstance.getId())
				.finished()
				.orderByHistoricActivityInstanceEndTime().asc()
				.list();
		for (HistoricActivityInstance activity : activities) {
		    System.out.println("[ " + activity.getActivityId() + " - " +  activity.getActivityName() + " ] 用时 " + activity.getDurationInMillis() + " 毫秒");
		}
		*/
		HistoricVariableInstance singleResult = processEngine.getHistoryService().createHistoricVariableInstanceQuery()
			.processInstanceId(processInstance.getId())
			.variableName("FINISHED").singleResult();
		if(singleResult != null) {
			System.out.println("流程是否结束: " + singleResult.getValue());
		}
		
		
		
	}
	
}
