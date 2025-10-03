package com.cuong.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cuong.base.BaseTest;
import com.cuong.pages.HomePage;
import com.cuong.pages.LoginPage;
import com.cuong.utils.ExtentManager;
import com.cuong.utils.DataProviders;

public class LoginTest extends BaseTest {
	
	private HomePage homePage;
	private LoginPage loginPage;
	
	@BeforeMethod
	public void setupPage() {
		homePage = new HomePage(getDriver());
		loginPage = new LoginPage(getDriver());
	}
	
	@Test(dataProvider="validLoginData", dataProviderClass = DataProviders.class)
	public void validLoginTest(String email, String pwd) {
		ExtentManager.logStep("Navigating to login menu");
		homePage.goToLogin();
		ExtentManager.logStep("Login with valid credentials");
		loginPage.login(email, pwd);
		ExtentManager.logStep("Verifying login status");
		Assert.assertTrue(homePage.getURL().contains("account"),"Login failed");
		ExtentManager.logStep("Validation success");
	}

	
}
