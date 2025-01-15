package Linktic.tests;

import Linktic.pages.AccionesPages;
import org.testng.annotations.Test;

public class AccionesTest extends TestBase {
    @Test
    public void accionesTest() {
        AccionesPages accionesPages = new AccionesPages(driver);
        accionesPages.realizarAcciones();
        accionesPages.actualizarCalculos();
        accionesPages.confirmarSi();

        // Ruta del archivo a cargar
        String filePath = "C:\\Users\\Usuario1\\Documents\\automatizacion\\subircalculos";
        accionesPages.cargarArchivo(filePath);
    }
}