package com.circles.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FunctionalTest {

    protected static WebDriver driver;
    public static String loginUrl;
    public static String emailAddress;
    public static String password;
    public static String facebookAccountEmailAddress;
    public static String facebookAccountPassword;
    public static String facebookTestComment;
    public static String facebookLoginUrl;
    public static String geckoDriverPath;

    @BeforeClass
    public static void setUp() throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = null;
        inputStream = new FileInputStream("config.properties");
        prop.load(inputStream);
        loginUrl = prop.getProperty("loginUrl");
        emailAddress = prop.getProperty("emailAddress");
        password = prop.getProperty("password");
        facebookAccountEmailAddress = prop.getProperty("facebookTestAccountUsername");
        facebookAccountPassword = prop.getProperty("facebookTestAccountPassword");
        facebookTestComment = prop.getProperty("testcomment");
        facebookLoginUrl = prop.getProperty("facebookLoginUrl");
        geckoDriverPath = prop.getProperty("geckoDriverPath");
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
    }

    @BeforeMethod
    public void preTestSetup() {
        FirefoxProfile ffoxprofile = new FirefoxProfile();
        ffoxprofile.setPreference("dom.webnotifications.enabled", false);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(ffoxprofile);
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test (priority=1)
    public void loginAndVerifyEmailAddress() {

        //Go to the login url
        driver.get(loginUrl);

        //Enter the login details and submit
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isInitialized());
        loginPage.enterEmailAddress(emailAddress);
        loginPage.enterPassword(password);
        PlanPage myPlanPage = loginPage.submit();
        Assert.assertTrue(myPlanPage.isInitialized());

        //Click on My Accounts Tab on Plan Page
        MyAccountPage myAccountPage = myPlanPage.clickOnMyAccountsTab();
        Assert.assertTrue(myAccountPage.isInitialized());

        //Verify the email address is the same as the one that we logged in
        String displayedEmailAddress = myAccountPage.getEmailAddressValue();
        Assert.assertEquals(emailAddress,displayedEmailAddress);

        //Logout
        myPlanPage.clickOnLogOutButton();

    }

    @Test (priority=2)
    public void postTestCommentLoginWithFacebook() throws InterruptedException {
        //Go to the login url
        driver.get(loginUrl);

        //Click on LoginWithFacebook
        PlanPage myPlanPage = null;
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isInitialized());
        String windowHandleBeforeFacebookLogin = driver.getWindowHandle();
        LoginWithFacebookPage loginWithFacebookPage = loginPage.clickLoginWithFacebookButton();
        Thread.sleep(10000);
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
            if (driver.getTitle().contains("Facebook")) {
                loginWithFacebookPage.enterFacebookEmailAddress(facebookAccountEmailAddress);
                loginWithFacebookPage.enterFacebookPassword(facebookAccountPassword);
                myPlanPage = loginWithFacebookPage.clickLogin();
            }
        }
        driver.switchTo().window(windowHandleBeforeFacebookLogin);

        //Click on Facebook Page Button of Circles.Life
        if (myPlanPage != null) {
            Assert.assertTrue(myPlanPage.isInitialized());
        } else {
            myPlanPage = new PlanPage(driver);
        }
        FacebookCirclesLifePage facebookCirclesLifePage = myPlanPage.clickFacebookButton();

        Thread.sleep(10000);
        //Go the Facebook Window and Post a Test Comment
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
            if (driver.getTitle().contains("Circles.Life")) {
                Thread.sleep(10000);
                facebookCirclesLifePage.postComment(facebookTestComment, driver);
            }
        }
        driver.switchTo().window(windowHandleBeforeFacebookLogin);
    }

    @Test (dependsOnMethods = { "postTestCommentLoginWithFacebook" })
    public void verifyCommentPostedInPreviousTest() throws InterruptedException {
        //Go to the login url
        driver.get(facebookLoginUrl);

        //Click on LoginWithFacebook
        LoginWithFacebookPage loginWithFacebookPage = new LoginWithFacebookPage(driver);
        loginWithFacebookPage.enterFacebookEmailAddress(facebookAccountEmailAddress);
        loginWithFacebookPage.enterFacebookPassword(facebookAccountPassword);
        loginWithFacebookPage.clickLogin();
        FacebookCirclesLifePage facebookCirclesLifePage = new FacebookCirclesLifePage(driver);
        facebookCirclesLifePage.verifyTestCommentPosted(facebookTestComment);
    }

    @AfterMethod
    public void cleanUp() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}