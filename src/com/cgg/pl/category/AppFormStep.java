package com.cgg.pl.category;

import java.util.List;

import com.cgg.model.AppFormConfig;
import com.cgg.model.AppFormConfig.Component;
import com.cgg.model.AppFormConfig.Config;
import com.cgg.model.AppFormConfig.Route;
import com.google.common.collect.HashMultimap;

import pages.FunnelComponent;
import pages.FunnelStep;
import utility.Checker;
import utility.WebDriverUtils;

public class AppFormStep extends FunnelStep {

	AppFormConfig config;
	
	public AppFormStep(WebDriverUtils driver){
		
		super(driver);
	}
	
	public AppFormStep(WebDriverUtils driver, AppFormConfig config){
		
		this(driver);
		this.config = config;
	}

	public AppFormConfig getConfig() {
		return config;
	}

	public void setConfig(AppFormConfig config) {
		this.config = config;
	}
	
	public boolean visitCurrentStep() {
		
		Config config2 = config.getCheckout().getConfig();
		Route route = config2.getRoutes().get(currentStep);
		List<Component> components = route.getComponents();
		
		boolean clickNextButton = false;
		if (!Checker.isEmpty(components)) {
			for (Component component : components) {
				String type = component.getType().toLowerCase();
				route.updateTypeCount(type);
				System.out.println("Route:" + route.getRoute() + ";Type:" + type +";Key:" + component.getKey());
				FunnelComponent step = FunnelComponent.fromString(type);
				if (step != null) {
					HashMultimap<String, String> userDataMap2 = this.getUserDataMap();
					step.visitFunnelComponentInAppForm(driver, component, route.getTypeCountMap().get(type), userDataMap2, config2.getVerticalShortCode());
					userDataMap2.putAll(step.getValues());
					if(!clickNextButton && step.isFunnelComponentExistInAppForm(driver, component, route.getTypeCountMap().get(type), userDataMap2, config2.getVerticalShortCode())){
						clickNextButton = step.needToClickNextButton();	
					}
					driver.explicitWait(1000);
				}
			}
		}
		
		return clickNextButton;
	}
	
	@Override
	public boolean noMoreSteps(){
		
		List<Route> totalSteps = config.getCheckout().getConfig().getRoutes();
		return currentStep >= totalSteps.size();
	}
	
	public Route getCurrentAppFormStep() {
		
		return config.getCheckout().getConfig().getRoutes().get(currentStep);
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

}
