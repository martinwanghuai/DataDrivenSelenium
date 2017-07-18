package com.cgg.pl.funnel;

import com.cgg.model.AppFormConfig.Route;
import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.pl.category.AppFormStep;

import pages.FunnelStep;
import utility.WebDriverUtils;

public abstract class SimpleFunnelChecker {

	WebDriverUtils driver = null;
	
	public SimpleFunnelChecker(){
		
	}
	
	public SimpleFunnelChecker(final WebDriverUtils driver){
		this.driver = driver;
	}
	
	public abstract boolean checkFunnelStep(final FunnelStep step);

	public abstract boolean isEnabled();
	
	public abstract boolean checkCurrentPage();
	
	public String getLink(final FunnelStep step) {
		String link = "";
		if(step instanceof AppFormStep){
			Route step1 = ((AppFormStep)step).getCurrentAppFormStep();
			if(step1!= null){
				link = step1.getRoute();
			}
		}else{
			StepInfo step1 = step.getCurrentStep();
			if(step1 != null){
				link = step1.getLink();
			}
		}
		return link;
	}
}
