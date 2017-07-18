package com.cgg.model;

import java.io.Serializable;
import java.util.List;

import com.cgg.model.PLJsonObject.Locale.EligibilityFeature;
import com.cgg.pl.category.AbstractAttributes;

public class PLAPIJsonObject <T extends AbstractAttributes> {

	private String approvalRate;
	private String name;
	private String lastUpdated;
	private String description;
	private String image;
	private String linkGeneralDesktop;
	private String linkGeneralMobile;
	private String linkGeneralTablet;
	private String linkFunnelDesktop;
	private String linkFunnelMobile;
	private String linkFunnelTablet;
	private String cggId;
	
	@Deprecated
	private Boolean hasApplyButton = false;
	
	private Boolean hasDesktopApplyButton = false;
	private Boolean hasMobileApplyButton = false;
	private Boolean hasTabletApplyButton = false;
	private List<String> footnotes;
	private Boolean showCTALeadCapture = false;
	private Boolean showApplyConfirmation = false;
	private String overlayType;
	private String overlayFunnelOnly;
	private String bestCardDesc;
	private Boolean sendLoanParameters = false;
	private String amountParameterName;
	private String tenureParameterName;
	private Provider provider;
	private List<CategoryType> productCategories;
	private List<CategoryType> purposeCategories;
	private List<String> features; // key features pro;
	private List<String> specifications; // key features cons;
	private Banner banner;
	private Fee fee;
	private LoanDetails loanDetails;
	private Others others;
	private T attributes;
	private List<EligibilityFeature> eligibilities; 
	private List<String> finePrints;
	private List<String> applyConfirmations; 
	
	public static class CategoryType implements Serializable{
		
		private String categoryType;

		public String getCategoryType() {
			return categoryType;
		}

		public void setCategoryType(String categoryType) {
			this.categoryType = categoryType;
		}
	}
	
	public static class Provider implements Serializable{
		
		private String cggId;
		private String name;
		private String image;
		private String link;
		private String linkMobile;
		private String description;
		private String providerDiscalimer;
		private String about;
		private String aboutShortDesc;
		private String terms;
		private String address;
		private String productDescription;
		
		public String getCggId() {
			return cggId;
		}
		public void setCggId(String cggId) {
			this.cggId = cggId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getLinkMobile() {
			return linkMobile;
		}
		public void setLinkMobile(String linkMobile) {
			this.linkMobile = linkMobile;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getProviderDiscalimer() {
			return providerDiscalimer;
		}
		public void setProviderDiscalimer(String providerDiscalimer) {
			this.providerDiscalimer = providerDiscalimer;
		}
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
		public String getAboutShortDesc() {
			return aboutShortDesc;
		}
		public void setAboutShortDesc(String aboutShortDesc) {
			this.aboutShortDesc = aboutShortDesc;
		}
		public String getTerms() {
			return terms;
		}
		public void setTerms(String terms) {
			this.terms = terms;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getProductDescription() {
			return productDescription;
		}
		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}
	}
	
	public static class Banner implements Serializable {
		
		private String bannerDesc;
		private String affiliateBannerDesc;
		private String bannerType;
		private String bannerExpiryDate;
		public String getBannerDesc() {
			return bannerDesc;
		}
		public void setBannerDesc(String bannerDesc) {
			this.bannerDesc = bannerDesc;
		}
		public String getAffiliateBannerDesc() {
			return affiliateBannerDesc;
		}
		public void setAffiliateBannerDesc(String affiliateBannerDesc) {
			this.affiliateBannerDesc = affiliateBannerDesc;
		}
		public String getBannerType() {
			return bannerType;
		}
		public void setBannerType(String bannerType) {
			this.bannerType = bannerType;
		}
		public String getBannerExpiryDate() {
			return bannerExpiryDate;
		}
		public void setBannerExpiryDate(String bannerExpiryDate) {
			this.bannerExpiryDate = bannerExpiryDate;
		}
	}
	
	public static class Fee implements Serializable {
		
		private List<FeeObject> aprDesc;
		private List<FeeObject> feeDesc;
		public List<FeeObject> getAprDesc() {
			return aprDesc;
		}
		public void setAprDesc(List<FeeObject> aprDesc) {
			this.aprDesc = aprDesc;
		}
		public List<FeeObject> getFeeDesc() {
			return feeDesc;
		}
		public void setFeeDesc(List<FeeObject> feeDesc) {
			this.feeDesc = feeDesc;
		}
	}
	
	public static class FeeObject implements Serializable {
		
		private String key;
		private String description;
		private String additionalInfo;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAdditionalInfo() {
			return additionalInfo;
		}
		public void setAdditionalInfo(String additionalInfo) {
			this.additionalInfo = additionalInfo;
		}
	}
	
	public static class LoanDetails implements Serializable {
		
		private Integer loanAmount;
		private Integer loanTenure;
		
		public Integer getLoanAmount() {
			return loanAmount;
		}
		public void setLoanAmount(Integer loanAmount) {
			this.loanAmount = loanAmount;
		}
		public Integer getLoanTenure() {
			return loanTenure;
		}
		public void setLoanTenure(Integer loanTenure) {
			this.loanTenure = loanTenure;
		}
	}
	
	public static class Others implements Serializable {
		
		private Boolean widgetHasOfferId;
		private Boolean widgetHasOfferLinkId;
		private String doubleClickDesktopAdId;
		private String doubleClickMobileAdId;
		private String doubleClickTabletAdId;
		
		public String getDoubleClickDesktopAdId() {
			return doubleClickDesktopAdId;
		}
		public void setDoubleClickDesktopAdId(String doubleClickDesktopAdId) {
			this.doubleClickDesktopAdId = doubleClickDesktopAdId;
		}
		public String getDoubleClickMobileAdId() {
			return doubleClickMobileAdId;
		}
		public void setDoubleClickMobileAdId(String doubleClickMobileAdId) {
			this.doubleClickMobileAdId = doubleClickMobileAdId;
		}
		public Boolean getWidgetHasOfferId() {
			return widgetHasOfferId;
		}
		public void setWidgetHasOfferId(Boolean widgetHasOfferId) {
			this.widgetHasOfferId = widgetHasOfferId;
		}
		public Boolean getWidgetHasOfferLinkId() {
			return widgetHasOfferLinkId;
		}
		public void setWidgetHasOfferLinkId(Boolean widgetHasOfferLinkId) {
			this.widgetHasOfferLinkId = widgetHasOfferLinkId;
		}
		public String getDoubleClickTabletAdId() {
			return doubleClickTabletAdId;
		}
		public void setDoubleClickTabletAdId(String doubleClickTabletAdId) {
			this.doubleClickTabletAdId = doubleClickTabletAdId;
		}
	}
	
	public static class Fineprints implements Serializable {
		
	}
	
	public static class ApplyConfirmations implements Serializable {
		
	}

	public String getApprovalRate() {
		return approvalRate;
	}

	public void setApprovalRate(String approvalRate) {
		this.approvalRate = approvalRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLinkGeneralDesktop() {
		return linkGeneralDesktop;
	}

	public void setLinkGeneralDesktop(String linkGeneralDesktop) {
		this.linkGeneralDesktop = linkGeneralDesktop;
	}

	public String getLinkGeneralMobile() {
		return linkGeneralMobile;
	}

	public void setLinkGeneralMobile(String linkGeneralMobile) {
		this.linkGeneralMobile = linkGeneralMobile;
	}

	public String getLinkFunnelDesktop() {
		return linkFunnelDesktop;
	}

	public void setLinkFunnelDesktop(String linkFunnelDesktop) {
		this.linkFunnelDesktop = linkFunnelDesktop;
	}

	public String getLinkFunnelMobile() {
		return linkFunnelMobile;
	}

	public void setLinkFunnelMobile(String linkFunnelMobile) {
		this.linkFunnelMobile = linkFunnelMobile;
	}

	public String getCggId() {
		return cggId;
	}

	public void setCggId(String cggId) {
		this.cggId = cggId;
	}

	public List<String> getFootnotes() {
		return footnotes;
	}

	public void setFootnotes(List<String> footnotes) {
		this.footnotes = footnotes;
	}

	public Boolean getShowCTALeadCapture() {
		return showCTALeadCapture;
	}

	public void setShowCTALeadCapture(Boolean showCTALeadCapture) {
		this.showCTALeadCapture = showCTALeadCapture;
	}

	public Boolean getShowApplyConfirmation() {
		return showApplyConfirmation;
	}

	public void setShowApplyConfirmation(Boolean showApplyConfirmation) {
		this.showApplyConfirmation = showApplyConfirmation;
	}

	public String getBestCardDesc() {
		return bestCardDesc;
	}

	public void setBestCardDesc(String bestCardDesc) {
		this.bestCardDesc = bestCardDesc;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}

	public List<String> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public LoanDetails getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}

	public Others getOthers() {
		return others;
	}

	public void setOthers(Others others) {
		this.others = others;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLinkGeneralTablet() {
		return linkGeneralTablet;
	}

	public void setLinkGeneralTablet(String linkGeneralTablet) {
		this.linkGeneralTablet = linkGeneralTablet;
	}

	public String getLinkFunnelTablet() {
		return linkFunnelTablet;
	}

	public void setLinkFunnelTablet(String linkFunnelTablet) {
		this.linkFunnelTablet = linkFunnelTablet;
	}

	public Boolean getHasDesktopApplyButton() {
		return hasDesktopApplyButton;
	}

	public void setHasDesktopApplyButton(Boolean hasDesktopApplyButton) {
		this.hasDesktopApplyButton = hasDesktopApplyButton;
	}

	public Boolean getHasMobileApplyButton() {
		return hasMobileApplyButton;
	}

	public void setHasMobileApplyButton(Boolean hasMobileApplyButton) {
		this.hasMobileApplyButton = hasMobileApplyButton;
	}

	public boolean isHasTabletApplyButton() {
		return hasTabletApplyButton;
	}

	public void setHasTabletApplyButton(boolean hasTabletApplyButton) {
		this.hasTabletApplyButton = hasTabletApplyButton;
	}

	public boolean isSendLoanParameters() {
		return sendLoanParameters;
	}

	public void setSendLoanParameters(boolean sendLoanParameters) {
		this.sendLoanParameters = sendLoanParameters;
	}

	public String getAmountParameterName() {
		return amountParameterName;
	}

	public void setAmountParameterName(String amountParameterName) {
		this.amountParameterName = amountParameterName;
	}

	public String getTenureParameterName() {
		return tenureParameterName;
	}

	public void setTenureParameterName(String tenureParameterName) {
		this.tenureParameterName = tenureParameterName;
	}

	public List<CategoryType> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<CategoryType> productCategories) {
		this.productCategories = productCategories;
	}

	public List<CategoryType> getPurposeCategories() {
		return purposeCategories;
	}

	public void setPurposeCategories(List<CategoryType> purposeCategories) {
		this.purposeCategories = purposeCategories;
	}

	public Boolean getHasTabletApplyButton() {
		return hasTabletApplyButton;
	}

	public void setHasTabletApplyButton(Boolean hasTabletApplyButton) {
		this.hasTabletApplyButton = hasTabletApplyButton;
	}

	public Boolean getSendLoanParameters() {
		return sendLoanParameters;
	}

	public void setSendLoanParameters(Boolean sendLoanParameters) {
		this.sendLoanParameters = sendLoanParameters;
	}

	public List<EligibilityFeature> getEligibilities() {
		return eligibilities;
	}

	public void setEligibilities(List<EligibilityFeature> eligibilities) {
		this.eligibilities = eligibilities;
	}

	public void setAttributes(T attributes) {
		this.attributes = attributes;
	}

	public T getAttributes() {
		return attributes;
	}

	public String getOverlayType() {
		return overlayType;
	}

	public void setOverlayType(String overlayType) {
		this.overlayType = overlayType;
	}

	public String getOverlayFunnelOnly() {
		return overlayFunnelOnly;
	}

	public void setOverlayFunnelOnly(String overlayFunnelOnly) {
		this.overlayFunnelOnly = overlayFunnelOnly;
	}

	public List<String> getFinePrints() {
		return finePrints;
	}

	public void setFinePrints(List<String> finePrints) {
		this.finePrints = finePrints;
	}

	public List<String> getApplyConfirmations() {
		return applyConfirmations;
	}

	public void setApplyConfirmations(List<String> applyConfirmations) {
		this.applyConfirmations = applyConfirmations;
	}

	public Boolean getHasApplyButton() {
		return hasApplyButton;
	}

	public void setHasApplyButton(Boolean hasApplyButton) {
		this.hasApplyButton = hasApplyButton;
	}
}
