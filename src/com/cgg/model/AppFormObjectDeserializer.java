package com.cgg.model;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.testng.collections.Lists;

import com.cgg.model.AppFormConfig.Attributes;
import com.cgg.model.AppFormConfig.Checkout;
import com.cgg.model.AppFormConfig.Component;
import com.cgg.model.AppFormConfig.Config;
import com.cgg.model.AppFormConfig.Options;
import com.cgg.model.AppFormConfig.Route;
import com.cgg.model.FunnelConfig.Choice;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class AppFormObjectDeserializer implements JsonDeserializer<AppFormConfig> {

	@Override
	public AppFormConfig deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		AppFormConfig obj = new AppFormConfig();
		final JsonObject config_json = json.getAsJsonObject().get("checkout").getAsJsonObject().get("config").getAsJsonObject();
		Config config = new Config();
		if(config_json.get("routes")!= null){
			config.setRoutes(this.deserializeRoutes(context, config_json));	
		}
		
		Checkout checkout = new Checkout();
		checkout.setConfig(config);
		obj.setCheckout(checkout);
		return obj;
	}
	
	public List<Route> deserializeRoutes(final JsonDeserializationContext context, JsonObject config_json){
		
		List<Route> routes = Lists.newArrayList();
		Iterator<JsonElement> ite = config_json.get("routes").getAsJsonArray().iterator();
		while(ite.hasNext()){
			JsonObject object = ite.next().getAsJsonObject();
			Route result = new Route();
			if( object.get("name") != null){
				result.setName(context.deserialize(object.get("name"), String.class));	
			}
			if( object.get("route") != null){
				result.setRoute(context.deserialize(object.get("route"), String.class));	
			}
			if( object.get("components") != null){
				result.setComponents(this.deserializeComponents(context, object));	
			}
			routes.add(result);
		}
		return routes;
	}
	
	public List<Component> deserializeComponents(final JsonDeserializationContext context, JsonObject config_json){
		
		List<Component> components = Lists.newArrayList();
		Iterator<JsonElement> ite = config_json.get("components").getAsJsonArray().iterator();
		while(ite.hasNext()){
			JsonObject object = ite.next().getAsJsonObject();
			Component result = new Component();
			if( object.get("type") != null){
				result.setType(context.deserialize(object.get("type"), String.class));	
			}
			if( object.get("className") != null){
				result.setClassName(context.deserialize(object.get("className"), String.class));	
			}
			if( object.get("ngModel") != null){
				result.setNgModel(context.deserialize(object.get("ngModel"), String.class));	
			}
			if( object.get("key") != null){
				result.setKey(context.deserialize(object.get("key"), String.class));	
			}
			if( object.get("attributes") != null){
				try{
					result.setAttributes(context.deserialize(object.get("attributes"), Attributes.class));	
				}catch(Exception e){
					result.setAttributes(this.deserializeAttributes(context, object));
				}
			}
			components.add(result);
		}
		return components;
	}
	
	public Attributes deserializeAttributes(final JsonDeserializationContext context, JsonObject config_json){
		
		Attributes attrs = new Attributes();
		Options option = new Options();
		
		JsonObject object = config_json.get("attributes").getAsJsonObject().get("options").getAsJsonObject();

		if (object.get("key") != null) {
			option.setKey(context.deserialize(object.get("key"), String.class));
		}

		if (object.get("errorMessage") != null) {
			option.setErrorMessage(context.deserialize(object.get("errorMessage"), String.class));
		}

		if (object.get("placeholder") != null) {
			option.setPlaceholder(context.deserialize(object.get("placeholder"), String.class));
		}

		if (object.get("mobilePlaceholder") != null) {
			option.setMobilePlaceholder(context.deserialize(object.get("mobilePlaceholder"), String.class));
		}

		if (object.get("textBefore") != null) {
			option.setTextBefore(context.deserialize(object.get("textBefore"), String.class));
		}

		if (object.get("items") != null) {
			option.setStrs(context.deserialize(object.get("items"), new TypeToken<List<String>>() {
			}.getType()));
		}

		if (object.get("pattern") != null) {
			option.setPattern(context.deserialize(object.get("pattern"), String.class));
		}

		if (object.get("showValidTick") != null) {
			option.setShowValidTick(context.deserialize(object.get("showValidTick"), String.class));
		}

		if (object.get("boxSize") != null) {
			option.setBoxSize(context.deserialize(object.get("boxSize"), String.class));
		}

		if (object.get("html") != null) {
			option.setHtml(context.deserialize(object.get("html"), String.class));
		}

		if (object.get("choices") != null) {
			option.setChoices(context.deserialize(object.get("html"), new TypeToken<List<Choice>>() {
			}.getType()));
		}
		
		attrs.setOptions(option);
		return attrs;
	}

}
