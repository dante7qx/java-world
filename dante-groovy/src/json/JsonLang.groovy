package json

import groovy.json.JsonSlurper
import groovy.json.JsonGenerator
import groovy.json.JsonOutput


class JsonLang {

	static void main(String[] args) {
		customOutput()
	}

	static def basicUse() {
		def jsonSlurper = new JsonSlurper()
		def object1 = jsonSlurper.parseText('{ "name": "John Doe" } /* some comment */')

		assert object1 instanceof Map
		assert object1.name == 'John Doe'
		println object1


		def object2 = jsonSlurper.parseText '''
	    { "simple": 123,
	      "fraction": 123.66,
	      "exponential": 123e12
	    }'''

		assert object2 instanceof Map
		assert object2.simple.class == Integer
		assert object2.fraction.class == BigDecimal
		assert object2.exponential.class == BigDecimal
		println object2

		def json = JsonOutput.toJson([name: 'John Doe', age: 42])
		assert json == '{"name":"John Doe","age":42}'
	}

	static def customOutput() {
		Person person = new Person(name: 'John', title: null, age: 21, password: 'secret',
		dob: Date.parse('yyyy-MM-dd', '1984-12-15'),
		favoriteUrl: new URL('http://groovy-lang.org/'))

		def generator = new JsonGenerator.Options()
				.excludeNulls()
				.dateFormat('yyyy@MM')
				.excludeFieldsByName('age', 'password')
				.excludeFieldsByType(URL)
				.build()
				
		assert generator.toJson(person) == '{"dob":"1984@12","name":"John"}'
		println generator.toJson(person)
	}
}
