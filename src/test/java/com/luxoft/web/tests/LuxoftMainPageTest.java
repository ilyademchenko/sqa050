package com.luxoft.web.tests;

import com.luxoft.web.page.LuxoftContactUsPage;
import com.luxoft.web.page.LuxoftHomePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LuxoftMainPageTest extends AbstractTest {

    private static LuxoftHomePage homePage;

    @BeforeAll
    public static void init() {
        setUp();
        homePage = new LuxoftHomePage(driver);
    }

    @Test
    public void verifyCopyright() {
        homePage = new LuxoftHomePage(driver);
        Assertions.assertTrue(
                homePage.copyrightInfo.getText().contains("2021"), "Current year in copyright");
    }

    @AfterAll
    public static void tearDown() {
        cleanUp();
    }
}
