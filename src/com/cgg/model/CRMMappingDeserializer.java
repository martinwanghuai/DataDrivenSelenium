package com.cgg.model;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.testng.collections.Lists;

import com.cgg.model.CRMMapping.Applicants;
import com.cgg.model.CRMMapping.Applications;
import com.cgg.model.CRMMapping.BaseObject;
import com.cgg.model.CRMMapping.Leads;
import com.cgg.model.CRMMapping.Mapping;
import com.cgg.model.CRMMapping.Products;
import com.cgg.model.CRMMapping.Providers;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class CRMMappingDeserializer implements JsonDeserializer<CRMMapping> {

	@Override
	public CRMMapping deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		
		CRMMapping obj = new CRMMapping();
		final JsonObject config_json = json.getAsJsonObject();
		if( config_json.get("leads")!= null){
			obj.setLeads(new Leads(this.deserializeBaseObject(context, config_json.get("leads").getAsJsonObject())));
		}

		if( config_json.get("providers")!= null){
			obj.setProviders(new Providers(this.deserializeBaseObject(context, config_json.get("providers").getAsJsonObject())));
		}
		
		if( config_json.get("products")!= null){
			obj.setProducts(new Products(this.deserializeBaseObject(context, config_json.get("products").getAsJsonObject())));
		}
		
		if( config_json.get("applications")!= null){
			obj.setApplications(new Applications(this.deserializeBaseObject(context, config_json.get("applications").getAsJsonObject())));
		}
		
		if( config_json.get("applicants")!= null){
			obj.setApplicants(new Applicants(this.deserializeBaseObject(context, config_json.get("applicants").getAsJsonObject())));
		}
		return obj;
	}
	
	private BaseObject deserializeBaseObject(final JsonDeserializationContext context, JsonObject config_json){
		
		BaseObject result = new BaseObject(); 
		if(config_json.get("module")!= null){
			result.setModule(context.deserialize(config_json.get("module"), String.class));
		}
		/*if(config_json.get("relationship")!= null){
			result.setModule(context.deserialize(config_json.get("relationship"), Relationship.class));
		}*/
		if(config_json.get("mapping")!= null){
			List<Mapping> mappings = Lists.newArrayList();
			Iterator<JsonElement> ite = config_json.get("mapping").getAsJsonArray().iterator();
			while(ite.hasNext()){
				JsonArray objects = ite.next().getAsJsonArray();
				Mapping mapping=  new Mapping();
				mapping.setStrs(context.deserialize(objects, new TypeToken<List<String>>(){}.getType()));
				mappings.add(mapping);
			}
			result.setMappings(mappings);
		}
		return result;
	}
}
