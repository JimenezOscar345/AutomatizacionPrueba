package Linktic.tests;

import Linktic.pages.EmitirPolizaPages;
import org.testng.annotations.Test;

public class EmitirPolizaTest extends TestBase {
    @Test
    public void generarCotizacionTest() {
        EmitirPolizaPages emitirPolizaPages = new EmitirPolizaPages(driver);
        emitirPolizaPages.generarCotizacion();
        emitirPolizaPages.irACargueContratacion();

        // Ruta del archivo a cargar
        String filePath = "C:\\Users\\Usuario1\\Documents\\automatizacion\\CARGUE DE CONTRATACIONCOL3009";
        emitirPolizaPages.seleccionarArchivo(filePath);
        emitirPolizaPages.cargarArchivo();

        // Esperar un momento antes de descargar el informe
        try {
            Thread.sleep(5000); // Esperar 5 segundos (ajusta el tiempo según sea necesario)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        emitirPolizaPages.descargarInforme();
    }
}