package org.java.world.spring.condition;

public class MacListService implements ListService {

	@Override
	public String showListCmd() {
		return "ls";
	}

}
