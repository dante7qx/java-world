package com.epolleo.bp.menu.bean;

import java.util.List;

/**
 * Date: 2013-3-28 上午11:05:56 Author: GuiLong Lai
 */
public class CallBackMenu extends Win8Menu {

	private static final long serialVersionUID = 2713429937469679595L;

	public CallBackMenu() {
	}

	public CallBackMenu(Win8Menu menu) {
		super(menu);
	}

	private List<Object> result;

	public List<Object> getResult() {
		return result;
	}

	public void setResult(List<Object> result) {
		this.result = result;
	}

}
