package com.cgg.pl.funnel;

import java.util.List;

import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.pl.category.AppFormStep;

import pages.FunnelStep;
import utility.Checker;
import utility.Constant;
import utility.WebDriverUtils;

public class KeyFinder extends SimpleFunnelChecker {

	Checker checker = null;
	
	public KeyFinder(final WebDriverUtils driver){
		super(driver);
		checker = new Checker(driver);
	}

	@Override
	public boolean checkFunnelStep(final FunnelStep step) {

		System.out.println("[" + this.getClass().getSimpleName() + "] Started\n");
		List<String> missingKeys = checker.findMissingTranslationsInPage();
		String link = this.getLink(step);
		if(missingKeys.size() > 0){
			StringBuilder sb = new StringBuilder();
			sb.append("[" + this.getClass().getSimpleName() + "]").append(link).append(":\n");
			for(String missingKey: missingKeys){
				sb.append(missingKey).append("\n");
			}
			System.out.println(sb.toString());
		}else{
			System.out.println("[" + this.getClass().getSimpleName() + "] No Missing Keys\n");
		}		
		return missingKeys.size() > 0 ? false: true;
	}
	
	@Override
	public boolean checkCurrentPage(){
		
		return this.checkFunnelStep(null);
	}
	
	@Override
	public boolean isEnabled(){
		
		return Constant.ENABLE_FUNNEL_TESTER;
	}
}
