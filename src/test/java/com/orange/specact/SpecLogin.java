package com.orange.specact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.orange.elements.EleLogin;
import com.orange.main.MainDriver;
import com.orange.main.Var;
import com.orange.utils.GenericActions;
import com.orange.utils.JSUtils;
import com.orange.utils.ReportingActions;

public class SpecLogin extends MainDriver {

	public static void logoutAndTearDown() {
		try {
			GenericActions.clickOnElementWithWait(EleLogin.logout_welcome(), 1);
			GenericActions.clickOnElementWithWait(EleLogin.logout(), 2);
			driver.quit();
			ReportingActions.passReport("All Browsers closed successfully");
		} catch (Exception e) {
			ReportingActions.failReport("Failed to close all browsers");
			logger.error("Exception in SpecLogin --> logoutAndTearDown : " + e);
		}
	}

	public static void loginToOrangeHRM() {
		try {
			String userId = prpTest.getProperty("userId");
			String password = prpTest.getProperty("password");

			GenericActions.enterTextInTextbox(EleLogin.login_userId(), userId);
			GenericActions.enterTextInTextbox(EleLogin.login_password(), password);
			GenericActions.clickOnElementWithWait(EleLogin.login_button(), 3);
			if (GenericActions.verifyTextPresenceOnPage("Welcome")) {
				ReportingActions.passReport("Login to OrangeHRM successfully");
			} else {
				ReportingActions.failReport("Login to OrangeHRM failed");
			}
		} catch (Exception e) {
			ReportingActions.failReport("Login to OrangeHRM failed");
			logger.error("Exception in SpecLogin --> loginToOrangeHRM : " + e);
		}
	}

	public static void navigateToMenuOption(String module, String subModule, String option) {
		try {
			module = module.trim();
			subModule = subModule.trim();
			option = option.trim();
			WebElement moduleWeb = driver.findElement(By.xpath("//b[text()='" + module + "']"));
			if (subModule.length() == 0) {
				GenericActions.clickOnElement(moduleWeb);
				ReportingActions.passReport("Navigated to the Module : " + module);
			}
			else if (option.length()==0){
				WebElement subModuleWeb = driver.findElement(By.xpath("//a[text()='" + subModule + "']"));
				JSUtils.clickWithElementName(subModuleWeb, subModule);
				ReportingActions.passReport("Navigated to the Sub-Module : " + subModule);
			} else {
				WebElement optionWeb = driver.findElement(By.xpath("//a[text()='"+option+"']"));
				JSUtils.clickWithElementName(optionWeb, option);
				ReportingActions.passReport("Navigated to the Option : " + option);
			}

		} catch (Exception e) {
			ReportingActions.failReport("Navigate to Menu option failed");
			logger.error("Exception in SpecLogin --> navigateToMenuOption : " + e);
		}
	}

}
