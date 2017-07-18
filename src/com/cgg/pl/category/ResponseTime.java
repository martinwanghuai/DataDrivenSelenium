package com.cgg.pl.category;

import java.io.Serializable;

public class ResponseTime {

	private String dayUnit;
	private Double days;
	private String hourUnit;
	private Double hours;
	private String minuteUnit;
	private Double minutes;

	public String getDayUnit() {
		return dayUnit;
	}

	public void setDayUnit(String dayUnit) {
		this.dayUnit = dayUnit;
	}

	public Double getDays() {
		return days;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public String getHourUnit() {
		return hourUnit;
	}

	public void setHourUnit(String hourUnit) {
		this.hourUnit = hourUnit;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public String getMinuteUnit() {
		return minuteUnit;
	}

	public void setMinuteUnit(String minuteUnit) {
		this.minuteUnit = minuteUnit;
	}

	public Double getMinutes() {
		return minutes;
	}

	public void setMinutes(Double minutes) {
		this.minutes = minutes;
	}
}
