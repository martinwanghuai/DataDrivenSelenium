package pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.collections.Lists;

import com.cgg.model.AppFormConfig.Component;
import com.cgg.model.AppFormConfig.Item;
import com.cgg.model.FunnelConfig.Choice;
import com.cgg.model.FunnelConfig.Items;
import com.cgg.model.FunnelConfig.Options;
import com.cgg.model.FunnelConfig.Template;
import com.cgg.model.FunnelConfig.TimeOptionItem;
import com.cgg.model.PersonalLoanConfig;
import com.cgg.model.ProvidersOrderByBanking;
import com.cgg.model.TestCase;
import com.google.common.collect.HashMultimap;
import com.mifmif.common.regex.Generex;

import utility.Checker;
import utility.Constant;
import utility.MathUtils;
import utility.WebDriverUtils;

public enum FunnelComponent {

	MULTIPLE_TRIGGER("cgg-tile-multiple-trigger-next-step") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "' and @key='" + template.getData().getKey() + "'])|(//*[@ng-switch-when='" + this.getType() + "'])");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

    		By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "' and @key='" + template.getData().getKey() + "'])|(//*[@ng-switch-when='" + this.getType() + "'])");
			if (!driver.isElementPresent(by)) {
				return;
			}
			
			WebElement elem = driver.findElement(by);
			Options options = template.getData().getOptions();
			List<Items> items = options.getItems();
			String itemType = items.get(0).getType();
			by = By.xpath(".//*[@ng-switch-when='" + itemType + "']");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = elem.findElements(by);
				if (elems.size() > 0) {
					// int index = MathUtils.getRandomValue(1, elems.size() +
					// 1);
					int index = 1;
					driver.clickButton(elems.get(index - 1));
					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(template.getData().getKey(), items.get(index - 1).getCggId());
					this.printKeyValues(template.getData().getKey(), items.get(index - 1).getCggId());
				}
			}
		}

		@Override
		public boolean needToClickNextButton() {

			return false;
		}
	},

	BORROW_MONEY("cgg-borrow-money") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[contains(@ng-switch-when, 'cgg-borrow-money')])["
					+ (templateIndex + 1) +"]/descendant::input[not(@view-number-model-string)]");
			return driver.isElementPresent(by);
			
			/*if (templateIndex > 0
					|| (TestCase.isMXURL(driver.getCurrentUrl()) && userDataMap.get("category").contains("carLoan"))) {
				return false;
			} else {
				By by = By.xpath("(//*[contains(@ng-switch-when, '" + this.getType()
						+ "')["+ (templateIndex + 1) + "]/descendant::input[not(@view-number-model-string)]");
				return driver.isElementPresent(by);
			}*/
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			this.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical, String type) {

			if (templateIndex > 0
					|| (TestCase.isMXURL(driver.getCurrentUrl()) && userDataMap.get("category").contains("carLoan"))) {
				return; // execute this once
			}

			By by = By.xpath(
					"(//*[contains(@ng-switch-when, '" + type + "')])["+(templateIndex + 1)+"]/descendant::input[not(@view-number-model-string)]");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);

				// 1. loan amount
				String loanAmount = new Generex(this.refineInputPattern(template)).random();
				driver.fillin_textbox(elems.get(0), loanAmount);
				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(template.getData().getKeyOne(), loanAmount);
				this.printKeyValues(template.getData().getKeyOne(), loanAmount);
				// 2. loan tenure
				if (elems.size() > 1) {

					String loanTenure = new Generex("[1-9]{1,3}").random();
					driver.fillin_textbox(elems.get(1), loanTenure);
					hashValues.put(template.getData().getKeyTwo(), loanTenure);
					this.printKeyValues(template.getData().getKeyTwo(), loanTenure);
				} else {
					by = By.xpath("//*[@ng-switch-when='" + type + "']/descendant::select");
					List<TimeOptionItem> items = template.getData().getTimeOptions().getItems();
					int randomItem = MathUtils.getRandomValue(0, items.size());
					driver.select_selectorByIndex(by, randomItem); // can
																	// improve
					hashValues.put(template.getData().getKeyTwo(), items.get(randomItem).getCggId());
					this.printKeyValues(template.getData().getKeyTwo(), items.get(randomItem).getCggId());
				}

				// 3. time unit
				List<TimeOptionItem> items = template.getData().getTimeUnitOptions().getItems();
				hashValues.put(template.getData().getKeyThree(), items.get(0).getCggId());
				this.printKeyValues(template.getData().getKeyThree(), items.get(0).getCggId());
			}
		}
	},

	BORROW_MONEY_WITH_RESTRICTION("cgg-borrow-money-with-restrictions") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			if (templateIndex > 0
					|| (TestCase.isMXURL(driver.getCurrentUrl()) && userDataMap.get("category").contains("carLoan"))) {
				return false;
			} else {
				By by = By.xpath("(//*[contains(@ng-switch-when, '" + this.getType()
						+ "')])[" + (templateIndex + 1) + "]/descendant::input[not(@view-number-model-string)]");
				return driver.isElementPresent(by);
			}
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			BORROW_MONEY.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
		}
	},

	INPUT_DATA_VALIDATION("cgg-data-validation"){
		
		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" +(templateIndex + 1) + "]/descendant::input");
			if (driver.isElementPresent(by)) {
					String input = "03100";
					driver.fillin_textbox(by, input);
					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(template.getData().getKey(), input);
					this.printKeyValues(template.getData().getKey(), input);
			}
		}
	},
	
	INPUT_NUMBER("cgg-input-number") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" +(templateIndex + 1)+ "]/descendant::input");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			NUMBER_INPUT.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
		}
	},

	NUMBER_INPUT("cgg-number-input") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) +"]/descendant::input");
			return driver.isElementPresent(by);
		}
		
		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical, String type) {

			By by = By.xpath("(//*[@ng-switch-when='" + type + "'])[" +(templateIndex + 1) + "]/descendant::input[not(@disabled)]");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);
					String pattern = Checker.isBlank(template.getData().getPattern()) ? Checker.REGEX_INPUT
							: template.getData().getKey().equalsIgnoreCase("propertyValue")? "[1-9]{7}":
								template.getData().getKey().equalsIgnoreCase("wantToBorrowTime")? "[1-3]{2}":this.refineInputPattern(template);
					HashMultimap<String, String> hashValues = this.getValues();
					for (WebElement elem : elems) {
						if (elem.isDisplayed()) {
							String input = "";
							int i = 0;
							do {
								input = new Generex(pattern).random();
								driver.fillin_textbox(elem, input);
								driver.explicitWait();
								i++; 
							} while (existErrorMsg(driver, elem) && i < 10);
							
							hashValues.put(template.getData().getKey(), input);
							input = elem.getAttribute("value");
							this.printKeyValues(template.getData().getKey(), input);
						}
					}	
			}
		}
		
		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			String key = component.getKey();
			By by = By.xpath("//*[@key='" + key + "']/descendant::input[not(@disabled)]");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);
				String pattern = Checker.REGEX_INPUT;
				HashMultimap<String, String> hashValues = this.getValues();
				for (WebElement elem : elems) {
					if (elem.isDisplayed()) {
						String input = "";
						int i = 0;
						do {
							input = new Generex(pattern).random();
							driver.fillin_textbox(elem, input);
							driver.explicitWait();
							i++; 
						} while (existErrorMsg(driver, elem) && i < 10);
						
						hashValues.put(key, input);
						input = elem.getAttribute("value");
						this.printKeyValues(key, input);
					}
				}
			}

		}
		

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			this.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
		}
	},

	AUTO_COMPLETE("cgg-autocomplete") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::input[contains(@class,'cgg-input-text')]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			if (vertical.equalsIgnoreCase("broadband")) {
				By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
						+ (templateIndex + 1) + "]/descendant::input[contains(@class,'cgg-input-text')]");
				if (driver.isElementPresent(by)) {
					List<WebElement> elems = driver.findElements(by);
					HashMultimap<String, String> hashValues = this.getValues();

					for (WebElement elem : elems) {
						String value = elem.getAttribute("value");
						String key = elem.getAttribute("name");
						
						if (key.equalsIgnoreCase("postcode")) {
							hashValues.put(key, value);
						} else if (key.equalsIgnoreCase("address")) {
							List<String> values = Lists.newArrayList("hed", "RUA");
							for(String value_temp: values){
								
								driver.fillin_textbox(elem, value_temp);
								by = By.xpath("//a[@class='cgg-help ng-scope']");
								if (existErrorMsg(driver, elem)) {
									continue;
								}
								
								By by1 = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
										+ (templateIndex + 1) + "]/descendant::li[contains(@class,'ng-binding ng-scope')]");
								driver.implicitWait(by1, 6000, 100);
								List<WebElement> elems2 = driver.findElements(by1);
								WebElement elem2 = MathUtils.getRandomValue(elems2);
								driver.clickButton(elem2);
								value = driver.getAttributeValue(by, "value");

								hashValues.put(key, value);
								this.printKeyValues(key, value);
								break;
							}
						}
					}
				}
			} else {
				By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
						+ (templateIndex + 1) + "]/descendant::input[contains(@class,'cgg-input-text')]");
				if (driver.isElementPresent(by)) {
					String input = "4000";
					driver.fillin_textbox(by, input);
					By by1 = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
							+ (templateIndex + 1) + "]/descendant::li[contains(@class,'ng-binding ng-scope')]");
					driver.implicitWait(by1, 6000, 100);
					List<WebElement> elems = driver.findElements(by1);
					WebElement elem = MathUtils.getRandomValue(elems);
					driver.clickButton(elem);
					String value = driver.getAttributeValue(by, "value");
					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(template.getData().getKey(), value);
					this.printKeyValues(template.getData().getKey(), value);
				}
			}
		}
	},

	INPUT_DURATION("cgg-input-duration") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::input[contains(@class,'cgg-input-text')]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::input[contains(@class,'cgg-input-text')]");
			if (driver.isElementPresent(by)) {
				String value = "0";
				driver.fillin_textbox(by, value);
				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(template.getData().getKey(), value);
				this.printKeyValues(template.getData().getKey(), value);
			}
		}
	},

	BUTTON_GROUP("cgg-button-group") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//div[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
					+ "]/descendant::button[@class='cgg-button-default cgg-button-2']");
			return driver.isElementPresent(by);

		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//div[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
					+ "]/descendant::button[@class='cgg-button-default cgg-button-2']");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);

				List<Choice> choices = template.getData().getChoices();
				int index = MathUtils.getRandomValue(0, choices.size());
				if (elems.size() > index) {
					driver.clickButton(elems.get(index));
				}

				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(template.getData().getKey(), choices.get(index).getId());
				this.printKeyValues(template.getData().getKey(), choices.get(index).getId());
			}
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//div[@data-key='" + component.getKey() + "' or @key='" + component.getKey()
					+ "']/descendant::button");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);
				driver.explicitWait();
				List<Choice> choices = component.getAttributes().getOptions().getChoices();
				// int index = MathUtils.getRandomValue(0, choices.size());
				int index = 0;
				if (index < elems.size()) {
					driver.clickButton(elems.get(index));
				}

				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(component.getKey(), choices.get(index).getId());
				this.printKeyValues(component.getKey(), choices.get(index).getId());
			}

		}

	},

	DROPDOWN("cgg-dropdown") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			String currentUrl = driver.getCurrentUrl();
			if (notKnowLicenseNumInDK(currentUrl, userDataMap)) {
				By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
						+ (templateIndex + 1) + "]/descendant::select[contains(@class,'selection-option')]");
				return driver.isElementPresent(by);
			} else {
				return false;
			}

		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();
			String key = template.getData().getKey();
			By by = By.xpath("//" + this.getType() + "[@ng-switch-when='" + this.getType() + "']/descendant::select[@name='" + key + "']");
			if (driver.isElementPresent(by)) {

				WebElement sel = driver.findElement(by);
				driver.explicitWait(3000);
				List<WebElement> lists = sel.findElements(By.tagName("option"));
				driver.explicitWait(3000);
				int index = MathUtils.getRandomValue(0, lists.size());
				driver.select_selectorByIndex(by, index); // first one
															// is
															// non-selectable
				String input = driver.getSelectorValue(by, index);
				hashValues.put(key, input);
				this.printKeyValues(key, input);
			}
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();

			By by = By.xpath("//" + this.getType() + "[not(ancestor::" + DROPDOWN_CACHED_API.getType() + ")]"
					+ "/descendant::select[@name='" + component.getKey() + "']");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				if (elem.isEnabled()) {
					List<Item> items = component.getAttributes().getOptions().getItems();
					int index = 1;
					if (items != null) {
						index = MathUtils.getRandomValue(0, items.size());
					} else {
						driver.explicitWait(1000);
						List<String> strs = component.getAttributes().getOptions().getStrs();
						index = MathUtils.getRandomValue(0, strs.size());
					}

					driver.select_selectorByIndex(by, index); // first one
																// is
																// non-selectable
					String input = driver.getSelectorValue(by, index);
					hashValues.put(component.getKey(), input);
					this.printKeyValues(component.getKey(), input);
				}
			}
		}
	},

	DROPDOWN_CACHED_API("cgg-cached-api-dropdown") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			
			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::select[contains(@class,'selection-option')]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();
			String key = template.getData().getKey();
			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::select[@name='" + key + "']");
			if (driver.isElementPresent(by)) {

				List<Items> items = template.getData().getOptions().getItems().stream()
						.filter(item -> !item.isDisabled()).collect(Collectors.toList());

				// int index = MathUtils.getRandomValue(0, items.size());
				int index = 1;
				driver.select_selectorByIndex(by, index + 1); // first one
																// is
																// non-selectable
				String value = driver.getSelectorValue(by, index + 1);
				hashValues.put(key, value);
				this.printKeyValues(key, value);
			}
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();

			By by = By.xpath("(//" + this.getType() + ")["+(templateIndex + 1)+"]/descendant::select[@name='"
					+ component.getKey() + "']");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				if (elem.isEnabled()) {
					int index = 0;
					driver.select_selectorByIndex(by, index + 1); // first one
																	// is
																	// non-selectable
					String value = driver.getSelectorValue(by, index + 1);
					hashValues.put(component.getKey(), value);
					this.printKeyValues(component.getKey(), value);
				}
			}
		}

	},

	MOMENTPICKER("cgg-moment-picker") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			return true;
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();

			By by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				if (elem.isEnabled()) {
					driver.clickButton(elem);
					driver.explicitWait();
					by = By.xpath("(//div[@class='moment-picker-specific-views'])[" + (templateIndex + 1)
							+ "]/descendant::td[@class='ng-binding ng-scope'][1]");
					driver.clickButton(by);
					driver.explicitWait();

					by = By.xpath("(//div[@class='moment-picker-specific-views'])[" + (templateIndex + 1)
							+ "]/descendant::td[@class='ng-binding ng-scope'][1]");
					driver.clickButton(by);
					driver.explicitWait();

					by = By.xpath("(//div[@class='moment-picker-specific-views'])[" + (templateIndex + 1)
							+ "]/descendant::td[@class='ng-binding ng-scope'][1]");
					driver.clickButton(by);
					driver.explicitWait();
					
					by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]"
							+ "/descendant::input[@moment-picker='displayDate']");
					elem = driver.findElement(by);
					hashValues.put(component.getKey(), elem.getAttribute("value"));
					this.printKeyValues(component.getKey(), elem.getAttribute("value"));
				}
			}
		}
	},

	DATEPICKER("cgg-datepicker") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();

			By by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				if (elem.isEnabled()) {
					driver.clickButton(elem);
					driver.explicitWait();
					by = By.xpath("(//div[@class='flatpickr-days'])[" + (templateIndex + 1)
							+ "]/span[@class='flatpickr-day selected' or @class='flatpickr-day today selected']");
					driver.clickButton(by);

					hashValues.put(component.getKey(), elem.getText());
					this.printKeyValues(component.getKey(), elem.getText());
				}
			} else {
				MOMENTPICKER.visitFunnelComponentInAppForm(driver, component, templateIndex, userDataMap, vertical);
			}
		}
	},

	DROPDOWN_API_NESTABLE("cgg-dropdown-api-nestable") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::select[@name]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			HashMultimap<String, String> hashValues = this.getValues();
			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])["
					+ (templateIndex + 1) + "]/descendant::select[@name and not(@disabled)]");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				int index = MathUtils.getRandomValue(0, elem.findElements(By.xpath("./option[not(@disabled)]")).size());
				driver.select_selectorByIndex(by, index);
				By byValue = By
						.xpath("./option[not(@disabled) and @selected='selected']");
				String value = elem.findElement(byValue).getText();
				hashValues.put(template.getData().getKey(), value);
				this.printKeyValues(template.getData().getKey(), value);
				driver.explicitWait(3000);
			}
		}
	},
	

	TILE_MULTIPLE_CHECKOUT("cgg-tile-multiple-checkout") {

		@Override
		public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//div[@data-key='" + component.getKey() + "' or @key='" + component.getKey()
					+ "']/descendant::div[@ng-click]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			List<Item> items = component.getAttributes().getOptions().getItems();
			By by = By.xpath("//div[@data-key='" + component.getKey() + "' or @key='" + component.getKey()
					+ "']/descendant::div[@ng-click]");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);
				if (elems.size() > 0) {
					int index = MathUtils.getRandomValue(0, elems.size());
					driver.explicitWait();
					driver.clickButton(elems.get(index));
					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(component.getKey(), items.get(index).getId());
					this.printKeyValues(component.getKey(), items.get(index).getId());
				}
			}
		}
	},

	TILE_MULTIPLE("cgg-tile-multiple") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			String key = template.getData().getKey();
			By by = null;
			if (key.equalsIgnoreCase("relationshipProvider") || key.equalsIgnoreCase("provider")
					|| key.equalsIgnoreCase("bankRelationship")) {

				by = By.xpath("(//div[@ng-switch-when='" + this.getType()
						+ "'])[" + (templateIndex + 1)+ "]/descendant::div[contains(@class,'cgg-table-cell')]");
			} else {
				by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
						+ "]/descendant::div[@class='cgg-table-cell']");
			}

			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			String key = template.getData().getKey();
			if (key.equalsIgnoreCase("relationshipProvider") || key.equalsIgnoreCase("provider")
					|| key.equalsIgnoreCase("bankRelationship")) {
				handleWithRelationshipBank(driver, template, templateIndex, key);
			} else {
				handleWithMultiChoices(driver, template, templateIndex, key);
			}
		}

		public void handleWithMultiChoices(final WebDriverUtils driver, final Template template,
				final int templateIndex, String key) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
					+ "]/descendant::div[@class='cgg-table-cell']");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);
				List<Items> items = template.getData().getOptions().getItems();
				List<Integer> itemIndexList = java.util.stream.IntStream.range(0, items.size()).boxed()
						.collect(Collectors.toList());
				List<Integer> chosenItemIndex = (List<Integer>) MathUtils.getRandomSublist(itemIndexList,
						MathUtils.getRandomValue(1, itemIndexList.size()));
				HashMultimap<String, String> hashValues = this.getValues();
				for (Integer index : chosenItemIndex) {
					if (index >= elems.size()) {
						System.out.println("Size:" + elems.size() + "; index:" + index);
					} else {
						driver.clickButton(elems.get(index));
						String id = items.get(index).getCggId() == null ? items.get(index).getId(): items.get(index).getCggId(); 
						if (id.equalsIgnoreCase("none")) {
							hashValues.removeAll(key);
							hashValues.put(key, items.get(index).getCggId());
							this.printKeyValues(key, items.get(index).getCggId());
							break;
						} else {
							hashValues.put(key, items.get(index).getCggId());
							this.printKeyValues(key, items.get(index).getCggId());
						}
					}
				}
			}
		}

		public void handleWithRelationshipBank(final WebDriverUtils driver, final Template template, final int templateIndex, String key) {
			// Expand show more results
			By by = By.xpath("//div[not(@class='ng-hide') and not(contains(@class,'no-show'))]/button[@class='show-more-button ng-binding']");
			
			int i = 0;
			while(driver.findElements(by).size() > 0 && i < 5) {
					driver.clickButton(by);
					driver.explicitWait();
					i++;
			} 
			driver.explicitWait();
			
			by = By.xpath("(//div[@ng-switch-when='" + this.getType()
					+ "'])[" +(templateIndex + 1) + "]/descendant::div[contains(@class,'cgg-table-cell')]");
			if (driver.isElementPresent(by)) {
				List<WebElement> elems = driver.findElements(by);

				List<Items> itemList = template.getData().getOptions().getItems();
				if (itemList != null && itemList.size() > 0) {

					List<Integer> itemIndexList = java.util.stream.IntStream.range(0, itemList.size()).boxed()
							.collect(Collectors.toList());
					List<Integer> chosenItemIndex = (List<Integer>) MathUtils.getRandomSublist(itemIndexList,
							MathUtils.getRandomValue(0, itemIndexList.size()) + 1 );
					HashMultimap<String, String> hashValues = this.getValues();
					for (Integer index : chosenItemIndex) {
						if (index >= elems.size()) {
							System.out.println("Size:" + elems.size() + "; index:" + index);
						} else {
							driver.clickButton(elems.get(index));
							hashValues.put(key, itemList.get(index).getCggId());
							this.printKeyValues(key, itemList.get(index).getCggId());
							driver.explicitWait(500);
						}
					}
				} else {
					PersonalLoanConfig config = PersonalLoanConfig.fromUrl(driver.getCurrentUrl());
					ProvidersOrderByBanking providers = config.loadProvidersOrderByBanking();
					List<Integer> itemIndexList = java.util.stream.IntStream.range(0, providers.getProviders().size())
							.boxed().collect(Collectors.toList());
					List<Integer> chosenItemIndex = (List<Integer>) MathUtils.getRandomSublist(itemIndexList,
							MathUtils.getRandomValue(0, itemIndexList.size()));
					HashMultimap<String, String> hashValues = this.getValues();
					for (Integer index : chosenItemIndex) {
						if (index >= elems.size()) {
							System.out.println("Size:" + elems.size() + "; index:" + index);
						} else {
							driver.clickButton(elems.get(index));
							hashValues.put(key, providers.getProviders().get(index).getCggId());
							this.printKeyValues(key, providers.getProviders().get(index).getCggId());
							driver.explicitWait(500);
						}
					}
				}
			}
		}
	},

	PHONE_INPUT("cgg-phone-input") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType()
					+ "'])["+ (templateIndex + 1) +"]/descendant::input[not(@readonly)]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType()
					+ "'])[" +(templateIndex + 1) + "]/descendant::input[not(@readonly)]");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				String currentUrl = driver.getCurrentUrl();
				String input = TestCase.isPTURL(currentUrl) ? "912345678"
						: (TestCase.isMXURL(currentUrl) || TestCase.isPHURL(currentUrl)) ? "2999999999"
								: TestCase.isTHURL(currentUrl) ? "0699999999" : "99999999";
				driver.fillin_textbox(elem, input);
				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(template.getData().getKey(), input);
				this.printKeyValues(template.getData().getKey(), input);
			}
		}
	},

	EMAIL_INPUT("cgg-email-input") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//" + this.getType() + "[@ng-switch-when='" + this.getType() + "']/descendant::input");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);

				String input = new Generex("[a-zA-Z]{6}").random() + "@test.com";
				driver.fillin_textbox(elem, input);
				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(template.getData().getKey(), input);
				this.printKeyValues(template.getData().getKey(), input);
			}
		}

		@Override
		public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//" + this.getType() + "[@key='" + component.getKey() + "']/descendant::input");
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);

				String input = "automated.qa@compareglobalgroup.com";
				driver.fillin_textbox(elem, input);
				HashMultimap<String, String> hashValues = this.getValues();
				hashValues.put(component.getKey(), input);
				this.printKeyValues(component.getKey(), input);
			}
		}
	},

	TEXT_INPUT("cgg-input-text") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])[" +(templateIndex + 1) + "]/descendant::input");
			if (!Checker.isBlank(template.getData().getKey())) {
				by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType()
						+ "'])[" + (templateIndex + 1) +"]/descendant::input[@name='" + template.getData().getKey() + "']");
			}

			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + "[@ng-switch-when='" + this.getType() + "'])[" +(templateIndex + 1) + "]/descendant::input");
			if (!Checker.isBlank(template.getData().getKey())) {
				by = By.xpath("//" + this.getType() + "[@ng-switch-when='" + this.getType()
						+ "']/descendant::input[@name='" + template.getData().getKey() + "']");
			}
			this.fillinText(driver, by, template.getData().getKey());
		}

		@Override
		public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]/descendant::input");
			if (!Checker.isBlank(component.getKey())) {
				by = By.xpath("//" + this.getType() + "/descendant::input[@name='" + component.getKey() + "']");
			}

			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//" + this.getType() + ")[" + (templateIndex + 1) + "]/descendant::input");
			String keyword = component.getKey();
			if (!Checker.isBlank(keyword)) {
				String key = keyword;
				if (!Checker.isBlank(component.getAttributes().getOptions().getKey())) {
					key = component.getAttributes().getOptions().getKey();
				}
				by = By.xpath("//" + this.getType() + "/descendant::input[@name='" + key + "']");
			}

			fillinText(driver, by, keyword);
		}

		public void fillinText(final WebDriverUtils driver, By by, String keyword) {
			if (driver.isElementPresent(by)) {
				WebElement elem = driver.findElement(by);
				String input = "";
				if (elem.isEnabled()) {
					
					List<String> inputList = Lists.newArrayList();
					
					if (keyword.toLowerCase().contains("phone")
							|| keyword.toLowerCase().contains("mobile")) {
						input = TestCase.isPTURL(driver.getCurrentUrl()) ? "912345678"
								: TestCase.isMXURL(driver.getCurrentUrl()) || TestCase.isDKURL(driver.getCurrentUrl())
										? "2999999999"
										: TestCase.isSGURL(driver.getCurrentUrl()) ? "99999999"
												: TestCase.isHKURL(driver.getCurrentUrl()) ? "66999999" : 
													TestCase.isIDURL(driver.getCurrentUrl())? "62999999999999":"9999999999";
						
						inputList.add(input);
						
					} else if (keyword.toLowerCase().contains("email")) {
						
						String email = new Generex("[a-zA-Z]{6}").random() + "@test.com";
						inputList.add(email);
						
					} else if (keyword.toLowerCase().contains("taxnumber")){
						
						inputList.add("123456789");
						
					} else if (keyword.toLowerCase().contains("idnumber")) {
						
						inputList.add("S0000007H");
						inputList.add("Z0000001");
						inputList.add("VECJ880326XXX");
						inputList.add("9999999999999999");
						inputList.add("S1234567I");
						inputList.add("S1212121J");
						inputList.add("G1234567X");

					} else if (keyword.toLowerCase().contains("presentaddresspostcode")) {

						inputList.add("1200-123");
						inputList.add("12008");
						inputList.add("898786");
						
					} else if (keyword.toLowerCase().contains("iban")) {

						inputList.add("FI21 1234 5600 0007 85");
						inputList.add("PT50444444444444444444444");
						inputList.add("19999999999");

					} else if (keyword.toLowerCase().contains("annualincome")
							|| keyword.toLowerCase().contains("monthlyrental")
							|| keyword.toLowerCase().contains("postcode")) {
						
						inputList.add("999999");

					} else if (keyword.toLowerCase().contains("beneficiaryaccountnumber")
							|| keyword.toLowerCase().contains("usataxid")) {
						
						inputList.add("123-45678-9");
						inputList.add("123456777777777777");
						
					} else if (keyword.toLowerCase().contains("rfc")) {
						
						inputList.add("VECJ880326XXX");

					} else if (keyword.toLowerCase().contains("cpr")) {
						
						inputList.add("221282-0000");

					} else if (keyword.toLowerCase().contains("ssn") 
							|| keyword.toLowerCase().contains("supplementalssn")) {

						inputList.add("311280-999J");
						inputList.add("12037649749");

					} else {
						inputList.add(new Generex("[1-9]{1,4}").random());
						inputList.add(new Generex("[a-zA-Z]{7,11}").random());
						inputList.add(new Generex("[a-zA-Z]{7,11} [a-zA-Z]{7,11}").random());
						inputList.add("9999999999");
						inputList.add("12345678");
						inputList.add("1999999999");
						inputList.add("62999999999999");
						inputList.add("王");
					}
					
					int i = 0;
					do {
						input = inputList.get(i);
						driver.fillin_textbox(elem, input);
						driver.explicitWait();
						i++; 
					} while (existErrorMsg(driver, elem) && i < inputList.size());

				} else {
					input = elem.getText();
				}

				HashMultimap<String, String> hashValues = this.getValues();
				String value = elem.getAttribute("value");
				hashValues.put(keyword, value);
				this.printKeyValues(keyword, value);
			}
		}
	},
	
	CHECKBOX_TERM("cgg-checkout-terms") {

		@Override
		public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			return CHECKBOX_INPUT.isFunnelComponentExistInAppForm(driver, component, templateIndex, userDataMap,
					vertical);
		}


		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			CHECKBOX_INPUT.visitFunnelComponentInAppForm(driver, component, templateIndex, userDataMap, vertical);
		}
	},

	CHECKBOX_INPUT("cgg-checkbox") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
					+ "]/descendant::span[contains(@class,'cgg-checkbox__box')]");

			if (!driver.isElementPresent(by)) {
				by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + templateIndex
						+ "]/descendant::span[contains(@class,'cgg-checkbox__box')]");
			}

			return driver.isElementPresent(by);
		}

		@Override
		public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//*[@data-key='" + component.getKey() + "' or @key='" + component.getKey()
					+ "']/descendant::span[contains(@class,'cgg-checkbox__box')]");
			return driver.isElementPresent(by);
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1)
					+ "]/descendant::span[@class='cgg-checkbox__box']");

			if (!driver.isElementPresent(by)) {
				by = By.xpath("(//*[@ng-switch-when='" + this.getType() + "'])[" + templateIndex
						+ "]/descendant::span[@class='cgg-checkbox__box']");
			}

			if (driver.isElementPresent(by) && !template.getData().getKey().equalsIgnoreCase("cannotFindAddress")) {
				List<WebElement> elems = driver.findElements(by);
				for (WebElement elem : elems) {
					driver.check_checkbox(elem);
				}
			}
		}

		@Override
		public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			By by = By.xpath("//*[@data-key='" + component.getKey() + "' or @key='" + component.getKey()
					+ "']/descendant::span[@class='cgg-checkbox__box']");

			if (driver.isElementPresent(by)) {
				driver.explicitWait();
				driver.scrollWindowToElementAt(by, 500);
				List<WebElement> elems = driver.findElements(by);
				for (WebElement elem : elems) {
					driver.check_checkbox(elem);
				}
			}
		}
	},

	DOB_INPUT_APPENDABLE("cgg-dob-input-appendable") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			return driver.isElementPresent(By.xpath(
					"(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input"));
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			DOB_INPUT.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
			By by = By.xpath(
					"(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input");
			if (driver.isElementPresent(by)) {
				List<WebElement> elemList = driver.findElements(by);

				List<WebElement> elems = Lists.newArrayList();
				for (WebElement elem : elemList) {
					if (elem.isDisplayed()) {
						elems.add(elem);
					}
				}

				if (elems.get(0).isEnabled() && elems.get(1).isEnabled() && elems.get(2).isEnabled()) {
					// Date:
					int day = MathUtils.getRandomValue(1, 30);
					driver.fillin_textbox(elems.get(0), day + "");
					// Month:
					int month = MathUtils.getRandomValue(1, 13);
					driver.fillin_textbox(elems.get(1), month + "");
					// Year:
					String pattern = "19[0-9]{2}";
					String year = new Generex(pattern).random();
					driver.fillin_textbox(elems.get(2), year);

					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(template.getData().getKey(),
							year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
					this.printKeyValues(template.getData().getKey(),
							year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
				}
			}
		}
	},

	DOB_INPUT("cgg-dob-input") {

		@Override
		public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template,
				final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

			return driver.isElementPresent(By.xpath(
					"(//*[@ng-switch-when='" + this.getType() + "'])[" + (templateIndex + 1) + "]/descendant::input"));
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical) {

			this.visitFunnelComponent(driver, template, templateIndex, userDataMap, vertical, this.getType());
		}

		@Override
		public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
				final HashMultimap<String, String> userDataMap, String vertical, String type) {

			By by = By.xpath("(//*[@ng-switch-when='" + type + "'])[" + (templateIndex + 1) + "]/descendant::input");
			if (driver.isElementPresent(by)) {
				List<WebElement> elemList = driver.findElements(by);

				List<WebElement> elems = Lists.newArrayList();
				for (WebElement elem : elemList) {
					if (elem.isDisplayed()) {
						elems.add(elem);
					}
				}

				if (elems.get(0).isEnabled() && elems.get(1).isEnabled() && elems.get(2).isEnabled()) {
					// Date:
					int day = MathUtils.getRandomValue(1, 30);
					driver.fillin_textbox(elems.get(0), day + "");
					// Month:
					int month = MathUtils.getRandomValue(1, 13);
					driver.fillin_textbox(elems.get(1), month + "");
					// Year:
					String pattern = "19[0-9]{2}";
					String year = new Generex(pattern).random();
					driver.fillin_textbox(elems.get(2), year);

					HashMultimap<String, String> hashValues = this.getValues();
					hashValues.put(template.getData().getKey(),
							year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
					this.printKeyValues(template.getData().getKey(),
							year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
				}
			}
		}
	};

	private String type;
	private HashMultimap<String, String> values = HashMultimap.create();

	FunnelComponent(final String type) {
		this.type = type;
	}

	public boolean isFunnelComponentExist(final WebDriverUtils driver, final Template template, final int templateIndex,
			final HashMultimap<String, String> userDataMap, String vertical) {
		return false;
	}

	public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
			final HashMultimap<String, String> userDataMap, String vertical) {
	}

	public void visitFunnelComponentInAppForm(final WebDriverUtils driver, final Component component,
			final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

		this.visitFunnelComponent(driver, null, templateIndex, userDataMap, vertical);
	}

	public boolean isFunnelComponentExistInAppForm(final WebDriverUtils driver, final Component component,
			final int templateIndex, final HashMultimap<String, String> userDataMap, String vertical) {

		return this.isFunnelComponentExist(driver, null, templateIndex, userDataMap, vertical);
	}

	public void visitFunnelComponent(final WebDriverUtils driver, final Template template, final int templateIndex,
			final HashMultimap<String, String> userDataMap, String vertical, String type) {

	}

	public boolean needToClickNextButton() {

		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static FunnelComponent fromString(String text) {

		if (!Checker.isBlank(text)) {
			for (FunnelComponent step : FunnelComponent.values()) {
				if (text.equalsIgnoreCase(step.getType())) {
					return step;
				}
			}
		}
		return null;
	}

	public boolean notKnowLicenseNumInDK(String currentUrl, HashMultimap<String, String> hashValues) {
		return TestCase.isDKURL(currentUrl) && hashValues.containsKey("knowLicensePlateNum")
				&& hashValues.get("knowLicensePlateNum").contains("false");
	}

	public String refineInputPattern(final Template template) {
		String pattern = !Checker.isBlank(template.getData().getPattern()) ? template.getData().getPattern()
				: !Checker.isBlank(template.getData().getKeyOnePattern()) ? template.getData().getKeyOnePattern() : "";

		String refinedPattern = "";
		if (!Checker.isBlank(template.getData().getMinLength())
				|| !Checker.isBlank(template.getData().getKeyOneMinLength())) {
			refinedPattern = "[1-9]" + pattern;
			if (refinedPattern.endsWith("+")) {
				refinedPattern = refinedPattern.substring(0, refinedPattern.lastIndexOf("+"));
			}

			if (!Checker.isBlank(template.getData().getMinLength())) {
				refinedPattern += "{" + template.getData().getMinLength() + ",";
				refinedPattern += Checker.isBlank(template.getData().getMaxLength()) ? template.getData().getMinLength()
						: template.getData().getMaxLength();
				refinedPattern += "}";
			} else if (!Checker.isBlank(template.getData().getKeyOneMinLength())) {
				refinedPattern += "{" + template.getData().getKeyOneMinLength() + ",";
				refinedPattern += Checker.isBlank(template.getData().getKeyOneMaxLength())
						? template.getData().getKeyOneMinLength() : template.getData().getKeyOneMaxLength();
				refinedPattern += "}";
			}
		} else {
			refinedPattern = pattern;
		}
		return refinedPattern;
	}

	public HashMultimap<String, String> getValues() {
		return values;
	}

	public void setValues(HashMultimap<String, String> values) {
		this.values = values;
	}
	
	public boolean existErrorMsg(final WebDriverUtils driver, final WebElement elem) {

//		By by = By.xpath(".//preceding-sibling::(span[@class='cgg-global-labelform ng-binding ng-scope']|label[@class='cgg-label ng-binding ng-scope'])");
		By by = By.xpath("//*[@ng-switch-when='cgg-headline-description']");

		int counter = 0;
		while (!driver.isElementPresent(by, elem) && counter < Constant.MAX_REPEAT_TIME) {
			driver.explicitWait();
			System.out.println("waiting:" + counter);
			counter++;
		}
		if (driver.isElementPresent(by, elem)) {
			driver.clickLink(elem.findElement(by));
			driver.explicitWait();
		}
		return driver.isElementPresent(By.xpath(
				"//div[@class='cgg-global-input--error-notification']|//div[@class='cgg-global-input--error-notification ng-binding']"));
	}
	
	public void printKeyValues(final String key, final String value){
		
		System.out.println(key + "=" + value);
	}

}
