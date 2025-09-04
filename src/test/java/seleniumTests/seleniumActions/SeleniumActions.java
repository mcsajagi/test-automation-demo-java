package seleniumTests.seleniumActions;

import org.openqa.selenium.WebDriver;
import seleniumTests.helpers.ElementActions;
import seleniumTests.pageObjects.ListingsPage;
import seleniumTests.pageObjects.MainPage;

public class SeleniumActions {

    private final MainPage mainPage;
    private final ListingsPage listingsPage;

    public SeleniumActions(WebDriver driver) {
        ElementActions elementActions = new ElementActions(driver);
        this.mainPage = new MainPage(driver, elementActions);
        this.listingsPage = new ListingsPage(driver, elementActions);
    }

    public void closePopupsIfPresent() {
        mainPage.closePopupsIfPresent();
    }

    public boolean analyzeDropdownForOption(String optionText) {
        mainPage.clickListingsLink();
        listingsPage.clickOnFilterButton();
        return listingsPage.analyzeAndCheckOption(optionText);
    }

    public void testFilterFunction() {
        mainPage.clickListingsLink();
        listingsPage.clickOnFilterButton();
        listingsPage.selectFromDropdown("Fender");
        listingsPage.clickOnfilterLookupButton();
    }
}