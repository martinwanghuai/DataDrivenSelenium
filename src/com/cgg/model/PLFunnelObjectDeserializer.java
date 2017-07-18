package com.cgg.model;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Layout;

import com.cgg.model.FunnelConfig.API;
import com.cgg.model.FunnelConfig.Choice;
import com.cgg.model.FunnelConfig.Config;
import com.cgg.model.FunnelConfig.Data;
import com.cgg.model.FunnelConfig.Funnel;
import com.cgg.model.FunnelConfig.Hint;
import com.cgg.model.FunnelConfig.Items;
import com.cgg.model.FunnelConfig.KeyTwoDefault;
import com.cgg.model.FunnelConfig.LeaveModal;
import com.cgg.model.FunnelConfig.Options;
import com.cgg.model.FunnelConfig.ProgressSetting;
import com.cgg.model.FunnelConfig.PromotionOptions;
import com.cgg.model.FunnelConfig.RequiredInfo;
import com.cgg.model.FunnelConfig.Spinner;
import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.model.FunnelConfig.Template;
import com.cgg.model.FunnelConfig.TemplateOption;
import com.cgg.model.FunnelConfig.TimeOptions;
import com.cgg.model.FunnelConfig.TimeUnitOptions;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class PLFunnelObjectDeserializer implements JsonDeserializer<FunnelConfig>{

	@Override
	public FunnelConfig deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {

		FunnelConfig obj = new FunnelConfig();
		
		final JsonObject config_json = json.getAsJsonObject().get("funnel").getAsJsonObject().get("config").getAsJsonObject();
		Config config = new Config();
		if( config_json.get("tagManagerStepNameList")!= null){
			config.setTagManagerStepNameList(context.deserialize(config_json.get("tagManagerStepNameList"), new TypeToken<List<String>>() {
			}.getType()));	
		}
		
		if( config_json.get("mobileLabel")!= null){
			config.setMobileLabel(context.deserialize(config_json.get("mobileLabel"), new TypeToken<List<String>>() {
			}.getType()));	
		}
		
		if( config_json.get("mobileSummaryLabel") != null){
			config.setMobileSummaryLabel(context.deserialize(config_json.get("mobileSummaryLabel"), String.class));	
		}
		
		if( config_json.get("tagManagerKeySubmited")!= null){
			config.setTagManagerKeySubmited(context.deserialize(config_json.get("tagManagerKeySubmited"), new TypeToken<List<String>>() {
			}.getType()));	
		}
		
		if( config_json.get("turnOnGoogleAnalytics") != null){
			config.setTurnOnGoogleAnalytics(context.deserialize(config_json.get("turnOnGoogleAnalytics"), Boolean.class));	
		}
		
		if( config_json.get("providerUrl") != null){
			config.setProviderUrl(context.deserialize(config_json.get("providerUrl"), String.class));	
		}
		
		if( config_json.get("destinationURLSetting")!= null){
			config.setDestinationURLSetting(context.deserialize(config_json.get("destinationURLSetting"), new TypeToken<List<String>>() {
			}.getType()));	
		}
		
		if( config_json.get("userCityIgnoreList")!= null){
			config.setUserCityIgnoreList(context.deserialize(config_json.get("userCityIgnoreList"), new TypeToken<List<String>>() {
			}.getType()));	
		}
		
		if( config_json.get("userCityServiceResultURL") != null){
			config.setUserCityServiceResultURL(context.deserialize(config_json.get("userCityServiceResultURL"), String.class));	
		}
		
		if( config_json.get("userCityServiceKey") != null){
			config.setUserCityServiceKey(context.deserialize(config_json.get("userCityServiceKey"), String.class));	
		}
		
		if( config_json.get("userCityServiceURL") != null){
			config.setUserCityServiceURL(context.deserialize(config_json.get("userCityServiceURL"), String.class));	
		}
		
		if( config_json.get("regionFormat") != null){
			config.setRegionFormat(context.deserialize(config_json.get("regionFormat"), String.class));	
		}
		
		if( config_json.get("country") != null){
			config.setCountry(context.deserialize(config_json.get("country"), String.class));	
		}
		
		if( config_json.get("vertical") != null){
			config.setVertical(context.deserialize(config_json.get("vertical"), String.class));	
		}
		
		if( config_json.get("verticalForDataTracking") != null){
			config.setVerticalForDataTracking(context.deserialize(config_json.get("verticalForDataTracking"), String.class));	
		}
		
		if( config_json.get("skipUrl") != null){
			config.setSkipUrl(context.deserialize(config_json.get("skipUrl"), String.class));	
		}
		
		if( config_json.get("hasSkipLink") != null){
			config.setHasSkipLink(context.deserialize(config_json.get("hasSkipLink"), Boolean.class));	
		}
		
		if( config_json.get("skipLinkText") != null){
			config.setSkipLinkText(context.deserialize(config_json.get("skipLinkText"), String.class));	
		}
		
		if( config_json.get("leftButtonText") != null){
			config.setLeftButtonText(context.deserialize(config_json.get("leftButtonText"), String.class));	
		}
		
		if( config_json.get("rightButtonText") != null){
			config.setRightButtonText(context.deserialize(config_json.get("leftButtonText"), String.class));	
		}
		
		if( config_json.get("lastStepRightButtonText") != null){
			config.setLastStepRightButtonText(context.deserialize(config_json.get("lastStepRightButtonText"), String.class));	
		}
		
		if( config_json.get("displayDisclaimer") != null){
			config.setDisplayDisclaimer(context.deserialize(config_json.get("displayDisclaimer"), Boolean.class));	
		}
		
		if( config_json.get("sendPostDataToUserCity") != null){
			config.setSendPostDataToUserCity(context.deserialize(config_json.get("sendPostDataToUserCity"), Boolean.class));	
		}
		
		if( config_json.get("disclaimerText") != null){
			config.setDisclaimerText(context.deserialize(config_json.get("disclaimerText"), String.class));	
		}
		
		if( config_json.get("tabletShowSummaryLabel") != null){
			config.setTabletShowSummaryLabel(context.deserialize(config_json.get("tabletShowSummaryLabel"), String.class));	
		}
		
		if( config_json.get("tabletHideSummaryLabel") != null){
			config.setTabletHideSummaryLabel(context.deserialize(config_json.get("tabletHideSummaryLabel"), String.class));	
		}
		
		if( config_json.get("progressSetting") != null){
			config.setProgressSetting(context.deserialize(config_json.get("progressSetting"), new TypeToken<List<ProgressSetting>>() {
			}.getType()));		
		}
		
		if( config_json.get("stepInfo") != null){
			config.setStepInfo(this.deserializeStepInfos(context, config_json));		
		}
		
		if( config_json.get("leaveModal") != null){
			config.setLeaveModal(context.deserialize(config_json.get("leaveModal"), LeaveModal.class));	
		}
		
		if( config_json.get("personalLoanInfoTitle") != null){
			config.setPersonalLoanInfoTitle(context.deserialize(config_json.get("personalLoanInfoTitle"), String.class));	
		}
		
		if( config_json.get("currency") != null){
			config.setCurrency(context.deserialize(config_json.get("currency"), String.class));	
		}
		
		if( config_json.get("promotionOptions") != null){
			config.setPromotionOptions(context.deserialize(config_json.get("promotionOptions"), PromotionOptions.class));	
		}
		Funnel funnel = new Funnel();
		funnel.setConfig(config);
		obj.setFunnel(funnel);
		
		return obj;
	}
	
	public List<StepInfo> deserializeStepInfos(final JsonDeserializationContext context, JsonObject config_json){
		
		List<StepInfo> steps = Lists.newArrayList();
		
		Iterator<JsonElement> ite = config_json.get("stepInfo").getAsJsonArray().iterator();
		while(ite.hasNext()){
			JsonObject object = ite.next().getAsJsonObject();
			StepInfo result = new StepInfo();
			if( object.get("label") != null){
				result.setLabel(context.deserialize(object.get("label"), String.class));	
			}
			if( object.get("link") != null){
				result.setLink(context.deserialize(object.get("link"), String.class));	
			}
			if( object.get("funnelKey") != null){
				result.setFunnelKey(context.deserialize(object.get("funnelKey"), String.class));	
			}
			if( object.get("currency") != null){
				result.setCurrency(context.deserialize(object.get("currency"), String.class));
			}
			if( object.get("statusBarValue") != null){
				result.setStatusBarValue(context.deserialize(object.get("statusBarValue"), String.class));	
			}
			
			if( object.get("requiredInfo") != null){
				result.setRequiredInfo(context.deserialize(object.get("requiredInfo"), RequiredInfo.class));	
			}
			
			if( object.get("templateOption") != null){
				result.setTemplateOption(deserializeTemplateOption(context, object));
			}	
			steps.add(result);
		}
		return steps;
		
	}
	
public TemplateOption deserializeTemplateOption(final JsonDeserializationContext context, JsonObject object) {
		
		TemplateOption option = new TemplateOption();
		JsonObject option_json = object.get("templateOption").getAsJsonObject();
		
		if(option_json.get("name") != null){
			option.setName(context.deserialize(object.get("name"), String.class));
		}
		
		if(option_json.get("template")!= null){
			option.setTemplate(this.deserializeTemplate(context, option_json));
		}
		
		return option;
	}

	public List<Template> deserializeTemplate(final JsonDeserializationContext context, JsonObject option_json) {
		
		List<Template> templates = Lists.newArrayList();
		Iterator<JsonElement> ite1 = option_json.get("template").getAsJsonArray().iterator();
		while(ite1.hasNext()){
			JsonObject template_json = ite1.next().getAsJsonObject();
			Template template = new Template();
			
			if(template_json.get("type") != null){
				template.setType(context.deserialize(template_json.get("type"), String.class));
			}
		
			if(template_json.get("data") instanceof JsonObject){
				template.setData(this.deserializeTemplateData(context, template_json));
			}
			templates.add(template);
		}
		
		return templates;
	}

	public Data deserializeTemplateData(final JsonDeserializationContext context, JsonObject template_json) {
		
		Data data = new Data();
		if(template_json.get("data") instanceof JsonObject){
			JsonObject data_json = template_json.get("data").getAsJsonObject();
			if(data_json.get("headline") != null){
				data.setHeadline(context.deserialize(data_json.get("headline"), String.class));
			}
			if(data_json.get("description") != null){
				data.setDescription(context.deserialize(data_json.get("description"), String.class));
			}
			if(data_json.get("label") != null){
				data.setLabel(context.deserialize(data_json.get("label"), String.class));
			}
			if((Object)data_json.get("hint") instanceof String){
				data.setHint(context.deserialize(data_json.get("hint"), String.class));
			}
			if(data_json.get("mobileHint") != null){
				data.setMobileHint(context.deserialize(data_json.get("mobileHint"), String.class));
			}
			if(data_json.get("iconFloat") != null){
				data.setIconFloat(context.deserialize(data_json.get("iconFloat"), String.class));
			}
			if(data_json.get("key") != null){
				data.setKey(context.deserialize(data_json.get("key"), String.class));
			}
			
			if(data_json.get("options") != null){
				data.setOptions(this.deserializeDataOptions(context, data_json));
			}
			if(data_json.get("hintLeft") != null){
				data.setHintLeft(context.deserialize(data_json.get("hintLeft"), Hint.class));
			}
			if(data_json.get("hintRight") != null){
				data.setHintRight(context.deserialize(data_json.get("hintRight"), Hint.class));
			}
			if(data_json.get("placeholder") != null){
				data.setPlaceholder(context.deserialize(data_json.get("placeholder"), String.class));
			}
			if(data_json.get("currency") != null){
				data.setCurrency(context.deserialize(data_json.get("currency"), String.class));
			}
			if(data_json.get("keyOneDefault") != null){
				data.setKeyOneDefault(context.deserialize(data_json.get("keyOneDefault"), String.class));
			}
			if(data_json.get("keyOneMinLength") != null){
				data.setKeyOneMinLength(context.deserialize(data_json.get("keyOneMinLength"), String.class));
			}
			if(data_json.get("keyOneMaxLength") != null){
				data.setKeyOneMaxLength(context.deserialize(data_json.get("keyOneMaxLength"), String.class));
			}
			if(data_json.get("keyOnePattern") != null){
				data.setKeyOnePattern(context.deserialize(data_json.get("keyOnePattern"), String.class));
			}
			
			JsonElement jsonElement = data_json.get("keyTwoDefault");
			if(jsonElement != null){
				if(jsonElement.isJsonObject()){
					data.setKeyTwoDefault(context.deserialize(jsonElement, KeyTwoDefault.class));	
				}else if(jsonElement.isJsonPrimitive()){
					data.setKeyTwoDefaut_str(context.deserialize(jsonElement, String.class));
				}
				
			}
			if(data_json.get("keyOne") != null){
				data.setKeyOne(context.deserialize(data_json.get("keyOne"), String.class));
			}
			if(data_json.get("keyTwo") != null){
				data.setKeyTwo(context.deserialize(data_json.get("keyTwo"), String.class));
			}
			if(data_json.get("keyTwoDropdown") != null){
				data.setKeyTwoDropdown(context.deserialize(data_json.get("keyTwoDropdown"), Boolean.class));
			}
			if(data_json.get("keyThree") != null){
				data.setKeyThree(context.deserialize(data_json.get("keyThree"), String.class));
			}
			if(data_json.get("regionFormat") != null){
				data.setRegionFormat(context.deserialize(data_json.get("regionFormat"), String.class));
			}
			if(data_json.get("displayRule") != null){
				data.setDisplayRule(context.deserialize(data_json.get("displayRule"), String.class));
			}
			if(data_json.get("timeOptions") != null){
				data.setTimeOptions(context.deserialize(data_json.get("timeOptions"), TimeOptions.class));
			}
			if(data_json.get("timeUnitOptions") != null){
				data.setTimeUnitOptions(context.deserialize(data_json.get("timeUnitOptions"), TimeUnitOptions.class));
			}
			if(data_json.get("inputUnit") != null){
				data.setInputUnit(context.deserialize(data_json.get("inputUnit"), String.class));
			}
			if(data_json.get("textAfter") != null){
				data.setTextAfter(context.deserialize(data_json.get("textAfter"), String.class));
			}
			if(data_json.get("minLength") != null){
				data.setMinLength(context.deserialize(data_json.get("minLength"), String.class));
			}
			if(data_json.get("maxLength") != null){
				data.setMaxLength(context.deserialize(data_json.get("maxLength"), String.class));
			}
			if(data_json.get("mobilePlaceholder") != null){
				data.setMobilePlaceholder(context.deserialize(data_json.get("mobilePlaceholder"), String.class));
			}
			if(data_json.get("keyDisplayName") != null){
				data.setKeyDisplayName(context.deserialize(data_json.get("keyDisplayName"), String.class));
			}
			if(data_json.get("countryCode") != null){
				data.setCountryCode(context.deserialize(data_json.get("countryCode"), String.class));
			}
			if(data_json.get("errorMessage") != null){
				data.setErrorMessage(context.deserialize(data_json.get("errorMessage"), String.class));
			}
			if(data_json.get("pattern") != null){
				data.setPattern(context.deserialize(data_json.get("pattern"), String.class));
			}
			if(data_json.get("choices") != null){
				data.setChoices(context.deserialize(data_json.get("choices"), new TypeToken<List<Choice>>() {
				}.getType()));
			}
		}
		
		return data;
	}
	
	public Options deserializeDataOptions(final JsonDeserializationContext context, JsonObject data_json) {
		
		Options options = new Options();
		JsonObject options_json = data_json.get("options").getAsJsonObject();
		if(options_json.get("showMoreMessage") != null){
			options.setShowMoreMessage(context.deserialize(options_json.get("showMoreMessage"), String.class));
		}
		if(options_json.get("layout") != null){
			options.setLayout(context.deserialize(options_json.get("layout"), new TypeToken<List<String>>() {
			}.getType()));
		}
		if(options_json.get("items") != null){
			options.setItems(context.deserialize(options_json.get("items"), new TypeToken<List<Items>>() {
			}.getType()));
		}
		if(options_json.get("maxShowPerPage") != null){
			options.setMaxShowPerPage(context.deserialize(options_json.get("maxShowPerPage"), Integer.class));
		}
		if(options_json.get("api") != null && options_json.get("api") instanceof JsonObject){
			options.setApi(context.deserialize(options_json.get("api"), API.class));
		}
		if(options_json.get("spinner") != null){
			options.setSpinner(context.deserialize(options_json.get("spinner"), Spinner.class));
		}
		return options;
	}

}
