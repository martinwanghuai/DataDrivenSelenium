package com.cgg.model;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import com.beust.jcommander.internal.Lists;
import com.cgg.model.PLJsonObject.Category;
import com.cgg.model.PLJsonObject.Fee;
import com.cgg.model.PLJsonObject.Interest;
import com.cgg.model.PLJsonObject.Locale;
import com.cgg.model.PLJsonObject.MinimumRequirement;
import com.cgg.model.PLJsonObject.MonthlyRebate;
import com.cgg.model.PLJsonObject.RelationshipInterest;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class PLJsonObjectDeserializer implements JsonDeserializer<PLJsonObjects> {

	@Override
	public PLJsonObjects deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {

		List<PLJsonObject> resultObjects = Lists.newArrayList();

		final JsonObject jsonObject = json.getAsJsonObject();
		final JsonElement products = jsonObject.get("products");

		Iterator<JsonElement> ite = products.getAsJsonArray().iterator();
		while (ite.hasNext()) {
			JsonObject it = ite.next().getAsJsonObject();
			PLJsonObject obj = new PLJsonObject();
			if(it.get("id") != null){
				obj.setId(context.deserialize(it.get("id"), String.class));
			}
			if(it.get("isActive") != null){
				obj.setActive(context.deserialize(it.get("isActive"), Boolean.class));	
			}
			if(it.get("showApplyButton") != null){
				obj.setShowApplyButton(context.deserialize(it.get("showApplyButton"), Boolean.class));	
			}
			if(it.get("sendLoanParameters") != null){
				obj.setSendLoanParameters(context.deserialize(it.get("sendLoanParameters"), Boolean.class));	
			}
			if(it.get("amountParameterName") != null){
				obj.setAmountParameterName(context.deserialize(it.get("amountParameterName"), String.class));	
			}
			if(it.get("tenureParameterName") != null){
				obj.setTenureParameterName(context.deserialize(it.get("tenureParameterName"), String.class));	
			}
			if(it.get("showDesktopApplyButton") != null){
				obj.setShowDesktopApplyButton(context.deserialize(it.get("showDesktopApplyButton"), Boolean.class));	
			}
			if(it.get("showMobileApplyButton") != null){
				obj.setShowMobileApplyButton(context.deserialize(it.get("showMobileApplyButton"), Boolean.class));	
			}
			if(it.get("showTabletApplyButton") != null){
				obj.setShowTabletApplyButton(context.deserialize(it.get("showTabletApplyButton"), Boolean.class));	
			}
			if (it.get("showApplyConfirmation") != null) {
				obj.setShowApplyConfirmation(context.deserialize(it.get("showApplyConfirmation"), Boolean.class));
			}
			if (it.get("showCTALeadCapture") != null) {
				obj.setShowCTALeadCapture(context.deserialize(it.get("showCTALeadCapture"), Boolean.class));
			}
			if(it.get("hasRelationshipWithProvider") != null){
				obj.setHasRelationshipWithProvider(
						context.deserialize(it.get("hasRelationshipWithProvider"), Boolean.class));	
			}
			if(it.get("bannerType") != null){
				obj.setBannerType(context.deserialize(it.get("bannerType"), String.class));	
			}
			if(it.get("bannerExpiryDate") != null){
				obj.setBannerExpiryDate(context.deserialize(it.get("bannerExpiryDate"), String.class));	
			}
			if(it.get("interestFrequencyUnit") != null){
				obj.setInterestFrequencyUnit(context.deserialize(it.get("interestFrequencyUnit"), String.class));	
			}
			if(it.get("promotionDiscount") != null){
				obj.setPromotionDiscount(context.deserialize(it.get("promotionDiscount"), String.class));	
			}
			if(it.get("categories") != null){
				obj.setCategories(context.deserialize(it.get("categories"), new TypeToken<List<Category>>() {
				}.getType()));	
			}
			if(it.get("purposeCategories") != null){
				obj.setPurposeCategories(context.deserialize(it.get("purposeCategories"), new TypeToken<List<Category>>() {
				}.getType()));	
			}
			if(it.get("interests") != null){
				obj.setInterests(context.deserialize(it.get("interests"), new TypeToken<List<Interest>>() {
				}.getType()));	
			}
			if(it.get("locales") != null){
				obj.setLocales(context.deserialize(it.get("locales"), new TypeToken<List<Locale>>() {
				}.getType()));	
			}
			if(it.get("minimumRequirements") != null){
				obj.setMinimumRequirements(
						context.deserialize(it.get("minimumRequirements"), new TypeToken<List<MinimumRequirement>>() {
						}.getType()));	
			}
			if(it.get("relationshipInterests") != null){
				obj.setRelationshipInterests(
						context.deserialize(it.get("relationshipInterests"), new TypeToken<List<RelationshipInterest>>() {
						}.getType()));	
			}
			if(it.get("fee") != null){
				obj.setFee(context.deserialize(it.get("fee"), Fee.class));	
			}
			if(it.get("calculateApr") != null){
				obj.setCalculateApr(context.deserialize(it.get("calculateApr"), Boolean.class));	
			}
			if(it.get("monthlyRebates") != null){
				obj.setMonthlyRebates(context.deserialize(it.get("monthlyRebates"), new TypeToken<List<MonthlyRebate>>() {
				}.getType()));	
			}
			if(it.get("providerId") != null){
				obj.setProviderId(context.deserialize(it.get("providerId"), String.class));	
			}
			if(it.get("lastUpdated") != null){
				obj.setLastUpdated(context.deserialize(it.get("lastUpdated"), String.class));	
			}
			resultObjects.add(obj);
		}

		final PLJsonObjects obj = new PLJsonObjects();
		obj.setJsonObjects(resultObjects);
		return obj;
	}
}
