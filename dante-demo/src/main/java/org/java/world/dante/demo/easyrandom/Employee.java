package org.java.world.dante.demo.easyrandom;

import java.util.Collection;
import java.util.Map;

public class Employee {
	private long id;
	private String firstName;
	private String lastName;
	private Collection<Employee> coworkers;
	private Map<YearQuarter, Grade> quarterGrades;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<Employee> getCoworkers() {
		return coworkers;
	}

	public void setCoworkers(Collection<Employee> coworkers) {
		this.coworkers = coworkers;
	}

	public Map<YearQuarter, Grade> getQuarterGrades() {
		return quarterGrades;
	}

	public void setQuarterGrades(Map<YearQuarter, Grade> quarterGrades) {
		this.quarterGrades = quarterGrades;
	}

}
