package com.cgg.consumer.stickyUserDetector;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.collections.Lists;

import com.cgg.producer.Builder;
import com.cgg.producer.LIBRISURLProducer;
import com.cgg.producer.URLProducerBuilder;
import com.cgg.webCrawler.AbstractThreadController;

import utility.Constant;
import utility.IOUtils;
import utility.Log;
import utility.WebDriverUtils;

public class StickyUserDetectorController extends AbstractThreadController {

	public StickyUserDetectorController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	protected void beforeProducerThread(Builder builder) {
		boolean homePageIsChecked = true;
		WebDriverUtils driver = new WebDriverUtils();
		
		while(!homePageIsChecked){//look for CIAB
			String toVisitLink = (String)builder.getToVisitLinks().remove();
			
			driver.openURL(toVisitLink);
			driver.waitForPageLoad();
			if(!driver.getCurrentUrl().startsWith("https://beta")){
				homePageIsChecked = true;
			}else{
				driver.shutDown();
				driver = new WebDriverUtils(Constant.DEFAULT_BROWSER, false);
			}
			
			builder.getToVisitLinks().addFirst(toVisitLink);
		}
	}

	@Override
	public Thread getProducerThread(final Builder builder){
		
		return new Thread(builder.getIsMobile() ? new LIBRISURLProducer((URLProducerBuilder)builder.width(743).height(288))
				: new LIBRISURLProducer((URLProducerBuilder)builder));
	}
	
	@Override
	public Thread getConsumerThread(final Builder builder){
		
		return new Thread(new StickyUserDetectorThread(builder));
	}

	@Override
	public void reportResults(final Builder result){
		
		String resultFile = result.getLogFile();
		List<String> redirectionEntries = IOUtils.loadExcelFile(resultFile);
		
		ConcurrentHashMap<String, List<String>> results = new ConcurrentHashMap<String, List<String>>();
		
		for(final String redirectionEntry: redirectionEntries){
			
			String[] strs = redirectionEntry.split(";");
			if(strs.length >= 2){
				String isCIAB = strs[strs.length - 1];
				String url = strs[strs.length - 2];
				
				List<String> urls = results.getOrDefault(isCIAB, Lists.newArrayList());
				urls.add(url);
				results.put(isCIAB, urls);	
			}
		}
		
		final StringBuilder sb = new StringBuilder();
		Iterator<String> keys = results.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			
			if(key.equalsIgnoreCase("CIAB")){
				sb.append("CIAB:\n");
			}else{
				sb.append("LIBRIS:\n");
			}
			
			List<String> values = results.get(key);
			for(String value: values){
				sb.append(value).append("\n");
			}
			sb.append("\n\n");
		}
		
		final String destFile_str = resultFile.replace(".log", ".txt");
		IOUtils.saveIntoFile(sb.toString(), destFile_str);
		
		System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str);
	}
	
	public static void main(String[] args){
		
		if(args.length == 4){
			
			long start = System.currentTimeMillis();
			int threadNo = 1;
			final int maxCount = 1000;
			
			final String startingUrl = args[0];
			final boolean isMobile = Boolean.parseBoolean(args[1]);
			final boolean needToCollectUrls = Boolean.parseBoolean(args[3]);
			final String fileSuffix = AbstractThreadController.extractFileSuffixFromUrl(startingUrl);
			final String saveFile = System.getProperty("user.dir") +"/src/StickyUser/StickyUserDetector" + fileSuffix + ".txt";
			
			StickyUserDetectorController driver = new StickyUserDetectorController(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
			driver.visitPagesToValidate();
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration/1000)/60 + " mins");
		}
	}
	
}
