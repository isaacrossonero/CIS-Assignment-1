package org.example;

import org.example.utils.Api;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AmazonPageObject {
    WebDriver driver;
    WebDriverWait wait;
    Api api;

    //method which passes the string url and opens it with the driver
    public boolean openAmazon(String url){
        //Chromedriver object
        driver = new ChromeDriver();
        //wait the thread
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(url);
        if (driver.getCurrentUrl().equals("https://www.amazon.co.uk/")){
            //current url is the same as expected so ture
            return true;
        }
        //not the same so false
        return false;
    }
    //accepting the popups when launching the website
    public void acceptPopups(){
        String xpath = "//*[@id=\"sp-cc-accept\"]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).submit();
    }
    //close the driver when test is finished
    public void closeDriver(){
        driver.close();
    }
    //getting the search bar for scraper
    public WebElement getSearchBar(){
        return driver.findElement(By.id("twotabsearchtextbox"));
    }
    //getting the search button for scraper
    public WebElement getSearchButton(){
                return driver.findElement(By.id("nav-search-submit-button"));
    }
    //returning the page attributes for every listing
    public List<WebElement> getPageAttributes(){
        String xpath = "//a[@class='a-link-normal s-no-outline']";
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        return driver.findElements(By.xpath(xpath));
    }
    //returning the product heading for every listing
    public String getProductHeading() {
        String xpath = "//span[@id='productTitle']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath)).getText();
    }
    //returning the product description for every listing
    public String getProductDescription() {
        String xpath = "//div[@id='feature-bullets']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath)).getText();
    }
    //returning the image Url for every listing
    public String getImageUrl(){
        WebElement img = driver.findElement(By.xpath("//img[@id='landingImage']"));
        String src = img.getAttribute("src");
        return src;
    }
    //returning the price in cents for every listing
    public String getPriceInCents() {
        String xpath = "//div[@id='corePrice_feature_div']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath)).getText();
    }
    //setting api service for REST API
    public void setApiService(Api apiService) {
        this.api = apiService;
    }
    //screen scraper
    public int scrapeAmazon(String product, String url) throws IOException, InterruptedException {
        //Setup - visits amazon.com
        Request requests = new Request();
        if (!openAmazon(url)){
            driver.quit();
            return -1;
        }
        acceptPopups();
        int response = 0;
        //Exercise
        getSearchBar().sendKeys(product);
        getSearchButton().submit();
        //declaring attributes
        String testUrl = "";
        String currentUrl = "";
        String heading = "";
        String description = "";
        String imageUrl = "";
        String priceInCents = "";
        //web element list
        List<WebElement> webElementList = null;
        JSONObject listing = new JSONObject();
        // for loop to get 5 items from amazon and create their alerts
        for (int i = 0; i < 5; i++) {
            webElementList = getPageAttributes();
            System.out.println(webElementList.get(i).getAttribute("href"));
            testUrl = webElementList.get(i).getAttribute("href");
            driver.navigate().to(testUrl);
            currentUrl = driver.getCurrentUrl();
            heading = getProductHeading();
            description = getProductDescription();
            imageUrl = getImageUrl();
            priceInCents = getPriceInCents();
            priceInCents = priceInCents.replace("£", "").replace("\n", "").replace(",", "");
            listing.put("alertType", 6);
            listing.put("heading", heading);
            listing.put("description", description);
            listing.put("url", currentUrl);
            listing.put("imageUrl", imageUrl);
            listing.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
            listing.put("priceInCents", priceInCents);
            requests.setApiService(api);
            response = requests.postRequest(listing.toString());
            System.out.println(response);
            driver.navigate().back();
        }
        return response;
    }

}
