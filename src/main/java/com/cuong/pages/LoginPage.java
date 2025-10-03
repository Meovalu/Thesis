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
	private By mail = By.id("input-email");
	private By pass = By.xpath("input-password");
	private By loginBtn = By.xpath("//button[normalize-space()='Login']");
	
	//Actions
	public void login(String email, String pwd) {
		keywords.enterText(mail, email);
		keywords.enterText(pass, pwd);
		keywords.click(loginBtn);
	}
}
