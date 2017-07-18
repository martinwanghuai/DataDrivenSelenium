package com.cgg.consumer.redirectionChecker;

import java.util.List;

import org.testng.collections.Lists;

import com.cgg.model.Redirection;
import com.cgg.producer.Builder;
import com.cgg.producer.RedirectionProducer;
import com.cgg.webCrawler.AbstractThreadController;
import com.cgg.webCrawler.SimpleThreadController;

import utility.IOUtils;
import utility.Log;

public class RedirectionCheckerController extends SimpleThreadController {

	public RedirectionCheckerController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
			String fileSuffix, String saveFile, int threadNo, int maxCount) {
		
		super(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
	}
	
	@Override
	public Thread getProducerThread(Builder builder) {
		
		return new Thread(new RedirectionProducer(builder));
	}

	@Override
	public Thread getConsumerThread(Builder builder) {

		return new Thread(builder.getIsMobile() ? 
				new RedirectionCheckerThread(builder.width(743).height(288))
				: new RedirectionCheckerThread(builder.width(0).height(0)));
	}

	@Override
	public void reportResults(final Builder builder) {

		String resultFile = builder.getLogFile();
		final List<String> result = Lists.newArrayList();
		List<String> redirectionEntries = IOUtils.loadExcelFile(resultFile);
		
		int totalCount = 0;
		for(final String redirectionEntry: redirectionEntries){
			if(redirectionEntry.toLowerCase().contains("checking result")){
				Redirection entry = Redirection.deseriablize(redirectionEntry);
				if(!entry.getToURL().equalsIgnoreCase(entry.getActualToURL())){
					result.add(entry.serialize());
					totalCount ++;
				}	
			}
		}
		
		final StringBuilder sb = new StringBuilder();
		
		if(result.size() > 0){
			sb.append("Please find here the following (" + totalCount + ") redirection urls with error:\n\n");

			for(String entry: result){
				sb.append(entry).append("\n");
			}
			
			final String destFile_str = resultFile.replace(".log", ".txt");
			IOUtils.saveIntoFile(sb.toString(), destFile_str);
			
			System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str);
		}else{
			System.out.println("Good job, NO error redirection rule found!!!");
		}
		
	}
	
	public static void main(String[] args){
		
			long start = System.currentTimeMillis();
			int threadNo = 6;
			final int maxCount = 1000;
			final String startingUrl = "http://hongkong.preview.compareglobal.co.uk";
			final boolean isMobile = false;
			final boolean needToCollectUrls = true;
			final String fileSuffix = AbstractThreadController.extractFileSuffixFromUrl(startingUrl);
			final String saveFile = System.getProperty("user.dir") +"/src/RedirectionRule/RedirectionChecker" + fileSuffix + ".txt";
			RedirectionCheckerController driver = new RedirectionCheckerController(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
			
			driver.visitPagesToValidate();
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration/1000)/60 + " mins");
	}

}
