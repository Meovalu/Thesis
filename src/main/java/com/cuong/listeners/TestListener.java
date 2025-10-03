package com.cuong.listeners;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.cuong.base.BaseTest;
import com.cuong.utils.ExtentManager;
import com.cuong.utils.RetryAnalyzer;

public class TestListener implements ITestListener, ISuiteListener, IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	// Triggered when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: " + testName);
	}

	// Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logStep("Test End: " + testName + " - ✔ Test Passed");
	}

	// Triggered when a Test Fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMessage = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		ExtentManager.logFailure(BaseTest.getDriver(), "Test Failed!", "Test End: " + testName + " - ❌ Test Failed");
	}

	// Triggered when a Test skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped " + testName);
	}

	// Triggered when a suite Starts
	@Override
	public void onStart(ISuite iSuite) {
		// Initialize the Extent Reports
		ExtentManager.getReporter();
	}

	// Triggered when the suite ends
	@Override
	public void onFinish(ISuite iSuite) {
		// Flush the Extent Reports
		ExtentManager.endTest();
		ExtentManager.endSuite();
	}

}
