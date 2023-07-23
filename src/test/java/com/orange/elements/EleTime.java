package com.orange.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.orange.main.MainDriver;
import com.orange.utils.ReportingActions;

public class EleTime extends MainDriver
{
	
	public static WebElement time_myTimesheets_periodDropdown()
	{
		try {
			WebElement element = driver.findElement(By.xpath(objRepo.getProperty("time_myTimesheets_periodDropdown")));
			ReportingActions.passReport("Webelement found --> time_myTimesheets_periodDropdown");
			return element;			
		} catch (Exception e) {
			ReportingActions.failReport("Webelement not found : time_myTimesheets_periodDropdown");
			logger.error("Exception in LoginElements --> time_myTimesheets_periodDropdown : "+e);
			return null;
		}
	}
	
}
