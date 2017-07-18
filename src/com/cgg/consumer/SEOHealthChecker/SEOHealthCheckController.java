package com.cgg.consumer.SEOHealthChecker;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.cgg.producer.Builder;
import com.cgg.producer.URLProducer;
import com.cgg.producer.URLProducerBuilder;
import com.cgg.webCrawler.AbstractThreadController;
import com.cgg.webCrawler.SimpleThreadController;

import utility.Constant;
import utility.IOUtils;
import utility.Log;

public class SEOHealthCheckController extends SimpleThreadController {

	public SEOHealthCheckController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	public Thread getProducerThread(Builder builder){
		
		return new Thread(builder.getIsMobile()
				? new URLProducer(
						(URLProducerBuilder) builder.width(Constant.MOBILE_WIDTH).height(Constant.MOBILE_HEIGHT))
				: new URLProducer((URLProducerBuilder) builder));
	}
	
	@Override
	public Thread getConsumerThread(Builder builder){
		
		return new Thread(builder.getIsMobile() ? 
				new SEOHealthCheckerThread(builder.width(743).height(288))
				: new SEOHealthCheckerThread(builder.width(0).height(0)));
	}
	
	@Override
	public void reportResults(final Builder builder){

		final ConcurrentHashMap<String, List<String>> result = builder.getTestingResult();
		
		final StringBuilder sb = new StringBuilder();
		if(result.size() > 0){
			sb.append("Please find here the following (" + result.size() + ") urls with non-optimized SEOs:\n\n");
			Iterator<String> ite = result.keySet().iterator();
			int i = 1;
			while(ite.hasNext()){
				final String url = ite.next();
				final List<String> values = result.get(url);

				sb.append(i + ") ").append(url).append("\nFound on the following non-optimized SEOs:\n");
				for(String value: values){
					sb.append(value).append("\n");	
				}
				sb.append("\n\n\n");
				
				i++;
			}
			
			final String destFile_str = builder.getLogFile().replace(".log", ".txt");
			IOUtils.saveIntoFile(sb.toString(), destFile_str);
			System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str);
		}else{
			System.out.println("Good job, all SEOs are optimized!!!");
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
			
			String saveFile = System.getProperty("user.dir") +"\\src\\SEOHealthChecker\\SEOHealthChecker" + fileSuffix + ".txt";
			if(isMobile){
				saveFile = System.getProperty("user.dir") +"\\src\\SEOHealthChecker\\SEOHealthChecker" + fileSuffix + "_mobile.txt";
			}
			
			SEOHealthCheckController driver = new SEOHealthCheckController(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
			driver.visitPagesToValidate();
//			driver.reportResults(System.getProperty("user.dir") +"/src/KeyFinder/KeyFinder_mexico-preview.compareglobal.co.uk__CheckResult.log");
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration/1000)/60 + " mins");
		}
	}
}
