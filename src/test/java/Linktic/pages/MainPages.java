package Linktic.pages;

import Linktic.utils.ConfigReader;
import Linktic.utils.EmailUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.mail.MessagingException;

public class MainPages {

    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[1]/label[2]/div/div[1]/div[2]/input")
    WebElement username;
    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[1]/div[2]/label/div/div[1]/div[2]/input")
    WebElement contra;
    @FindBy(xpath = "/html/body/div[1]/div/div/main/aside/div/div[2]/div/div/button[2]/span[2]")
    WebElement ingresar;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/label/form/label/div/div[1]/div/input") //
    WebElement otpField;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[3]/button[2]/span[2]") //
    WebElement validar;

    WebDriver driver;

    public MainPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login() {
        username.sendKeys(ConfigReader.readProperty("usuario"));
        contra.sendKeys(ConfigReader.readProperty("pasword"));
    }

    public void clic() {
        ingresar.click();

        // Esperar unos segundos para asegurarse de que el último OTP se reciba antes de intentar obtenerlo
        try {
            Thread.sleep(15000); // Esperar 15 segundos
            EmailUtils.refreshEmail(); // Refrescar el correo electrónico después de esperar
        } catch (InterruptedException | MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void enterOTP() {
        String otp = EmailUtils.getOTP();
        if (otp != null) {
            otpField.sendKeys(otp);
            validar.click(); // Hacer clic en el botón de validar después de ingresar el OTP

            // Esperar unos segundos después de validar el OTP
            try {
                Thread.sleep(5000); // Esperar 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("OTP is null");
        }
    }
}