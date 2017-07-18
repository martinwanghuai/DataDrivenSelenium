package com.cgg.consumer.stickyUserDetector;

import com.cgg.producer.Builder;
import com.cgg.webCrawler.SimpleThread;

import utility.Checker;

public class StickyUserDetectorThread extends SimpleThread {

	public StickyUserDetectorThread(final Builder<Builder> builder){
		
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

				visitedLinks.add(toVisitLink);
				
				String content = driver.getResponseHtml(toVisitLink);
				Boolean isCIAB = this.isCIAB(content);
				String result = isCIAB ? "CIAB": "LIBRIS";
				logger.info(this +";" + toVisitLink + ";" + result);
				System.out.println(this +";" + toVisitLink + ";" + result);
			}
		}
		return -1;
	}
	
	public boolean isCIAB(final String content){

		return content.contains("assets.ciab.compareglobal.co.uk") ? true: false;
	}
}
