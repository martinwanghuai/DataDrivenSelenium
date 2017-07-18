package com.cgg.consumer.keyFinder;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
public class KeyFinderThread extends SimpleThread {

	public KeyFinderThread(final Builder builder){

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

				List<String> matchedList = checker
						.findMissingTranslationsInPage();

				if (matchedList.size() > 0) {
					for (String missingKey : matchedList) {
						logger.info("Found missing translations in URL: "
								+ toVisitLink + ":" + missingKey);
						
						System.out.println("Found missing translations in URL: "
								+ toVisitLink + ":" + missingKey);
					}
				}
				counter++;
			}
		}
		return -1;
	}
}
