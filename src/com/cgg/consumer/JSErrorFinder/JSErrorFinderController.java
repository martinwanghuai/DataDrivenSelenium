package com.cgg.consumer.JSErrorFinder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cgg.producer.Builder;
import com.cgg.producer.URLProducer;
import com.cgg.producer.URLProducerBuilder;
import com.cgg.webCrawler.AbstractThreadController;
import com.cgg.webCrawler.SimpleThreadController;
import com.google.common.collect.Lists;

import utility.Checker;
import utility.Constant;
import utility.IOUtils;
import utility.Log;

public class JSErrorFinderController extends SimpleThreadController{

	public JSErrorFinderController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	public Thread getProducerThread(Builder builder){
		
		return new Thread(builder.getIsMobile() ? new URLProducer((URLProducerBuilder)builder.width(743).height(288))
				: new URLProducer((URLProducerBuilder)builder));
	}
	
	@Override
	public Thread getConsumerThread(Builder builder){
		
		return new Thread(builder.getIsMobile() ? 
				new JSErrorFinderThread(builder.width(743).height(288))
				: new JSErrorFinderThread(builder.width(0).height(0)));
	}
	
	private String extractMissingKey(final String originalKey){
		
		String result = originalKey;
		int startIndex = originalKey.indexOf("\"");
		if(startIndex > -1){
			result = result.substring(startIndex + "\"".length());	
		}
		
		int endIndex = result.indexOf("\"");
		if(endIndex > -1){
			result = result.substring(0, endIndex);
		}
		
		startIndex = result.indexOf(">");
		if(startIndex > -1){
			result = result.substring(startIndex + ">".length());
		}
		
		endIndex = result.indexOf("<");
		if(endIndex > -1){
			result = result.substring(0, endIndex);
		}
		
		return result.trim();
	}
	
	@Override
	public void reportResults(final Builder builderResult){

		String resultFile = builderResult.getLogFile();
		final Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> JSErrorFile = IOUtils.loadExcelFile(resultFile);
		
		for(final String JSErrorString: JSErrorFile){
			if(!Checker.isBlank(JSErrorString)){
				final String[] strs = JSErrorString.split("&");
				if(strs.length == 3){
					
					final String url = strs[1].trim();
					final String[] JSErrorStrs = strs[2].trim().split("%");
					
					List<String> JSErrorList = Lists.newArrayList();
					if(result.containsKey(url)){
						JSErrorList = result.get(url);
					}
					
					for(String JSError: JSErrorStrs){
						if(!JSErrorList.contains(JSError)){
							JSErrorList.add(JSError);
						}	
					}
					result.put(url, JSErrorList);
				}	
			}
		}
		
		final StringBuilder sb = new StringBuilder();
		
		if(result.size() > 0){
			sb.append("Please find here the following (" + result.size() + ") pages with JavaScritp Errors:\n\n");
			Iterator<String> ite = result.keySet().iterator();
			
			int i = 1;
			while(ite.hasNext()){
				final String url = ite.next();

				List<String> JSErrorList = result.get(url);
				
				if(JSErrorList.size() > 0){
					String error ="";
					int counter = 0;
					for (final String JSError : JSErrorList) {
						if(JSError.contains("SEVERE") && !JSError.contains(".svg")){
							error += JSError + "\n\n";
							counter ++;
						}
					}
					if(counter > 0){
						sb.append( i + ") ").append( url).append("\nFound the following ").append(counter).append(" SEVERE JavaScript Errors:\n");
						sb.append(error);
						sb.append("\n\n\n");
						i++;	
					}
				}
			}
			
			final String destFile_str = resultFile.replace(".log", ".txt");
			IOUtils.saveIntoFile(sb.toString(), destFile_str);
			
			System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str);
		}else{
			System.out.println("Good job, NO JavaScript error found!!!");
		}
	}
	
	public static void main(String[] args){
		
		if(args.length == 3){
			
			Constant.ENABLE_BROWSER_MOB = false;
			Constant.ENABLE_FUNNEL_TESTER = false;
			
			long start = System.currentTimeMillis();
			int threadNo = 6;
			final int maxCount = 1000;
			
			final String startingUrl = args[0];
			final boolean isMobile = Boolean.parseBoolean(args[1]);
			final boolean needToCollectUrls = Boolean.parseBoolean(args[2]);
			final String fileSuffix = AbstractThreadController.extractFileSuffixFromUrl(startingUrl);
			final String saveFile = System.getProperty("user.dir") +"/src/JSErrorFinder/JSErrorFinder" + fileSuffix + "_" + Constant.DEFAULT_BROWSER + ".txt";
			
			JSErrorFinderController driver = new JSErrorFinderController(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
			driver.visitPagesToValidate();
//			driver.reportResults(System.getProperty("user.dir") +"/src/JSErrorFinder/JSErrorFinder_hongkong.staging.compareglobal.co.uk__Chrome_CheckResult.log");
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration/1000)/60 + " mins");
		}
	}
	
}
