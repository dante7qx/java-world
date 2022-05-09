package com.epolleo.bp.menu.bean;

import java.io.Serializable;

/**
 * Date: 2013-3-26 下午2:57:55 Author: GuiLong Lai
 */
public class Win8Menu implements Serializable {
	private static final long serialVersionUID = 914138735229225944L;
	private String title;
	private String url;
	private String funcCode;
	private int height;
	private int width;
	private int cssHeight;
	private int cssWidth;
	private String menuInfo;
	private String menuKind;
	private String backColor;
	private String showIn;
	private String target;
	
	public Win8Menu(){
	}
	
	public Win8Menu(Win8Menu menu){
		this.title = menu.getTitle();
		this.url = menu.getUrl();
		this.funcCode = menu.getFuncCode();
		this.height = menu.getHeight();
		this.width = menu.getWidth();
		this.menuInfo = menu.getMenuInfo();
		this.menuKind = menu.getMenuKind();
		this.backColor = menu.getBackColor();
		this.cssHeight = menu.getCssHeight();
		this.cssWidth = menu.getCssWidth();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}

	public String getMenuKind() {
		return menuKind;
	}

	public void setMenuKind(String menuKind) {
		this.menuKind = menuKind;
	}

	public int getCssHeight() {
		return 8 * height + 2 * (height - 1);
	}

	public void setCssHeight() {
		this.cssHeight = 8 * height + 2 * (height - 1);
	}

	public int getCssWidth() {
		return 18 * width + 2 * (width - 1);
	}

	public void setCssWidth() {
		this.cssWidth = 18 * width + 2 * (width - 1);
	}

	public String getShowIn() {
		return showIn;
	}

	public void setShowIn(String showIn) {
		this.showIn = showIn;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
