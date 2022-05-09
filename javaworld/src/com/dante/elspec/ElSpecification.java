package com.dante.elspec;

import javax.el.ELProcessor;

public class ElSpecification {

	public static void main(String[] args) {
		ELProcessor el = new ELProcessor();
		System.out.println(el.eval("Math.random()"));
		assert (el.eval("Math.random()") instanceof Double);
	}

}
