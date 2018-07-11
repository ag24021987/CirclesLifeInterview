package com.circles.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginWithFacebookPage extends PageObject {

    public LoginWithFacebookPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement facebookEmailAddress;

    @FindBy(xpath = "//*[@id=\"pass\"]")
    private WebElement facebookPassword;

    @FindBy(xpath = "//*[@id=\"loginbutton\"]")
    private WebElement facebookLoginButton;

    public boolean isInitialized() {
        return facebookEmailAddress.isDisplayed();
    }

    public void enterFacebookEmailAddress(String emailAddress){
        this.facebookEmailAddress.clear();
        this.facebookEmailAddress.sendKeys(emailAddress);
    }

    public void enterFacebookPassword(String password){
        this.facebookPassword.clear();
        this.facebookPassword.sendKeys(password);
    }

    public PlanPage clickLogin() {
        facebookLoginButton.click();
        return new PlanPage(driver);
    }
}
