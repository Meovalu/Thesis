package com.cuong.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cuong.keywords.Keywords;
import com.cuong.base.BaseTest;

public class HomePage {
	private Keywords keywords;
	
	public HomePage(WebDriver driver) {
		this.keywords = BaseTest.getActionDriver();
	}
	
	//Locators
	private By loginMenu = By.xpath("//a[@aria-expanded='false']");
	private By loginBtn = By.xpath("//a[normalize-space()='Login']");
	
	//Actions
	public void goToLogin() {
		keywords.click(loginMenu);
		keywords.click(loginBtn);
	}
	
	public String getURL() {
		return keywords.getCurrentURL();
	}
}
