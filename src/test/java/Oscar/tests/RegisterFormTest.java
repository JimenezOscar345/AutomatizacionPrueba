package Oscar.tests;

import Oscar.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterFormTest {
    WebDriver driver;
    RegisterPage page;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("file:///C:/Users/Usuario1/Documents/prueba/inicio.html"); // cambia por tu ruta local
        page = new RegisterPage(driver);
    }

    @Test
    public void testRegistroExitoso() {
        page.fillForm("Oscar Jiménez", "oscar@example.com", "123456");
        page.submitForm();
        String alertText = page.getAlertText();
        Assert.assertEquals(alertText, "¡Registro exitoso!");
        page.acceptAlert();
    }

    @Test
    public void testCorreoYaExistente() {
        page.fillForm("Oscar Jiménez", "oscar@example.com", "123456");
        page.submitForm();
        String error = page.getErrorMessage();
        Assert.assertEquals(error, "El correo ya está registrado.");
    }

    @Test
    public void testPasswordInvalida() {
        page.fillForm("Oscar Jiménez", "oscar@example.com", "123");
        page.submitForm();
        String error = page.getErrorMessage();
        Assert.assertEquals(error, "La contraseña debe tener al menos 6 caracteres.");
    }

    @Test
    public void testCamposFaltantes() {
        page.fillForm("", "oscar@example.com", "123456");
        page.submitForm();
        String error = page.getErrorMessage();
        Assert.assertEquals(error, "Por favor, complete todos los campos.");
    }

    @AfterClass
    public void teardown() {
        //driver.quit();
    }
}
