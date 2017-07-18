package com.cgg.model;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.testng.collections.Lists;

import com.cgg.pl.category.PersonalInstalment_HK;
import com.cgg.pl.category.PersonalLoan;
import com.cgg.pl.category.TermLoan_FI;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import utility.Checker;
import utility.MathUtils;

public class PLJsonObject {

	protected String id;
	protected boolean isActive;
	protected boolean showApplyButton;
	protected boolean sendLoanParameters;
	protected String amountParameterName;
	protected String tenureParameterName;
	protected boolean showDesktopApplyButton;
	protected boolean showMobileApplyButton;
	protected boolean showTabletApplyButton;
	protected boolean showCTALeadCapture;
	protected boolean showApplyConfirmation;
	protected boolean hasRelationshipWithProvider;
	protected String bannerType;
	protected String bannerExpiryDate;
	protected String interestFrequencyUnit;
	protected String promotionDiscount;
	protected List<Category> categories = Lists.newArrayList();
	protected List<Category> purposeCategories = Lists.newArrayList();
	protected List<Interest> interests = Lists.newArrayList();
	protected List<RelationshipInterest> relationshipInterests = Lists.newArrayList();
	protected List<Locale> locales = Lists.newArrayList();
	protected List<MinimumRequirement> minimumRequirements = Lists.newArrayList();
	protected Fee fee;
	protected String providerId;
	protected String lastUpdated;
	protected boolean calculateApr;
	protected List<MonthlyRebate> monthlyRebates;
	
	public static class MonthlyRebate implements Serializable{
	
		private double loanAmountMin;
		private double loanTenureMin;
		private List<MonthlyCashRebate> monthlyCashRebates;
		
		public double getLoanAmountMin() {
			return loanAmountMin;
		}
		public void setLoanAmountMin(double loanAmountMin) {
			this.loanAmountMin = loanAmountMin;
		}
		public double getLoanTenureMin() {
			return loanTenureMin;
		}
		public void setLoanTenureMin(double loanTenureMin) {
			this.loanTenureMin = loanTenureMin;
		}
		public List<MonthlyCashRebate> getMonthlyCashRebates() {
			return monthlyCashRebates;
		}
		public void setMonthlyCashRebates(List<MonthlyCashRebate> monthlyCashRebates) {
			this.monthlyCashRebates = monthlyCashRebates;
		}
	}
	
	public static class MonthlyCashRebate implements Serializable{
		
		private int monthIndex;
		private double cashRebate;
		public int getMonthIndex() {
			return monthIndex;
		}
		public void setMonthIndex(int monthIndex) {
			this.monthIndex = monthIndex;
		}
		public double getCashRebate() {
			return cashRebate;
		}
		public void setCashRebate(double cashRebate) {
			this.cashRebate = cashRebate;
		}
	}
	
	public static class RelationshipInterest implements Serializable{
		
		private double loanAmountMax;
		private double loanAmountMin;
		private double loanTenureMin;
		private double loanTenureMax;
		private double interestValue;
		private double apr;
		
		public double getLoanAmountMax() {
			return loanAmountMax;
		}
		public void setLoanAmountMax(double loanAmountMax) {
			this.loanAmountMax = loanAmountMax;
		}
		public double getLoanAmountMin() {
			return loanAmountMin;
		}
		public void setLoanAmountMin(double loanAmountMin) {
			this.loanAmountMin = loanAmountMin;
		}
		public double getLoanTenureMin() {
			return loanTenureMin;
		}
		public void setLoanTenureMin(double loanTenureMin) {
			this.loanTenureMin = loanTenureMin;
		}
		public double getLoanTenureMax() {
			return loanTenureMax;
		}
		public void setLoanTenureMax(double loanTenureMax) {
			this.loanTenureMax = loanTenureMax;
		}
		public double getInterestValue() {
			return interestValue;
		}
		public void setInterestValue(double interestValue) {
			this.interestValue = interestValue;
		}
		public double getApr() {
			return apr;
		}
		public void setApr(double apr) {
			this.apr = apr;
		}
	}
	
	public static class Category implements Serializable{
		
		boolean isFeatured;
		private String featuredRank;
		private String categoryType;
		public boolean isFeatured() {
			return isFeatured;
		}
		public void setFeatured(boolean isFeatured) {
			this.isFeatured = isFeatured;
		}
		public String getFeaturedRank() {
			return featuredRank;
		}
		public void setFeaturedRank(String featuredRank) {
			this.featuredRank = featuredRank;
		}
		public String getCategoryType() {
			return categoryType;
		}
		public void setCategoryType(String categoryType) {
			this.categoryType = categoryType;
		}
		
	}
	
	public static class Interest implements Serializable{
		
		private double loanAmountMax;
		private double loanAmountMin;
		private double loanTenureMin;
		private double loanTenureMax;
		private double interestValue;
		private String interestValueMax;
		private double apr;
		private String quickLoanHandling;
		private String quickLoanPayment;
		private String quickLoanTotal;
		private double processingFee;
		private double fixedRate;
		
		@JsonCreator
		public Interest(@JsonProperty("loanAmountMax") double loanAmountMax,
				@JsonProperty("loanAmountMin") double loanAmountMin, @JsonProperty("loanTenureMin") double loanTenureMin,
				@JsonProperty("loanTenureMax") double loanTenureMax, @JsonProperty("fixedRate") double fixedRate) {
			this.loanAmountMax = loanAmountMax;
			this.loanAmountMin = loanAmountMin;
			this.loanTenureMin = loanTenureMin;
			this.loanTenureMax = loanTenureMax;
			this.fixedRate = fixedRate;
		}
		
		public double getLoanAmountMax() {
			return loanAmountMax;
		}
		public void setLoanAmountMax(double loanAmountMax) {
			this.loanAmountMax = loanAmountMax;
		}
		public double getLoanAmountMin() {
			return loanAmountMin;
		}
		public void setLoanAmountMin(double loanAmountMin) {
			this.loanAmountMin = loanAmountMin;
		}
		public double getLoanTenureMin() {
			return loanTenureMin;
		}
		public void setLoanTenureMin(double loanTenureMin) {
			this.loanTenureMin = loanTenureMin;
		}
		public double getLoanTenureMax() {
			return loanTenureMax;
		}
		public void setLoanTenureMax(double loanTenureMax) {
			this.loanTenureMax = loanTenureMax;
		}
		public double getInterestValue() {
			return interestValue;
		}
		public void setInterestValue(double interestValue) {
			this.interestValue = interestValue;
		}
		public double getApr() {
			return apr;
		}
		public void setApr(double apr) {
			this.apr = apr;
		}
		public String getQuickLoanHandling() {
			return quickLoanHandling;
		}
		public void setQuickLoanHandling(String quickLoanHandling) {
			this.quickLoanHandling = quickLoanHandling;
		}
		public String getInterestValueMax() {
			return interestValueMax;
		}
		public void setInterestValueMax(String interestValueMax) {
			this.interestValueMax = interestValueMax;
		}
		public String getQuickLoanPayment() {
			return quickLoanPayment;
		}
		public void setQuickLoanPayment(String quickLoanPayment) {
			this.quickLoanPayment = quickLoanPayment;
		}
		public String getQuickLoanTotal() {
			return quickLoanTotal;
		}
		public void setQuickLoanTotal(String quickLoanTotal) {
			this.quickLoanTotal = quickLoanTotal;
		}
		public double getProcessingFee() {
			return processingFee;
		}
		public void setProcessingFee(double processingFee) {
			this.processingFee = processingFee;
		}

		public double getFixedRate() {
			return fixedRate;
		}

		public void setFixedRate(double fixedRate) {
			this.fixedRate = fixedRate;
		}
	}
	
	public static class Locale implements Serializable{
		
		private String localeName;
		private String loanName;
		private String productName;
		private String loanDescription;
		private String loanImage;
		private String loanLink;
		private String funnelLoanLink;
		private String loanLinkMobile;
		private String funnelLoanLinkMobile;
		private String bannerDesc;
		private String affiliateBannerDesc;
		private String footerDesc;
		private String bannerIcon;
		private String widgetHasOfferId;
		private String widgetHasOfferLinkId;
		private String bestDesc;
		private String doubleClickDesktopAdId;
		private String doubleClickMobileAdId;
		private String featuredText;
		private List<EligibilityFeature> eligibilityFeatures = Lists.newArrayList();
		private List<KeyFeature> keyFeatures = Lists.newArrayList();
		private List<Attribute> attributes = Lists.newArrayList();
		private List<LocaleApr> aprs = Lists.newArrayList();
		private List<LocaleFee> fees = Lists.newArrayList();
		private ResponseTime responseTime;

		public static class ResponseTime implements Serializable{
			
			private String minutes;
			private String minuteUnit;
			private String hours;
			private String hourUnit;
			private String days;
			private String dayUnit;
			
			public ResponseTime(String minutes, String minuteUnit, String hours, String hourUnit, String days,
					String dayUnit) {
				super();
				this.minutes = minutes;
				this.minuteUnit = minuteUnit;
				this.hours = hours;
				this.hourUnit = hourUnit;
				this.days = days;
				this.dayUnit = dayUnit;
			}
			
			public String getMinutes() {
				return minutes;
			}
			public void setMinutes(String minutes) {
				this.minutes = minutes;
			}
			public String getMinuteUnit() {
				return minuteUnit;
			}
			public void setMinuteUnit(String minuteUnit) {
				this.minuteUnit = minuteUnit;
			}
			public String getHours() {
				return hours;
			}
			public void setHours(String hours) {
				this.hours = hours;
			}
			public String getHourUnit() {
				return hourUnit;
			}
			public void setHourUnit(String hourUnit) {
				this.hourUnit = hourUnit;
			}
			public String getDays() {
				return days;
			}
			public void setDays(String days) {
				this.days = days;
			}
			public String getDayUnit() {
				return dayUnit;
			}
			public void setDayUnit(String dayUnit) {
				this.dayUnit = dayUnit;
			}
		}

		public static class EligibilityFeature implements Serializable{
			private String key;
			private String description;
			private String additionalInfo;
			
			public EligibilityFeature(String key, String description){
				
				this.key = key;
				this.description = description;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((description == null) ? 0 : description.hashCode());
				result = prime * result + ((key == null) ? 0 : key.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) {
					return true;
				}
				if (obj == null) {
					return false;
				}
				if (!(obj instanceof EligibilityFeature)) {
					return false;
				}
				EligibilityFeature other = (EligibilityFeature) obj;
				if (description == null) {
					if (other.description != null) {
						return false;
					}
				} else if (!description.equals(other.description)) {
					return false;
				}
				if (key == null) {
					if (other.key != null) {
						return false;
					}
				} else if (!key.equals(other.key)) {
					return false;
				}
				return true;
			}

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
		
		public static class KeyFeature implements Serializable{
			String description;
			private String isNegative;
			public String getIsNegative() {
				return isNegative;
			}
			public void setIsNegative(String isNegative) {
				this.isNegative = isNegative;
			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
		}
		
		public static class Attribute implements Serializable{
			private String description;
			private String isNegative;
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
		}

		public static class LocaleApr implements Serializable{
			String description;

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}
		}
		
		public static class LocaleFee implements Serializable{
			String description;

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}
			
		}

		public String getLocaleName() {
			return localeName;
		}

		public void setLocaleName(String localeName) {
			this.localeName = localeName;
		}

		public String getLoanName() {
			return loanName;
		}

		public void setLoanName(String loanName) {
			this.loanName = loanName;
		}

		public String getLoanDescription() {
			return loanDescription;
		}

		public void setLoanDescription(String loanDescription) {
			this.loanDescription = loanDescription;
		}

		public String getLoanImage() {
			return loanImage;
		}

		public void setLoanImage(String loanImage) {
			this.loanImage = loanImage;
		}

		public String getLoanLink() {
			return loanLink;
		}

		public void setLoanLink(String loanLink) {
			this.loanLink = loanLink;
		}

		public String getFunnelLoanLink() {
			return funnelLoanLink;
		}

		public void setFunnelLoanLink(String funnelLoanLink) {
			this.funnelLoanLink = funnelLoanLink;
		}

		public String getLoanLinkMobile() {
			return loanLinkMobile;
		}

		public void setLoanLinkMobile(String loanLinkMobile) {
			this.loanLinkMobile = loanLinkMobile;
		}

		public String getFunnelLoanLinkMobile() {
			return funnelLoanLinkMobile;
		}

		public void setFunnelLoanLinkMobile(String funnelLoanLinkMobile) {
			this.funnelLoanLinkMobile = funnelLoanLinkMobile;
		}

		public String getBannerDesc() {
			return bannerDesc;
		}

		public void setBannerDesc(String bannerDesc) {
			this.bannerDesc = bannerDesc;
		}

		public String getFooterDesc() {
			return footerDesc;
		}

		public void setFooterDesc(String footerDesc) {
			this.footerDesc = footerDesc;
		}

		public String getBannerIcon() {
			return bannerIcon;
		}

		public void setBannerIcon(String bannerIcon) {
			this.bannerIcon = bannerIcon;
		}

		public String getWidgetHasOfferId() {
			return widgetHasOfferId;
		}

		public void setWidgetHasOfferId(String widgetHasOfferId) {
			this.widgetHasOfferId = widgetHasOfferId;
		}

		public String getWidgetHasOfferLinkId() {
			return widgetHasOfferLinkId;
		}

		public void setWidgetHasOfferLinkId(String widgetHasOfferLinkId) {
			this.widgetHasOfferLinkId = widgetHasOfferLinkId;
		}

		public String getBestDesc() {
			return bestDesc;
		}

		public void setBestDesc(String bestDesc) {
			this.bestDesc = bestDesc;
		}


		public List<KeyFeature> getKeyFeatures() {
			return keyFeatures;
		}

		public void setKeyFeatures(List<KeyFeature> keyFeatures) {
			this.keyFeatures = keyFeatures;
		}

		public List<LocaleApr> getAprs() {
			return aprs;
		}

		public void setAprs(List<LocaleApr> aprs) {
			this.aprs = aprs;
		}

		public List<LocaleFee> getFees() {
			return fees;
		}

		public void setFees(List<LocaleFee> fees) {
			this.fees = fees;
		}

		public List<EligibilityFeature> getEligibilityFeatures() {
			return eligibilityFeatures;
		}

		public void setEligibilityFeatures(List<EligibilityFeature> eligibilityFeatures) {
			this.eligibilityFeatures = eligibilityFeatures;
		}

		public String getAffiliateBannerDesc() {
			return affiliateBannerDesc;
		}

		public void setAffiliateBannerDesc(String affiliateBannerDesc) {
			this.affiliateBannerDesc = affiliateBannerDesc;
		}

		public String getDoubleClickMobileAdId() {
			return doubleClickMobileAdId;
		}

		public void setDoubleClickMobileAdId(String doubleClickMobileAdId) {
			this.doubleClickMobileAdId = doubleClickMobileAdId;
		}

		public String getFeaturedText() {
			return featuredText;
		}

		public void setFeaturedText(String featuredText) {
			this.featuredText = featuredText;
		}

		public String getDoubleClickDesktopAdId() {
			return doubleClickDesktopAdId;
		}

		public void setDoubleClickDesktopAdId(String doubleClickDesktopAdId) {
			this.doubleClickDesktopAdId = doubleClickDesktopAdId;
		}

		public List<Attribute> getAttributes() {
			return attributes;
		}

		public void setAttributes(List<Attribute> attributes) {
			this.attributes = attributes;
		}

		public ResponseTime getResponseTime() {
			return responseTime;
		}

		public void setResponseTime(ResponseTime responseTime) {
			this.responseTime = responseTime;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
	}
	
	public static class MinimumRequirement implements Serializable{
		
		private String requirementType;
		private String value;
		private String valueType;
		private String operator;
		public String getRequirementType() {
			return requirementType;
		}
		public void setRequirementType(String requirementType) {
			this.requirementType = requirementType;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getValueType() {
			return valueType;
		}
		public void setValueType(String valueType) {
			this.valueType = valueType;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
	}
	
	public static final class StartUpFee {
		public final long value;

		@JsonCreator
		public StartUpFee(@JsonProperty("value") long value) {
			this.value = value;
		}
	}
	
	public static class Fee implements Serializable{
		
		private double annualHandlingFee;
		private String annualHandlingFeePercent;
		private double maxAnnualHandlingFee;
		private double fixedHandlingFee;
		private String handlingFeeProfile;
		private double startUpFee;
		private String startUpFeePercent;
		private String minStartUpFee;
		private double maxStartUpFee;
		private String startUpFeeDeductedOfLoan;
		private String installmentMonthlyFee;
		private boolean hasInstallmentMonthlyFee;
		private String maxInstallmentMonthlyFee;
		private double annualRate;
		private double minMonthlyRepayment;
		private double maxMonthlyRepayment;
		private String minMonthlyRepaymentPercent;
		private String monthlyRepaymentPercent;
		private String eir;
		private double transferRate;
		private String interestProfile;
		private String computationLogic;
		private boolean requiresLifeInsurance;
		private String quickLoanLowestApr;
		private String quickLoanLowestAprDetails;
		private String quickLoanMinPaymentTenure;
		private String quickLoanMinPaymentTotal;
		private double monthlyFee;
		private StartUpFee startUpFees[];
		
		public Fee(@JsonProperty("monthlyFee") double monthlyFee,
				@JsonProperty("startUpFees") StartUpFee[] startUpFees) {
			this.monthlyFee = monthlyFee;
			this.startUpFees = startUpFees;
		}
		
		public double getAnnualHandlingFee() {
			return annualHandlingFee;
		}
		public void setAnnualHandlingFee(double annualHandlingFee) {
			this.annualHandlingFee = annualHandlingFee;
		}
		public String getAnnualHandlingFeePercent() {
			return annualHandlingFeePercent;
		}
		public void setAnnualHandlingFeePercent(String annualHandlingFeePercent) {
			this.annualHandlingFeePercent = annualHandlingFeePercent;
		}
		public String getHandlingFeeProfile() {
			return handlingFeeProfile;
		}
		public void setHandlingFeeProfile(String handlingFeeProfile) {
			this.handlingFeeProfile = handlingFeeProfile;
		}
		public double getStartUpFee() {
			return startUpFee;
		}
		public void setStartUpFee(double startUpFee) {
			this.startUpFee = startUpFee;
		}
		public String getStartUpFeePercent() {
			return startUpFeePercent;
		}
		public void setStartUpFeePercent(String startUpFeePercent) {
			this.startUpFeePercent = startUpFeePercent;
		}
		public String getStartUpFeeDeductedOfLoan() {
			return startUpFeeDeductedOfLoan;
		}
		public void setStartUpFeeDeductedOfLoan(String startUpFeeDeductedOfLoan) {
			this.startUpFeeDeductedOfLoan = startUpFeeDeductedOfLoan;
		}
		public String getInstallmentMonthlyFee() {
			return installmentMonthlyFee;
		}
		public void setInstallmentMonthlyFee(String installmentMonthlyFee) {
			this.installmentMonthlyFee = installmentMonthlyFee;
		}
		public double getAnnualRate() {
			return annualRate;
		}
		public void setAnnualRate(double annualRate) {
			this.annualRate = annualRate;
		}
		public double getMinMonthlyRepayment() {
			return minMonthlyRepayment;
		}
		public void setMinMonthlyRepayment(double minMonthlyRepayment) {
			this.minMonthlyRepayment = minMonthlyRepayment;
		}
		public double getMaxMonthlyRepayment() {
			return maxMonthlyRepayment;
		}
		public void setMaxMonthlyRepayment(double maxMonthlyRepayment) {
			this.maxMonthlyRepayment = maxMonthlyRepayment;
		}
		public String getMinMonthlyRepaymentPercent() {
			return minMonthlyRepaymentPercent;
		}
		public void setMinMonthlyRepaymentPercent(String minMonthlyRepaymentPercent) {
			this.minMonthlyRepaymentPercent = minMonthlyRepaymentPercent;
		}
		public String getMonthlyRepaymentPercent() {
			return monthlyRepaymentPercent;
		}
		public void setMonthlyRepaymentPercent(String monthlyRepaymentPercent) {
			this.monthlyRepaymentPercent = monthlyRepaymentPercent;
		}
		public String getEir() {
			return eir;
		}
		public void setEir(String eir) {
			this.eir = eir;
		}
		public double getTransferRate() {
			return transferRate;
		}
		public void setTransferRate(double transferRate) {
			this.transferRate = transferRate;
		}
		public String getInterestProfile() {
			return interestProfile;
		}
		public void setInterestProfile(String interestProfile) {
			this.interestProfile = interestProfile;
		}
		public String getQuickLoanLowestApr() {
			return quickLoanLowestApr;
		}
		public void setQuickLoanLowestApr(String quickLoanLowestApr) {
			this.quickLoanLowestApr = quickLoanLowestApr;
		}
		public String getQuickLoanLowestAprDetails() {
			return quickLoanLowestAprDetails;
		}
		public void setQuickLoanLowestAprDetails(String quickLoanLowestAprDetails) {
			this.quickLoanLowestAprDetails = quickLoanLowestAprDetails;
		}
		public String getQuickLoanMinPaymentTenure() {
			return quickLoanMinPaymentTenure;
		}
		public void setQuickLoanMinPaymentTenure(String quickLoanMinPaymentTenure) {
			this.quickLoanMinPaymentTenure = quickLoanMinPaymentTenure;
		}
		public String getQuickLoanMinPaymentTotal() {
			return quickLoanMinPaymentTotal;
		}
		public void setQuickLoanMinPaymentTotal(String quickLoanMinPaymentTotal) {
			this.quickLoanMinPaymentTotal = quickLoanMinPaymentTotal;
		}
		public double getMaxAnnualHandlingFee() {
			return maxAnnualHandlingFee;
		}
		public void setMaxAnnualHandlingFee(double maxAnnualHandlingFee) {
			this.maxAnnualHandlingFee = maxAnnualHandlingFee;
		}
		public double getMaxStartUpFee() {
			return maxStartUpFee;
		}
		public void setMaxStartUpFee(double maxStartUpFee) {
			this.maxStartUpFee = maxStartUpFee;
		}
		public String getMaxInstallmentMonthlyFee() {
			return maxInstallmentMonthlyFee;
		}
		public void setMaxInstallmentMonthlyFee(String maxInstallmentMonthlyFee) {
			this.maxInstallmentMonthlyFee = maxInstallmentMonthlyFee;
		}
		public String getComputationLogic() {
			return computationLogic;
		}
		public void setComputationLogic(String computationLogic) {
			this.computationLogic = computationLogic;
		}
		public double getFixedHandlingFee() {
			return fixedHandlingFee;
		}
		public void setFixedHandlingFee(double fixedHandlingFee) {
			this.fixedHandlingFee = fixedHandlingFee;
		}
		public String getMinStartUpFee() {
			return minStartUpFee;
		}
		public void setMinStartUpFee(String minStartUpFee) {
			this.minStartUpFee = minStartUpFee;
		}
		public boolean isRequiresLifeInsurance() {
			return requiresLifeInsurance;
		}
		public void setRequiresLifeInsurance(boolean requiresLifeInsurance) {
			this.requiresLifeInsurance = requiresLifeInsurance;
		}
		public boolean isHasInstallmentMonthlyFee() {
			return hasInstallmentMonthlyFee;
		}
		public void setHasInstallmentMonthlyFee(boolean hasInstallmentMonthlyFee) {
			this.hasInstallmentMonthlyFee = hasInstallmentMonthlyFee;
		}

		public double getMonthlyFee() {
			return monthlyFee;
		}

		public void setMonthlyFee(double monthlyFee) {
			this.monthlyFee = monthlyFee;
		}

		public StartUpFee[] getStartUpFees() {
			return startUpFees;
		}

		public void setStartUpFees(StartUpFee[] startUpFees) {
			this.startUpFees = startUpFees;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isShowApplyButton() {
		return showApplyButton;
	}

	public void setShowApplyButton(boolean showApplyButton) {
		this.showApplyButton = showApplyButton;
	}

	public boolean isHasRelationshipWithProvider() {
		return hasRelationshipWithProvider;
	}

	public void setHasRelationshipWithProvider(boolean hasRelationshipWithProvider) {
		this.hasRelationshipWithProvider = hasRelationshipWithProvider;
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

	public String getInterestFrequencyUnit() {
		return interestFrequencyUnit;
	}

	public void setInterestFrequencyUnit(String interestFrequencyUnit) {
		this.interestFrequencyUnit = interestFrequencyUnit;
	}

	public String getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(String promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<RelationshipInterest> getRelationshipInterests() {
		return relationshipInterests;
	}

	public void setRelationshipInterests(List<RelationshipInterest> relationshipInterests) {
		this.relationshipInterests = relationshipInterests;
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public void setLocales(List<Locale> locales) {
		this.locales = locales;
	}

	public List<MinimumRequirement> getMiniumuRequirements() {
		return minimumRequirements;
	}

	public void setMinimumRequirements(List<MinimumRequirement> miniumuRequirements) {
		this.minimumRequirements = miniumuRequirements;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public static Predicate<PLJsonObject> IS_FEATURED = new Predicate<PLJsonObject>(){
		
		@Override
		public boolean apply(PLJsonObject obj){
			
			boolean isFeatured = false;
			for(Category category:obj.getCategories()){
				if(category.isFeatured()){
					isFeatured = true;
					break;
				}
			}
			
			return isFeatured;
		}
	};
	
	public static Predicate<PLJsonObject> IS_ACTIVE = new Predicate<PLJsonObject>(){
		
		@Override
		public boolean apply(PLJsonObject obj){
			return obj.isActive();
		}
	};
	
	public static Predicate<PLJsonObject> IS_CATEGORY_TYPE(final String categoryType){
		
		return new Predicate<PLJsonObject>(){
			
			@Override
			public boolean apply(PLJsonObject obj){
				
				if(obj.getId().equals("53480")){
					return false;
				}
				
				boolean isCategoryType = false;
				List<Category> categories = obj.getPurposeCategories();
//				categories.addAll(obj.getCategories());
				for (Category category : categories) {
					if (category.getCategoryType() != null && category.getCategoryType().toLowerCase().trim().equals(categoryType.toLowerCase().trim())) {
						isCategoryType = true;
						break;
					}
				}
				
				return isCategoryType;
			}
		};
	};
	
	public static Predicate<PLJsonObject> IS_SPECIFIED_LANGUAGE(final String language){
		
		return new Predicate<PLJsonObject>(){
			
			@Override
			public boolean apply(PLJsonObject obj){
				
				boolean isSpecifiedLanguage = false;
				for(Locale locale:obj.getLocales()){
					if(locale.getLocaleName().toLowerCase().trim().contains(language.toLowerCase().trim())){
						isSpecifiedLanguage = true;
						break;
					}
				}
				
				return isSpecifiedLanguage;
			}
		};
	};
	
	public static Predicate<PLJsonObject> IS_VALID_LOAN_AMOUNT(final double loanAmount){
		
		return new Predicate<PLJsonObject>(){
			
			@Override
			public boolean apply(PLJsonObject obj){
				
				boolean isValidLoanAmount = false;
				for(Interest interest: obj.getInterests()){
					if(MathUtils.isNotLessThan(loanAmount, interest.getLoanAmountMin())
							&& MathUtils.isNotLessThan(interest.getLoanAmountMax(), loanAmount)){
						isValidLoanAmount = true;
						break;
					}
				}
				return isValidLoanAmount;
			}
		};
	};
	
	public static Predicate<PLJsonObject> IS_VALID_LOAN_TENURE(final double loanTenure){
		
		return new Predicate<PLJsonObject>(){
			
			@Override
			public boolean apply(PLJsonObject obj){
				
				boolean isValidLoanTenure = false;
				for(Interest interest: obj.getInterests()){
					if(MathUtils.isNotLessThan(loanTenure, interest.getLoanTenureMin())
							&& MathUtils.isNotLessThan(interest.getLoanTenureMax(), loanTenure)){
						isValidLoanTenure = true;
						break;
					}
				}
				return isValidLoanTenure;
			}
		};
	}; 
	
	public static PersonalInstalment_HK convertPLJsonObjectIntoPersonalInstalmentHK(final PersonalLoanConfig instance, final PLJsonObject obj) {
		
		PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
		if(loan != null){
			PersonalInstalment_HK result = new PersonalInstalment_HK(loan);
			return result;	
		}else{
			return null;
		}
	}
	
	public static TermLoan_FI convertPLJsonObjectIntoTermLoan(final PersonalLoanConfig instance, final PLJsonObject obj) {
		
		PersonalLoan loan = PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
		if(loan != null){
			TermLoan_FI result = new TermLoan_FI(loan);
			double monthlyInterestRate = result.getAnnualInterestRate(); 
			result.setInterestRate(MathUtils.round(monthlyInterestRate*12, 2));
			//Monthly repayment
			double adjustedLoanAmount = instance.getLoanAmount();
			if(obj.getFee() != null){
				if(obj.getFee().getStartUpFee() > 0.0){
					adjustedLoanAmount += obj.getFee().getStartUpFee();
				}
				if(!Checker.isBlank(obj.getFee().getStartUpFeePercent())){
					adjustedLoanAmount += MathUtils.extractDouble(obj.getFee().getStartUpFeePercent()) * instance.getLoanAmount()/100.0;
				}
				if(obj.getFee().getAnnualHandlingFee() > 0.0){
					adjustedLoanAmount += obj.getFee().getAnnualHandlingFee() * (instance.getLoanTenure_Month()/12.0);
				}else if(!Checker.isBlank(obj.getFee().getAnnualHandlingFeePercent())){
					adjustedLoanAmount +=  instance.getLoanAmount() * (MathUtils.extractDouble(obj.getFee().getAnnualHandlingFeePercent())/100.0)*(instance.getLoanTenure_Month()/12.0);
				}
			}

			double monthlyRepayment = FinanceLib.pmt(monthlyInterestRate/100.0, instance.getLoanTenure_Month(), - adjustedLoanAmount, 0, false);
			if(obj.getFee() != null && !Checker.isBlank(obj.getFee().getInstallmentMonthlyFee())){
				monthlyRepayment += MathUtils.extractDouble(obj.getFee().getInstallmentMonthlyFee());	
			}
			
			result.setMonthlyRepayment(MathUtils.round(monthlyRepayment, 2));
			
			//min monthly repayment
			double minMonthlyRepayment = 0.0;
			if(obj.getFee().getMinMonthlyRepayment() > 0.0){
				minMonthlyRepayment = obj.getFee().getMinMonthlyRepayment();
			}
			if(!Checker.isBlank(obj.getFee().getMinMonthlyRepaymentPercent())){
				double minMonthlyRepaymentPercentage = Double.parseDouble(obj.getFee().getMinMonthlyRepaymentPercent()); 
				if( minMonthlyRepaymentPercentage > 0.0 ){
					minMonthlyRepayment =  Math.max(minMonthlyRepaymentPercentage * instance.getLoanAmount() / 100.0, minMonthlyRepayment);
				}	
			}
			
			if(result.getMonthlyRepayment() < minMonthlyRepayment || (result.getMonthlyRepayment() > obj.getFee().getMaxMonthlyRepayment() && obj.getFee().getMaxMonthlyRepayment() > 0.0)){
				return null;
			}else{
				//APR
				double[] valueArray = new double[instance.getLoanTenure_Month() + 1];
				valueArray[0] = - instance.getLoanAmount();
				for(int i =  1; i <= instance.getLoanTenure_Month(); i ++){
					valueArray[i] = monthlyRepayment; 
				}
				double irrValue = MathUtils.irr(valueArray);
				
				double  APR = (Math.pow(1 + irrValue, 12) -1) * 100 ;
				result.setApr(MathUtils.round(APR, 2));
				
				return result;				
			}
		}else{
			return null;
		}
	}
	
	public static Function<PLJsonObject, PersonalLoan> TO_RESULT_OBJECT(final PersonalLoanConfig instance){
		
		return new Function<PLJsonObject, PersonalLoan>(){
			
			@Override
			public PersonalLoan apply(final PLJsonObject obj){
			
				return PersonalLoan.getPersonalLoansFromJsonObject(instance, obj);
			}
		};
	}

	public boolean isShowApplyConfirmation() {
		return showApplyConfirmation;
	}

	public void setShowApplyConfirmation(boolean showApplyConfirmation) {
		this.showApplyConfirmation = showApplyConfirmation;
	}

	public static Predicate<PLJsonObject> getIS_FEATURED() {
		return IS_FEATURED;
	}

	public static void setIS_FEATURED(Predicate<PLJsonObject> iS_FEATURED) {
		IS_FEATURED = iS_FEATURED;
	}

	public static Predicate<PLJsonObject> getIS_ACTIVE() {
		return IS_ACTIVE;
	}

	public static void setIS_ACTIVE(Predicate<PLJsonObject> iS_ACTIVE) {
		IS_ACTIVE = iS_ACTIVE;
	}

	public List<MinimumRequirement> getMinimumRequirements() {
		return minimumRequirements;
	}

	public boolean isShowCTALeadCapture() {
		return showCTALeadCapture;
	}

	public void setShowCTALeadCapture(boolean showCTALeadCapture) {
		this.showCTALeadCapture = showCTALeadCapture;
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

	public boolean isShowDesktopApplyButton() {
		return showDesktopApplyButton;
	}

	public void setShowDesktopApplyButton(boolean showDesktopApplyButton) {
		this.showDesktopApplyButton = showDesktopApplyButton;
	}

	public boolean isShowMobileApplyButton() {
		return showMobileApplyButton;
	}

	public void setShowMobileApplyButton(boolean showMobileApplyButton) {
		this.showMobileApplyButton = showMobileApplyButton;
	}

	public boolean isShowTabletApplyButton() {
		return showTabletApplyButton;
	}

	public void setShowTabletApplyButton(boolean showTabletApplyButton) {
		this.showTabletApplyButton = showTabletApplyButton;
	}

	public List<Category> getPurposeCategories() {
		return purposeCategories;
	}

	public void setPurposeCategories(List<Category> purposeCategories) {
		this.purposeCategories = purposeCategories;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean isCalculateApr() {
		return calculateApr;
	}

	public void setCalculateApr(boolean calculateApr) {
		this.calculateApr = calculateApr;
	}

	public List<MonthlyRebate> getMonthlyRebates() {
		return monthlyRebates;
	}

	public void setMonthlyRebates(List<MonthlyRebate> monthlyRebates) {
		this.monthlyRebates = monthlyRebates;
	}
}
