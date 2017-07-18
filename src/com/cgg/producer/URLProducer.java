package com.cgg.producer;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cgg.webCrawler.AbstractThread;

import utility.Checker;
import utility.IOUtils;



/** Apply BFS (or DFS) strategy to visit all links within a url, and check whether linked page contains errors/unsafe-data	 
 * This project employes several techniques: 
	1. Breadth-first search (or depth-first search) strategy; 
	2. Selenium interacts with HTML, e.g., parse html page to get all links; 
	click link to open a new window; 
	judge whether pages contain bugs (e.g., errors/unsafe-data);
	take screenshot for problematic pages;
 * @author martin.wang
 *
 */
public class URLProducer extends AbstractThread{

	protected int maxVisit = 200;
	protected int counter = 0;
	protected int maxVisitForSinglePage = 200;
	
	public URLProducer(){
		super();
	}
	
	public URLProducer(final URLProducerBuilder builder){

		super(builder);
		this.maxVisit = builder.getMaxVisit();
		this.counter = builder.getCounter();
		this.maxVisitForSinglePage = builder.getMaxVisitForSinglePage();
	}
	
	@Override
	protected boolean isInterestedURLToVisit(final String url, final String domainUrl){
				
		return Checker.isValidLinkButNotAsset(url, domainUrl) && !url.contains("blog"); 
	}
	
	@Override
	protected int visitLinks(int counter) throws Exception{
		
		while(!Thread.interrupted() && toVisitLinks.size() > 0){
			
			String toVisitLink = toVisitLinks.remove();
			
			if(!visitedLinks.contains(toVisitLink) && this.isInterestedURLToVisit(toVisitLink, domainUrl) && counter < maxVisit){
				
				counter ++;
				logger.info(this + ": Visiting (" + counter + "):" + toVisitLink);
				System.out.println(this + ":Visiting (" + counter + "):" + toVisitLink);
				
				visitedLinks.add(toVisitLink);
				driver.openURL(toVisitLink);
				driver.waitForPageLoad();
				
				By by = By.cssSelector("[href]");
				this.addEvents(by, 0, 0, maxVisitForSinglePage, toVisitLink);
				
				logger.info(this + ": Visited:" + visitedLinks.size() + ";toVisit:" + toVisitLinks.size() + "\n\n");
				System.out.println(this + ":Visited:" + visitedLinks.size() + ";toVisit:" + toVisitLinks.size() + "\n\n");
			}
		}
		
		return -1;
	}

	
	public void addEvents(final By by, final int startingIndex, int counter, final int maxVisit, final String currentURL){
		
		
		List<WebElement> links = driver.findElements(by);
		 
		for(int i = startingIndex; i < links.size() && counter < maxVisit; i ++){
			
			counter ++;
			String URL = driver.getAttributeValue(by, i, "href");
			final String url_in_utf8 = IOUtils.toUTF8String(URL); 
			if (this.isInterestedURLToVisit(url_in_utf8, domainUrl)
					&& !toVisitLinks.contains(url_in_utf8)
					&& !visitedLinks.contains(url_in_utf8)) {
				toVisitLinks.add(url_in_utf8);
			}
		}
	}
}
