package Linktic.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverHelper {
    private static WebDriver driver;

    private DriverHelper() {
    }

    public static WebDriver getDriver() {
        if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            switch (ConfigReader.readProperty("browser")) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
//                    options.addArguments("--headless");
//                    options.addArguments("--no-sandbox");
//                    options.addArguments("--disable-dev-shm-usage");
//                    options.addArguments("--disable-gpu");
//                    options.addArguments("--disable-extensions");
//                    options.addArguments("--disable-software-rasterizer");
                    driver = new ChromeDriver(options);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
            }
        }
        return driver;
    }
}