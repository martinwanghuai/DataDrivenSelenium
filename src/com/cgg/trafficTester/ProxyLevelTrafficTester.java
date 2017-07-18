package com.cgg.trafficTester;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Lists;

import utility.IOUtils;
import utility.WebDriverUtils;
import utility.WebDriverUtils.Type;

public class ProxyLevelTrafficTester implements Runnable{

	private int counter_CIAB = 0;
	private int counter_LIBRIS = 0;  
	private Logger log;
	private String ID;
	
	private String url;
	private int maxNo;
	private Type browserType;
	
	public ProxyLevelTrafficTester(final Logger log, final String ID, final Type browserType, final String url){
		
		this.log = log;
		this.ID = ID;
		this.url = url;
		this.browserType = browserType;
		
	}
	
	@Override
	public void run(){
		
		long startTime = System.currentTimeMillis();
		
		loopToTestTraffic(0, url);
		
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime) / (1000);
		
		log.info(this + ": Duration=" + duration + " secs");
		System.out.println(this + ": Duration=" + duration + " secs");

		
		DecimalFormat df = new DecimalFormat( "#,###,###,##0.00%" );
		
		System.out.println(this + ": CIAB:" + counter_CIAB + "("
				+ df.format(((double) counter_CIAB / (double) maxNo))
				+ "); LIBRIS:" + counter_LIBRIS + "("
				+ df.format(((double) counter_LIBRIS / (double) maxNo)) + ")");
		
		log.info(this + ":CIAB:" + counter_CIAB + "("
				+ df.format(((double) counter_CIAB / (double) maxNo))
				+ "); LIBRIS:" + counter_LIBRIS + "("
				+ df.format(((double) counter_LIBRIS / (double) maxNo)) + ")");
	}
	
	public void loopToTestTraffic(int i, final String url){
		
		try{
			while(true){
				WebDriverUtils driver = new WebDriverUtils(browserType, false);
				driver.openURL(url);
				if(browserType != Type.HtmlUnit){
					driver.waitForPageLoad();	
				}

				String currentUrl = IOUtils.toUTF8String(driver.getCurrentUrl());
				
				if(currentUrl.startsWith("http://beta")){
					counter_CIAB ++;
					/*int size = driver.findElements(By.xpath("//head/link[contains(@href, 'assets.ciab.compareglobal.co.uk')]")).size();
					System.out.println(this + ":" + i + ":" + currentUrl + ":CIAB: find assets.ciab.compareglobal.co.uk:" + size);*/
//					log.info(this+ ":" + i + ":" + currentUrl + ":CIAB: find assets.ciab.compareglobal.co.uk/:" + size);	
					log.info(this+ ":" + i + ":" + currentUrl);
					checkUserJourneys(driver, i, true, currentUrl);
					System.out.println(this + ":" + i + ": flip flopped");
					log.info(this + ":" + i + ": flip flopped");
//					checkCommonErrors(driver, i, true, "http://beta-staging.singsaver.com.sg/personal-loan/personal-instalment","http://beta-staging.singsaver.com.sg/credit-card/best-deals");
				}else{
					counter_LIBRIS ++;
					int size = driver.findElements(By.xpath("//head/link[contains(@href, '/build/assets/cgg-confidence-mix/css/')]")).size();
					System.out.println(this + ":" + i + ":" + currentUrl + ": LIBRIS: find /build/assets/cgg-confidence-mix/css/:" + size);
					log.info(this + ":" + i + ":" + currentUrl + ": LIBRIS: find /build/assets/cgg-confidence-mix/css/:" + size);
					/*checkUserJourneys(driver, i, false, currentUrl);
					System.out.println(this + ":" + i + ": flip flopped");
					log.info(this + ":" + i + ": flip flopped");*/
//					checkCommonErrors(driver, i, false, "http://dev.singsaver.com.sg/personal-loan/instalment","http://dev.singsaver.com.sg/credit-card/best-deals");
				}
//				driver.explicitWait(10*60*1000);//wait 10 mins
				driver.shutDown();
//				driver.deleteAllCookies();
				i ++;
			}
		}catch(Exception e){
			this.loopToTestTraffic(i + 1, url);
		}
	}
	
	private void checkUserJourneys(final WebDriverUtils driver, final int index, final boolean isCIAB, final String currentUrl){
		
		/*List<String> lists = isCIAB
				? Lists.newArrayList("http://beta-staging.singsaver.com.sg/personal-loan/personal-instalment",
						"http://beta-staging.singsaver.com.sg/credit-card/best-deals",
						"http://beta-staging.singsaver.com.sg/credit-card/dining")
				: Lists.newArrayList("http://staging.singsaver.com.sg/personal-loan/instalment",
						"http://staging.singsaver.com.sg/credit-card/best-deals",
						"http://staging.singsaver.com.sg/credit-card/dining");
		
		for(String url: lists){
			checkUserJourney(driver, index, isCIAB, url);
		}*/
		
		try{
			boolean hasFlipFlop = false;
			long start = System.currentTimeMillis();
			while(!hasFlipFlop && System.currentTimeMillis() - start < 120 * 60 * 1000 ){
				
//				List<String> lists = Lists.newArrayList(currentUrl);
				List<String> lists = isCIAB
						? Lists.newArrayList("http://beta-staging.moneyhero.com.hk/en/personal-loan/personal-instalment/results/",
								"http://beta-staging.moneyhero.com.hk/en/credit-card/best-deals/all/results/")
						: Lists.newArrayList("http://staging.moneyhero.com.hk/en/personal-loan/personal-instalment/",
								"http://staging.moneyhero.com.hk/en/credit-card/best-deals/");
				
				for(String url: lists){
					int size = checkUserJourney(driver, index, isCIAB, url);
					if(size == 0){
						hasFlipFlop = true;
						break;
					}
				}
			}			
		}catch(Exception e){
			checkUserJourneys(driver, index, isCIAB, currentUrl);
		}
	}

	private int checkUserJourney(final WebDriverUtils driver, final int index, final boolean isCIAB, final String url) {
		
		driver.openURL(url);
		System.out.println(this + ":" + index + ": visiting:" + url);
		log.info(this + ":" + index + ": visiting: " + url);
		
		if(browserType != Type.HtmlUnit){
			driver.waitForPageLoad();	
		}
		driver.explicitWait(3*1000); //wait 3 secs
		
		int size = 0;
		if(isCIAB){
			size = driver.findElements(By.xpath("//head/link[contains(@href, 'assets.ciab.compareglobal.co.uk')]")).size();
			System.out.println(this + ":" + index + ":" + url + ":CIAB: find assets.ciab.compareglobal.co.uk:" + size);
			log.info(this+ ":" + index + ":" + url + ":CIAB: find assets.ciab.compareglobal.co.uk/:" + size);
			
			if(size > 0){
				By by = By.xpath("//a[@class='cgg-button-action cgg-hidden-xs ng-binding']");
				List<WebElement> elems = driver.findElements(by);
				WebElement elem = elems.get(0);
				driver.clickButton(elem);
				
				System.out.println(this + ":" + index + ": click Apply button");
				log.info(this + ":" + index + ": click Apply button");
				driver.closeAllPopUpWins();
			}
			
		}else{
			size = driver.findElements(By.xpath("//head/link[contains(@href, '/build/assets/cgg-confidence-mix/css/')]")).size();
			
			System.out.println(this + ":" + index + ":" + url + ": LIBRIS: find /build/assets/cgg-confidence-mix/css/:" + size);
			log.info(this + ":" + index + ":" + url + ": LIBRIS: find /build/assets/cgg-confidence-mix/css/:" + size);
			
			if(size > 0){
				By by = By.xpath("//a[@class='goto_site btn btn-lg btn--cag-apply btn-compare-now goto_site apply-now']");
				List<WebElement> elems = driver.findElements(by);
				WebElement elem = elems.get(0);
				driver.clickButton(elem);	
				
				System.out.println(this + ":" + index + ": click Apply button");
				log.info(this + ":" + index + ": click Apply button");
				driver.closeAllPopUpWins();	
			}
		}
		return size;
	}
	
	
	private void checkElementsInPage(final WebDriverUtils driver, final int index, final boolean isCIAB, final String currentUrl){
		
		if(isCIAB){
			int size = driver.findElements(By.xpath("//head/link[contains(@href, 'assets.ciab.compareglobal.co.uk')]")).size();
			System.out.println(this + ":" + index + ":" + currentUrl + ":CIAB: find assets.ciab.compareglobal.co.uk:" + size);
			log.info(this+ ":" + index + ":" + currentUrl + ":CIAB: find assets.ciab.compareglobal.co.uk:" + size);	
		}else{
			int size = driver.findElements(By.xpath("//head/link[@href='/themes/cgg-confidence-blue/assets/iconia/CGG-icomoon/libris-style.css']")).size();
			System.out.println(this + ":" + index + ":" + currentUrl + ": LIBRIS: find libris-style.css:" + size);
			log.info(this + ":" + index + ":" + currentUrl + ": LIBRIS: find libris-style.css:" + size);
		}
	}
	
	private void checkCommonErrors(final WebDriverUtils driver, final int index, final boolean isCIAB, final String... toVisitLinks){

//		Checker checker = new Checker(driver);
		for(final String toVisitLink: toVisitLinks){
			driver.openURL(toVisitLink);
			driver.waitForPageLoad();
		
			/*if(checker.hasCommonErrorsInPage()){
				System.out.println(this + ":" + index +": found error in: " + toVisitLink);
				log.info(this + ":" + index + ": found error in: " + toVisitLink);
			}else{
				checkElementsInPage(driver, index, isCIAB, toVisitLink);
			}*/
			
			checkElementsInPage(driver, index, isCIAB, toVisitLink);
		}
	}
	
	public String toString(){
		
		String s = VWOLevelTrafficTestDriver.simpleDateFormat.format(System.currentTimeMillis());
		return this.ID + ":" + s ;
	}
}
