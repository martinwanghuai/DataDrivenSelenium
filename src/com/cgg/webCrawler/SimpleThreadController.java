package com.cgg.webCrawler;

import com.cgg.producer.Builder;

public class SimpleThreadController extends AbstractThreadController {

	public SimpleThreadController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	public Thread getProducerThread(Builder builder) {
		return null;
	}

	@Override
	public Thread getConsumerThread(Builder builder) {
		return null;
	}

	@Override
	public void reportResults(Builder result) {

	}

}
