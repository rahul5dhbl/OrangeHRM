package com.orange.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.orange.main.MainDriver;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenericActions extends MainDriver {
	public static Properties getProperties(String filePath) {
		try {
			Properties p = new Properties();
			FileReader reader = new FileReader(filePath);
			p.load(reader);
			return p;
		} catch (Exception e) {
			ReportingActions.failReport("Failed to get properties file : " + filePath);
			logger.error("Exception in GenericActions --> getProperties : " + e);
			return null;
		}
	}

	public static void createAndRunXMLfileForSingleTest(String testName, String classPath) {
		try {
			XmlSuite suite = new XmlSuite();
			suite.setName("Suite"); // this means <suite name = "Suite">

			XmlTest test = new XmlTest(suite);
			test.setName("Test"); // this means <test name = "Test">
			test.setThreadCount(5);
			List<XmlTest> testList = new ArrayList<XmlTest>();
			testList.add(test);
			suite.setTests(testList);

			List<XmlClass> classes = new ArrayList<XmlClass>(); // <classes>
			XmlClass clas = new XmlClass(classPath);
			clas.setName(classPath);
			classes.add(clas); // this means <class name = clas>
			test.setXmlClasses(classes);

			List<XmlInclude> methods = new ArrayList<XmlInclude>();
			XmlInclude include = new XmlInclude(testName);
			include.addParameter("testName", testName);

			methods.add(include);
			clas.setIncludedMethods(methods);

			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG testng = new TestNG();
			testng.setXmlSuites(suites);
			testng.run();

		} catch (Exception e) {
			logger.error("Exception in GenericActions --> createAndRunXMLfileForSingleTest : " + e);
			ReportingActions
					.failReport("Failed to create and run XML file for single test for test case name : " + testName);
		}
	}

	public static void createAndRunXMLfileForMultipleTestsInSingleClass(Map<String, String[]> testData,
			String classPath) {
		try {
			XmlSuite suite = new XmlSuite();
			suite.setName("Suite"); // this means <suite name = "Suite">

			XmlTest test = new XmlTest(suite);
			test.setName("Test"); // this means <test name = "Test">
			test.setThreadCount(5);
			List<XmlTest> testList = new ArrayList<XmlTest>();
			testList.add(test);
			suite.setTests(testList);

			List<XmlClass> classes = new ArrayList<XmlClass>(); // <classes>
			XmlClass clas = new XmlClass(classPath);
			clas.setName(classPath);
			classes.add(clas); // this means <class name = clas>
			test.setXmlClasses(classes);

			Set<String> allTCs = testData.keySet();

			List<XmlInclude> methods = new ArrayList<XmlInclude>();
			for (String testCase : allTCs) {
				if (testData.get(testCase)[12].equalsIgnoreCase("Y")) {
					testCase = testCase.trim();
					XmlInclude include = new XmlInclude(testCase);
					include.addParameter("testName", testCase);

					methods.add(include);
				} else {
					System.out.println("Test Case : " + testCase + " has not flag 'Y' hence not executed");
				}
			}

			clas.setIncludedMethods(methods);

			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG testng = new TestNG();
			testng.setXmlSuites(suites);
			testng.run();
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> createAndRunXMLfileForMultipleTestsInSingleClass : " + e);
			ReportingActions
					.failReport("Failed to create and run XML file for multiple tests for class name : " + classPath);
		}
	}

	public static Map<String, String> createMapForClass(String classPath) {
		try {
			Map<String, String> testCaseClass = new HashMap<String, String>();
			Set<String> allTestCases = testData.keySet();
			for (String testCase : allTestCases) {
				if (testData.get(testCase)[13].equalsIgnoreCase(classPath)) {
					testCaseClass.put(testCase, classPath);
				}
			}
			return testCaseClass;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void createAndRunXMLfileForMultipleTestsInMultipleClasses(Map<String, String[]> testData,
			Set<String> classPaths) {
		try {
			XmlSuite suite = new XmlSuite();
			suite.setName("Suite");

			XmlTest test = new XmlTest(suite);
			test.setName("Test");
			test.setThreadCount(5);
			List<XmlTest> testList = new ArrayList<XmlTest>();
			testList.add(test);
			suite.setTests(testList);
			List<XmlClass> classes = new ArrayList<XmlClass>();

			for (String classPath : classPaths) {
				Map<String, String> testMap = createMapForClass(classPath);
				XmlClass clas = new XmlClass(classPath);
				clas.setName(classPath);
				classes.add(clas);
				List<XmlInclude> methods = new ArrayList<XmlInclude>();

				Set<String> allTestCases = testMap.keySet();
				for (String testCase : allTestCases) {
					if (testData.get(testCase)[12].equalsIgnoreCase("Y")) {
						testCase = testCase.trim();
						XmlInclude include = new XmlInclude(testCase);
						include.addParameter("testName", testCase);

						methods.add(include);
					} else {
						System.out.println("Test Case : " + testCase + " has not flag 'Y' hence not executed");
					}
				}
			}

			test.setXmlClasses(classes);

			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add(suite);
			TestNG testng = new TestNG();
			testng.setXmlSuites(suites);
			testng.run();
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> createAndRunXMLfileForMultipleTestsInMultipleClasses : " + e);
			ReportingActions.failReport("Failed to create and run XML file for multiple tests for multiples classes");
		}
	}

	public static WebDriver launchBrowser(String browserName, String testCaseName) {
		try {
			logger.info("---------------------------||" + testCaseName + "||------------------------------ \n"
					+ "-------------------------||Test Case Started||------------------------");

			if (browserName.equalsIgnoreCase("chrome")) {
				if (driver == null) {
					// System.out.println(System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
					// System.setProperty("webdriver.chrome.driver",
					// System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
					System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
					driver = new ChromeDriver();
					System.out.println("Chrome Browser Launched Successfully");
					ReportingActions.passReport("Chrome Browser launched successfully ");
				}
			} else if (browserName.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\driver\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				ReportingActions.passReport("Internet Explorer Browser Launched Successfully");
			} else {
				browserName = "chrome";
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
				driver = new ChromeDriver();
				ReportingActions.passReport("Chrome Browser Launched Successfully");
			}
			return driver;
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> launchBrowser : " + e);
			ReportingActions.failReport("Failed to launch browser" + e);
			return null;
		}
	}

	public static void enterTextInTextbox(WebElement textbox, String textToBeEntered) {
		try {
			textbox.sendKeys(textToBeEntered);
			System.out.println("Successfully entered in textbox --> " + textToBeEntered);
			ReportingActions.passReport("Successfully entered in textbox --> " + textToBeEntered);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> enterTextInTextbox : " + e);
			ReportingActions.failReport("Failed to enter text in textbox : " + textToBeEntered);
		}
	}

	public static void enterTextInTextboxWithWait(WebElement textbox, String textToBeEntered, int waitTimeInSeconds) {
		try {
			textbox.sendKeys(textToBeEntered);
			Thread.sleep(waitTimeInSeconds * 1000);
			ReportingActions.passReport("Successfully entered in textbox --> " + textToBeEntered);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> enterTextInTextbox : " + e);
			ReportingActions.failReport("Failed to enter text in textbox with wait : " + textToBeEntered);
		}
	}

	public static void clickOnElement(WebElement elementForClick) {
		try {
			elementForClick.click();
			ReportingActions.passReport("Successfully clicked on element ");
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> clickOnElement : " + e);
			ReportingActions.failReport("Failed to click on webelement");
		}
	}

	public static void clickOnElementWithElementName(WebElement elementForClick, String elementName) {
		try {
			elementForClick.click();
			ReportingActions.passReport("Successfully clicked on element : " + elementName);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> clickOnElementWithElementName : " + e);
			ReportingActions.failReport("Failed to click on webelement : " + elementName);
		}
	}

	public static void hoverOnElementWithElementName(WebElement elementForHover, String elementName) {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(elementForHover);
			ReportingActions.passReport("Successfully hovered on element : " + elementName);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> hoverOnElement : " + e);
			ReportingActions.failReport("Failed to hover on webelement : " + elementName);
		}
	}

	public static void clickOnElementWithWait(WebElement elementForClick, int waitTimeInSeconds) {
		try {
			elementForClick.click();
			Thread.sleep(waitTimeInSeconds * 1000);
			ReportingActions.passReport("Successfully clicked on element ");
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> clickOnElementWithWait : " + e);
			ReportingActions.failReport("Failed to click on WebElement and wait");
		}
	}

	public static void selectDropdownWithVisibleText(WebElement dropdownElement, String visibleText) {
		try {
			Select sel = new Select(dropdownElement);
			sel.selectByVisibleText(visibleText);
			ReportingActions.passReport("Successfully selected dropdown --> " + visibleText);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> selectDropdownWithVisibleText : " + e);
			ReportingActions.failReport("Failed to select dropdown with visible text");
		}
	}

	public static boolean isAlertPresent() {
		try {
			try {
				driver.switchTo().alert();
				ReportingActions.infoReport("Alert is present");
				return true;
			} catch (Exception e) {
				ReportingActions.infoReport("Alert is not present");
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> isAlertPresent : " + e);
			ReportingActions.failReport("Failed to detect alert");
			return false;
		}
	}

	public static boolean isCheckboxSelected(WebElement checkbox) {
		try {
			boolean result = checkbox.isSelected();
			if (result) {
				ReportingActions.infoReport("Checkbox is selected");
			} else {
				ReportingActions.infoReport("Checkbox is not selected");
			}
			return result;
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> isCheckboxSelected : " + e);
			ReportingActions.failReport("Failed to detect checkbox is selected or not");
			return false;
		}
	}

	public static boolean isElementPresent(String xpath) {
		try {
			List<WebElement> allElements = driver.findElements(By.xpath(xpath));
			int size = allElements.size();
			if (size > 0) {
				ReportingActions.passReport("WebElement is present");
				return true;
			} else {
				ReportingActions.failReport("WebElement is not present");
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> isElementPresent : " + e);
			ReportingActions.failReport("Failed to detect element is present");
			return false;
		}
	}

	public static boolean isElementAbsent(String xpath) {
		try {
			List<WebElement> allElements = driver.findElements(By.xpath(xpath));
			int size = allElements.size();
			if (size > 0) {
				ReportingActions.passReport("WebElement is present");
				return false;
			} else {
				ReportingActions.failReport("WebElement is not present");
				return true;
			}
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> isElementAbsent : " + e);
			ReportingActions.failReport("Failed to detect element is not present");
			return false;
		}
	}

	public static String getCurrentDate(String format) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
			LocalDateTime now = LocalDateTime.now();
			return dtf.format(now);
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> getCurrentDate : " + e);
			ReportingActions.failReport("Failed to get current date");
			return null;
		}
	}

	public static boolean verifyTextPresenceOnPage(String text) {
		try {
			int count = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]")).size();
			if (count > 0) {
				ReportingActions.passReport("Text : " + text + " is present on the current page");
				return true;
			} else {
				ReportingActions.failReport("Text : " + text + " is not present on the current page");
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> verifyTextPresenceOnPage : " + e);
			ReportingActions.failReport("Failed to verify text on the page");
			return false;
		}
	}

	public static void displayDataInMap(Map<String, String> dataInApp) {
		try {
			Set<String> allKeys = dataInApp.keySet();
			for (String key : allKeys) {
				System.out.println("Key : " + key + "                                               and Value : "
						+ dataInApp.get(key));
			}
		} catch (Exception e) {
			logger.error("Exception in GenericActions --> displayDataInMap : " + e);
			ReportingActions.failReport("Failed to display data in map");
		}
	}
}
