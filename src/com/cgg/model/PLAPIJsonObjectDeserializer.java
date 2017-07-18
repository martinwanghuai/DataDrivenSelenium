package com.cgg.model;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.testng.collections.Lists;

import com.cgg.model.PLAPIJsonObject.Banner;
import com.cgg.model.PLAPIJsonObject.CategoryType;
import com.cgg.model.PLAPIJsonObject.Fee;
import com.cgg.model.PLAPIJsonObject.LoanDetails;
import com.cgg.model.PLAPIJsonObject.Others;
import com.cgg.model.PLAPIJsonObject.Provider;
import com.cgg.model.PLJsonObject.Locale.EligibilityFeature;
import com.cgg.pl.category.Category;
import com.cgg.pl.category.PersonalLoan;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import utility.ReflectionUtils;

public class PLAPIJsonObjectDeserializer<T extends PersonalLoan> implements JsonDeserializer<PLAPIJsonObjects> {

	private Category category;
	private String country;
	
	public PLAPIJsonObjectDeserializer(final Category category, final String country){
		
		this.category = category;
		this.country = country;
	}
	
	private T fromJson(final JsonObject obj, final JsonDeserializationContext context) throws JsonParseException {
		
		PLAPIJsonObject result = new PLAPIJsonObject();
		if(obj.get("approvalRate") != null){
			result.setApprovalRate(context.deserialize(obj.get("approvalRate"), String.class));	
		}
		if(obj.get("name") != null){
			result.setName(context.deserialize(obj.get("name"), String.class));	
		}
		if(obj.get("lastUpdated") != null){
			result.setDescription(context.deserialize(obj.get("lastUpdated"), String.class));	
		}
		if(obj.get("description") != null){
			result.setDescription(context.deserialize(obj.get("description"), String.class));	
		}
		if(obj.get("image") != null){
			result.setImage(context.deserialize(obj.get("image"), String.class));	
		}
		if(obj.get("linkGeneralDesktop") != null){
			result.setLinkGeneralDesktop(context.deserialize(obj.get("linkGeneralDesktop"), String.class));	
		}
		if(obj.get("linkGeneralMobile") != null){
			result.setLinkGeneralMobile(context.deserialize(obj.get("linkGeneralMobile"), String.class));	
		}
		if(obj.get("linkGeneralTablet") != null){
			result.setLinkGeneralTablet(context.deserialize(obj.get("linkGeneralTablet"), String.class));	
		}
		if(obj.get("linkFunnelDesktop") != null){
			result.setLinkFunnelDesktop(context.deserialize(obj.get("linkFunnelDesktop"), String.class));	
		}
		if(obj.get("linkFunnelMobile") != null){
			result.setLinkFunnelMobile(context.deserialize(obj.get("linkFunnelMobile"), String.class));	
		}
		if(obj.get("linkFunnelTablet") != null){
			result.setLinkFunnelTablet(context.deserialize(obj.get("linkFunnelTablet"), String.class));	
		}
		if(obj.get("cggId") != null){
			result.setCggId(context.deserialize(obj.get("cggId"), String.class));	
		}
		if(obj.get("hasApplyButton") != null){
			result.setHasApplyButton(context.deserialize(obj.get("hasApplyButton"), Boolean.class));
		}
		if(obj.get("hasDesktopApplyButton") != null){
			result.setHasDesktopApplyButton(context.deserialize(obj.get("hasDesktopApplyButton"), Boolean.class));	
		}
		if(obj.get("hasMobileApplyButton") != null){
			result.setHasMobileApplyButton(context.deserialize(obj.get("hasMobileApplyButton"), Boolean.class));	
		}
		if(obj.get("hasTabletApplyButton") != null){
			result.setHasTabletApplyButton(context.deserialize(obj.get("hasTabletApplyButton"), Boolean.class));	
		}
		if(obj.get("footnote") != null){
			result.setFootnotes(context.deserialize(obj.get("footnote"), new TypeToken<List<String>>(){}.getType()));	
		}
		if(obj.get("showCTALeadCapture") != null){
			result.setShowCTALeadCapture(context.deserialize(obj.get("showCTALeadCapture"), Boolean.class));	
		}
		if(obj.get("showApplyConfirmation") != null){
			result.setShowApplyConfirmation(context.deserialize(obj.get("showApplyConfirmation"), Boolean.class));	
		}
		if(obj.get("overlayType") != null){
			result.setOverlayType(context.deserialize(obj.get("overlayType"), String.class));	
		}
		if(obj.get("overlayFunnelOnly") != null){
			result.setOverlayFunnelOnly(context.deserialize(obj.get("overlayFunnelOnly"), String.class));	
		}
		if(obj.get("bestCardDesc") != null){
			result.setBestCardDesc(context.deserialize(obj.get("bestCardDesc"), String.class));	
		}
		if(obj.get("sendLoanParameters") != null){
			result.setSendLoanParameters(context.deserialize(obj.get("sendLoanParameters"), Boolean.class));	
		}
		if(obj.get("amountParameterName") != null){
			result.setAmountParameterName(context.deserialize(obj.get("amountParameterName"), String.class));	
		}
		if(obj.get("tenureParameterName") != null){
			result.setTenureParameterName(context.deserialize(obj.get("tenureParameterName"), String.class));	
		}
		if(obj.get("provider") != null){
			result.setProvider(context.deserialize(obj.get("provider"), Provider.class));	
		}
		if(obj.get("productCategories") != null){
			result.setProductCategories(context.deserialize(obj.get("productCategories"), new TypeToken<List<CategoryType>>(){}.getType()));	
		}
		if(obj.get("purposeCategories") != null){
			result.setPurposeCategories(context.deserialize(obj.get("purposeCategories"), new TypeToken<List<CategoryType>>(){}.getType()));	
		}
		if(obj.get("feature") != null){
			result.setFeatures(context.deserialize(obj.get("feature"), new TypeToken<List<String>>(){}.getType()));	
		}
		if(obj.get("specification") != null){
			result.setSpecifications(context.deserialize(obj.get("specification"), new TypeToken<List<String>>(){}.getType()));	
		}
		if(obj.get("banner") != null){
			result.setBanner(context.deserialize(obj.get("banner"), Banner.class));	
		}
		if(obj.get("fee") != null){
			result.setFee(context.deserialize(obj.get("fee"), Fee.class));	
		}
		if(obj.get("loandetails") != null){
			result.setLoanDetails(context.deserialize(obj.get("loandetails"), LoanDetails.class));	
		}
		if(obj.get("others") != null){
			result.setOthers(context.deserialize(obj.get("others"), Others.class));	
		}
		if(obj.get("attributes") != null){
			result.setAttributes(context.deserialize(obj.get("attributes"), this.getCategory().getAttributeClass()));
		}
		if(obj.get("eligibilityFeatures") != null){
			result.setEligibilities(context.deserialize(obj.get("eligibilityFeatures"), new TypeToken<List<EligibilityFeature>>(){}.getType()));	
		}else if(obj.get("eligibility") != null){
			result.setEligibilities(context.deserialize(obj.get("eligibility"), new TypeToken<List<EligibilityFeature>>(){}.getType()));	
		}
		
		if(obj.get("fineprints") != null){
			result.setFinePrints(context.deserialize(obj.get("fineprints"), new TypeToken<List<String>>(){}.getType()));	
		}
		if(obj.get("applyConfirmations") != null){
			result.setApplyConfirmations(context.deserialize(obj.get("applyConfirmations"), new TypeToken<List<String>>(){}.getType()));	
		}
		
		String clzName = "";
		if(result.getProductCategories()!= null && result.getProductCategories().size() > 0){
			Category categoryTemp = Category.fromJsonText(((CategoryType)result.getProductCategories().get(0)).getCategoryType(), country);
			if(categoryTemp == null){
				System.out.println("ID:" + result.getCggId() + " cannot map to any product category");
			}else{
				clzName = category.getClass().getPackage().getName() + "." + categoryTemp;	
			}
			
			if(result.getProductCategories().size() > 1){
				System.out.println("ID:" + result.getCggId() + " have more than 1 product category");
			}
		}else{
			clzName = category.getClass().getPackage().getName() + "." + category.name();
		}
		
		if(!ReflectionUtils.peekClass(clzName)){
			clzName = "com.cgg.pl.purposeCategory." + category.name();
		}
		Class<T> clazz = (Class<T>)ReflectionUtils.loadClass(clzName);
		return (T)ReflectionUtils.invokeConstructor(clazz, PLAPIJsonObject.class, result);
	}
	
	@Override
	public PLAPIJsonObjects deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		
		final PLAPIJsonObjects obj = new PLAPIJsonObjects();
		final JsonObject jsonObject = json.getAsJsonObject();

		//Featured products
		List<T> featuredProductList = Lists.newArrayList();
		final JsonElement featuredProducts = jsonObject.get("featuredProducts");
		Iterator<JsonElement> ite = featuredProducts.getAsJsonArray().iterator();
		while(ite.hasNext()){
			JsonObject it = ite.next().getAsJsonObject();
			T product = this.fromJson(it, context);
			product.setSponsoredProduct(true);
			featuredProductList.add(product);
		}
		obj.setFeaturedProductsForAPI(featuredProductList);
		
		//Non-Featured Products
		List<T> productList = Lists.newArrayList();
		final JsonElement products = jsonObject.get("products");
		Iterator<JsonElement> ite1 = products.getAsJsonArray().iterator();
		while(ite1.hasNext()){
			JsonObject it = ite1.next().getAsJsonObject();
			productList.add(this.fromJson(it, context));
		}
		obj.setProductsForAPI(productList);
		
		//Other properties
		if(jsonObject.get("timestamp") != null){
			obj.setTimestamp(context.deserialize(jsonObject.get("timestamp"), Long.class));	
		}
		if(jsonObject.get("totalFeaturedProducts") != null){
			obj.setTotalFeaturedProducts(context.deserialize(jsonObject.get("totalFeaturedProducts"), Integer.class));	
		}
		if(jsonObject.get("totalNonFeaturedProducts") != null){
			obj.setTotalNonFeaturedProducts(context.deserialize(jsonObject.get("totalNonFeaturedProducts"), Integer.class));	
		}
		if(jsonObject.get("totalRecords") != null){
			obj.setTotalRecords(context.deserialize(jsonObject.get("totalRecords"), Integer.class));	
		}
		if(jsonObject.get("dataFileLastUpdateDate") != null){
			obj.setDataFileLastUpdateDate(context.deserialize(jsonObject.get("dataFileLastUpdateDate"), String.class));	
		}
		
		return obj;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
