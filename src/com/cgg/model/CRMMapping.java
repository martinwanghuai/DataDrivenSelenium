package com.cgg.model;

import java.io.Serializable;
import java.util.List;

public class CRMMapping {

	private Leads leads;
	private Providers providers;
	private Products products;
	private Applications applications;
	private Applicants applicants;
	
	public static class BaseObject implements Serializable{
		private String module;
		private List<Mapping> mappings;
		private Relationship relationship;
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public List<Mapping> getMappings() {
			return mappings;
		}
		public void setMappings(List<Mapping> mappings) {
			this.mappings = mappings;
		}
		public Relationship getRelationship() {
			return relationship;
		}
		public void setRelationship(Relationship relationship) {
			this.relationship = relationship;
		}
		
	}
	
	public static class Relationship extends BaseObject{
		private String providers;
		private String products;
		private String applications;
		private String leads;
		public String getProviders() {
			return providers;
		}
		public void setProviders(String providers) {
			this.providers = providers;
		}
		public String getProducts() {
			return products;
		}
		public void setProducts(String products) {
			this.products = products;
		}
		public String getApplications() {
			return applications;
		}
		public void setApplications(String applications) {
			this.applications = applications;
		}
		public String getLeads() {
			return leads;
		}
		public void setLeads(String leads) {
			this.leads = leads;
		}
	}
	
	public static class Mapping extends BaseObject{
		private List<String> strs;

		public List<String> getStrs() {
			return strs;
		}

		public void setStrs(List<String> strs) {
			this.strs = strs;
		}
	}
	
	public static class Leads extends BaseObject{
		
		public Leads(BaseObject obj){
			this.setModule(obj.getModule());
			this.setRelationship(obj.getRelationship());
			this.setMappings(obj.getMappings());
		}
	}
	
	public static class Providers extends BaseObject{
	
		public Providers(BaseObject obj){
			this.setModule(obj.getModule());
			this.setRelationship(obj.getRelationship());
			this.setMappings(obj.getMappings());
		}
	}
	
	public static class Products extends BaseObject{
		
		public Products(BaseObject obj){
			this.setModule(obj.getModule());
			this.setRelationship(obj.getRelationship());
			this.setMappings(obj.getMappings());
		}
	}
	
	public static class Applications extends BaseObject{
		
		public Applications(BaseObject obj){
			this.setModule(obj.getModule());
			this.setRelationship(obj.getRelationship());
			this.setMappings(obj.getMappings());
		}
		
	}
	
	
	public static class Applicants extends BaseObject{
		
		public Applicants(BaseObject obj){
			this.setModule(obj.getModule());
			this.setRelationship(obj.getRelationship());
			this.setMappings(obj.getMappings());
		}
	}

	public Leads getLeads() {
		return leads;
	}

	public void setLeads(Leads leads) {
		this.leads = leads;
	}

	public Providers getProviders() {
		return providers;
	}

	public void setProviders(Providers providers) {
		this.providers = providers;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Applications getApplications() {
		return applications;
	}

	public void setApplications(Applications applications) {
		this.applications = applications;
	}

	public Applicants getApplicants() {
		return applicants;
	}

	public void setApplicants(Applicants applicants) {
		this.applicants = applicants;
	}
}
