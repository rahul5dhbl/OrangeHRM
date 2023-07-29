package com.orange.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.orange.main.MainDriver;

public class JSUtils extends MainDriver {
	public static void click(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (driver);
			js.executeScript("arguments[0].click();", element);
			ReportingActions.passReport("Clicked on Element successfully");
		} catch (Exception e) {
			logger.error("Exception in JSActions --> clickOnElement : " + e);
			ReportingActions.failReport("Failed to click on element");
		}
	}
	
	public static void clickWithElementName(WebElement element, String elementName) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (driver);
			js.executeScript("arguments[0].click();", element);
			ReportingActions.passReport("Clicked on Element successfully : " + elementName);
		} catch (Exception e) {
			logger.error("Exception in JSActions --> clickOnElement : " + e);
			ReportingActions.failReport("Failed to click on element : " + elementName);
		}
	}

	public static void enterValueInFields(WebElement element, String textToBeEntered) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) (driver);
			js.executeScript("arguments[0].value='arguments[0]';", element, textToBeEntered);
			System.out.println("Text entered successfully : " + textToBeEntered);
		} catch (Exception e) {
			logger.error("Exception in JSActions --> enterValueInFields : " + e);
			ReportingActions.failReport("Failed to enter text in textboxt : " + textToBeEntered);
		}
	}
}
