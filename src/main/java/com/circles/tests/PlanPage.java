package com.circles.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlanPage extends PageObject {

    public PlanPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath="//div[@class='hidden-md-down']/div/a[3]")
    private WebElement myAccountTab;

    @FindBy(xpath = "//*[@id=\"st-container\"]/div/div/div[2]/span/div/div/footer/div/div/div/div")
    private WebElement buyThisPlanButton;

    @FindBy(xpath = "//*[@id=\"st-container\"]/div/div/div[1]/div/div/div[2]/div/a[6]/div")
    private WebElement logOutButton;

    @FindBy(xpath = "//*[@id=\"st-container\"]/div/div/div[2]/div/div[3]/div[2]/a[1]")
    private WebElement facebookButton;

    public boolean isInitialized() {
        return buyThisPlanButton.isDisplayed();
    }

    public void clickOnLogOutButton() {
        logOutButton.click();
    }

    public MyAccountPage clickOnMyAccountsTab() {
        myAccountTab.click();
        return new MyAccountPage(driver);
    }

    public FacebookCirclesLifePage clickFacebookButton() throws InterruptedException {
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", facebookButton);
        facebookButton.click();
        return new FacebookCirclesLifePage(driver);
    }


}
