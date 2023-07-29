package com.orange.testcases;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orange.main.MainDriver;
import com.orange.main.Var;
import com.orange.specact.SpecLogin;
import com.orange.utils.ExcelActions;
import com.orange.utils.GenericActions;
import com.orange.utils.ReportingActions;

public class MyInfo extends MainDriver{
	String testCaseName;
	String passMessage;
	String failMessage;
	String[] dataExec;
	@BeforeSuite
	public void setUpBeforeSuite()
	{
		try
		{
			prpTest = GenericActions.getProperties("./TestData.properties");
			objRepo = GenericActions.getProperties("./ObjectRepository.properties");
			String testDataPath = prpTest.getProperty("testDataFilePath");
			String dataSheet = prpTest.getProperty("testDataSheet");
			testData = ExcelActions.ReadDataFromExcelInMap(testDataPath, dataSheet);			
			ReportingActions.startRegPack();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in Time --> setUpBeforeSuite : "+e );
		}
	}

	@BeforeMethod
	@Parameters({"testName"})
	public void setUpBeforeTest(String testName)
	{
		testCaseName = testName;
		try
		{
			//test = report.startTest(testCaseName);
			String browser = prpTest.getProperty("browser");
			String url = prpTest.getProperty("url");
			dataExec = testData.get(testCaseName);
			ReportingActions.startTest(testCaseName);
			
			passMessage = dataExec[Var.PassMessage];
			failMessage = dataExec[Var.FailMessage];
			
			driver = GenericActions.launchBrowser(browser, testCaseName);
			driver.get(url);
			driver.manage().window().maximize();
			Thread.sleep(3000);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			SpecLogin.loginToOrangeHRM();
		}
		catch(Exception e)
		{
			System.out.println("Exception in Login --> setUp : "+e );
			ReportingActions.failReport("Failed to Setup Before Test");
		}
	}

	@Test
	public void startAddingTestCasesFromHere()
	{
		try 
		{
			
		}
		catch (Exception e)
		{
			ReportingActions.failReport(failMessage);
		}
	}
	
	@AfterMethod
	public void tearDownAfterTest()
	{
		
		SpecLogin.logoutAndTearDown();
		report.flush();
		//report.endTest(test);
	}
	
	@AfterSuite
	public void tearDownAfterSuite()
	{
		//report.flush();
		ReportingActions.endRegPack();
	}
}
