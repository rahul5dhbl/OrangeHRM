package com.orange.specact;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.model.Report;
import com.orange.elements.EleLogin;
import com.orange.elements.EleTime;
import com.orange.main.MainDriver;
import com.orange.main.Var;
import com.orange.utils.GenericActions;
import com.orange.utils.JSUtils;
import com.orange.utils.ReportingActions;

public class SpecTime extends MainDriver{

	public static void viewTimesheet(String timePeriod) {
		try {
			GenericActions.selectDropdownWithVisibleText(EleTime.time_myTimesheets_periodDropdown(), timePeriod);
			boolean flag = GenericActions.isElementPresent("//h1[@id='actionLogHeading']");
			if(flag)
			{
				List<WebElement> allRows = driver.findElements(By.xpath("//th[text()='Action']/ancestor::table[1]/tbody/tr"));
				ReportingActions.infoReport("Records present in My TImesheet for Period : "+timePeriod+" is : "+allRows.size());
			}
			else
			{
				ReportingActions.passReport("No records present");
			}
		} catch (Exception e) {
			ReportingActions.failReport("Failed to view Report");
			logger.error("Exception in SpecTime --> viewTimesheet : " + e);
		}
	}
	

}
