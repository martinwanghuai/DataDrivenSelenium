package com.cgg.consumer.JSErrorFinder;

import java.util.Date;
import java.util.logging.Level;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.cgg.producer.Builder;
import com.cgg.webCrawler.SimpleThread;

import utility.Checker;

/**
 * This is a concurrent version of WebCrawler which aims to reduce running time.
 * However, this class is different WebCrawler since it loads link data from
 * external files while WebCrawler collects link data (via DFS/BFS) at runtime.
 * 
 * @author martin.wang
 *
 */
public class JSErrorFinderThread extends SimpleThread {

	public JSErrorFinderThread(final Builder builder){

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

				System.out.println(this + " is checking(" + counter + "): " + toVisitLink);
				driver.openURL(toVisitLink);
				visitedLinks.add(toVisitLink);
				driver.waitForPageLoad();
				driver.explicitWait(30*1000); //wait js events to execute
				
				//For Chrome
				StringBuilder sb = new StringBuilder();
				
				LogEntries logEntries = driver.getWebDriver_existing().manage().logs().get(LogType.BROWSER);
				if(logEntries.getAll().size() > 0){
					
					for (LogEntry entry : logEntries) {
						if(entry.getLevel() == Level.SEVERE){
							String JSError = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage();
							sb.append(JSError).append("%");	
						}
					}

					System.out.println(this + " Found JavaScript Error in URL: "
							+ toVisitLink);
					logger.info("Found JavaScript Error in URL& "
							+ toVisitLink + "&" + sb.toString() + "\n");
				}
				
				counter++;
			}
		}
		return -1;
	}
}
