package com.cgg.webCrawler;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;

import com.cgg.producer.Builder;

import utility.Checker;
import utility.WebDriverUtils;

public abstract class AbstractThread implements Runnable{
	
	protected int threadID = 0, weight = 0, height = 0;
	protected ConcurrentLinkedDeque<String> toVisitLinks = new ConcurrentLinkedDeque<String>();
	protected ConcurrentLinkedDeque<String> visitedLinks = new ConcurrentLinkedDeque<String>();
	protected ConcurrentLinkedDeque<String> simpleVisitedLinks = new ConcurrentLinkedDeque<String>();
	protected ConcurrentHashMap<String, List<String>> testingResult = new ConcurrentHashMap<String, List<String>>();
	
	protected WebDriverUtils driver = null;
	protected Checker checker = null;
	protected String domainUrl;
	protected Logger logger = null;

	protected abstract int visitLinks(int linkNo) throws Exception;
	protected abstract boolean isInterestedURLToVisit(final String url, final String domainUrl);
	
	protected int counter = 0;

	private void setup(final ConcurrentLinkedDeque<String> toVisitLinks, final ConcurrentLinkedDeque<String> visitedLinks,
			final ConcurrentLinkedDeque<String> simpleVisitedLinks,
			final ConcurrentHashMap<String, List<String>> testingResult,
			final int threadID, final String domainUrl, final Logger logger) {

		checker = new Checker(driver);
		this.toVisitLinks = toVisitLinks;
		this.visitedLinks = visitedLinks;
		this.simpleVisitedLinks = simpleVisitedLinks;
		this.testingResult = testingResult;
		this.threadID = threadID;
		this.domainUrl = domainUrl;
		this.logger = logger;
	}

	protected AbstractThread(){
		
	}
	
	protected AbstractThread(final Builder builder){
		
		driver = builder.getWidth() != 0 && builder.getHeight() != 0 ? new WebDriverUtils(builder.getWidth(), builder.getHeight()): new WebDriverUtils();
		setup(builder.getToVisitLinks(), builder.getVisitedLinks(), builder.getSimpleVisitedLinks(), builder.getTestingResult(), builder.getThreadID(), builder.getDomainUrl(), builder.getLogger());
	}
	
	@Override
	public void run(){
		
		long startTime = System.currentTimeMillis();
		this.visitLinks_robust();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime) / (1000);
		
		logger.info(this + ": Duration=" + duration + " secs");
		System.out.println(this + ": Duration=" + duration + " secs");
		
		driver.closeAllWins();
	}
	
	public void visitLinks_robust() {

		try {
			counter = visitLinks(counter);
		} catch (Exception e) {
			
			if(counter > -1){
				logger.error(e);
				e.printStackTrace();
				visitLinks_robust();	
			}
		}
	}
	
	public static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss z");
	
	public String toString() {

		SimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
		String s = SimpleDateFormat.format(System.currentTimeMillis());
		return "[Thread " + this.threadID + ":" + s + "]" ;
	}
	public WebDriverUtils getDriver() {
		return driver;
	}
	public void setDriver(WebDriverUtils driver) {
		this.driver = driver;
	}
	public int getThreadID() {
		return threadID;
	}
	public void setThreadID(int threadID) {
		this.threadID = threadID;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public ConcurrentLinkedDeque<String> getToVisitLinks() {
		return toVisitLinks;
	}
	public void setToVisitLinks(ConcurrentLinkedDeque<String> toVisitLinks) {
		this.toVisitLinks = toVisitLinks;
	}
	public ConcurrentLinkedDeque<String> getVisitedLinks() {
		return visitedLinks;
	}
	public void setVisitedLinks(ConcurrentLinkedDeque<String> visitedLinks) {
		this.visitedLinks = visitedLinks;
	}
	public ConcurrentLinkedDeque<String> getSimpleVisitedLinks() {
		return simpleVisitedLinks;
	}
	public void setSimpleVisitedLinks(ConcurrentLinkedDeque<String> simpleVisitedLinks) {
		this.simpleVisitedLinks = simpleVisitedLinks;
	}
	public Checker getChecker() {
		return checker;
	}
	public void setChecker(Checker checker) {
		this.checker = checker;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
}
