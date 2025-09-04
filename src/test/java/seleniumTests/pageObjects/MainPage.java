package seleniumTests.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import seleniumTests.helpers.ElementActions;

public class MainPage {

    private final WebDriver driver;
    private final ElementActions elementActions;

    private final By listingsLink = By.xpath("//span[normalize-space()='Listings']");
    private final By consentButton = By.xpath("//p[contains(@class, 'fc-button-label') and text()='Consent']");
    private final By noFilterButton = By.xpath("//button[contains(text(), 'No filter')]");

    public MainPage(WebDriver driver, ElementActions elementActions) {
        this.driver = driver;
        this.elementActions = elementActions;
    }

    public void clickListingsLink() {
        elementActions.safeClick(listingsLink);
    }

    public void closePopupsIfPresent() {
        if (isElementPresent(consentButton)) {
            elementActions.safeClick(consentButton);
        }
        if (isElementPresent(noFilterButton)) {
            elementActions.safeClick(noFilterButton);
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}