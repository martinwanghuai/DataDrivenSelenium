package com.cgg.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.testng.collections.Maps;

import com.cgg.model.FunnelConfig.Choice;

public class AppFormConfig {

	private Checkout checkout;
	
	public static class Checkout implements Serializable{
		
		private Config config;

		public Config getConfig() {
			return config;
		}

		public void setConfig(Config config) {
			this.config = config;
		}
	}
	
	public static class Config implements Serializable{
		
		private String verticalShortCode;
		private List<Route> routes;

		public List<Route> getRoutes() {
			return routes;
		}

		public void setRoutes(List<Route> routes) {
			this.routes = routes;
		}

		public String getVerticalShortCode() {
			return verticalShortCode;
		}

		public void setVerticalShortCode(String verticalShortCode) {
			this.verticalShortCode = verticalShortCode;
		}
	}
	
	public static class Route implements Serializable{
		
		private String name;
		private String route;
		private List<Component> components;
		private Map<String, Integer> typeCountMap = Maps.newHashMap();
		
		public void updateTypeCount(String type){
			if(typeCountMap.containsKey(type)){
				typeCountMap.put(type, typeCountMap.get(type)+1);
			}else{
				typeCountMap.put(type, 0);
			}
		}
		
		public void clearTypeCount(){
			typeCountMap.clear();
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRoute() {
			return route;
		}
		public void setRoute(String route) {
			this.route = route;
		}
		public List<Component> getComponents() {
			return components;
		}
		public void setComponents(List<Component> components) {
			this.components = components;
		}

		public Map<String, Integer> getTypeCountMap() {
			return typeCountMap;
		}

		public void setTypeCountMap(Map<String, Integer> typeCountMap) {
			this.typeCountMap = typeCountMap;
		}
	}
	
	public static class Component implements Serializable{
		
		private String type;
		private String className;
		private String ngModel;
		private String key;
		private Attributes attributes;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getNgModel() {
			return ngModel;
		}
		public void setNgModel(String ngModel) {
			this.ngModel = ngModel;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Attributes getAttributes() {
			return attributes;
		}
		public void setAttributes(Attributes attributes) {
			this.attributes = attributes;
		}
	}
	
	public static class Attributes implements Serializable{
		
		private Options options;

		public Options getOptions() {
			return options;
		}

		public void setOptions(Options options) {
			this.options = options;
		}
	}
	
	public static class Options implements Serializable{
		
		private String key;
		private String errorMessage;
		private String placeholder;
		private String mobilePlaceholder;
		private String textBefore;
		private List<Item> items;
		private List<String> strs;
		private String pattern;
		private Boolean showValidTick;
		private Integer boxSize;
		private String html;
		private List<Choice> choices;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public String getPlaceholder() {
			return placeholder;
		}
		public void setPlaceholder(String placeholder) {
			this.placeholder = placeholder;
		}
		public String getMobilePlaceholder() {
			return mobilePlaceholder;
		}
		public void setMobilePlaceholder(String mobilePlaceholder) {
			this.mobilePlaceholder = mobilePlaceholder;
		}
		public String getTextBefore() {
			return textBefore;
		}
		public void setTextBefore(String textBefore) {
			this.textBefore = textBefore;
		}
		public List<Item> getItems() {
			return items;
		}
		public void setItems(List<Item> items) {
			this.items = items;
		}
		public String getPattern() {
			return pattern;
		}
		public void setPattern(String pattern) {
			this.pattern = pattern;
		}
		public Boolean getShowValidTick() {
			return showValidTick;
		}
		public void setShowValidTick(Boolean showValidTick) {
			this.showValidTick = showValidTick;
		}
		public Integer getBoxSize() {
			return boxSize;
		}
		public void setBoxSize(Integer boxSize) {
			this.boxSize = boxSize;
		}
		public String getHtml() {
			return html;
		}
		public void setHtml(String html) {
			this.html = html;
		}
		public List<Choice> getChoices() {
			return choices;
		}
		public void setChoices(List<Choice> choices) {
			this.choices = choices;
		}
		public List<String> getStrs() {
			return strs;
		}
		public void setStrs(List<String> strs) {
			this.strs = strs;
		}
	}
	
	public static class Item implements Serializable{
		
		private String id;
		private String name;
		private String type;
		private String selectionType;
		private String text;
		private String className;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getSelectionType() {
			return selectionType;
		}
		public void setSelectionType(String selectionType) {
			this.selectionType = selectionType;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
	}

	public Checkout getCheckout() {
		return checkout;
	}

	public void setCheckout(Checkout checkout) {
		this.checkout = checkout;
	}
}
