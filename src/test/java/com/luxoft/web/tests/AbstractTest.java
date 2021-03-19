package com.luxoft.web.tests;

import com.luxoft.web.page.LuxoftHomePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AbstractTest {

    public static WebDriver driver;
    protected static String baseUrl = "http://www.luxoft.com";
    protected static WebDriverWait wait;

    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "lib/geckodriver.exe");
        driver = System.getProperty("web.driver") == "chrome" ? new ChromeDriver() : new FirefoxDriver();
        // явное ожидание, применяется при вызове
        wait = new WebDriverWait(driver, 7);
        driver.navigate().to(baseUrl);
        driver.manage().window().maximize();
        // неявное ожидание, применяется при всём множестве действий драйвера с браузером ПО УМОЛЧАНИЮ
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static void cleanUp() {
        driver.quit();
    }

}
