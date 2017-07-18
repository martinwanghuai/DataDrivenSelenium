package com.cgg.model;

import java.util.List;

import utility.Checker;

public class DataDictionary {

	private String nameDescription;
	private String order;
	private String fieldNameUsedInJSON;
	private String formatSaved;
	private String funnelCheckout;
	private String sampleDataRange;
	private String required;
	private String example;
	private String notes;
	private String CRMModules;
	private String CRMFieldName;
	private String CRMDataType;
	private String CRMNotes1;
	private String CRMNotes2;
	
	private String getString(List<String> src, int index){
		
		return index >= src.size() || Checker.isBlank(src.get(index)) ? "": src.get(index);
	}
	
	public DataDictionary(List<String> strs){
		
		this.nameDescription = this.getString(strs, 0);
		this.order = this.getString(strs, 1);
		this.fieldNameUsedInJSON = this.getString(strs, 2);
		this.formatSaved = this.getString(strs, 3);
		this.funnelCheckout = this.getString(strs, 4);
		this.sampleDataRange = this.getString(strs, 5);
		this.required = this.getString(strs, 6);
		this.example = this.getString(strs, 7);
		this.notes = this.getString(strs, 8);
		this.CRMModules = this.getString(strs, 9);
		this.CRMFieldName = this.getString(strs, 10);
		this.CRMDataType = this.getString(strs, 11);
		this.CRMNotes1 = this.getString(strs, 12);
		this.CRMNotes2 = this.getString(strs, 13);
	}
	
	public String getNameDescription() {
		return nameDescription;
	}
	public void setNameDescription(String nameDescription) {
		this.nameDescription = nameDescription;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFieldNameUsedInJSON() {
		return fieldNameUsedInJSON;
	}
	public void setFieldNameUsedInJSON(String fieldNameUsedInJSON) {
		this.fieldNameUsedInJSON = fieldNameUsedInJSON;
	}
	public String getFormatSaved() {
		return formatSaved;
	}
	public void setFormatSaved(String formatSaved) {
		this.formatSaved = formatSaved;
	}
	public String getFunnelCheckout() {
		return funnelCheckout;
	}
	public void setFunnelCheckout(String funnelCheckout) {
		this.funnelCheckout = funnelCheckout;
	}
	public String getSampleDataRange() {
		return sampleDataRange;
	}
	public void setSampleDataRange(String sampleDataRange) {
		this.sampleDataRange = sampleDataRange;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		required = required;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCRMModules() {
		return CRMModules;
	}
	public void setCRMModules(String cRMModules) {
		CRMModules = cRMModules;
	}
	public String getCRMFieldName() {
		return CRMFieldName;
	}
	public void setCRMFieldName(String cRMFieldName) {
		CRMFieldName = cRMFieldName;
	}
	public String getCRMDataType() {
		return CRMDataType;
	}
	public void setCRMDataType(String cRMDataType) {
		CRMDataType = cRMDataType;
	}
	public String getCRMNotes1() {
		return CRMNotes1;
	}
	public void setCRMNotes1(String cRMNotes1) {
		CRMNotes1 = cRMNotes1;
	}
	public String getCRMNotes2() {
		return CRMNotes2;
	}
	public void setCRMNotes2(String cRMNotes2) {
		CRMNotes2 = cRMNotes2;
	}

	@Override
	public String toString() {
		return "DataDictionary [nameDescription=" + nameDescription + ", order=" + order + ", fieldNameUsedInJSON="
				+ fieldNameUsedInJSON + ", formatSaved=" + formatSaved + ", funnelCheckout=" + funnelCheckout
				+ ", sampleDataRange=" + sampleDataRange + ", required=" + required + ", example=" + example
				+ ", notes=" + notes + ", CRMModules=" + CRMModules + ", CRMFieldName=" + CRMFieldName
				+ ", CRMDataType=" + CRMDataType + ", CRMNotes1=" + CRMNotes1 + ", CRMNotes2=" + CRMNotes2 + "]";
	}
}
