package com.epolleo.bp.menu.bean;

/**
 * Date: 2013-3-28 上午11:07:54 Author: GuiLong Lai
 */
public class NoticeMenu extends Win8Menu {

	private static final long serialVersionUID = -3488604073079632507L;

	public NoticeMenu() {
	}

	public NoticeMenu(Win8Menu menu) {
		super(menu);
	}

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
