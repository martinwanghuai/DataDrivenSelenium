package com.cgg.trafficTester;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.collections.Lists;

import utility.IOUtils;
import utility.Log;
import utility.WebDriverUtils;
import utility.WebDriverUtils.Type;

public class VWOLevelTrafficTestDriver {
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public void startTrafficTest(int threadNo, Type browserType){
		
		List<Thread> threadPool = Lists.newArrayList();
		
		String logFile = System.getProperty("user.dir")
				+ "/src/TrafficTester/VWOLevelTrafficTest.log";
		IOUtils.deleteFile(logFile);
		
		Logger logger = Log.getLoggerForClass("TrafficTester", logFile, true);
		
		for(int i = 0; i < threadNo; i ++){
			
			Thread thread = new Thread(new VWOLevelTrafficTester(logger, "Thread[" + i + "]:", browserType, "http://dev.singsaver.com.sg/credit-card/best-deals/", "http://dev.singsaver.com.sg/personal-loan/instalment"));
			thread.start();
			threadPool.add(thread);
		}
		
		try{
			for(Thread t : threadPool) {
		          // waits for this thread to die
		          t.join();
		     }	
			
			System.out.println("Finish all traffic checking");
			logger.info("Finish all traffic checking");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(final String[] args){
		
		VWOLevelTrafficTestDriver driver = new VWOLevelTrafficTestDriver();
		driver.startTrafficTest(3, WebDriverUtils.Type.FireFox);
	}

}
