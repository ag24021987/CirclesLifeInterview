package com.circles.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(xpath="//input[@name='email']")
    private WebElement emailAddress;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"st-container\"]/div/div/div[2]/div[1]/div[2]/div/div/div[2]/div/div[3]/form/div[4]/div/div/button")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@id=\"st-container\"]/div/div/div[2]/div[1]/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div[1]/span/button/div/div")
    private WebElement loginWithFacebookButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return emailAddress.isDisplayed();
    }

    public void enterEmailAddress(String emailAddress){
       this.emailAddress.clear();
       this.emailAddress.sendKeys(emailAddress);
    }

    public void enterPassword(String password){
        this.password.clear();
        this.password.sendKeys(password);
    }

    public LoginWithFacebookPage clickLoginWithFacebookButton() {
        loginWithFacebookButton.click();
        return new LoginWithFacebookPage(driver);
    }

    public PlanPage submit(){
        submitButton.click();
        return new PlanPage(driver);
    }

}
