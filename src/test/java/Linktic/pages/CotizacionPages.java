package Linktic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class CotizacionPages {

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/aside/div/div[2]/div[2]/div/div/div[2]/div[2]/a/div[2]/div") // Reemplaza con el XPATH correcto
    WebElement consultarCotizacion;
    @FindBy(xpath = "/html/body/div[1]/div/div[3]/main/div[1]/div[2]/button[2]/span[2]/i[2]") // Reemplaza con el XPATH correcto
    WebElement generarCotizacion;
    @FindBy(xpath = "/html/body/div[3]/div/div/div[1]/div[2]/div") // Reemplaza con el XPATH correcto
    WebElement cargarCotizacion;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[1]/div/div[1]/div/p[1]") // Reemplaza con el XPATH correcto
    WebElement cargarArchivo;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/button[2]/span[2]") // Reemplaza con el XPATH correcto
    WebElement botonCargar;
    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div/button/span[2]") // Reemplaza con el XPATH correcto
    WebElement botonFinal;

    WebDriver driver;

    public CotizacionPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void consultarCotizacion() {
        consultarCotizacion.click();
    }

    public void generarCotizacion() {
        generarCotizacion.click();
    }

    public void cargarCotizacion() {
        cargarCotizacion.click();
    }

    public void cargarArchivo(String filePath) {
        cargarArchivo.click(); // Hacer clic en el elemento para abrir el diálogo de selección de archivo

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

            // Hacer clic en el botón de cargar
            botonCargar.click();

            // Esperar a que se procese la carga
            Thread.sleep(5000); // Esperar 5 segundos (ajusta el tiempo según sea necesario)

            // Hacer clic en el botón final
            botonFinal.click();
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}