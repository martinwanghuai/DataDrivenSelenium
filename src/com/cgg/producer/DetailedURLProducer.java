package com.cgg.producer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

import utility.Checker;
import utility.IOUtils;
import utility.WebDriverUtils;

public class DetailedURLProducer extends URLProducer {

	public static List<By> eventNames = Lists.newArrayList(/*By.cssSelector("[ng-click]"), By.cssSelector("[onclick]"),*/ 
			By.cssSelector("[href]"), By.xpath("//img"), By.cssSelector("[content]"));
	
	public DetailedURLProducer(){
		super();
	}
	
	public DetailedURLProducer(final URLProducerBuilder builder){

		super(builder);
		
	}

	public int extractNumberFromString(final String str){
		
		Pattern pattern = Pattern.compile("\\[([0-9]+)\\]");
		Matcher matcher = pattern.matcher(str);
		return matcher.find()? Integer.parseInt(matcher.group(1)) : -1 ;
	}
	
	public boolean isVisitedLink(final String toVisitLink){
		
		if(!this.isUrl(toVisitLink)){
			
			String[] urls = toVisitLink.split(";");
			int i = 0;
			for(; i < urls.length - 1; i ++){
				final String url = urls[i];
				this.clickEvents(driver, url);
			}
			
			return simpleVisitedLinks.contains(this.getOuterHtml(driver, urls[i]));	
		}else{
			return simpleVisitedLinks.contains(this.getLastUrl(toVisitLink));
		}
	}
	
	public void updateVisitedLinks(final String toVisitLink){
		
		if(!this.isUrl(toVisitLink)){
			String[] urls = toVisitLink.split(";");
			
			int i = 0;
			for(; i < urls.length - 1; i ++){
				final String url = urls[i];
				this.clickEvents(driver, url);
			}
			
			visitedLinks.add(this.getOuterHtml(driver, urls[i]));	
			simpleVisitedLinks.add(this.getOuterHtml(driver, urls[i]));
		}else{
			visitedLinks.add(toVisitLink);
			simpleVisitedLinks.add(this.getLastUrl(toVisitLink));
		}
	}
	
	public String getLastUrl(final String toVisitLink){
		
		String[] urls = toVisitLink.split(";");
		return urls[urls.length - 1];
	}
	
	public boolean isUrl(final String toVisitLink){
		
		return !toVisitLink.toLowerCase().contains("[ng-click]") && !toVisitLink.toLowerCase().contains("[onclick]"); 
	}
	
	public void openURL(final WebDriverUtils driver, final String toVisitLink){
		
		if(this.isUrl(toVisitLink)){
			clickEvents(driver, this.getLastUrl(toVisitLink), true, 0);
		}else{
			String[] urls = toVisitLink.split(";");
			for(final String url: urls){
				clickEvents(driver, url, true, 0);
			}	
		}
	}

	public String getOuterHtml(final WebDriverUtils driver, final String url){
		
		String outerHTML = "";
		if(url.toLowerCase().contains("[ng-click]")){
			int index = extractNumberFromString(url.toLowerCase());
			WebElement elem = driver.findElements(By.cssSelector("[ng-click]")).get(index);
			final String html = elem.getAttribute("outerHTML");
			outerHTML = html;
		}else if(url.toLowerCase().contains("[onclick]")){
			int index = extractNumberFromString(url.toLowerCase());
			WebElement elem = driver.findElements(By.cssSelector("[onclick]")).get(index);
			final String html = elem.getAttribute("outerHTML");
			outerHTML = html;
		}else{
			outerHTML = url;
		}
		
		return outerHTML;
	}
	
	
	public void clickEvents(final WebDriverUtils driver, final String url) {
		this.clickEvents(driver, url, false, 0);
	}
	
	public void clickEvents(final WebDriverUtils driver, final String url, final boolean verbose, final int counter) {
		
		try{
			if(url.toLowerCase().contains("[ng-click]")){
				int index = extractNumberFromString(url.toLowerCase());
				WebElement elem = driver.findElements(By.cssSelector("[ng-click]")).get(index);
				driver.clickButton(elem);
				driver.waitForPageLoad();
				
				if(verbose){
					final String html = elem.getAttribute("outerHTML");
					logger.info(this +":" + html);
					System.out.println(this + ":"+html);	
				}
			}else if(url.toLowerCase().contains("[onclick]")){
				int index = extractNumberFromString(url.toLowerCase());
				WebElement elem = driver.findElements(By.cssSelector("[onclick]")).get(index);
				driver.clickButton(elem);
				driver.waitForPageLoad();
				
				if(verbose){
					final String html = elem.getAttribute("outerHTML");
					logger.info(this +":" +html);
					System.out.println(this + ":"+html);	
				}
				
				
			}else{
				driver.openURL(url);
				driver.waitForPageLoad();
				if(verbose){
					logger.info(this +":" +url);
					System.out.println(this + ":" + url);	
				}
			}	
		}catch(StaleElementReferenceException e1){
			logger.error(e1);
			
			if(counter < 5){
				driver.explicitWait(1000);
				this.clickEvents(driver, url, verbose, counter + 1);	
			}
			
		}
		
	}
	
	public boolean isInterestedURLToConsume(final String url){

		String lastUrl = this.getLastUrl(url);
		return this.isUrl(lastUrl) ? Checker.isValidLinkOrAssert(lastUrl)
				&& !lastUrl.contains("blog") && !toVisitLinks.contains(url) : true;
	}
	
	@Override
	public boolean isInterestedURLToVisit(final String url, final String domainUrl){

		String lastUrl = this.getLastUrl(url);
		return this.isUrl(lastUrl) ? Checker.isValidLinkWithinSameDomain(lastUrl, domainUrl)
				&& !lastUrl.contains("blog") && !toVisitLinks.contains(url) : true;
	}
	
	@Override
	public int visitLinks(int counter) throws Exception {
		
		while(!Thread.interrupted() && toVisitLinks.size() > 0){
			
			String toVisitLink = toVisitLinks.remove();
			
			if(!this.isVisitedLink(toVisitLink) && counter < maxVisit){

				if(this.isInterestedURLToVisit(toVisitLink, domainUrl)){
					counter ++;
					logger.info(this + ":Visiting (" + counter + "):" + toVisitLink);
					System.out.println(this + ":Visiting (" + counter + "):" + toVisitLink);
					this.updateVisitedLinks(toVisitLink);
					
					this.openURL(driver, toVisitLink);
					driver.waitForPageLoad();
					
					this.addEvents(eventNames, 0, 0, maxVisitForSinglePage, toVisitLink);
					
					logger.info(this + ":Visited:" + visitedLinks.size() + ";toVisit:" + toVisitLinks.size() + "\n\n");
					System.out.println(this + ": Visited:" + visitedLinks.size() + ";toVisit:" + toVisitLinks.size() + "\n\n");	
				}else if(this.isInterestedURLToConsume(toVisitLink)){
					this.updateVisitedLinks(toVisitLink);
				}
			}
		}
		
		return -1;
	}
	
	
	public void addEvents(final List<By> eventNames, final int startingIndex, int counter, final int maxVisit, final String currentURL){

		
		for(int k = 0; k < eventNames.size(); k ++){
			
			By eventName = eventNames.get(k);
			if(eventName.toString().contains("href")){
				// save all urls in current page
				addEvents(counter, maxVisit, currentURL, eventName, "href");
			}else if(eventName.toString().contains("img")){ 
				//save all imgs in current page
				addEvents(counter, maxVisit, currentURL, eventName, "src");
			} else if(eventName.toString().contains("content")){
				//save all SEO keys in current page
				addEvents(counter, maxVisit, currentURL, eventName, "content");
			}
		}
	}
	
	public String getUrlPath(final String currentUrl, final String url){

		return currentUrl + ";" + url;
	}
	
	public void addNgClickEvent(int counter, final int maxVisit, final String currentURL, int i, WebElement link){
		
		toVisitLinks.add(currentURL + ";[ng-click]["+i+"]");
	}
	
	public void addOnClickEvent(int counter, final int maxVisit, final String currentURL, int i, WebElement link){
		
		toVisitLinks.add(currentURL + ";[onclick]["+i+"]");
	}

	public void addEvents(int counter, final int maxVisit, final String currentURL, By by, final String contributeName) {
		
		List<WebElement> links = driver.findElements(by);
		for(int index = 0; index < links.size(); index ++){
			String url = driver.getAttributeValue(by, index, contributeName);
			String UrlPath = IOUtils.toUTF8String(this.getUrlPath(currentURL, url));
			if (this.isInterestedURLToConsume(UrlPath)
					&& !this.isVisitedLink(UrlPath)) {
				toVisitLinks.add(UrlPath);
			}
		}
	}
}

