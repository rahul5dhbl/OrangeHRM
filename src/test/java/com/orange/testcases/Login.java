package com.orange.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orange.elements.EleLogin;
import com.orange.main.MainDriver;
import com.orange.main.Var;
import com.orange.specact.SpecLogin;
import com.orange.utils.ExcelActions;
import com.orange.utils.GenericActions;
import com.orange.utils.ReportingActions;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Login extends MainDriver {

	String testCaseName;
	String passMessage;
	String failMessage;
	String[] dataExec;

	@BeforeSuite
	public void setUpBeforeSuite() {
		try {
			prpTest = GenericActions.getProperties("./TestData.properties");
			objRepo = GenericActions.getProperties("./ObjectRepository.properties");
			String testDataPath = prpTest.getProperty("testDataFilePath");
			String dataSheet = prpTest.getProperty("testDataSheet");
			testData = ExcelActions.ReadDataFromExcelInMap(testDataPath, dataSheet);
			ReportingActions.startRegPack();

		} catch (Exception e) {
			ReportingActions.failReport("Failed to close all browsers");
			logger.error("Exception in Login --> setUpBeforeSuite : " + e);
		}
	}

	@BeforeMethod
	@Parameters({ "testName" })
	public void setUpBeforeTest(String testName) {
		testCaseName = testName;
		try {
			// test = report.startTest(testCaseName);
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

		} catch (Exception e) {
			ReportingActions.failReport("Failed to Setup Before Test");
			logger.error("Exception in Login --> setUpBeforeTest : " + e);
		}
	}

	@Test
	public void loginToOrangeHRM() {
		try {
			String textForPass = dataExec[Var.Param1];
			String userId = prpTest.getProperty("userId");
			String password = prpTest.getProperty("password");

			GenericActions.enterTextInTextbox(EleLogin.login_userId(), userId);
			GenericActions.enterTextInTextbox(EleLogin.login_password(), password);
			GenericActions.clickOnElementWithWait(EleLogin.login_button(), 3);
			if (GenericActions.verifyTextPresenceOnPage(textForPass)) {
				ReportingActions.passReport(passMessage);
			} else {
				ReportingActions.failReport(failMessage);
			}
		} catch (Exception e) {
			ReportingActions.failReport(failMessage);//pass e here
			logger.error("Exception in Login --> loginToOrangeHRM : " + e);
		}
	}

	@AfterMethod
	public void tearDownAfterTest() {
		try {
			SpecLogin.logoutAndTearDown();
			report.flush();
		} catch (Exception e) {
			ReportingActions.failReport("Failed to teardown after test");
			logger.error("Exception in Login --> tearDownAfterTest : " + e);
		}
	}

	@AfterSuite
	public void tearDownAfterSuite() {
		try {
			ReportingActions.endRegPack();
		} catch (Exception e) {
			ReportingActions.failReport("Failed to teardown after suite");
			logger.error("Exception in Login --> tearDownAfterSuite : " + e);
		}
	}
}
