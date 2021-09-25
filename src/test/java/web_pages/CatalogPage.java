package web_pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CatalogPage {
    WebDriver driver;

    @FindBy(css = "[class='product-image-container']")
    private List<WebElement> productContainers;

    @FindBy(css = ".quick-view")
    private List<WebElement> quickViews;

    String wishListButtonStr = "#buy_block #wishlist_button";

    @FindBy(css = "[title = Close]")
    private WebElement close;

    @FindBy(css = "#product h1[itemprop='name']")
    private WebElement productName;

    @FindBy(css = "p#add_to_cart button")
    private WebElement addToCartButton;

    @FindBy(css = "div#layer_cart span[title='Close window']")
    private WebElement closeWindow;

    @FindBy(css = ".shopping_cart a")
    private WebElement viewShoppingCart;

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @Step("Add product in wishlist")
    public List<String> addProductsToWishList(int numberInList){
        int size = productContainers.size();

        if(size >= numberInList){
            List<String> productNames = new ArrayList<>();
            for (int i = 0; i < numberInList; i++){
                productNames.add(selectProducts(i));
                WebDriverWait wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(wishListButtonStr))).click();
                wait.until(ExpectedConditions.elementToBeClickable(close)).click();
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(close)).click();
            }
            return productNames;
        }
        return null;
    }

    @Step("Retrieve product name")
    public String  retrieveProductName(){
        return productName.getText();
    }

    @Step("Add products to cart")
    public List<String> addProductsToCart(int numberInList){
        int size = productContainers.size();

        if(size >= numberInList){
            List<String> productNames = new ArrayList<>();
            for (int i = 0; i < numberInList; i++){
                productNames.add(selectProducts(i));
                WebDriverWait wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
                driver.switchTo().defaultContent();
                wait.until(ExpectedConditions.elementToBeClickable(closeWindow)).click();
            }
            return productNames;
        }
    return null;
    }

    @Step("Select products")
    public String selectProducts(int numberInList){
        new Actions(driver).moveToElement(productContainers.get(numberInList)).perform();
        new Actions(driver).moveToElement(quickViews.get(numberInList)).click().perform();
        driver.switchTo().frame(0);
        return retrieveProductName().trim();
    }

    @Step("Go to shopping cart")
    public void goToShoppingCart(){
        viewShoppingCart.click();
    }
}
