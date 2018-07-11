package com.circles.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends PageObject {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath="//*[@id=\"st-container\"]/div/div/div[2]/div[1]/div/div[2]/div/div/div/div[2]/div[2]/div/div/div/form/div[3]/div/input")
    private WebElement emailAddress;

    public boolean isInitialized() {
        return emailAddress.isDisplayed();
    }

    public String getEmailAddressValue() {
        return emailAddress.getAttribute("value");
    }
}
