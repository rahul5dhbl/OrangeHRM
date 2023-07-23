package com.orange.demo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.core.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.dom4j.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlMethodSelector;
import org.testng.xml.XmlMethodSelectors;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.orange.main.MainDriver;
import com.orange.main.Var;
import com.orange.utils.ExcelActions;
import com.orange.utils.GenericActions;
import com.orange.utils.ReportingActions;

public class TestDemo extends MainDriver {

	public static void main(String[] args) {
		String testName = "loginToOrangeHRM";
		String classPath = "com.orange.testcases.Login";
		// createXMLfile(testName, "com.orange.testcases.Login");
		// createAndRunXMLfileForSingleTest(testName, classPath);
		prpTest = GenericActions.getProperties("./TestData.properties");
		objRepo = GenericActions.getProperties("./ObjectRepository.properties");
		String testDataPath = prpTest.getProperty("testDataFilePath");
		String dataSheet = prpTest.getProperty("testDataSheet");
		testData = ExcelActions.ReadDataFromExcelInMap(testDataPath, dataSheet);
		createAndRunXMLfileForSingleTest(testName, classPath);
		// createAndRunXMLfileForMultipleTestsInSingleClass(testData,
		// "com.orange.testcases.Login");
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	public static void createXMLfile(String testName, String packageName) {
		try {
			File file = new File(System.getProperty("user.dir") + "\\testng-orange.xml");
			if (file.exists()) {
				file.delete();
			}

			org.jdom.Document doc = new org.jdom.Document();

			Element suite = new Element("suite");
			suite.setAttribute("name", "Suite");

			Element test = new Element("test");
			test.setAttribute("thread-count", "5");
			test.setAttribute("name", "Test");

			Element classes = new Element("classes");

			Element clas = new Element("class");
			clas.setAttribute("name", packageName);// replace "com.orange.testcases.Login" with packageName here

			Element methods = new Element("methods");

			Element include = new Element("include");
			include.setAttribute("name", testName);// replace "loginToOrangeHRM" with testName here

			Element parameter = new Element("parameter");
			parameter.setAttribute("name", "testName");
			parameter.setAttribute("value", testName);

			suite.addContent(test);
			test.addContent(classes);
			classes.addContent(clas);
			clas.addContent(methods);
			methods.addContent(include);
			include.addContent(parameter);

			doc.setRootElement(suite);
			XMLOutputter outter = new XMLOutputter();
			outter.setFormat(Format.getPrettyFormat());
			outter.output(doc, new FileWriter(new File(System.getProperty("user.dir") + "\\testng-orange.xml")));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
