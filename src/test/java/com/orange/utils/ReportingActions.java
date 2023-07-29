package com.orange.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.orange.main.MainDriver;

public class ReportingActions extends MainDriver
{	
	static String AuthName = prpTest.getProperty("AuthName");
	static String TestCategory= prpTest.getProperty("TestCategory");
	static String TestDevice = prpTest.getProperty("TestDevice");
	public static void startRegPack()
	{				

		try 
		{
			//String timestamp = GenericActions.getCurrentDate("yyyy-MM-dd_HH-mm-ss");
			//report = new ExtentReports("./ExecutionResult/Report_"+timestamp+".html");
			//test = report.startTest("BradescoRPA");
			report = new ExtentReports();
			String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
			String TestReportName = "TestReport-"+ timestamp + ".html";
			ExtentSparkReporter spark = new ExtentSparkReporter("./ExecutionResult/"+TestReportName);
			spark.config().setTheme(Theme.STANDARD);
			spark.config().setDocumentTitle("MyReport");

			report.attachReporter(spark);
		
		}
		catch (Exception e) 
		{
			System.out.println("Exception in ReportingActions --> startRegPack : "+e);
			logger.error("Exception in ReportingActions --> startRegPack : "+e);
		}
	}
	
	public static void startTest(String testCaseName) {
		try {
			 test = report.createTest(testCaseName).assignAuthor(AuthName).assignCategory(TestCategory)
						.assignDevice(TestDevice);
				
				test.info(testCaseName + " has started");
				logger.info("\n------------------------"+testCaseName+"-----------------\n"
						+ "----------------------Test Case STarted------------------");
		}catch (Exception e) 
			{
				System.out.println("Exception in ReportingActions --> startTest : "+e);
				logger.error("Exception in ReportingActions --> startTest : "+e);
			}
	}
	
	public static void passReport(String passMessage)
	{
		try 
		{
			//test.log(LogStatus.PASS, passMessage);
			test.pass(passMessage);
			logger.info(passMessage);
		}
		catch (Exception e) 
		{
			System.out.println("Exception in ReportingActions --> passReport : "+e);
			logger.error("Exception in ReportingActions --> passReport : "+e);
			
		}
	}
	
	public static void failReport(String failMessage)
	{
		try 
		{
			String screenshotFilePath = getScreenshot("");
			test.fail(failMessage, MediaEntityBuilder.createScreenCaptureFromPath(screenshotFilePath).build());
			//test.log(LogStatus.FAIL, failMessage);	
			logger.info(failMessage);
		}
		catch (Exception e) 
		{
			System.out.println("Exception in ReportingActions --> failReport : "+e);
			logger.error("Exception in ReportingActions --> failReport : "+e);
		}
	}
	
	public static void infoReport(String infoMessage)
	{
		try 
		{
			//test.log(LogStatus.INFO, infoMessage);
			test.info(infoMessage);
			logger.info(infoMessage);
		}
		catch (Exception e) 
		{
			System.out.println("Exception in ReportingActions --> infoReport : "+e);
			logger.error("Exception in ReportingActions --> infoReport : "+e);
		}
	}

	
	//Creating a method getScreenshot and passing two parameters 
	//driver and screenshotName
	public static String getScreenshot(String screenshotName) throws Exception {
	                //below line is just to append the date format with the screenshot name to avoid duplicate names		
	                String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
	                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
			String destination = System.getProperty("user.dir") + "/Screenshots/"+screenshotName+dateName+".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
	                //Returns the captured file path
			return destination;
	}
	
	
	public static void endRegPack()
	{
		try
		{
			report.flush();
		}
		catch (Exception e) 
		{
			System.out.println("Exception in ReportingActions --> endRegPack : "+e);
			logger.error("Exception in ReportingActions --> endRegPack : "+e);
		}
	}

	
}
