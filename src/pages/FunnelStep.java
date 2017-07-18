package pages;

import java.util.List;

import org.openqa.selenium.By;

import com.cgg.model.FunnelConfig;
import com.cgg.model.FunnelConfig.Config;
import com.cgg.model.FunnelConfig.StepInfo;
import com.cgg.model.FunnelConfig.Template;
import com.google.common.collect.HashMultimap;

import utility.Checker;
import utility.WebDriverUtils;

public class FunnelStep {

	protected WebDriverUtils driver;
	FunnelConfig config;
	protected int currentStep = 0;
	protected HashMultimap<String, String> userDataMap = HashMultimap.create();
	public static By NEXT_BUTTON = By.xpath("(//button[@class='continue-button']|//a[contains(@class,'cgg-button-next') and not(contains(@class,'disabled'))])");
	
	public FunnelStep(WebDriverUtils driver) {

		this.driver = driver;
	}

	public FunnelStep(WebDriverUtils driver, FunnelConfig config) {

		this.driver = driver;
		this.config = config;
	}
	
	public boolean visitCurrentStep() {
		
		Config config2 = config.getFunnel().getConfig();
		StepInfo stepInfo = config2.getStepInfo().get(currentStep);
		List<Template> templates = stepInfo.getTemplateOption().getTemplate();

		boolean clickNextButton = true;
		if (!Checker.isEmpty(templates)) {
			for (Template template : templates) {
				String type = template.getType().toLowerCase();
				stepInfo.updateTypeCount(type);
				
				String key = (template.getData() == null || template.getData().getKey() == null) ? "": "; key:" +  template.getData().getKey();
				System.out.println("Link:" + stepInfo.getLink() + ";Type:" + type + key);
				FunnelComponent step = FunnelComponent.fromString(type);
				if (step != null) {
					HashMultimap<String, String> userDataMap2 = this.getUserDataMap();
					
					boolean elementExist = step.isFunnelComponentExist(driver, template, stepInfo.getTypeCountMap().get(type), userDataMap2, config2.getVertical());
					step.visitFunnelComponent(driver, template, stepInfo.getTypeCountMap().get(type), userDataMap2, config2.getVertical());
					userDataMap2.putAll(step.getValues());
					if(elementExist){
						clickNextButton = step.needToClickNextButton();	
					}
				}
			}
		}
		
		stepInfo.clearTypeCount();
		return clickNextButton;
	}

	public void setupNextStep(){
		
		currentStep ++;
	}
	
	public boolean noMoreSteps(){
		
		List<StepInfo> totalSteps = config.getFunnel().getConfig().getStepInfo();
		return currentStep >= totalSteps.size();
	}
	
	public StepInfo getCurrentStep() {
		
		return this.noMoreSteps()? null: config.getFunnel().getConfig().getStepInfo().get(currentStep);
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public HashMultimap<String, String> getUserDataMap() {
		return userDataMap;
	}

	public void setUserDataMap(HashMultimap<String, String> userDataMap) {
		this.userDataMap = userDataMap;
	}
}
