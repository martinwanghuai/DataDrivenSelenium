package com.cgg.consumer.redirectionChecker;

import java.util.List;

import com.cgg.model.Platform;
import com.cgg.model.Redirection;
import com.cgg.producer.Builder;
import com.cgg.webCrawler.AbstractThread;
import com.cgg.webCrawler.SimpleThread;
import com.google.common.collect.Lists;

import utility.IOUtils;

public class RedirectionCheckerThread extends SimpleThread {

	public RedirectionCheckerThread(final Builder builder){
		super(builder);
	}

	@Override
	protected boolean isInterestedURLToVisit(final String url, final String domainUrl){
		
		return true;
	}
	
	@Override
	public int visitLinks(int counter) {
		
		while (!Thread.interrupted() && toVisitLinks.size() > 0) {

			Redirection redirectionEntry = Redirection.deseriablize(toVisitLinks.remove());

			if (!visitedLinks.contains(redirectionEntry.serialize())) {

				visitedLinks.add(redirectionEntry.serialize());
				checkRedirectionRule(redirectionEntry);
				counter++;
			}
		}
		return -1;
	}
	
	private void checkRedirectionRule(final Redirection redirectionEntry){
		
		
		List<String> domains = Lists.newArrayList();
		/*if(redirectionEntry.getPlatform() == Platform.LIBRIS || redirectionEntry.getPlatform() == Platform.LIBRIS_AND_CIAB){
//			domains.add("http://www.moneyhero.com.hk");
			domains.add("http://www.money101.com.tw");
		}*/
		if(redirectionEntry.getPlatform() == Platform.CIAB || redirectionEntry.getPlatform() == Platform.LIBRIS_AND_CIAB){
			domains.add(domainUrl);
			
			logger.info(this + " is checking(" + counter + "): " + redirectionEntry);
			System.out.println(this + " is checking(" + counter + "): " + redirectionEntry);
			
			for(final String domain: domains){
				String requestURL = domain + redirectionEntry.getFromURL();
				driver.openURL(requestURL);
				driver.explicitWait(1000);

				String actual = IOUtils.toUTF8String(driver.getCurrentUrl());
				String substring = actual.substring(actual.indexOf(domain) + domain.length());
				redirectionEntry.setActualToURL(substring);

				logger.info("Checking Result:" + redirectionEntry);
				System.out.println("Checking Result:" + redirectionEntry);
			}

		}

		
	}
}
