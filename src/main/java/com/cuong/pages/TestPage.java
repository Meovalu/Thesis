package com.cuong.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.cuong.keywords.Keywords;
import com.cuong.base.BaseTest;

public class TestPage {
	private Keywords keywords;
	
	public TestPage(WebDriver driver) {
		this.keywords = BaseTest.getActionDriver();
	}
	
	//Locators
	private By confirmBtn = By.id("confirmBtn");
	private By resultTxt = By.id("demo");
	private By drag = By.xpath("//p[normalize-space()='Drag me to my target']");
	private By drop = By.xpath("//div[@id='droppable']");
	private By name = By.id("name");
	
	//Actions
	public Boolean verifyAcceptAlert() {
		keywords.click(confirmBtn);
		keywords.acceptAlert();
		return keywords.getText(resultTxt).contains("You pressed OK!");
	}
	
	public Boolean verifyCancelAlert() {
		keywords.click(confirmBtn);
		keywords.dismissAlert();
		return keywords.getText(resultTxt).contains("You pressed Cancel!");
	}
	
	public Boolean verifyAlertMsg() {
		keywords.click(confirmBtn);
		return keywords.getText(resultTxt).contains("Press a button!");
	}

	public Boolean verifyUrl(String url) {
		keywords.enterText(name, "This is a sample text");
		return keywords.getCurrentURL().contentEquals(url);
	}
	
	public Boolean verifyDnD() {
		keywords.dragAndDrop(drag, drop);
		return true;
	}

}
