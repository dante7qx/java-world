package org.dante.springmvc.web.converter.converter;

import org.dante.springmvc.web.converter.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConverterController {
	
	// http://localhost:8080/dante-springmvc/converter
	@RequestMapping(value = "/convert", produces="application/x-dante")
	public @ResponseBody Person convert(@RequestBody Person person) {
		return person;
	}
	
}
