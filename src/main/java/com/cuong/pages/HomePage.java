package com.cuong.pages;

import org.openqa.selenium.WebDriver;

import com.cuong.keywords.Keywords;
import com.cuong.base.BaseTest;

public class HomePage {
	private Keywords keywords;
	
	public HomePage(WebDriver driver) {
		this.keywords = BaseTest.getActionDriver();
	}
	
	public String getURL() {
		return keywords.getCurrentURL();
	}
}
