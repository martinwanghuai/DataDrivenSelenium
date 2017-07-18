package com.cgg.producer;

import org.openqa.selenium.By;

import utility.Constant;
import utility.WebDriverUtils;

public class LIBRISURLProducer extends URLProducer {

	public static boolean homePageIsChecked = false;
	
	public LIBRISURLProducer(final URLProducerBuilder builder){

		super(builder);
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


}
