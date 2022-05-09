package com.dante.annotation;

@Functional
public class FunctionalTest {

	@Functional(name = { "查询产品", "新增产品" }, value = { "garsp.product.query", "garsp.product.save" })
	public void savePerson() {
		System.out.println("Person save successfully.");
	}

	@Functional(name = { "查询产品", "更新产品" }, value = { "garsp.product.query", "garsp.product.update" })
	public void updatePerson() {
		System.out.println("Person update successfully.");
	}

	public static void main(String[] args) {
		FunctionalUtils.handleType(FunctionalTest.class);
		FunctionalUtils.handleMethod(FunctionalTest.class);

		// FunctionalTest test = new FunctionalTest();
		// test.savePerson();

	}
}
