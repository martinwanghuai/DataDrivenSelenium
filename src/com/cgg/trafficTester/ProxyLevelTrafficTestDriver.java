package com.cgg.trafficTester;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

import utility.IOUtils;
import utility.Log;
import utility.WebDriverUtils;
import utility.WebDriverUtils.Type;

public class ProxyLevelTrafficTestDriver {
		
		public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		public static ConcurrentLinkedDeque<String> visitedLinks = new ConcurrentLinkedDeque<String>();
		
		
		public String getRandomLink(final List<String> links, final ConcurrentLinkedDeque<String> visitedLinks){
			
			String url = "";
			do{
			 url = links.get(RandomUtils.nextInt(0, links.size()));
			}while(visitedLinks.contains(url));
			
			return url;
		}
		
		public void startTrafficTest(int threadNo, Type browserType, final boolean randomTest){
			
			List<Thread> threadPool = Lists.newArrayList();
			
			String logFile = System.getProperty("user.dir")
					+ "/src/TrafficTester/ProxyLevelTrafficTest.log";
			IOUtils.deleteFile(logFile);
			
			Logger logger = Log.getLoggerForClass("TrafficTester", logFile, true);
			
			List<String> links = randomTest
				? IOUtils.loadExcelFile(System.getProperty("user.dir")
						+ "/src/TrafficTester/ProxyLevelTrafficTest_LinkList.txt")
						
				: Lists.newArrayList(
//						"http://staging.money101.com.tw/"
//						"http://staging.money101.com.tw/信用卡/信用卡常用名詞解釋"
						"http://staging.singsaver.com.sg/"
						/*"http://staging.moneyhero.com.hk/en/personal-loan/personal-instalment/results/",
						"http://staging.moneyhero.com.hk/en/credit-card/best-deals/all/results"*/);
			
			for(int i = 0; i < threadNo; i ++){
				
				String url = links.get(i%links.size());
				if(randomTest){
					url = this.getRandomLink(links, visitedLinks);
					visitedLinks.addFirst(url);
				}
				
				Thread thread = new Thread(new ProxyLevelTrafficTester(logger, "Thread[" + i + "]:", browserType, url));
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
			
			ProxyLevelTrafficTestDriver driver = new ProxyLevelTrafficTestDriver();
			driver.startTrafficTest(1, WebDriverUtils.Type.Chrome, false);
		}
}
