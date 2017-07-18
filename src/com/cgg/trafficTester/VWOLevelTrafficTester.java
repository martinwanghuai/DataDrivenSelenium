package com.cgg.trafficTester;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.google.common.collect.Lists;

import utility.WebDriverUtils;
import utility.WebDriverUtils.Type;

public class VWOLevelTrafficTester implements Runnable{

	private int counter_CIAB = 0;
	private int counter_LIBRIS = 0;  
	private Logger log;
	private String ID;
	
	private List<String> urls;
	private int maxNo;
	private Type browserType;
	
	public VWOLevelTrafficTester(final Logger log, final String ID, final Type browserType, final String... urls){
		
		this.log = log;
		this.ID = ID;
		this.urls = Lists.newArrayList(urls);
		this.browserType = browserType;
		
	}
	
	@Override
	public void run(){
		
		long startTime = System.currentTimeMillis();
		
		loopToTestTraffic(0, urls);
		
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
	
	public void loopToTestTraffic(int i, final List<String> urls){
		
		try{
			while(true){
				WebDriverUtils driver = new WebDriverUtils(browserType, false);
				final String url = urls.get(i % urls.size());
				driver.openURL(url);
				if(browserType != Type.HtmlUnit){
					driver.waitForPageLoad();	
				}

				String currentUrl = driver.getWebDriver_existing().getCurrentUrl();
				
				if(currentUrl.startsWith("http://beta")){
					counter_CIAB ++;
					this.checkElementsInPage(driver, i, true, currentUrl);
					i ++;
					driver.shutDown();
				}else if(currentUrl.startsWith("http://dev.singsaver.com.sg")){
					counter_LIBRIS ++;
					this.checkElementsInPage(driver, i, false, currentUrl);
					i ++;
					driver.shutDown();
				}
			}
		}catch(Exception e){
			this.loopToTestTraffic(i + 1, urls);
		}
	}
	
	private void checkElementsInPage(final WebDriverUtils driver, final int index, final boolean isCIAB, final String currentUrl){
		
		if(isCIAB){
			int size = driver.findElements(By.xpath("//head/link[contains(@href, 'cloudfront.net/singapore/singsaver.com.sg/production/global/')]")).size();
			System.out.println(this + ":" + index + ":" + currentUrl + ":CIAB: find ciab-style:" + size);
			log.info(this+ ":" + index + ":" + currentUrl + ":CIAB: find ciab-style:" + size);	
		}else{
			int size = driver.findElements(By.xpath("//head/link[@href='/themes/cgg-confidence-blue/assets/iconia/CGG-icomoon/libris-style.css']")).size();
			System.out.println(this + ":" + index + ":" + currentUrl + ": LIBRIS: find libris-style:" + size);
			log.info(this + ":" + index + ":" + currentUrl + ": LIBRIS: find libris-style:" + size);
		}
	}
	
	public String toString(){
		
		String s = VWOLevelTrafficTestDriver.simpleDateFormat.format(System.currentTimeMillis());
		return this.ID + ":" + s ;
	}
}
