package seleniumTests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import seleniumTests.helpers.SlowDownListener;
import seleniumTests.seleniumActions.SeleniumActions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SeleniumBase {

    protected WebDriver driver;
    protected SeleniumActions seleniumActions;

    @BeforeMethod
    public void openPage() {

        boolean headless = Boolean.parseBoolean(System.getenv("HEADLESS_MODE"));
        ChromeOptions options = new ChromeOptions();

        String chromeDriverVersion = "140.0.7339.80";
        System.setProperty("webdriver.chrome.driver", downloadChromeDriver(chromeDriverVersion));

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

    private String downloadChromeDriver(String version) {
        String downloadUrl = "https://chromedriver.storage.googleapis.com/" + version + "/chromedriver_linux64.zip";
        String downloadDir = System.getProperty("user.dir") + "/chromedriver/";

        try {
            File dir = new File(downloadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String wgetCommand = "wget -q " + downloadUrl + " -P " + downloadDir;
            String unzipCommand = "unzip -q " + downloadDir + "chromedriver_linux64.zip -d " + downloadDir;

            Runtime.getRuntime().exec(wgetCommand).waitFor();
            Runtime.getRuntime().exec(unzipCommand).waitFor();

            return downloadDir + "chromedriver";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("ChromeDriver letöltés vagy telepítés nem sikerült.");
        }
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