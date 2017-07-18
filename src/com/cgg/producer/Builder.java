package com.cgg.producer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;

public class Builder <T extends Builder>{
	
	private ConcurrentLinkedDeque<String> toVisitLinks = null;
	private ConcurrentLinkedDeque<String> visitedLinks = null;
	private String domainUrl;
	private Logger logger = null;
	private String logFile = null;
	private int threadID = 0, width = 0, height = 0;
	private boolean isMobile;
	private ConcurrentLinkedDeque<String> simpleVisitedLinks = null;
	private ConcurrentHashMap<String, List<String>> testingResult = null;
	
	public T threadID(final int threadID){
		
		this.threadID = threadID;
		return (T)this;
	}
	
	public T isMobile(final boolean isMobile){
		
		this.isMobile = isMobile;
		return (T)this;
	}
	
	public T logFile(final String logFile){
		
		this.logFile = logFile;
		return (T)this;
	}
	
	public T testingResult(final ConcurrentHashMap<String, List<String>> testingResult){
		
		this.testingResult = testingResult;
		return (T)this;
	}
	
	public T toVisitLinks(final ConcurrentLinkedDeque<String> toVisitLinks){
		
		this.toVisitLinks = toVisitLinks;
		return (T)this;
	}
	
	public T visitedLinks(final ConcurrentLinkedDeque<String> visitedLinks){
		
		this.visitedLinks = visitedLinks;
		return (T)this;
	}
	
	public T simpleVisitedLinks(final ConcurrentLinkedDeque<String> simpleVisitedLinks){
		
		this.simpleVisitedLinks = simpleVisitedLinks;
		return (T)this;
	}
	
	public T domainUrl(final String domainUrl){
		
		this.domainUrl = domainUrl;
		return (T)this;
	}
	
	public T logger(final Logger logger){
		
		this.logger = logger;
		return (T)this;
	}
	
	public T width(final int width){
		
		this.width = width;
		return (T)this;
	}
	
	public T height(final int height){
		
		this.height = height;
		return (T)this;
	}
	
	public int getThreadID(){
		return threadID;
	}
	
	public String getDomainUrl() {
		return domainUrl;
	}

	public Logger getLogger() {
		return logger;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean getIsMobile() {
		return isMobile;
	}
	
	public String getLogFile(){
		return logFile;
	}

	public ConcurrentLinkedDeque<String> getToVisitLinks() {
		return toVisitLinks;
	}

	public ConcurrentLinkedDeque<String> getVisitedLinks() {
		return visitedLinks;
	}

	public ConcurrentLinkedDeque<String> getSimpleVisitedLinks() {
		return simpleVisitedLinks;
	}
	
	public ConcurrentHashMap<String, List<String>> getTestingResult() {
		return testingResult;
	}
}
