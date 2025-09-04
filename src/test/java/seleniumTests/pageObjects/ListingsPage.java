package seleniumTests.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import seleniumTests.data.DropdownOption;
import seleniumTests.helpers.ElementActions;

import java.util.List;

public class ListingsPage {

    private WebDriver driver;
    private ElementActions elementActions;

    private final By filterButton = By.xpath("//button[@data-bs-toggle='collapse' and @data-bs-target='#filterDiv' and contains(normalize-space(.), 'Filter')]");
    private final By dropdownTrigger = By.xpath("//div[@class='ss-values']//div[contains(@class, 'ss-placeholder') and contains(text(), 'Select Value')]");
    private final By filterLookupButton = By.xpath("//button[contains(@class, 'btn') and contains(@class, 'btn-primary') and @type='submit' and contains(., 'Filter') and .//i[contains(@class, 'bi-search')]]");
    private final By searchInput = By.xpath("//input[@type='search' and @placeholder='Search']");
    private final By allOptionsLocator = By.xpath("//div[@class='ss-list']//div[@class='ss-option']");

    public ListingsPage(WebDriver driver, ElementActions elementActions) {
        this.driver = driver;
        this.elementActions = elementActions;
        PageFactory.initElements(driver, this);
    }

    public void clickOnFilterButton() {
        elementActions.safeClick(filterButton);
    }

    public void clickOnfilterLookupButton() {
        elementActions.safeClick(filterLookupButton);
    }

    public boolean analyzeAndCheckOption(String expectedText) {
        elementActions.safeClickWithJSFallback(dropdownTrigger);

        List<DropdownOption> dropdownOptions = elementActions.getElementsAsDropdownOptions(allOptionsLocator);

        System.out.println("Dropdown opciók listája:");
        boolean found = false;
        for (DropdownOption option : dropdownOptions) {
            System.out.printf("%d: %s%n", option.getIndex(), option.getText());
            if (option.getText().equalsIgnoreCase(expectedText)) {
                found = true;
            }
        }

        System.out.println("Van '" + expectedText + "' az opciók között? " + found);
        return found;
    }

    public void selectFromDropdown(String visibleText) {
        elementActions.safeClickWithJSFallback(dropdownTrigger);
        elementActions.waitForVisibility(searchInput, 10);
        elementActions.safeSendKeys(searchInput, visibleText);

        By optionLocator = By.xpath("//div[@class='ss-list']//div[@class='ss-option' and normalize-space(text())='" + visibleText + "']");
        elementActions.safeClickWithJSFallback(optionLocator);
    }
}