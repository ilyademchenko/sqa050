package com.luxoft.web.tests;

import com.luxoft.web.page.LuxoftHomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LuxoftMainPageTest extends AbstractTest {

    private LuxoftHomePage homePage;

    @Test
    public void verifyCopyright() {
        homePage = new LuxoftHomePage(driver);
        Assertions.assertTrue(
                homePage.copyrightInfo.getText().contains("2021"), "Current year in copyright");
    }
}
