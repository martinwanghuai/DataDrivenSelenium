package com.cgg.consumer.keyFinder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.collections.Lists;

import com.cgg.producer.Builder;
import com.cgg.producer.URLProducer;
import com.cgg.producer.URLProducerBuilder;
import com.cgg.webCrawler.AbstractThreadController;
import com.cgg.webCrawler.SimpleThreadController;

import utility.Constant;
import utility.ExcelUtils;
import utility.IOUtils;

public class KeyFinderController extends SimpleThreadController{

	public KeyFinderController(String startingUrl, boolean isMobile, boolean needToCollectUrls,
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
				new KeyFinderThread(builder.width(743).height(288))
				: new KeyFinderThread(builder.width(0).height(0)));
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
	public void reportResults(final Builder builder){

		String resultFile = builder.getLogFile();
		final Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> missingKeys = IOUtils.loadExcelFile(resultFile);
		final String destFile_str = resultFile.replace(".log", ".txt");
		IOUtils.deleteFile(destFile_str);
		
		final String destFile_str2 = resultFile.replace(".log", "_keys.txt");		
		IOUtils.deleteFile(destFile_str2);
		
		for(final String missingKey: missingKeys){
			final String[] strs = missingKey.split(":");
			if(strs.length == 4){
				
				final String key = strs[3].trim();
				final String value = strs[1].trim() +":" +strs[2].trim();
				
				List<String> urls = Lists.newArrayList();
				if(result.containsKey(key)){
					urls = result.get(key);
				}
				
				if(!urls.contains(value)){
					urls.add(value);
				}
				
				result.put(key, urls);
			}
		}
		
		final StringBuilder sb = new StringBuilder();
		final StringBuilder sb2 = new StringBuilder();
		List<String> refinedMissingKeys = Lists.newArrayList();
		
		if(result.size() > 0){
			sb.append("Please find here the following (" + result.size() + ") missing translations:\n\n");
			Iterator<String> ite = result.keySet().iterator();
			
			int i = 1;
			while(ite.hasNext()){
				final String originalKey = ite.next();
				final String missingKey = this.extractMissingKey(originalKey); // always show refined keys
				refinedMissingKeys.add(missingKey);
				
				sb.append(i + ") ").append(missingKey).append("\nFound on the following URLs:\n");
				List<String> urls = result.get(originalKey);
				sb.append(urls.get(urls.size() - 1 )).append("\n\n"); // only show the last URL
				
				sb.append("\n\n\n");
				sb2.append(missingKey).append("\n");
				i++;
			}
			
			IOUtils.saveIntoFile(sb.toString(), destFile_str);
			IOUtils.saveIntoFile(sb2.toString(), destFile_str2);
			
			final String destFile_str3 = resultFile.replace(".log", "_" + Constant.FILE_KeyChecking);
			new ExcelUtils(Constant.USRDIR + Constant.PATH_TESTDATA + Constant.FILE_KeyChecking, "Import keys").saveIntoExcelColumns(refinedMissingKeys, destFile_str3, 0);;
			
			List<String> SEOKeys = refinedMissingKeys.stream().filter(str -> this.isSEO(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "SEO").saveIntoExcelColumns(SEOKeys, destFile_str3, 0);;
			
			List<String> PLKeys = refinedMissingKeys.stream().filter(str -> this.isPL(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "PL").saveIntoExcelColumns(PLKeys, destFile_str3, 0);;
			
			List<String> CCKeys = refinedMissingKeys.stream().filter(str -> this.isCC(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "CC").saveIntoExcelColumns(CCKeys, destFile_str3, 0);;
			
			List<String> BBKeys = refinedMissingKeys.stream().filter(str -> this.isBB(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "BB").saveIntoExcelColumns(BBKeys, destFile_str3, 0);;
			
			List<String> CIKeys = refinedMissingKeys.stream().filter(str -> this.isCI(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "CI").saveIntoExcelColumns(CIKeys, destFile_str3, 0);;
			
			List<String> SharedKeys = refinedMissingKeys.stream().filter(str -> this.isSharedKey(str)).collect(Collectors.toList());
			new ExcelUtils(destFile_str3, "Shared keys").saveIntoExcelColumns(SharedKeys, destFile_str3, 0);;
			
			System.out.println("Finish generating reports, pls refer to the following file for report details:" + destFile_str3);
		}else{
			System.out.println("Good job, NO missing translations found!!!");
		}
	}
	
	public boolean isSEO(final String str){
		
		String key = str.toLowerCase();
		boolean isSEO = false;
		if(key.contains(".header") && key.contains(".title")){
			isSEO = true;
		}else if(key.contains(".addition") && key.contains(".header")){
			isSEO = true;
		}else if(key.contains("meta.")){
			isSEO = true;
		}else if(key.contains(".description") && key.contains("meta.")){
			isSEO = true;
		}else if(key.contains(".page") && key.contains(".title")){
			isSEO = true;
		}
		
		return isSEO;
	}
	
	public boolean isCC(final String str){
		
		String key = str.toLowerCase();
		boolean isCC = false;
		
		if(!this.isSEO(key)){
			if(key.contains("cc.") || key.contains("creditcard") || key.contains("creditcards") || key.contains("credit-card")){
				isCC = true;
			}
		}
		
		return isCC;
	}
	
	public boolean isPL(final String str){
		
		String key = str.toLowerCase();
		boolean isPL = false;
		if(!this.isSEO(str)){
			if(key.contains("pl.") || key.contains("personalloan") || key.contains("personalloans") || key.contains("personal-loans")|| key.contains("personal-loan")){
				isPL = true;
			}
		}
		
		return isPL;
	}
	
	public boolean isBB(final String str){
		
		String key = str.toLowerCase();
		boolean isBB = false;
		if(!this.isSEO(str)){
			if(key.contains("bb.") || key.contains("broadband") || key.contains("broadbands")){
				isBB = true;
			}
		}
		
		return isBB;
	}
	
	public boolean isCI(final String str){
		
		String key = str.toLowerCase();
		boolean isCI = false;
		if(!this.isSEO(str)){
			if(key.contains("ci.") || key.contains("travelinsurance")){
				isCI = true;
			}
		}
		
		return isCI;
	}
	
	public boolean isSharedKey(final String str){
		
		return !isSEO(str) && !isCC(str) && !isPL(str) && !isBB(str) && !isCI(str);
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
			
			String saveFile = System.getProperty("user.dir") +"/src/KeyFinder/KeyFinder" + fileSuffix + ".txt";
			if(isMobile){
				saveFile = System.getProperty("user.dir") +"/src/KeyFinder/KeyFinder" + fileSuffix + "_mobile.txt";
			}
			
			KeyFinderController driver = new KeyFinderController(startingUrl, isMobile, needToCollectUrls, fileSuffix, saveFile, threadNo, maxCount);
			driver.visitPagesToValidate();
//			driver.reportResults(new Builder().logFile(System.getProperty("user.dir") +"/src/KeyFinder/KeyFinder_hongkong.preview.compareglobal.co.uk_en__CheckResult.log"));
			long duration = System.currentTimeMillis() - start;
			System.out.println("Total duration = " + (duration/1000)/60 + " mins");
		}
	}
	
}
