package Linktic.pages;

import Linktic.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Response;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPages {

    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[1]/label[2]/div/div[1]/div[2]/input")
    WebElement username;
    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[1]/div[2]/label/div/div[1]/div[2]/input")
    WebElement contra;
    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[2]/div/div/button[2]/span[2]")
    WebElement ingresar;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/label/form/label/div/div[1]/div/input")
    WebElement otpField;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[3]/button[2]/span[2]")
    WebElement validar;

    WebDriver driver;
    DevTools devTools;

    public MainPages(WebDriver driver) {
        this.driver = driver;
        this.devTools = ((ChromeDriver) driver).getDevTools();
        this.devTools.createSession();
        PageFactory.initElements(driver, this);
    }

    public void login() {
        username.sendKeys(ConfigReader.readProperty("usuario"));
        contra.sendKeys(ConfigReader.readProperty("pasword"));
    }

    public void clic() {
        ingresar.click();
    }

    public void enterOTP() {
        String otp = getOTPFromNetwork();
        if (otp != null) {
            otpField.clear(); // Borrar el campo OTP antes de ingresar uno nuevo
            otpField.sendKeys(otp);
            validar.click();
        } else {
            throw new IllegalArgumentException("OTP is null");
        }
    }

    private String getOTPFromNetwork() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        final String[] otp = {null};

        devTools.addListener(Network.responseReceived(), responseReceived -> {
            Response response = responseReceived.getResponse();
            if (response.getUrl().contains("send-otp")) {
                String responseBody = devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody();
                otp[0] = extractOTP(responseBody);
            }
        });

        try {
            Thread.sleep(9000); // Esperar 9 segundos para dar tiempo a que se genere el OTP
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return otp[0];
    }

    private String extractOTP(String responseBody) {
        Pattern pattern = Pattern.compile("\"otp\":\"(\\d{6})\"");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        MainPages mainPages = new MainPages(driver);
        mainPages.login();
        mainPages.clic();
        mainPages.enterOTP();

        // Cierra el navegador después de la prueba
        driver.quit();
    }
}