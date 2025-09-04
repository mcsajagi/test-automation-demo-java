package seleniumTests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import seleniumTests.helpers.SlowDownListener;
import seleniumTests.seleniumActions.SeleniumActions;

import java.time.Duration;

public class SeleniumBase {

    protected WebDriver driver;
    protected SeleniumActions seleniumActions;

    @BeforeMethod
    public void openPage() {

        boolean headless = Boolean.parseBoolean(System.getenv("HEADLESS_MODE"));
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--window-size=1920x1080");
            options.addArguments("--disable-software-rasterizer");
        }

        ChromeDriver rawDriver = new ChromeDriver(options);
        SlowDownListener listener = new SlowDownListener();
        WebDriver decoratedDriver = new EventFiringDecorator(listener).decorate(rawDriver);

        this.driver = decoratedDriver;
        driver.get("https://gsfanatic.com");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));

        seleniumActions = new SeleniumActions(driver);
        seleniumActions.closePopupsIfPresent();
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterMethod
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}