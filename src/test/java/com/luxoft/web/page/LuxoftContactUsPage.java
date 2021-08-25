package com.luxoft.web.page;

import com.luxoft.web.config.CsvUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LuxoftContactUsPage {

    private WebDriver driver;
    private WebDriverWait contactUsPageWait;

//    private static final String citySelectResultsLocator = "//li[contains(@class,'select2-results__option') and contains(text(),'%s')]";
    private static final String phoneNumberByCityLocator = "//*[contains(@class,'call-results')]/h5[contains(text(), '%s')]/following-sibling::h2";

    @FindBy(xpath = "//li[@class='tabs__nav__item ']/h2[contains(text(),'Call')]/span[contains(text(),'us')]/../..")
    public WebElement callUsTabButton;

    @FindBy(xpath = "//li[@class='tabs__nav__item']/h2[contains(text(),'Write')]/span[contains(text(),'to us')]/../..")
    public WebElement writeToUsTabButton;

    @FindBy(xpath = "//div[contains(@class, 'current')]//span[contains(@aria-labelledby, 'select2-country')]//span[@role='textbox']")
    public WebElement countrySelectContainer;

    @FindBy(xpath = "//span[contains(@class, 'select2-results')]//li[contains(@class,'select2-results__option')]")
    public List<WebElement> countrySelectResults;

    @FindBy(xpath = "//div[@style='' and @class='city call-select']")
    public WebElement citySelectContainer;

    @FindBy(xpath = "//li[contains(@class,'select2-results__option')]")
    public List<WebElement> citySelectResults;

    @FindBy(css = ".select2-results__options")
    public WebElement countrySelectResultsContainer;

    @FindBy(xpath = "//span[contains(@class, 'select2-selection__rendered') and contains(text(), 'Select')]")
    public WebElement messageCategorySelectContainer;

    @FindBy(xpath = "//li[contains(@id, 'select2-form_text_')]")
    public List<WebElement> messageCategorySelectOptionsList;

    public LuxoftContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.contactUsPageWait = new WebDriverWait(driver, 2);
        PageFactory.initElements(driver, this);
    }

    @Step("Select Call us tab")
    public LuxoftContactUsPage selectCallUsTab() {
        contactUsPageWait.until(elementToBeClickable(callUsTabButton)).click();
        return this;
    }

    @Step("Select country = {0} with city = {1}")
    public LuxoftContactUsPage selectCountryWithCity(String country, String city) {
        contactUsPageWait.until(elementToBeClickable(countrySelectContainer)).click();

        contactUsPageWait.until(visibilityOf(countrySelectResultsContainer));
        countrySelectResults.stream()
                .forEach(e -> System.out.println(e.getText()));
        WebElement countryElement = countrySelectResults.stream()
                .filter( e -> e.getText().equals(country))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        contactUsPageWait.until(elementToBeClickable(countryElement)).click();
        citySelectContainer.click();
        WebElement cityElement = citySelectResults.stream()
                .filter(e -> e.getText().equals(city))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        contactUsPageWait.until(elementToBeClickable(cityElement)).click();
        return this;
    }

    @Step("Verify phone number for city = {0} is {1}")
    public LuxoftContactUsPage verifyPhoneNumberEquals(String city, String expectedPhoneNumber) {
        String actualPhoneNumber = contactUsPageWait.until(visibilityOfElementLocated(By.xpath(String.format(phoneNumberByCityLocator, city)))).getText();
        assertEquals(expectedPhoneNumber, actualPhoneNumber, "Phone number");
        return this;
    }

    public void selectWriteUsTab() {
        contactUsPageWait.until(elementToBeClickable(writeToUsTabButton)).click();
    }

    private List<String> getListOfMessageCategories() {
        messageCategorySelectContainer.click();
        List<String> listOfMessageCategories = new ArrayList<>();
        messageCategorySelectOptionsList.forEach(e -> listOfMessageCategories.add(e.getText()));
        return listOfMessageCategories;
    }

    public void verifyListOfMessageCategoriesEqualsToFile(String filePath) {
        List<String> expectedList = CsvUtils.obtainListFromCsv(filePath);
        List<String> actualResult = getListOfMessageCategories();

        assertEquals(expectedList, actualResult, "compare lists");
    }
}
