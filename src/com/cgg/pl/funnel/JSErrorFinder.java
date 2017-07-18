package com.cgg.pl.funnel;

import java.util.Date;
import java.util.logging.Level;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.pl.category.AppFormStep;

import pages.FunnelStep;
import utility.Constant;
import utility.WebDriverUtils;

public class JSErrorFinder extends SimpleFunnelChecker {

	public JSErrorFinder(final WebDriverUtils driver){
		
		super(driver);
	}
	@Override
	public boolean checkFunnelStep(final FunnelStep step) {
		
		System.out.println("[" + this.getClass().getSimpleName() + "] Started\n");
		String link = this.getLink(step);
		StringBuilder sb = new StringBuilder();
		boolean hasJSError = false;
		LogEntries logEntries = driver.getWebDriver_existing().manage().logs().get(LogType.BROWSER);
		if(logEntries.getAll().size() > 0){
			for (LogEntry entry : logEntries) {
				if(entry.getLevel() == Level.SEVERE){
					hasJSError = true;
					String JSError = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage();
					sb.append(JSError).append("\n");	
				}
			}
		}
		
		if(hasJSError){
			System.out.println("[" + this.getClass().getSimpleName() + "]" + link + ": Found JavaScript Error in URL: "
					+ driver.getCurrentUrl() + "\n" + sb.toString());	
		}else{
			System.out.println("[" + this.getClass().getSimpleName() + "] No JS Errors\n");
		}
		

		return true;
	}
	
	@Override
	public boolean checkCurrentPage(){
		
		return this.checkFunnelStep(null);
	}

	@Override
	public boolean isEnabled() {
		
		return Constant.ENABLE_FUNNEL_TESTER;
	}

}
