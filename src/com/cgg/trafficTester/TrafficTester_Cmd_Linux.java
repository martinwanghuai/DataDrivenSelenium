package com.cgg.trafficTester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

public class TrafficTester_Cmd_Linux {

	private Logger logger;
	
	public TrafficTester_Cmd_Linux(){
		
		try {
			String logFile = System.getProperty("user.dir")
					+ "/src/TrafficTester/TrafficTest_curl_Linux.log";

			logger = Logger.getLogger(this.getClass().getName());
			FileAppender appender = new FileAppender(new EnhancedPatternLayout(), logFile,
					true);
			logger.addAppender(appender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveCmdOutput(final Process process, final int index){

		try{
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String s;
	        while ((s = stdInput.readLine()) != null) {
	            System.out.println(index + ":" + s);
	            logger.info(index + ":" + s);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void testCurl(){
	
		try{
			Runtime runTime = Runtime.getRuntime();
			
			int i = 0;
			while(true){
				Process p_cc = runTime.exec("curl -sI production.singsaver.com.sg/credit-card/best-deals");
				saveCmdOutput(p_cc, i);
				Thread.sleep(1000);

				Process p_pl = runTime.exec("curl -sI production.singsaver.com.sg/personal-loan/credit-line");
				saveCmdOutput(p_pl, i);
				Thread.sleep(1000);
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		TrafficTester_Cmd_Linux tool = new TrafficTester_Cmd_Linux();
		tool.testCurl();	
	}
}
