package utility;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public final class Checker {
	
	public final static String REGEX_EMAIL = "[A-Za-z]{1,3}\\@[A-Za-z]{1,3}\\.[A-Za-z]{3}";
	public final static String REGEX_INPUT = "[1-9]{3,6}";
	public final static String MISSING_KEY_PATTERN = "^([^@$�,%/{}]+\\.)+([^@$�,%{}])+$"; 

	private static List<String> excludeList = Lists.newArrayList(
			"moneyhero.com.hk", "hutchgo.com", "moneyhero.com.hk's", "money101.com.tw",
			"topcompare.be", "compareeuropegroup.com", "voorwaarden.het", "vertaaEnsin.fi",
			"m.a.x", "www.moneyhero.com.hk", "www.hsbc.com.hk", "vertaaensin.fi",
			"komplett.no", "flust.no","hotels.com","blush.no", "kaligo.com",
			"citigroup.com", "singsaver.com.sg", "e.g.", "j.d.power", "kommer", 
			"p.a.", "p.a", "i.e.", "etc.", "e.l.","m.v.", "f.eks.", "expedia.com", "anz.tw", 
			"i.pay", "am", "pm", "a.m.", "p.m.", "skype.com", 
			"singapore.preview.compareglobal.co.uk", "comparaGuru.com", "limiitti.fi", 
			"korot.voit", "avulla", "täältä", "täältä", "pikavippi.fistä", "lainaaja.fi",
			"limiitti.fistä", "everyday.fi", "laina.fi", "j.w.-yhtiöt", "Everyday.fistä"
			, "euro24.fi", "saldo.com", "pikavippi.fi", "hankinnat.", "comparehero.my", 
			"comparaJá.pt", "halomoney.co.id", "J.D.", "moneymax.ph", "moneyguru.co.th",
			"glossary.meta.description", "og.home.glossary.description", "glossary.page.title",
			"og.home.glossary.title");
	
	public static Predicate<String> FILTER_EXCLUDELIST = new Predicate<String>() {
	
		@Override
		public boolean apply(final String matchedKey){
			
			for(String excludeKey: excludeList){
				if(matchedKey.toLowerCase().contains(excludeKey.toLowerCase())){
					return false;
				}
			}
			
			return true;
		}
	};
	
	public static Predicate<String> FILTER_HTTP = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.toLowerCase().contains("http:") && !matchedKey.toLowerCase().contains("https:");
		}
	};
	
	public static Predicate<String> FILTER_JS = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.toLowerCase().contains(".js")
					&& !matchedKey.toLowerCase().contains("onerror")
					&& !matchedKey.toLowerCase().contains("onclick")
					&& !matchedKey.toLowerCase().contains("ng-click")
					&& !matchedKey.toLowerCase().contains("ng-show")
					&& !matchedKey.toLowerCase().contains("ng-model")
					&& !matchedKey.toLowerCase().contains("ng-class")
					&& !matchedKey.toLowerCase().contains("window")
					&& !matchedKey.toLowerCase().contains("history")
					&& !matchedKey.toLowerCase().contains("queryform")
					&& !matchedKey.toLowerCase().contains("preventdefault")
					&& !matchedKey.toLowerCase().contains("preventpropagation");
		}
	};
	
	public static Predicate<String> FILTER_IMAGE = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.toLowerCase().contains(".png") && !matchedKey.toLowerCase().contains(".icon") && !matchedKey.toLowerCase().contains("ico");
		}
	};
	
	public static Predicate<String> FILTER_CSS = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.toLowerCase().contains(".css")
					&& !matchedKey.toLowerCase().contains("color")
					&& !matchedKey.toLowerCase().contains("font-size")
					&& !matchedKey.toLowerCase().contains("font-weight")
					&& !matchedKey.toLowerCase().contains("margin")
					&& !matchedKey.toLowerCase().contains("height")
					&& !matchedKey.toLowerCase().contains("width")
					&& !matchedKey.toLowerCase().contains("background");
		}
	};
	
	public static Predicate<String> FILTER_NUMBER = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.matches("\\d+(\\.\\d+)*(\\.|!|\\*|\\))*");
		}
	};
	
	public static Predicate<String> FILTER_SPECIALCASE = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.matches("^([^@$�,%/]+\\.)+(\"?>)+$");
		}
	};
	
	public static Predicate<String> FILTER_BLANK = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !Checker.isBlank(matchedKey);
		}
	};
	
	public static Predicate<String> FILTER_CONSECUTIVEDOT = new Predicate<String>(){
		
		@Override
		public boolean apply(final String matchedKey){
			return !matchedKey.contains("..");
		}
	};
	
	private WebDriverUtils driver;
	
	public Checker(final WebDriverUtils driver) {
		
		this.driver = driver;
	}
	
	public static boolean isValidLinkOrAssert(final String url){
		
		return isValidLink(url) || isValidAsset(url);
	}
	
	
	public static boolean containsUrl(ConcurrentLinkedDeque<String> urlList, String url) {
		
		for(String url_temp: urlList) {
			if(UrlComparer.urlsMatch(url_temp, url)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidLink(final String url){
		
		return !Checker.isBlank(url) && (url.startsWith("http://") || url.startsWith("https://"));
	}
	
	public static boolean isValidLinkWithinSameDomain(final String url, final String domainUrl){
		
		return Checker.isValidLink(url)
				&& withinSameDomain(url, domainUrl);
	}
	
	public static boolean isValidLinkWithinSameDomainOrAsset(final String url, final String domainUrl){
		
		return (isValidLinkWithinSameDomain(url, domainUrl) || isValidAsset(url)) ? true : false;
	}

	public static boolean isValidAsset(final String url) {
		
		return url.endsWith(".ico") || url.endsWith(".png") || url.endsWith(".svg")
		|| url.endsWith(".js") || url.endsWith(".jpg")  
		|| url.contains("js?") || url.endsWith(".image") ;
	}
	
	public static boolean isValidLinkButNotAsset(final String url, final String domainUrl){
		
		return (Checker.isValidLinkWithinSameDomain(url, domainUrl) && !isValidAsset(url)) ? true : false;
	}
	
	public static boolean withinSameDomain(final String url, final String startingUrl) {
		
		if(url.startsWith(startingUrl)){
			return true;
		}
		
		if(url.startsWith(startingUrl.replaceAll("http://", "https://"))){
			return true;
		}
		
		if(url.startsWith(startingUrl.replaceAll("http://beta-staging", "http://www"))){
			return true;
		}
		
		if(url.startsWith(startingUrl.replaceAll("http://staging", "http://www"))){
			return true;
		}
		
		return false;
	}
	
	public boolean hasCommonErrorsInPage(){
		
		final By by = By.xpath("//body");
		final WebElement elem = driver.findElement(by);
		if(this.isEmptyPage(elem, by)){
			return false;
		}

		List<String> commonErrors = Lists.newArrayList("400", "404", "500", "505", "Not Found", "ERROR");
		
		return hasErrorsInPages(elem, by, commonErrors);
	}
	
	public List<String> findMissingTranslationsInPage(){
		
		List<String> matchedKeysFromHead = this.findMissingTranslationsInPageHead();
		List<String> matchedKeysFromBody = this.findMissingTranslationsInPageBody();
		return ListUtils.union(matchedKeysFromHead, matchedKeysFromBody);
	}
	
	public List<String> findMissingTranslationsInPageHead(){
		
		final By by = By.xpath("//head/*[not(self::style or self::link or self::script or self::noscript)]");
		final List<WebElement> elems = driver.findElements(by);
		
		List<String> matchedKeys = this.findAttributeKeysInWebElements(elems, 0, Checker.MISSING_KEY_PATTERN);
		
		return this.cleanupMatchedKeys(matchedKeys);
	}
	
	public List<String> findMissingTranslationsInPageBody(){
		
		final By by = By.xpath("//body");
		final WebElement elem = driver.findElement(by);
		
		List<String> matchedKeys = this.findAttributeKeysInWebElement(elem, by, Checker.MISSING_KEY_PATTERN, false);
		return this.cleanupMatchedKeys(matchedKeys);
	}
	
	public List<String> cleanupMatchedKeys(final List<String> matchedKeys){
		
		matchedKeys.removeAll(excludeList);
		return FluentIterable.from(matchedKeys).filter(Predicates.and(FILTER_HTTP, FILTER_JS, FILTER_EXCLUDELIST, FILTER_IMAGE, FILTER_CSS, FILTER_NUMBER, FILTER_SPECIALCASE, FILTER_BLANK, FILTER_CONSECUTIVEDOT)).toList();
	}
	
	public boolean hasErrorsInPages(final WebElement elem, final By by, final List<String> errors){
		
		boolean hasError = this.haveErrorsInPage(elem, by, errors);
		
		if(!hasError && driver.hasPopUpWin()){
			driver.switchToPopUpWin();
			hasError = this.haveErrorsInPage(driver.findElement(by), By.xpath("//body"), errors);
			driver.switchToBaseWin();
		}

		return hasError;
	}
	
	public List<String> findAttributeKeysInPages(final String pageSrc, final String pattern){
		
		return driver.matchStringPatternsInPageSource(pageSrc, pattern);
	}
	
	public List<String> findAttributeKeysInWebElements(final List<WebElement> elems, int startIndex, final String pattern){
		
		List<String> matchedKeys = Lists.newArrayList();
		int i = startIndex;
		
		try{
			for(; i < elems.size(); i ++){
				matchedKeys.addAll(driver.matchStringPatternsInOuterHTMLOfWebElement(elems.get(i), pattern));
			}	
		}catch(Exception e){
			matchedKeys.addAll(this.findAttributeKeysInWebElements(elems, i + 1, pattern));
		}
		
		return matchedKeys;
	}
	
	public List<String> findAttributeKeysInWebElement(final WebElement elem, final By by, final String pattern, final boolean isCheckOuterHTML){
		
		List<String> matchedKeys = driver.matchStringPatternsInWebElement(elem, by, pattern, isCheckOuterHTML);
		
		if(matchedKeys.size() == 0 && driver.hasPopUpWin()){
			driver.switchToPopUpWin();
			matchedKeys = driver.matchStringPatternsInWebElement(driver.findElement(by), by, pattern, isCheckOuterHTML);
			driver.switchToBaseWin();
		}

		return matchedKeys;
	}
	
	private boolean isEmptyPage(final WebElement elem, final By by){
		
		return driver.isBlankPage(elem, by);
	}
	
	
	private boolean haveErrorsInPage(final WebElement elem, final By by, final List<String> errors){
		
		boolean hasError = false;
		
		for(final String error: errors){
			hasError = this.haveErrorInPage(driver, elem, by, error);
			if(hasError){
				break;
			}
		}
		
		return hasError;
	}
	
	private boolean haveErrorInPage(final WebDriverUtils driver, final WebElement elem, final By by, final String error){
		
		
		if(error.split("\\s").length > 2 ){
			return driver.textPresentInPage(elem, by, error) ? true: false;	
		}else{
			final String pattern = "^" + error + "$";
			return driver.matchStringPatternsInWebElement(elem, by, pattern, false).size() > 0 ? true: false;
		}
		
	}

	public static final boolean isBlank(final String s) {

		return s == null || s.trim().equals("") || s.equalsIgnoreCase("null");
	}

	public static final boolean isNull(final String s) {

		return s == null || s.trim().equals("");
	}

	public static final boolean isEmpty(final Object[] s) {

		return s == null || s.length == 0;
	}
	
	public static final boolean isEmpty(final List<? extends Object> s){
		
		return s == null || s.size() == 0;
	}
}
