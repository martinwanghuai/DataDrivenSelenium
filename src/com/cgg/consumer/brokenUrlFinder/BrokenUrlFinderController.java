package com.cgg.consumer.brokenUrlFinder;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.collections.Lists;
import org.testng.collections.Maps;

import com.cgg.producer.Builder;
import com.cgg.producer.DetailedURLProducer;
import com.cgg.producer.URLProducerBuilder;
import com.cgg.webCrawler.AbstractThreadController;
import com.cgg.webCrawler.SimpleThreadController;

import utility.Constant;
import utility.IOUtils;
import utility.Log;
import utility.MathUtils;

public class BrokenUrlFinderController extends SimpleThreadController{
	
	public BrokenUrlFinderController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	public Thread getProducerThread(Builder builder){
		
		return new Thread(builder.getIsMobile() ? new DetailedURLProducer((URLProducerBuilder)builder.width(743).height(288))
				: new DetailedURLProducer((URLProducerBuilder)builder));
	}
	
	@Override
	public Thread getConsumerThread(Builder builder){
		
		URLProducerBuilder urlBuilder = new URLProducerBuilder().create(builder);
		
		return new Thread(urlBuilder.getIsMobile() ?
				  new BrokenUrlFinderThread(urlBuilder.width(743).height(288))
				: new BrokenUrlFinderThread(urlBuilder.width(0).height(0)));
	}
	
	@Override
	public void reportResults(final Builder resultBuilder){
		
		String resultFile = resultBuilder.getLogFile();
		final Map<Double, List<String>> result = Maps.newHashMap();
		List<String> errorUrls = IOUtils.loadExcelFile(resultFile);
		
		final String destFile_str = resultFile.replace(".log", ".txt");
		IOUtils.deleteFile(destFile_str);
		
		int totalCount = 0;
		for(final String errorUrl: errorUrls){

			if(errorUrl.toLowerCase().contains("found error")){
				final String[] strs = errorUrl.substring(errorUrl.indexOf("found error")).split(":");	
				final Double errorCode = MathUtils.extractDouble(strs[1].trim());
				final String url = errorUrl.substring(errorUrl.indexOf("in:") + "in:".length());
				List<String> urls = Lists.newArrayList();
				if(result.containsKey(errorCode)){
					urls = result.get(errorCode);
				}
				
				if(!urls.contains(url)){
					urls.add(url);
				}
				
				result.put(errorCode, urls);
				totalCount ++;
			}
		}
		
		final StringBuilder sb = new StringBuilder();
		
		if(result.size() > 0){
			sb.append("Please find here the following (" + totalCount + ") urls with error:\n\n");
			Iterator<Double> ite = result.keySet().iterator();
			
			int i = 1;
			while(ite.hasNext()){
				final Double errorCode = ite.next();
				
					List<String> urls = result.get(errorCode);
					sb.append(i + ") ").append(errorCode.intValue()).append("(" + urls.size()+ ")\nFound on the following URLs:\n");
					
					for(final String url: urls){
						String[] fullUrls = url.split(";");
						int totalUrls = fullUrls.length;
						for(int j = 0; j < totalUrls; j ++){
							for(int k = 0; k < j; k++){
								sb.append("\t");	
							}
							sb.append(fullUrls[totalUrls - j - 1] + "\n");
						}
						sb.append("\n\n");
					}
					sb.append("\n\n\n");
					i++;	
			}
			
			
			IOUtils.saveIntoFile(sb.toString(), destFile_str);
			
			System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str);
		}else{
			System.out.println("Good job, NO broken url found!!!");
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
			String saveFile = System.getProperty("user.dir") + "/src/BrokenUrlFinder/BrokenURLFinder" + fileSuffix
					+ ".txt";
			if(isMobile){
				saveFile = System.getProperty("user.dir") + "/src/BrokenUrlFinder/BrokenURLFinder" + fileSuffix
						+ "_mobile.txt";
			}
			
			BrokenUrlFinderController driver = new BrokenUrlFinderController(startingUrl, isMobile,
					needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);

			driver.visitPagesToValidate();
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration / 1000) / 60 + " mins");
			
		}
	}
}
