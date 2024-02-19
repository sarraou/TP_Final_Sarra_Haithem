package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    By usernameLoc = By.id("user-name");
    By passwordLoc = By.id("password");
    By loginButtonLoc = By.id("login-button");
    By MessageErreurLoc = By.xpath("//h3[@data-test = 'error']");

    protected WebDriver driver;
    private WebDriverWait wait;
    private String url = "https://www.saucedemo.com/";

    //Constructeur de LoginPage
    // @param driver

    public LoginPage(WebDriver driver) {
        this.driver = driver;}

    public void LoginInformations(String username, String password) {
        driver.findElement(usernameLoc).sendKeys(username);
        driver.findElement(passwordLoc).sendKeys(password);
    }

    public void LoginButton(){
        driver.findElement(loginButtonLoc).click();
    }

    public String MessageErreur(){
        return driver.findElement(MessageErreurLoc).getText();
    }
}
