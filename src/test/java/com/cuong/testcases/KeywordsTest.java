package com.cuong.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cuong.base.BaseTest;
import com.cuong.pages.TestPage;
import com.cuong.utils.ExtentManager;

public class KeywordsTest extends BaseTest {
	
	private TestPage testPage;
	
	@BeforeMethod
	public void setupPage() {
		testPage = new TestPage(getDriver());
	}
	
	@Test
	public void acceptAlertTest() {
		ExtentManager.logStep("Verifying accepting alert");
		Assert.assertTrue(testPage.verifyAcceptAlert(), "Cannot accept aleart");
		ExtentManager.logStep("Verify complete");
	}
	
	public void denyAlertTest() {
		ExtentManager.logStep("Verifying denying alert");
		Assert.assertTrue(testPage.verifyCancelAlert(), "Cannot deny aleart");
		ExtentManager.logStep("Verify complete");
	}
	
	public void alertMessageTest() {
		ExtentManager.logStep("Verifying alert message");
		Assert.assertTrue(testPage.verifyAlertMsg(), "Alert message not match");
		ExtentManager.logStep("Verify complete");
	}
	
	@Test
	public void urlTest() {
		ExtentManager.logStep("Verifying page url");
		Assert.assertTrue(testPage.verifyUrl("Selenium"), "Url not match");
		ExtentManager.logStep("Verify complete");
	}
	
	public void dndTest() {
		ExtentManager.logStep("Verifying dnd");
		Assert.assertTrue(testPage.verifyDnD(), "Dnd didn't work");
		ExtentManager.logStep("Verify complete");
	}

}
