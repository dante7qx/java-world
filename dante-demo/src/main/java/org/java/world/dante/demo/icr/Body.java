package org.java.world.dante.demo.icr;

import java.util.List;

public class Body <T>{
	private String imageCount;
	private List<T> imageList;

	public String getImageCount() {
		return imageCount;
	}

	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}

	public List<T> getImageList() {
		return imageList;
	}

	public void setImageList(List<T> imageList) {
		this.imageList = imageList;
	}

}
