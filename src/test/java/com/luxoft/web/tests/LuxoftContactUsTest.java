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

    @AfterAll
    public static void tearDown() {
        cleanUp();
    }
}
