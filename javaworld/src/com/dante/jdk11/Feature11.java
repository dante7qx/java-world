package com.dante.jdk11;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;


public class Feature11 {

	@Test
	public void testCollectionToArray() {
		Set<String> names = Set.of("Fred", "Wilma", "Barney", "Betty");
		String[] copy = new String[names.size()];
		names.toArray(copy);
		System.out.println(Arrays.toString(copy));
		System.out.println(Arrays.toString(names.toArray(String[]::new)));
	}

	@Test
	public void testStrip() {
		String text = "  \u2000a  b  ";
		assertEquals("a  b", text.strip());
		assertEquals("\u2000a  b", text.trim());
		assertEquals("a  b  ", text.stripLeading());
		assertEquals("  \u2000a  b", text.stripTrailing());
	}

}
