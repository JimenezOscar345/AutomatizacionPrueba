package Linktic.tests;

import Linktic.utils.BrowserUtils;
import Linktic.utils.ConfigReader;
import Linktic.utils.DriverHelper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class TestBase {
    public static WebDriver driver;

    @BeforeMethod
    public void setup() {
        if (driver == null) {
            driver = DriverHelper.getDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
            // Leer la URL desde el archivo de configuración
            String serverUrl = ConfigReader.readProperty("andina");
            System.out.println("Test URL: " + serverUrl);
            driver.get(serverUrl);
        }
    }

    @AfterSuite
    public void tearDown() {
        // Cerrar el navegador después de que todos los tests se hayan ejecutado
        if (driver != null) {
            // driver.quit();
            driver = null;
        }
    }
}