package seleniumTests.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.data.DropdownOption;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ElementActions {

    private final WebDriver driver;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    public void safeClick(By locator) {
        performActionWithRetry(locator, WebElement::click);
    }

    public void safeClickWithJSFallback(By locator) {
        try {
            safeClick(locator);
        } catch (Exception e) {
            performActionWithRetry(locator, element -> {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            });
        }
    }

    public void safeSendKeys(By locator, String text) {
        performActionWithRetry(locator, element -> {
            element.clear();
            element.sendKeys(text);
        });
    }

    public WebElement waitForElement(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForVisibility(By locator, int timeoutSeconds) {
        waitForElement(locator, timeoutSeconds);
    }

    private void performActionWithRetry(By locator, Consumer<WebElement> action) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement element = waitForElement(locator, 10);
                scrollToElement(element);
                action.accept(element);
                return;
            } catch (StaleElementReferenceException | ElementNotInteractableException | NoSuchElementException e) {
                System.out.println("Retrying due to: " + e.getClass().getSimpleName());
            }
            attempts++;
        }
        throw new RuntimeException("Failed to interact with element after 3 attempts: " + locator);
    }

    private void scrollToElement(WebElement element) {
        try {
            new Actions(driver).moveToElement(element).perform();
        } catch (Exception ignored) {
        }
    }

    public List<DropdownOption> getElementsAsDropdownOptions(By locator) {
        List<WebElement> elements = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(driver -> {
                    List<WebElement> found = driver.findElements(locator);
                    return (found != null && !found.isEmpty()) ? found : null;
                });

        return IntStream.range(1, elements.size())
                .mapToObj(i -> {
                    String text = elements.get(i).getText().trim();
                    return text.isEmpty() ? null : new DropdownOption(i, text);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}