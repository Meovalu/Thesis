package com.cuong.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cuong.keywords.Keywords;
import com.cuong.base.BaseTest;

public class LoginPage {
	private Keywords keywords;
	
	public LoginPage(WebDriver driver) {
		this.keywords = BaseTest.getActionDriver();
	}
	
	//Locators
	private By user = By.id("user-name");
	private By pass = By.id("password");
	private By loginBtn = By.xpath("//input[@id='login-button']");
	
	//Actions
	public void login(String username, String pwd) {
		keywords.enterText(user, username);
		keywords.enterText(pass, pwd);
		keywords.click(loginBtn);
	}
}
