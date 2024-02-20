package Tests;

import Pages.LoginPage;
import Pages.ProductsPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
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
import java.util.List;

public class ProductsPageTest {
    private WebDriver driver;
    String url = "https://www.saucedemo.com/";
    // Éléments de la page de connexion
    String vUsername = "standard_user";
    String vPassword = "secret_sauce";
    private LoginPage loginPage;
    private ProductsPage productsPage;

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
        productsPage = new ProductsPage(driver);
    }

    @Test
    public void testAffichageNomsProduits() {
        loginPage.LoginInformations(vUsername, vPassword);
        loginPage.LoginButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));
        List<String> listeNomsProduits = productsPage.NomProduits();

        for (int i = 0; i < listeNomsProduits.size() - 1; i++) {
            String produitActuel = listeNomsProduits.get(i);
            String produitSuivant = listeNomsProduits.get(i + 1);
            // Comparer les noms de produits
            Assert.assertTrue(produitActuel.compareTo(produitSuivant) <= 0, "La liste de produits " +
                    "n'est pas ordonnée en ordre croissant");
        }
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