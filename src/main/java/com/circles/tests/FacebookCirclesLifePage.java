package com.circles.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import sun.jvm.hotspot.utilities.Assert;

public class FacebookCirclesLifePage extends PageObject{

    public FacebookCirclesLifePage(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//div[starts-with(@id,'js')]//form/div[1]/div[2]/div")
    private WebElement postCommentBox;

    @FindBy(xpath = "//div[starts-with(@id,'js')]/div[2]/div[2]/div/div[2]/div/span[2]/div/button")
    private WebElement postCommentButton;

    @FindBy(xpath = "//*[@id=\"userNavigationLabel\"]")
    private WebElement accountSettingsButton;

    @FindBy(xpath = "//li[@class='_54ni navSubmenu _6398 _6393 __MenuItem']")
    private WebElement activityLogButton;

    public void postComment(String comment, WebDriver driver) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(postCommentBox);
        actions.click();
        actions.sendKeys(comment);
        actions.build().perform();
        postCommentButton.click();
    }

    public void verifyTestCommentPosted(String comment) throws InterruptedException {
        accountSettingsButton.click();
        Thread.sleep(1000);
        activityLogButton.click();
        Thread.sleep(1000);
        org.testng.Assert.assertTrue(driver.getPageSource().contains(comment));
    }

}
