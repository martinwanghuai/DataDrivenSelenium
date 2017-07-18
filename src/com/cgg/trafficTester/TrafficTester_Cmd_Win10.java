package com.cgg.trafficTester;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import utility.IOUtils;

public class TrafficTester_Cmd_Win10 {
	
private Logger logger;
	
	public TrafficTester_Cmd_Win10(){
		
		try {
			String logFile = System.getProperty("user.dir")
					+ "/src/TrafficTester/TrafficTest_dig_Win10.log";

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
	
	public void testDig(){
	
		try{
			Runtime runTime = Runtime.getRuntime();
			
			int i = 0;
			while(true){
				Process process = Runtime.getRuntime()
						.exec("C:/Program Files/dig/bin/dig @ns-249.awsdns-31.com singsaver.com.sg");
				saveCmdOutput(process, i);
				Thread.sleep(1000);

				process = runTime.exec("ipconfig /flushdns");
				saveCmdOutput(process, i);
				Thread.sleep(1000);
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		for(int i = 0; i < 10; i ++){
			TrafficTester_Cmd_Win10 tool = new TrafficTester_Cmd_Win10();
			tool.testDig();	
		}
	}

}
