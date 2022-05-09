package org.java.world.dante.demo.easyrandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.TypePredicates;
import org.junit.Test;

/**
 * https://github.com/j-easy/easy-random/wiki
 * 
 * @author dante
 *
 */
public class EastRandomTests {
	
	@Test
	public void givenDefaultConfiguration_thenGenerateSingleObject() {
	    EasyRandom generator = new EasyRandom();
	    Person person = generator.nextObject(Person.class);
	    
	    System.out.println(person);
	    assertNotNull(person.getAge());
	    assertNotNull(person.getFirstName());
	    assertNotNull(person.getLastName());
	}
	
	@Test
	public void givenDefaultConfiguration_thenGenerateObjectsList() {
	    EasyRandom generator = new EasyRandom();
	    List<Person> persons = generator.objects(Person.class, 5)
	        .collect(Collectors.toList());
	    System.out.println(persons);
	    assertEquals(5, persons.size());
	}
	
	@Test
	public void givenCustomConfiguration_thenGenerateSingleEmployee() {
	    EasyRandomParameters parameters = new EasyRandomParameters();
	    parameters.stringLengthRange(3, 3);
	    parameters.collectionSizeRange(5, 5);
	    parameters.excludeField(FieldPredicates.named("lastName").and(FieldPredicates.inClass(Employee.class)));
	    parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));

	    EasyRandom generator = new EasyRandom(parameters);
	    Employee employee = generator.nextObject(Employee.class);

	    assertEquals(3, employee.getFirstName().length());
	    assertEquals(5, employee.getCoworkers().size());
	    assertEquals(5, employee.getQuarterGrades().size());

	    assertNull(employee.getLastName());
	}
}
