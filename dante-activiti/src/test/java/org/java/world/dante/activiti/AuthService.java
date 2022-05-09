package org.java.world.dante.activiti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务受理人
	 * 
	 * @return
	 */
	public String getAssigneeUser() {
		return "PK_AssigneeUser";
	}
	
	/**
	 * 任务候选人
	 * 
	 * @return
	 */
	public List<String> getCandidateUsers() {
		List<String> users = new ArrayList<>();
		users.add("PK_Candidate_User");
		return users;
	}
	
	/**
	 * 任务候选组
	 * 
	 * @return
	 */
	public List<String> getCandidateGroups() {
		List<String> groups = new ArrayList<>();
		groups.add("PK_Candidate_Group");
		return groups;
	}

}
