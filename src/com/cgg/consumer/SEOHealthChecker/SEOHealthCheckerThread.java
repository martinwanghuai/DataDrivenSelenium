package com.cgg.consumer.SEOHealthChecker;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cgg.producer.Builder;
import com.cgg.webCrawler.SimpleThread;

import utility.Checker;

public class SEOHealthCheckerThread extends SimpleThread {

	public SEOHealthCheckerThread(final Builder builder){

		super(builder);
	}

	@Override
	protected boolean isInterestedURLToVisit(final String url, final String domainUrl){
				
		return Checker.isValidLinkButNotAsset(url, domainUrl);
	}
	
	@Override
	public int visitLinks(int counter) {
		
		while (!Thread.interrupted() && toVisitLinks.size() > 0) {

			String toVisitLink = toVisitLinks.remove();

			if (!visitedLinks.contains(toVisitLink)
					&& this.isInterestedURLToVisit(toVisitLink, domainUrl)) {

				logger.info(this + " is checking(" + counter + "): " + toVisitLink);
				System.out.println(this + " is checking(" + counter + "): " + toVisitLink);
				driver.openURL(toVisitLink);
				visitedLinks.add(toVisitLink);
				driver.waitForPageLoad();
				driver.explicitWait(5*1000);

				String errMsg = "";
				int size = driver.findElements(By.xpath("//h1|//H1")).size();
				if(size != 1){
					errMsg = "H1 in URL: " + size + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}
				
				size = driver.findElements(By.xpath("//title")).size();
				if(size != 1){
					errMsg = "Title in URL: " + size + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}
				
				size = driver.findElements(By.xpath("//meta[@name='description']")).size();
				if(size != 1){
					errMsg = "Meta description in URL: " + size + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}
				
				size = driver.findElements(By.xpath("//meta[@name='robots' and contains(@content,'noindex')]")).size();
				if(size != 0){
					errMsg = "Meta non-indexed robots in URL: " + size + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}

				size = driver.findElements(By.xpath("//meta[@name='keywords']")).size();
				if(size != 0){
					errMsg = "Meta non-indexed robots in URL: " + size + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}

				int responseCode = this.containCannonicalRedirectLoop();
				if(responseCode != 200){
					errMsg = "Cannonical Redirect Loop in URL:" + responseCode + "\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}
				
				if(this.missOpenGraph()){
					errMsg = "Miss Open Graph (og:image) in URL\n";
					logger.info(errMsg);
					System.out.println(errMsg);
					addTestingResult(toVisitLink, errMsg);
				}
				
				counter++;
			}
		}
		return -1;
	}

	public void addTestingResult(String toVisitLink, String errMsg) {
		List<String> values;
		if (testingResult.get(toVisitLink) == null) {
			values = new ArrayList<String>();
			values.add(errMsg);
		} else {
			values = testingResult.get(toVisitLink);
			values.add(errMsg);
		}
		testingResult.put(toVisitLink, values);
	}
	
	public int containCannonicalRedirectLoop(){
		
		WebElement elem = driver.findElement(By.xpath("//link[@rel='canonical']"));
		String link = elem.getAttribute("href");
		return driver.checkUrl(link);
	}
	
	public boolean missOpenGraph(){
		
		boolean missOpenGraph = false;
		By by = By.xpath("//meta[@property='og:image']");
		if(driver.isElementPresent(by)){
			WebElement elem = driver.findElement(by);
			String url = elem.getAttribute("content");
			int responseCode = driver.checkUrl(url);
			missOpenGraph = (responseCode == 404);
		}
		
		return missOpenGraph;
	}
	
	
}
