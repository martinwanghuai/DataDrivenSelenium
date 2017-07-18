package com.cgg.model;

import java.util.List;

public class CarInsurances {

	private List<String> brand;
	private Integer count;
	private Long timestamp;
	private List<String> model;
	private List<String> year;
	private List<String> engine;
	private List<String> cars;
	private List<String> submodel;
	private List<String> transmission;
	
	public List<String> getBrand() {
		return brand;
	}
	public void setBrand(List<String> brand) {
		this.brand = brand;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public List<String> getModel() {
		return model;
	}
	public void setModel(List<String> model) {
		this.model = model;
	}
	public List<String> getYear() {
		return year;
	}
	public void setYear(List<String> year) {
		this.year = year;
	}
	public List<String> getEngine() {
		return engine;
	}
	public void setEngine(List<String> engine) {
		this.engine = engine;
	}
	public List<String> getCars() {
		return cars;
	}
	public void setCars(List<String> cars) {
		this.cars = cars;
	}
	public List<String> getSubmodel() {
		return submodel;
	}
	public void setSubmodel(List<String> submodel) {
		this.submodel = submodel;
	}
	public List<String> getTransmission() {
		return transmission;
	}
	public void setTransmission(List<String> transmission) {
		this.transmission = transmission;
	}
}
