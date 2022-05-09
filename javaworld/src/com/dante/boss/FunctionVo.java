package com.dante.boss;

import java.util.ArrayList;
import java.util.List;

public class FunctionVo {
	private final static String BLANK = "　　";
	private String funcCode;
	private String funcName;
	private int level;
	private int index;
	private List<FunctionVo> childs;

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<FunctionVo> getChilds() {
		if(this.childs == null) {
			this.childs = new ArrayList<FunctionVo>();
		}
		return childs;
	}

	public String getExcelFuncName() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < level; i++) {
			builder.append(BLANK);
		}
		builder.append(funcName);
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return this.getFuncName() + " --> " + this.getLevel() + " --> " + this.getIndex();
	}

}
