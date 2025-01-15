package Linktic.tests;

import Linktic.pages.CotizacionPages;
import org.testng.annotations.Test;

public class CotizacionTest extends TestBase {
    @Test
    public void cotizacionTest() {
        CotizacionPages cotizacionPages = new CotizacionPages(driver);
        cotizacionPages.consultarCotizacion();
        cotizacionPages.generarCotizacion();
        cotizacionPages.cargarCotizacion();

        // Ruta del archivo a cargar
        String filePath = "C:\\Users\\Usuario1\\Documents\\automatizacion\\CARGUE INICIAL DE COTIZACION";
        cotizacionPages.cargarArchivo(filePath);
        // Esperar un momento antes de finalizar el test
        try {
            Thread.sleep(5000); // Esperar 5 segundos (ajusta el tiempo según sea necesario)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}