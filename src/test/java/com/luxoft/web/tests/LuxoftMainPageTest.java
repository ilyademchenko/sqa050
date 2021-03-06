package com.luxoft.web.tests;

import com.luxoft.web.page.LuxoftContactUsPage;
import com.luxoft.web.page.LuxoftHomePage;
import org.junit.jupiter.api.*;

import java.time.Year;

public class LuxoftMainPageTest extends AbstractTest {

    private static LuxoftHomePage homePage;

    @BeforeAll
    public static void init() {
        setUp();
        homePage = new LuxoftHomePage(driver);
    }

    @Test
    @DisplayName("Verify that copyright contains current year")
    public void verifyCopyright() {
        homePage = new LuxoftHomePage(driver);
        Assertions.assertTrue(
                homePage.getCopyrightInfoText().contains(Year.now().toString()), "Current year in copyright");
    }

    @Test
    @DisplayName("Verify that social networks are visible")
    public void verifySocials() {
        homePage.acceptAllCookies.click();
        homePage.verifyFacebookSocialButtonIsVisible();
        System.out.println("");
    }

    @AfterAll
    public static void tearDown() {
        cleanUp();
    }
}
