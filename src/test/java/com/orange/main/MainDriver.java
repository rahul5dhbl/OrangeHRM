package com.orange.main;

import java.util.Map;
import java.util.Properties;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class MainDriver 
{
	public static WebDriver driver;
	public static Properties prpTest;
	public static Properties objRepo;
	public static ExtentReports report;
	public static ExtentTest test;
	public static Map<String, String[]> testData;
	public static final Logger logger = LogManager.getLogger(MainDriver.class);
	



	/*public void mainTestCase()
	{
		prpTest = GenericActions.getProperties("./TestData.properties");
		objRepo = GenericActions.getProperties("./ObjectRepository.properties");
		driver = GenericActions.launchBrowser(prpTest.getProperty("browser"));
	}*/
	
}
