package com.dante.juc;

public class CarJUC {
	int id;
	int manufacturerId;
	String model;
	int year;
	float rating;

	public CarJUC(int id, int manufacturerId, String model, int year) {
		this.id = id;
		this.manufacturerId = manufacturerId;
		this.model = model;
		this.year = year;
	}

	void setRating(float rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Car (id=" + id + ", manufacturerId=" + manufacturerId + ", model=" + model + ", year=" + year
				+ ", rating=" + rating;
	}
}
