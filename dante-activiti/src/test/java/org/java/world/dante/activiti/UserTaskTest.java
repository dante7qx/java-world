package org.java.world.dante.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTaskTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(UserTaskTest.class);
	
	@Test
	public void juelExpression() {
		repositoryService.createDeployment().addClasspathResource("diagrams/candidateByJuel.bpmn").deploy();
		identityService.deleteUser("PK_AssigneeUser");
		identityService.deleteUser("PK_Candidate_User");
		identityService.deleteGroup("PK_Candidate_Group");
		
		Group group = identityService.newGroup("PK_Candidate_Group");
		group.setName("PK_Candidate_Group");
		identityService.saveGroup(group);
		
		User user1 = identityService.newUser("PK_AssigneeUser");
		user1.setFirstName("PK_AssigneeUser");
		identityService.saveUser(user1);
		
		User user2 = identityService.newUser("PK_Candidate_User");
		user2.setFirstName("PK_Candidate_User");
		identityService.saveUser(user2);
		
		Map<String, Object> vars = new HashMap<>();
		vars.put("authService", new AuthService());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("candidateByJuelProcess", vars);
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("PK_AssigneeUser").list();
		tasks.forEach(t -> logger.info("{} 被指派任务: {}", t.getAssignee(), t.getName()));
		
		List<IdentityLink> links = taskService.getIdentityLinksForTask(task.getId());
		links.forEach(l -> logger.info("任务候选组 {}, 候选人 {}。", l.getGroupId(), l.getUserId()));
		
	}
	
}
