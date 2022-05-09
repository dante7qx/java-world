package com.dante.spi.impl;

import com.dante.spi.BookService;

public class EnBookServiceImpl implements BookService {

	@Override
	public void read() {
		System.out.println("I want to read English book！");
	}

}
