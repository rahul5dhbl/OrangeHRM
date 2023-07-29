package com.orange.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.orange.main.MainDriver;
import com.orange.utils.ReportingActions;

public class EleLogin extends MainDriver
{
	public static WebElement login_userId()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("login_userId")));
			ReportingActions.passReport("Webelement found --> login_userId");
			return element;		
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : login_userId");
			logger.error("Exception in LoginElements --> login_userId : "+e);
			return null;
			
		}
	}
	
	public static WebElement login_password()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("login_password")));
			ReportingActions.passReport("Webelement found --> login_password");
			return element;			
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : login_password");
			logger.error("Exception in LoginElements --> login_password : "+e);
			return null;
		}
	}
	
	public static WebElement login_button()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("login_button")));
			ReportingActions.passReport("Webelement found --> login_button");
			return element;			
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : login_button");
			logger.error("Exception in LoginElements --> login_button : "+e);
			return null;
		}
	}
	
	public static WebElement logout()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("logout")));
			ReportingActions.passReport("Webelement found --> logout");
			return element;			
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : logout");
			logger.error("Exception in LoginElements --> logout : "+e);
			return null;
		}
	}
	
	public static WebElement logout_welcome()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("logout_welcome")));
			ReportingActions.passReport("Webelement found --> logout_welcome");
			return element;			
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : logout_welcome");
			logger.error("Exception in LoginElements --> logout_welcome : "+e);
			return null;
		}
	}
	
}
