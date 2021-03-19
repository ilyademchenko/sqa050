package com.luxoft.web.page;

import com.luxoft.web.tests.AbstractTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LuxoftHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "logo")
    public WebElement logo;

    @FindBy(className = "footer__copyright-info")
    public WebElement copyrightInfo;

    @FindBy(css = "div.header__menu > ul > li:nth-child(6) > a")
    public WebElement headerContactUsLink;

    @FindBy(css = ".footer__connect .career-banner-btn")
    public WebElement footerConnectWithUsButton;

    @FindBy(partialLinkText = "ACCEPT")
    public WebElement acceptAllCookies;

    public LuxoftHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 2);
        PageFactory.initElements(driver, this);
    }

    @Step("Get copyright info text")
    public String getCopyrightInfoText() {
        return copyrightInfo.getText();
    }

    @Step("Click Contact Us on header")
    public LuxoftHomePage clickHeaderContactUs() {
        headerContactUsLink.click();
        return this;
    }

    @Step("Click Connect with Us on footer")
    public LuxoftHomePage clickFooterConnectWithUs() {
        wait.until(elementToBeClickable(footerConnectWithUsButton)).click();
        InputStream inputStream =
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment("Con", inputStream);

        return this;
    }

    @Step("Return to home page by click on logo")
    public LuxoftHomePage returnToHomePageByLogoClick() {
        logo.click();
        wait.until(ExpectedConditions.visibilityOf(headerContactUsLink));
        return this;
    }
}
