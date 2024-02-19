package Tests;

import Pages.LoginPage;
import Pages.ProductsPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class ProductsPageTest {
    private WebDriver driver;
    String url = "https://www.saucedemo.com/";
    // Éléments de la page de connexion
    String vUsername = "standard_user";
    String vPassword = "secret_sauce";
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void SetUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        options.addArguments("--disable-gpu"); // Cette option est nécessaire sur certaines versions de Windows
        options.addArguments("--window-size=1920,1200"); // Définit la taille de la fenêtre, importante pour certaines applications

        productsPage = new ProductsPage(driver);
        loginPage = new LoginPage(driver);
        driver.get(url);
    }

    @Test
    public void testAffichageNomsProduits() {
        loginPage.LoginInformations(vUsername, vPassword);
        loginPage.LoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));
        List<String> listeNomsProduits = productsPage.NomProduits();
        Assert.assertTrue(!listeNomsProduits.isEmpty());
        System.out.println("Les Nom des produits affichés sur cette page :");
        for (String nom : listeNomsProduits) {
            System.out.println(nom);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}