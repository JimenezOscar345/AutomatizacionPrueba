package Linktic.tests;

import Linktic.pages.MainPages;
import org.testng.annotations.Test;

public class MainTest extends TestBase {
    @Test
    public void url() {
        MainPages mainPages = new MainPages(driver);
        mainPages.login();
        mainPages.clic();
        mainPages.enterOTP();
    }

}