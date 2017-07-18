package com.cgg.webCrawler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;
import org.testng.collections.Lists;

import com.cgg.consumer.keyFinder.KeyFinderThread;
import com.cgg.producer.Builder;
import com.cgg.producer.URLProducer;
import com.cgg.producer.URLProducerBuilder;

import utility.IOUtils;
import utility.Log;

public abstract class AbstractThreadController {
	
	protected String startingUrl;
	protected boolean isMobile;
	protected boolean needToCollectUrls;
	protected String fileSuffix;
	protected String saveFile;
	protected int threadNo = 6;
	protected int maxCount = 1000;
	
	public AbstractThreadController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {

		this.startingUrl = startingUrl;
		this.isMobile = isMobile;
		this.needToCollectUrls = needToCollectUrls;
		this.fileSuffix = fileSuffix;
		this.saveFile = saveFile;
		this.threadNo = threadNo;
		this.maxCount = maxCount;
	}
	
	public abstract Thread getProducerThread(final Builder builder);
	public abstract Thread getConsumerThread(final Builder builder);
	public abstract void reportResults(final Builder result);
		
	public static String extractFileSuffixFromUrl(final String Url){
		
		if(Url.startsWith("http://")){
			return Url.replace("http://", "_").replace("/", "_");	
		}else if(Url.startsWith("https://")){
			return Url.replace("https://", "_").replace("/", "_");
		}
		return Url;
		
	}
	
	public void visitPagesToValidate() {

		if (needToCollectUrls) {
			startProducerThreads();
		}

		// 2. Visit all urls to validate
		final Builder result = this.startConsumerThreads();
		
		// 3. re-format checking results
		this.reportResults(result);
	}
	
	protected void beforeProducerThread(Builder builder) {
		
		try{
			//Get enough contents for producer threads 
			Thread thread = this.getProducerThread(builder.threadID(0));
			thread.start();	
			while(builder.getToVisitLinks().size() < threadNo){
				Thread.sleep(10 * 1000);	
			}
			 
			thread.interrupt();			
		}catch(Exception e){
			 e.printStackTrace();
		}
	}

	private void startProducerThreads() {
		
		try {
			ConcurrentLinkedDeque<String> toVisitLinks = new ConcurrentLinkedDeque<String>();
			toVisitLinks.addFirst(startingUrl);
			ConcurrentLinkedDeque<String> visitedLinks = new ConcurrentLinkedDeque<String>();
			ConcurrentLinkedDeque<String> simpleVisitedLinks = new ConcurrentLinkedDeque<String>();
			String logFile = saveFile.replace(".txt", ".log");
			final Logger logger = Log.getLoggerForClass(URLProducer.class.getName(), logFile, false);
			
			URLProducerBuilder builder = new URLProducerBuilder().toVisitLinks(toVisitLinks)
					.visitedLinks(visitedLinks).simpleVisitedLinks(simpleVisitedLinks).domainUrl(startingUrl).logger(logger).logFile(logFile)
					.isMobile(isMobile).maxVisit(maxCount);
			
			beforeProducerThread(builder);

			List<Thread> threadPool = Lists.newArrayList();
			for(int i = 0; i < threadNo; i ++){
				Thread thread = this.getProducerThread(builder.threadID(i));
				thread.start();	
				threadPool.add(thread);
			}

			for(Thread t : threadPool) {
			      t.join();
			}	

			afterProducerThread(builder);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	protected void afterProducerThread(URLProducerBuilder builder) {
		
		StringBuilder links = new StringBuilder();
		for(String visitedLink: builder.getVisitedLinks()){
			links.append(visitedLink.toString()).append("\n");
		}
		IOUtils.saveIntoFile(links.toString(), saveFile);
		
		builder.getLogger().info("Finish visiting URLs, pls refer to the following file for url list:" + saveFile);
		System.out.println("Finish visiting URLs, pls refer to the following file for url list:" + saveFile);
	}

	public Builder startConsumerThreads(){
		
		List<String> links = IOUtils.loadExcelFile(saveFile);
		ConcurrentLinkedDeque<String> toVisitLinks = new ConcurrentLinkedDeque<String>();
		toVisitLinks.addAll(links);
		final String logFile = saveFile.replace(".txt", "_CheckResult.log");
		IOUtils.deleteFile(logFile);
		
		final Logger logger = Log.getLoggerForClass(KeyFinderThread.class.getName(),
				logFile, true);
		
		Builder builder = new Builder().toVisitLinks(toVisitLinks)
				.visitedLinks(new ConcurrentLinkedDeque<String>())
				.simpleVisitedLinks(new ConcurrentLinkedDeque<String>()).domainUrl(startingUrl)
				.testingResult(new ConcurrentHashMap<String, List<String>>())
				.logger(logger)
				.logFile(logFile)
				.isMobile(isMobile);
		
		List<Thread> threadPool = createConsumerThreadPool(builder);
		
		try{
			for(Thread t : threadPool) {
		          t.join();
		     }	
			
			System.out.println("Finish checking all URLs, Pls find log file in:" + logFile);
			logger.info("Finish checking all URLs");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return builder;
	}
	
	protected List<Thread> createConsumerThreadPool(Builder builder) {
		
		List<Thread> threadPool = Lists.newArrayList();
		int maxThreads = builder.getToVisitLinks().size() > 0 && builder.getToVisitLinks().size() < threadNo ? builder.getToVisitLinks().size(): threadNo; 
		
		int i = 1;
		
		for(; i < maxThreads; i ++){
			Thread thread = this.getConsumerThread(builder.threadID(i));
			thread.start();
			threadPool.add(thread);
		}
		
		//for last one
		Thread thread = this.getConsumerThread(builder.threadID(i));
		thread.start();
		threadPool.add(thread);
		
		return threadPool;
	}

}
