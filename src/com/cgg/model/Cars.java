package com.cgg.model;

import java.io.Serializable;
import java.util.List;

public class Cars {

	private List<Car> cars;
	private Integer count;
	private Long timestamp;
	
	public static class Car implements Serializable {
		private String brand;
		private String model;
		private String year;
		private String engine;
		private String trim;
		private String nem_id;
		private String next_id;
		public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getEngine() {
			return engine;
		}
		public void setEngine(String engine) {
			this.engine = engine;
		}
		public String getTrim() {
			return trim;
		}
		public void setTrim(String trim) {
			this.trim = trim;
		}
		public String getNem_id() {
			return nem_id;
		}
		public void setNem_id(String nem_id) {
			this.nem_id = nem_id;
		}
		public String getNext_id() {
			return next_id;
		}
		public void setNext_id(String next_id) {
			this.next_id = next_id;
		}
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
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
}
