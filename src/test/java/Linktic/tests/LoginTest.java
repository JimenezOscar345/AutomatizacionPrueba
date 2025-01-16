package Linktic.tests;

import Linktic.pages.MainPages;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
    @Test
    public void loginTest() {
        MainPages mainPages = new MainPages(driver);
        mainPages.login();
        mainPages.clic();
        mainPages.enterOTP();
    }
}
