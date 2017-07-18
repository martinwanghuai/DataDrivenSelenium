package com.cgg.webCrawler;

import com.cgg.producer.Builder;

public class SimpleThread extends AbstractThread{

	public SimpleThread(final Builder builder){
		
		super(builder);
	}

	@Override
	protected int visitLinks(int linkNo) throws Exception {
		
		return -1;
	}

	@Override
	protected boolean isInterestedURLToVisit(String url, String domainUrl) {
		
		return true;
	}

}
