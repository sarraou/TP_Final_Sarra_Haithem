package Pages;

import com.github.dockerjava.core.exec.ListConfigsCmdExec;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage {
    protected WebDriver driver;
    public By produits_url = By.className("inventory_item");
    public ProductsPage(WebDriver driver) {
        this.driver = driver;}

    //Méthode pour récupérer les noms des produits présents sur l'Url.
    public List<String> NomProduits() {
        List<String> noms = new ArrayList<>();
        List<WebElement> toutProduits = driver.findElements(produits_url);
        for (WebElement produit : toutProduits ){
            noms.add(produit.findElement(By.className("inventory_item_name")).getText());
        }
        return noms;
    }

}
