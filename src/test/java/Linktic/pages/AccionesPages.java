package Linktic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class AccionesPages {

    @FindBy(xpath = "/html/body/div[1]/div/div[3]/main/div[1]/div[2]/button[1]/span[2]") // XPath para el botón de Acciones
    WebElement acciones;
    @FindBy(xpath = "/html/body/div[3]/div/div/div[2]/div[2]/label") // XPath para el botón de Actualizar cálculos
    WebElement actualizarCalculos;
    @FindBy(xpath = "/html/body/div[4]/div/div[2]/div/div[2]/div[2]/button[2]/span[2]") // XPath para el botón de Sí
    WebElement confirmarSi;

    WebDriver driver;
    WebDriverWait wait;

    public AccionesPages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void realizarAcciones() {
        wait.until(ExpectedConditions.elementToBeClickable(acciones)).click();
    }

    public void actualizarCalculos() {
        wait.until(ExpectedConditions.elementToBeClickable(actualizarCalculos)).click();
    }

    public void confirmarSi() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmarSi)).click();
    }

    public void cargarArchivo(String filePath) {
        try {
            // Copiar la ruta del archivo al portapapeles
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

            // Usar la clase Robot para pegar la ruta del archivo y presionar Enter
            Robot robot = new Robot();
            robot.delay(2000); // Esperar 2 segundos para asegurarse de que el diálogo esté abierto
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(1000); // Esperar 1 segundo
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            // Esperar a que el archivo se cargue
            Thread.sleep(5000); // Esperar 5 segundos (ajusta el tiempo según sea necesario)
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}