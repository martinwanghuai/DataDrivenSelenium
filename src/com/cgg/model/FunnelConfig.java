package com.cgg.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.testng.collections.Maps;

public class FunnelConfig {

	private Funnel funnel;
	
	public static class Funnel implements Serializable{
		
		private Config config;

		public Config getConfig() {
			return config;
		}

		public void setConfig(Config config) {
			this.config = config;
		}
	}
	
	public static class Config implements Serializable{
		
		private List<String> tagManagerStepNameList;
		private List<String> mobileLabel;
		private String mobileSummaryLabel;
		private List<String> tagManagerKeySubmited;
		private boolean turnOnGoogleAnalytics;
		private String providerUrl;
		private String userCityServiceURL;
		private String userCityServiceKey;
		private String userCityServiceResultURL;
		
		private List<String> destinationURLSetting;
		private List<String> userCityIgnoreList;
		private String regionFormat;
		private String country;
		private String vertical;
		private String verticalForDataTracking;
		private String skipUrl;
		private boolean hasSkipLink;
		private String skipLinkText;
		private String leftButtonText;
		private String rightButtonText;
		private String lastStepRightButtonText;
		private boolean displayDisclaimer;
		private boolean sendPostDataToUserCity;
		private String disclaimerText;
		private String tabletShowSummaryLabel;
		private String tabletHideSummaryLabel;
		private List<ProgressSetting> progressSetting;
		private List<StepInfo> stepInfo;
		private LeaveModal leaveModal;
		private String personalLoanInfoTitle;
		private String currency;
		private PromotionOptions promotionOptions;
		public List<String> getTagManagerStepNameList() {
			return tagManagerStepNameList;
		}
		public void setTagManagerStepNameList(List<String> tagManagerStepNameList) {
			this.tagManagerStepNameList = tagManagerStepNameList;
		}
		public List<String> getTagManagerKeySubmited() {
			return tagManagerKeySubmited;
		}
		public void setTagManagerKeySubmited(List<String> tagManagerKeySubmited) {
			this.tagManagerKeySubmited = tagManagerKeySubmited;
		}
		public List<String> getMobileLabel() {
			return mobileLabel;
		}
		public void setMobileLabel(List<String> mobileLabel) {
			this.mobileLabel = mobileLabel;
		}
		public String getMobileSummaryLabel() {
			return mobileSummaryLabel;
		}
		public void setMobileSummaryLabel(String mobileSummaryLabel) {
			this.mobileSummaryLabel = mobileSummaryLabel;
		}
		public boolean isTurnOnGoogleAnalytics() {
			return turnOnGoogleAnalytics;
		}
		public void setTurnOnGoogleAnalytics(boolean turnOnGoogleAnalytics) {
			this.turnOnGoogleAnalytics = turnOnGoogleAnalytics;
		}
		public String getProviderUrl() {
			return providerUrl;
		}
		public void setProviderUrl(String providerUrl) {
			this.providerUrl = providerUrl;
		}
		public List<String> getDestinationURLSetting() {
			return destinationURLSetting;
		}
		public void setDestinationURLSetting(List<String> destinationURLSetting) {
			this.destinationURLSetting = destinationURLSetting;
		}
		public List<String> getUserCityIgnoreList() {
			return userCityIgnoreList;
		}
		public void setUserCityIgnoreList(List<String> userCityIgnoreList) {
			this.userCityIgnoreList = userCityIgnoreList;
		}
		public String getRegionFormat() {
			return regionFormat;
		}
		public void setRegionFormat(String regionFormat) {
			this.regionFormat = regionFormat;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getVertical() {
			return vertical;
		}
		public void setVertical(String vertical) {
			this.vertical = vertical;
		}
		public String getVerticalForDataTracking() {
			return verticalForDataTracking;
		}
		public void setVerticalForDataTracking(String verticalForDataTracking) {
			this.verticalForDataTracking = verticalForDataTracking;
		}
		public String getSkipUrl() {
			return skipUrl;
		}
		public void setSkipUrl(String skipUrl) {
			this.skipUrl = skipUrl;
		}
		public boolean isHasSkipLink() {
			return hasSkipLink;
		}
		public void setHasSkipLink(boolean hasSkipLink) {
			this.hasSkipLink = hasSkipLink;
		}
		public String getSkipLinkText() {
			return skipLinkText;
		}
		public void setSkipLinkText(String skipLinkText) {
			this.skipLinkText = skipLinkText;
		}
		public String getLeftButtonText() {
			return leftButtonText;
		}
		public void setLeftButtonText(String leftButtonText) {
			this.leftButtonText = leftButtonText;
		}
		public String getRightButtonText() {
			return rightButtonText;
		}
		public void setRightButtonText(String rightButtonText) {
			this.rightButtonText = rightButtonText;
		}
		public String getLastStepRightButtonText() {
			return lastStepRightButtonText;
		}
		public void setLastStepRightButtonText(String lastStepRightButtonText) {
			this.lastStepRightButtonText = lastStepRightButtonText;
		}
		public boolean isDisplayDisclaimer() {
			return displayDisclaimer;
		}
		public void setDisplayDisclaimer(boolean displayDisclaimer) {
			this.displayDisclaimer = displayDisclaimer;
		}
		public boolean isSendPostDataToUserCity() {
			return sendPostDataToUserCity;
		}
		public void setSendPostDataToUserCity(boolean sendPostDataToUserCity) {
			this.sendPostDataToUserCity = sendPostDataToUserCity;
		}
		public String getDisclaimerText() {
			return disclaimerText;
		}
		public void setDisclaimerText(String disclaimerText) {
			this.disclaimerText = disclaimerText;
		}
		public String getTabletShowSummaryLabel() {
			return tabletShowSummaryLabel;
		}
		public void setTabletShowSummaryLabel(String tabletShowSummaryLabel) {
			this.tabletShowSummaryLabel = tabletShowSummaryLabel;
		}
		public String getTabletHideSummaryLabel() {
			return tabletHideSummaryLabel;
		}
		public void setTabletHideSummaryLabel(String tabletHideSummaryLabel) {
			this.tabletHideSummaryLabel = tabletHideSummaryLabel;
		}
		public List<ProgressSetting> getProgressSetting() {
			return progressSetting;
		}
		public void setProgressSetting(List<ProgressSetting> progressSetting) {
			this.progressSetting = progressSetting;
		}
		public List<StepInfo> getStepInfo() {
			return stepInfo;
		}
		public void setStepInfo(List<StepInfo> stepInfo) {
			this.stepInfo = stepInfo;
		}
		public LeaveModal getLeaveModal() {
			return leaveModal;
		}
		public void setLeaveModal(LeaveModal leaveModal) {
			this.leaveModal = leaveModal;
		}
		public String getPersonalLoanInfoTitle() {
			return personalLoanInfoTitle;
		}
		public void setPersonalLoanInfoTitle(String personalLoanInfoTitle) {
			this.personalLoanInfoTitle = personalLoanInfoTitle;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public PromotionOptions getPromotionOptions() {
			return promotionOptions;
		}
		public void setPromotionOptions(PromotionOptions promotionOptions) {
			this.promotionOptions = promotionOptions;
		}
		public String getUserCityServiceURL() {
			return userCityServiceURL;
		}
		public void setUserCityServiceURL(String userCityServiceURL) {
			this.userCityServiceURL = userCityServiceURL;
		}
		public String getUserCityServiceKey() {
			return userCityServiceKey;
		}
		public void setUserCityServiceKey(String userCityServiceKey) {
			this.userCityServiceKey = userCityServiceKey;
		}
		public String getUserCityServiceResultURL() {
			return userCityServiceResultURL;
		}
		public void setUserCityServiceResultURL(String userCityServiceResultURL) {
			this.userCityServiceResultURL = userCityServiceResultURL;
		}
	}

	public static class PromotionOptions implements Serializable {
		
		private String customerPromiseImage;
		private String promotionTitle;
		private String promotionDescription;
		private List<PromotionPros> promotionPros;
		private String weHaveCompared;
		public String getCustomerPromiseImage() {
			return customerPromiseImage;
		}
		public void setCustomerPromiseImage(String customerPromiseImage) {
			this.customerPromiseImage = customerPromiseImage;
		}
		public String getPromotionTitle() {
			return promotionTitle;
		}
		public void setPromotionTitle(String promotionTitle) {
			this.promotionTitle = promotionTitle;
		}
		public String getPromotionDescription() {
			return promotionDescription;
		}
		public void setPromotionDescription(String promotionDescription) {
			this.promotionDescription = promotionDescription;
		}
		public List<PromotionPros> getPromotionPros() {
			return promotionPros;
		}
		public void setPromotionPros(List<PromotionPros> promotionPros) {
			this.promotionPros = promotionPros;
		}
		public String getWeHaveCompared() {
			return weHaveCompared;
		}
		public void setWeHaveCompared(String weHaveCompared) {
			this.weHaveCompared = weHaveCompared;
		}
	}

	public static class PromotionPros implements Serializable {
		
		private String iconClass;
		private String description;
		public String getIconClass() {
			return iconClass;
		}
		public void setIconClass(String iconClass) {
			this.iconClass = iconClass;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public static class ImageArray implements Serializable {
		
		private String url;
		private String alt;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getAlt() {
			return alt;
		}
		public void setAlt(String alt) {
			this.alt = alt;
		}
	}

	public static class LeaveModal implements Serializable {
		
		private String question;
		private String description;
		private String yesMessage;
		private String noMessage;
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getYesMessage() {
			return yesMessage;
		}
		public void setYesMessage(String yesMessage) {
			this.yesMessage = yesMessage;
		}
		public String getNoMessage() {
			return noMessage;
		}
		public void setNoMessage(String noMessage) {
			this.noMessage = noMessage;
		}
	}
	
	
	public static class ProgressSetting implements Serializable{
		
		private String icon;
		private Integer value;
		private String url;
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}
	
	public static class TemplateOption implements Serializable {
		
		private String name;
		private List<Template> template;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Template> getTemplate() {
			return template;
		}
		public void setTemplate(List<Template> template) {
			this.template = template;
		}
	}
	
	public static class Template implements Serializable {
		private String type;
		private Data data;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Data getData() {
			return data;
		}
		public void setData(Data data) {
			this.data = data;
		}
	}
	
	public static class Spinner implements Serializable {
		
		private String placeholder;

		public String getPlaceholder() {
			return placeholder;
		}

		public void setPlaceholder(String placeholder) {
			this.placeholder = placeholder;
		}
	}
	
	public static class API implements Serializable {
		
		private String parsableURL;
		private String data;
		public String getParsableURL() {
			return parsableURL;
		}
		public void setParsableURL(String parsableURL) {
			this.parsableURL = parsableURL;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
	}
	
/*	public static class Placeholder implements Serializable {
		
		private String DD;
		private String MM;
		private String YYYY;
	}
*/	
	public static class Options implements Serializable {
		
		private String showMoreMessage;
		private List<String> layout;
		private List<Items> items;
		private Integer maxShowPerPage;
		private API api;
		private Spinner spinner;
		
		public List<String> getLayout() {
			return layout;
		}
		public void setLayout(List<String> layout) {
			this.layout = layout;
		}
		public List<Items> getItems() {
			return items;
		}
		public void setItems(List<Items> items) {
			this.items = items;
		}
		public Integer getMaxShowPerPage() {
			return maxShowPerPage;
		}
		public void setMaxShowPerPage(Integer maxShowPerPage) {
			this.maxShowPerPage = maxShowPerPage;
		}
		public String getShowMoreMessage() {
			return showMoreMessage;
		}
		public void setShowMoreMessage(String showMoreMessage) {
			this.showMoreMessage = showMoreMessage;
		}
		public API getApi() {
			return api;
		}
		public void setApi(API api) {
			this.api = api;
		}
		public Spinner getSpinner() {
			return spinner;
		}
		public void setSpinner(Spinner spinner) {
			this.spinner = spinner;
		}
	}
	
	public static class Items implements Serializable {
		
		private String type;
		private String selectionType;
		private String text;
		private String gtmId;
		private String cggId;
		private String id;
		private String className;
		private String url;
		private String name;
		private boolean disabled;
		
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
		public String getGtmId() {
			return gtmId;
		}
		public void setGtmId(String gtmId) {
			this.gtmId = gtmId;
		}
		public String getCggId() {
			return cggId;
		}
		public void setCggId(String cggId) {
			this.cggId = cggId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isDisabled() {
			return disabled;
		}
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
	}

	public static class TimeOptions implements Serializable {
		
		private List<TimeOptionItem> items;

		public List<TimeOptionItem> getItems() {
			return items;
		}

		public void setItems(List<TimeOptionItem> items) {
			this.items = items;
		}
	}
	
	
	public static class TimeUnitOptions implements Serializable {
		
		private List<TimeOptionItem> items;
		private String selectionStyleClass;
		public List<TimeOptionItem> getItems() {
			return items;
		}
		public void setItems(List<TimeOptionItem> items) {
			this.items = items;
		}
		public String getSelectionStyleClass() {
			return selectionStyleClass;
		}
		public void setSelectionStyleClass(String selectionStyleClass) {
			this.selectionStyleClass = selectionStyleClass;
		}
	}
	
	public static class TimeOptionItem implements Serializable {
		
		private String id;
		private String gtmId;
		private String cggId;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGtmId() {
			return gtmId;
		}
		public void setGtmId(String gtmId) {
			this.gtmId = gtmId;
		}
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
	}
	
	public static class Hint implements Serializable {
		private String label;
		private String hint;
		private String iconFlot;
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getHint() {
			return hint;
		}
		public void setHint(String hint) {
			this.hint = hint;
		}
		public String getIconFlot() {
			return iconFlot;
		}
		public void setIconFlot(String iconFlot) {
			this.iconFlot = iconFlot;
		} 
	}
	
	public static class KeyTwoDefault implements Serializable {
		private String id;
		private String gtmId;
		private String cggId;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGtmId() {
			return gtmId;
		}
		public void setGtmId(String gtmId) {
			this.gtmId = gtmId;
		}
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
	}
	
	public static class Choice implements Serializable {
		
		private String id;
		private String cggId;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
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
	}
	
	public static class Data implements Serializable {
		
		//Data for Step 1
		private String headline;
		private String description;
		
		private String label;
		private String hint;
		private String mobileHint;
		private String iconFloat;
		
		private String key;
		private Options options;
		
		//Data for Step 2
		private Hint hintLeft;
		private Hint hintRight;
		private String placeholder;
		private String currency;
		private String keyOneDefault;
		private String keyOneMinLength;
		private String keyOneMaxLength;
		private String keyOnePattern;
		private KeyTwoDefault keyTwoDefault;
		private String keyTwoDefaut_str;
		private String keyOne;
		private String keyTwo;
		private Boolean keyTwoDropdown;
		private String keyThree;
		private String regionFormat;
		private String displayRule;
		private TimeOptions timeOptions;
		private TimeUnitOptions timeUnitOptions;
		
		private String inputUnit;
		private String textAfter;
		private String minLength;
		private String maxLength;
		private String mobilePlaceholder;

		private String keyDisplayName;
		private String countryCode;
		private String errorMessage;
		private String pattern;
		private List<Choice> choices; 
		
		public String getHeadline() {
			return headline;
		}
		public void setHeadline(String headline) {
			this.headline = headline;
		}
		public String getMobileHint() {
			return mobileHint;
		}
		public void setMobileHint(String mobileHint) {
			this.mobileHint = mobileHint;
		}
		public String getIconFloat() {
			return iconFloat;
		}
		public void setIconFloat(String iconFloat) {
			this.iconFloat = iconFloat;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Options getOptions() {
			return options;
		}
		public void setOptions(Options options) {
			this.options = options;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getHint() {
			return hint;
		}
		public void setHint(String hint) {
			this.hint = hint;
		}
		public Hint getHintLeft() {
			return hintLeft;
		}
		public void setHintLeft(Hint hintLeft) {
			this.hintLeft = hintLeft;
		}
		public Hint getHintRight() {
			return hintRight;
		}
		public void setHintRight(Hint hintRight) {
			this.hintRight = hintRight;
		}
		public String getPlaceholder() {
			return placeholder;
		}
		public void setPlaceholder(String placeholder) {
			this.placeholder = placeholder;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getKeyOneDefault() {
			return keyOneDefault;
		}
		public void setKeyOneDefault(String keyOneDefault) {
			this.keyOneDefault = keyOneDefault;
		}
		public String getKeyOneMinLength() {
			return keyOneMinLength;
		}
		public void setKeyOneMinLength(String keyOneMinLength) {
			this.keyOneMinLength = keyOneMinLength;
		}
		public String getKeyOneMaxLength() {
			return keyOneMaxLength;
		}
		public void setKeyOneMaxLength(String keyOneMaxLength) {
			this.keyOneMaxLength = keyOneMaxLength;
		}
		public String getKeyOnePattern() {
			return keyOnePattern;
		}
		public void setKeyOnePattern(String keyOnePattern) {
			this.keyOnePattern = keyOnePattern;
		}
		public KeyTwoDefault getKeyTwoDefault() {
			return keyTwoDefault;
		}
		public void setKeyTwoDefault(KeyTwoDefault keyTwoDefault) {
			this.keyTwoDefault = keyTwoDefault;
		}
		public String getKeyOne() {
			return keyOne;
		}
		public void setKeyOne(String keyOne) {
			this.keyOne = keyOne;
		}
		public String getKeyTwo() {
			return keyTwo;
		}
		public void setKeyTwo(String keyTwo) {
			this.keyTwo = keyTwo;
		}
		public Boolean getKeyTwoDropdown() {
			return keyTwoDropdown;
		}
		public void setKeyTwoDropdown(Boolean keyTwoDropdown) {
			this.keyTwoDropdown = keyTwoDropdown;
		}
		public String getKeyThree() {
			return keyThree;
		}
		public void setKeyThree(String keyThree) {
			this.keyThree = keyThree;
		}
		public String getRegionFormat() {
			return regionFormat;
		}
		public void setRegionFormat(String regionFormat) {
			this.regionFormat = regionFormat;
		}
		public String getDisplayRule() {
			return displayRule;
		}
		public void setDisplayRule(String displayRule) {
			this.displayRule = displayRule;
		}
		public TimeOptions getTimeOptions() {
			return timeOptions;
		}
		public void setTimeOptions(TimeOptions timeOptions) {
			this.timeOptions = timeOptions;
		}
		public TimeUnitOptions getTimeUnitOptions() {
			return timeUnitOptions;
		}
		public void setTimeUnitOptions(TimeUnitOptions timeUnitOptions) {
			this.timeUnitOptions = timeUnitOptions;
		}
		public String getInputUnit() {
			return inputUnit;
		}
		public void setInputUnit(String inputUnit) {
			this.inputUnit = inputUnit;
		}
		public String getTextAfter() {
			return textAfter;
		}
		public void setTextAfter(String textAfter) {
			this.textAfter = textAfter;
		}
		public String getMinLength() {
			return minLength;
		}
		public void setMinLength(String minLength) {
			this.minLength = minLength;
		}
		public String getMaxLength() {
			return maxLength;
		}
		public void setMaxLength(String maxLength) {
			this.maxLength = maxLength;
		}
		public String getMobilePlaceholder() {
			return mobilePlaceholder;
		}
		public void setMobilePlaceholder(String mobilePlaceholder) {
			this.mobilePlaceholder = mobilePlaceholder;
		}
		public String getKeyDisplayName() {
			return keyDisplayName;
		}
		public void setKeyDisplayName(String keyDisplayName) {
			this.keyDisplayName = keyDisplayName;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public String getPattern() {
			return pattern;
		}
		public void setPattern(String pattern) {
			this.pattern = pattern;
		}
		public String getKeyTwoDefaut_str() {
			return keyTwoDefaut_str;
		}
		public void setKeyTwoDefaut_str(String keyTwoDefaut_str) {
			this.keyTwoDefaut_str = keyTwoDefaut_str;
		}
		public List<Choice> getChoices() {
			return choices;
		}
		public void setChoices(List<Choice> choices) {
			this.choices = choices;
		}
	}
	
	public static class RequiredInfo implements Serializable {
		
		private boolean category;
		private boolean phone;
		private boolean name;
		private boolean emailAddress;
		private boolean passport;
		private boolean bankRelationship;
		private boolean emi_fmin;
		private boolean residencyStatus;
		private boolean wantToBorrowTime;
		private boolean wantToBorrowTimeUnit;
		private boolean wantToBorrow;
		
		public boolean isCategory() {
			return category;
		}

		public void setCategory(boolean category) {
			this.category = category;
		}

		public boolean isPhone() {
			return phone;
		}

		public void setPhone(boolean phone) {
			this.phone = phone;
		}

		public boolean isName() {
			return name;
		}

		public void setName(boolean name) {
			this.name = name;
		}

		public boolean isEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(boolean emailAddress) {
			this.emailAddress = emailAddress;
		}

		public boolean isBankRelationship() {
			return bankRelationship;
		}

		public void setBankRelationship(boolean bankRelationship) {
			this.bankRelationship = bankRelationship;
		}

		public boolean isEmi_fmin() {
			return emi_fmin;
		}

		public void setEmi_fmin(boolean emi_fmin) {
			this.emi_fmin = emi_fmin;
		}

		public boolean isResidencyStatus() {
			return residencyStatus;
		}

		public void setResidencyStatus(boolean residencyStatus) {
			this.residencyStatus = residencyStatus;
		}

		public boolean isWantToBorrowTime() {
			return wantToBorrowTime;
		}

		public void setWantToBorrowTime(boolean wantToBorrowTime) {
			this.wantToBorrowTime = wantToBorrowTime;
		}

		public boolean isWantToBorrowTimeUnit() {
			return wantToBorrowTimeUnit;
		}

		public void setWantToBorrowTimeUnit(boolean wantToBorrowTimeUnit) {
			this.wantToBorrowTimeUnit = wantToBorrowTimeUnit;
		}

		public boolean isWantToBorrow() {
			return wantToBorrow;
		}

		public void setWantToBorrow(boolean wantToBorrow) {
			this.wantToBorrow = wantToBorrow;
		}

		public boolean isPassport() {
			return passport;
		}

		public void setPassport(boolean passport) {
			this.passport = passport;
		}
		
	}
	public static class StepInfo implements Serializable {
		
		private String label;
		private String link;
		private String funnelKey;
		private String statusBarValue;
		private String currency;
		private RequiredInfo requiredInfo;
		private TemplateOption templateOption;
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
		
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getFunnelKey() {
			return funnelKey;
		}
		public void setFunnelKey(String funnelKey) {
			this.funnelKey = funnelKey;
		}
		public String getStatusBarValue() {
			return statusBarValue;
		}
		public void setStatusBarValue(String statusBarValue) {
			this.statusBarValue = statusBarValue;
		}
		public RequiredInfo getRequiredInfo() {
			return requiredInfo;
		}
		public void setRequiredInfo(RequiredInfo requiredInfo) {
			this.requiredInfo = requiredInfo;
		}
		public TemplateOption getTemplateOption() {
			return templateOption;
		}
		public void setTemplateOption(TemplateOption templateOption) {
			this.templateOption = templateOption;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public Map<String, Integer> getTypeCountMap() {
			return typeCountMap;
		}
		public void setTypeCountMap(Map<String, Integer> typeCountMap) {
			this.typeCountMap = typeCountMap;
		}
		
	}

	public Funnel getFunnel() {
		return funnel;
	}

	public void setFunnel(Funnel funnel) {
		this.funnel = funnel;
	}
}
