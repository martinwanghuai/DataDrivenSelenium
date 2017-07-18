package com.cgg.consumer.performance;

import java.io.Serializable;
import java.util.List;

public class GooglePageSpeedObject {
	
	private String kind;
	private String id;
	private Integer responseCode;
	private String title;
	private RuleGroups ruleGroups;
	private PageStats pageStats;
	private FormattedResults formattedResults;
	private Version version;
	
	public static class RuleGroups implements Serializable {
		
		private SPEED SPEED;

		public SPEED getSPEED() {
			return SPEED;
		}

		public void setSPEED(SPEED sPEED) {
			SPEED = sPEED;
		}
	}
	
	public static class SPEED implements Serializable {
		
		private Integer score;

		public Integer getScore() {
			return score;
		}

		public void setScore(Integer score) {
			this.score = score;
		}
	}
	
	public static class PageStats implements Serializable {
		
		private Integer numberResources;
		private Integer numberHosts;
		private String totalRequestBytes;
		private Integer numberStaticResources;
		private String htmlResponseBytes;
		private String cssResponseBytes;
		private String imageResponseBytes;
		private String javascriptResponseBytes;
		private String otherResponseBytes;
		private Integer numberJsResources;
		private Integer numberCssResources;
		public Integer getNumberResources() {
			return numberResources;
		}
		public void setNumberResources(Integer numberResources) {
			this.numberResources = numberResources;
		}
		public Integer getNumberHosts() {
			return numberHosts;
		}
		public void setNumberHosts(Integer numberHosts) {
			this.numberHosts = numberHosts;
		}
		public String getTotalRequestBytes() {
			return totalRequestBytes;
		}
		public void setTotalRequestBytes(String totalRequestBytes) {
			this.totalRequestBytes = totalRequestBytes;
		}
		public Integer getNumberStaticResources() {
			return numberStaticResources;
		}
		public void setNumberStaticResources(Integer numberStaticResources) {
			this.numberStaticResources = numberStaticResources;
		}
		public String getHtmlResponseBytes() {
			return htmlResponseBytes;
		}
		public void setHtmlResponseBytes(String htmlResponseBytes) {
			this.htmlResponseBytes = htmlResponseBytes;
		}
		public String getCssResponseBytes() {
			return cssResponseBytes;
		}
		public void setCssResponseBytes(String cssResponseBytes) {
			this.cssResponseBytes = cssResponseBytes;
		}
		public String getImageResponseBytes() {
			return imageResponseBytes;
		}
		public void setImageResponseBytes(String imageResponseBytes) {
			this.imageResponseBytes = imageResponseBytes;
		}
		public String getJavascriptResponseBytes() {
			return javascriptResponseBytes;
		}
		public void setJavascriptResponseBytes(String javascriptResponseBytes) {
			this.javascriptResponseBytes = javascriptResponseBytes;
		}
		public String getOtherResponseBytes() {
			return otherResponseBytes;
		}
		public void setOtherResponseBytes(String otherResponseBytes) {
			this.otherResponseBytes = otherResponseBytes;
		}
		public Integer getNumberJsResources() {
			return numberJsResources;
		}
		public void setNumberJsResources(Integer numberJsResources) {
			this.numberJsResources = numberJsResources;
		}
		public Integer getNumberCssResources() {
			return numberCssResources;
		}
		public void setNumberCssResources(Integer numberCssResources) {
			this.numberCssResources = numberCssResources;
		}
	}
	
	public static class FormattedResults implements Serializable {
		private String locale;
		private RuleResults ruleResults;
		public String getLocale() {
			return locale;
		}
		public void setLocale(String locale) {
			this.locale = locale;
		}
		public RuleResults getRuleResults() {
			return ruleResults;
		}
		public void setRuleResults(RuleResults ruleResults) {
			this.ruleResults = ruleResults;
		}
	}
	
	public static class RuleResults implements Serializable {
		private PageResult AvoidLandingPageRedirects;
		private PageResult EnableGzipCompression;
		private PageResult LeverageBrowserCaching;
		private PageResult MainResourceServerResponseTime;
		private PageResult MinifyCss;
		private PageResult MinifyHTML;
		private PageResult MinifyJavaScript;
		private PageResult MinimizeRenderBlockingResources;
		private PageResult OptimizeImages;
		private PageResult PrioritizeVisibleContent;
		public PageResult getAvoidLandingPageRedirects() {
			return AvoidLandingPageRedirects;
		}
		public void setAvoidLandingPageRedirects(PageResult avoidLandingPageRedirects) {
			AvoidLandingPageRedirects = avoidLandingPageRedirects;
		}
		public PageResult getEnableGzipCompression() {
			return EnableGzipCompression;
		}
		public void setEnableGzipCompression(PageResult enableGzipCompression) {
			EnableGzipCompression = enableGzipCompression;
		}
		public PageResult getLeverageBrowserCaching() {
			return LeverageBrowserCaching;
		}
		public void setLeverageBrowserCaching(PageResult leverageBrowserCaching) {
			LeverageBrowserCaching = leverageBrowserCaching;
		}
		public PageResult getMainResourceServerResponseTime() {
			return MainResourceServerResponseTime;
		}
		public void setMainResourceServerResponseTime(PageResult mainResourceServerResponseTime) {
			MainResourceServerResponseTime = mainResourceServerResponseTime;
		}
		public PageResult getMinifyCss() {
			return MinifyCss;
		}
		public void setMinifyCss(PageResult minifyCss) {
			MinifyCss = minifyCss;
		}
		public PageResult getMinifyHTML() {
			return MinifyHTML;
		}
		public void setMinifyHTML(PageResult minifyHTML) {
			MinifyHTML = minifyHTML;
		}
		public PageResult getMinifyJavaScript() {
			return MinifyJavaScript;
		}
		public void setMinifyJavaScript(PageResult minifyJavaScript) {
			MinifyJavaScript = minifyJavaScript;
		}
		public PageResult getMinimizeRenderBlockingResources() {
			return MinimizeRenderBlockingResources;
		}
		public void setMinimizeRenderBlockingResources(PageResult minimizeRenderBlockingResources) {
			MinimizeRenderBlockingResources = minimizeRenderBlockingResources;
		}
		public PageResult getOptimizeImages() {
			return OptimizeImages;
		}
		public void setOptimizeImages(PageResult optimizeImages) {
			OptimizeImages = optimizeImages;
		}
		public PageResult getPrioritizeVisibleContent() {
			return PrioritizeVisibleContent;
		}
		public void setPrioritizeVisibleContent(PageResult prioritizeVisibleContent) {
			PrioritizeVisibleContent = prioritizeVisibleContent;
		}
	}
	
	/*public static class OptimizeImages implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class PrioritizeVisibleContent implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class MinimizeRenderBlockingResources implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class MinifyJavaScript implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class MinifyHTML implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class MinifyCss implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class MainResourceServerResponseTime implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class LeverageBrowserCaching implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class EnableGzipCompression implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	
	public static class AvoidLandingPageRedirects implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private URLBlocks urlBlocks;
	}
	*/
	public static class PageResult implements Serializable {
		private String localizedRuleName;
		private Double ruleImpact;
		private List<String> groups;
		private Summary summary;
		private List<URLBlock> urlBlocks;
		public String getLocalizedRuleName() {
			return localizedRuleName;
		}
		public void setLocalizedRuleName(String localizedRuleName) {
			this.localizedRuleName = localizedRuleName;
		}
		public Double getRuleImpact() {
			return ruleImpact;
		}
		public void setRuleImpact(Double ruleImpact) {
			this.ruleImpact = ruleImpact;
		}
		public List<String> getGroups() {
			return groups;
		}
		public void setGroups(List<String> groups) {
			this.groups = groups;
		}
		public Summary getSummary() {
			return summary;
		}
		public void setSummary(Summary summary) {
			this.summary = summary;
		}
		public List<URLBlock> getUrlBlocks() {
			return urlBlocks;
		}
		public void setUrlBlocks(List<URLBlock> urlBlocks) {
			this.urlBlocks = urlBlocks;
		}
	}
	
	public static class URLBlock implements Serializable {
		
		private Summary header;
		private List<URL> urls;
		public Summary getHeader() {
			return header;
		}
		public void setHeader(Summary header) {
			this.header = header;
		}
		public List<URL> getUrls() {
			return urls;
		}
		public void setUrls(List<URL> urls) {
			this.urls = urls;
		}
	}
	
	public static class URL implements Serializable {
		
		private Summary result;

		public Summary getResult() {
			return result;
		}

		public void setResult(Summary result) {
			this.result = result;
		}
	}
	
	public static class Summary implements Serializable {
		private String format;
		private List<Args> args;
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public List<Args> getArgs() {
			return args;
		}
		public void setArgs(List<Args> args) {
			this.args = args;
		}
	}
	
	public static class Args implements Serializable {
		
		private String type;
		private String key;
		private String value;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static class Version implements Serializable {
		private Integer major;
		private Integer minor;
		public Integer getMajor() {
			return major;
		}
		public void setMajor(Integer major) {
			this.major = major;
		}
		public Integer getMinor() {
			return minor;
		}
		public void setMinor(Integer minor) {
			this.minor = minor;
		}
	}

	@Override
	public String toString() {
		return "GooglePageSpeedObject [kind=" + kind + ", id=" + id + ", responseCode=" + responseCode + ", title="
				+ title + ", ruleGroups=" + ruleGroups + ", pageStats=" + pageStats + ", formattedResults="
				+ formattedResults + ", version=" + version + "]";
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RuleGroups getRuleGroups() {
		return ruleGroups;
	}

	public void setRuleGroups(RuleGroups ruleGroups) {
		this.ruleGroups = ruleGroups;
	}

	public PageStats getPageStats() {
		return pageStats;
	}

	public void setPageStats(PageStats pageStats) {
		this.pageStats = pageStats;
	}

	public FormattedResults getFormattedResults() {
		return formattedResults;
	}

	public void setFormattedResults(FormattedResults formattedResults) {
		this.formattedResults = formattedResults;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
}
