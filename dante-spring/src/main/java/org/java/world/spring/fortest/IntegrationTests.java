package org.java.world.spring.fortest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@ActiveProfiles("prod")
public class IntegrationTests {

	@Autowired
	private UserService userService;

	@Test
	public void prodTest() {
		String expected = "from prod profile.";
		String actual = userService.getContent();

		Assert.assertEquals(expected, actual);
	}

}
