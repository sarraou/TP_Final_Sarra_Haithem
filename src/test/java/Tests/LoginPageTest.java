package Tests;

import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginPageTest {
    private WebDriver driver;
    String url = "https://www.saucedemo.com/";
    // Éléments de la page de connexion
    String vUsername = "standard_user";
    String vPassword = "secret_sauce";
    String invUsername = "user_Invalide";
    String invPassword = "secret_Invalide";
    private LoginPage loginPage;
    @Parameters("Browser")
    @BeforeMethod
    public void SetUp(String Browser) {

            if (Browser.equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-gpu"); // Cette option est nécessaire sur certaines versions de Windows
                options.addArguments("--window-size=1920,1200"); // Définit la taille de la fenêtre, importante pour certaines applications
                driver = new ChromeDriver(options);
            } else if (Browser.equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1200");
                driver = new FirefoxDriver(options);
            } else if (Browser.equals("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1200");
                driver = new EdgeDriver(options);
            }
        driver.get(url);
        loginPage = new LoginPage(driver);
    }

    // Méthode pour se connecter avec des identifiants valides
    @Test(priority = 1)
    public void testValidLogin() throws InterruptedException {
        loginPage.LoginInformations(vUsername,vPassword);
        loginPage.LoginButton();
        //Thread.sleep(4000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));
        Assert.assertEquals("https://www.saucedemo.com/inventory.html",driver.getCurrentUrl());
    }

    // Méthode pour se connecter avec des identifiants Invalides
    @Test(priority = 2)
    public void testInValidLogin() {
        loginPage.LoginInformations(invUsername,invPassword);
        loginPage.LoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(MessageErreur));
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service",loginPage.MessageErreur());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
