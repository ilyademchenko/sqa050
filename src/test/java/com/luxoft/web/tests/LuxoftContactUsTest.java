package com.luxoft.web.tests;

import com.luxoft.web.config.TestWatcher;
import com.luxoft.web.page.LuxoftContactUsPage;
import com.luxoft.web.page.LuxoftHomePage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@ExtendWith(TestWatcher.class)
public class LuxoftContactUsTest extends AbstractTest {

    private static LuxoftHomePage homePage;

    private static LuxoftContactUsPage contactUsPage;

    @BeforeAll
    public static void init() {
        setUp();
        homePage = new LuxoftHomePage(driver);
        contactUsPage = new LuxoftContactUsPage(driver);

        homePage.clickHeaderContactUs()
                .clickFooterConnectWithUs()
                .acceptAllCookies.click();
        contactUsPage
                .selectCallUsTab();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/phoneNumberData.csv", numLinesToSkip = 1)
    public void verifyContactPhoneNumberRefactoredSteps(String countryToSelect, String cityToSelect, String expectedPhoneNumber) {
        contactUsPage
                .selectCountryWithCity(countryToSelect, cityToSelect)
                .verifyPhoneNumberEquals(cityToSelect, expectedPhoneNumber);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/phoneNumberData.csv", numLinesToSkip = 1)
    public void verifyContactPhoneNumber(String countryToSelect, String cityToSelect, String expectedPhoneNumber) {
        driver.findElement(By.cssSelector("div.header__menu > ul > li:nth-child(6) > a")).click();
//        driver.findElement(By.linkText("CONTACT US")).click();
        WebElement contactUsFooterButton = driver.findElement(By.cssSelector(".footer__connect .career-banner-btn"));
        wait.until(elementToBeClickable(contactUsFooterButton)).click();
        wait.until(elementToBeClickable(By.xpath("//li[@class='tabs__nav__item ']/h2[contains(text(),'Call')]" +
                "/span[contains(text(),'us')]/../.."))).click();
        wait.until(elementToBeClickable(By.cssSelector("#tab-2 > div > div > div > div:nth-child(1) > span"))).click();
        //country
        wait.until(elementToBeClickable(By.xpath(String.format("//li[contains(@class,'select2-results__option') and contains(text(),'%s')]", countryToSelect)))).click();
        //city
        wait.until(elementToBeClickable(By.xpath("//div[@style='' and @class='city call-select']"))).click();
        wait.until(elementToBeClickable(By.xpath(String.format("//li[contains(@class,'select2-results__option') and contains(text(),'%s')]", cityToSelect)))).click();

        String phoneNumber = wait.until(visibilityOfElementLocated(By.xpath("//*[contains(@class,'call-results')]/h5[contains(text(), '" + cityToSelect + "')]/following-sibling::h2"))).getText();
        Assertions.assertEquals(expectedPhoneNumber, phoneNumber, "Phone number");
        driver.navigate().to(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        cleanUp();
    }
}
