package com.dante.spi.impl;

import com.dante.spi.BookService;

public class CnBookServiceImpl implements BookService {

	@Override
	public void read() {
		System.out.println("我要读中文书！");

	}

}
