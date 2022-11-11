package test.cps3230.tests;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Request;
import org.example.utils.Api;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MyStepdefs {
    //objects
    WebDriver driver =null;
    Api apiService;
    Request request;
    WebDriverWait wait;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        System.setProperty("webdriver.chrome.driver", "E:\\UM\\Yr 3\\CPS3230 - Fundamentals of Software Testing\\assignment 1\\chromedriver.exe");
        driver = new ChromeDriver();
        //wait the thread
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //accessing login page
        driver.get("http://www.marketalertum.com/Alerts/Login");
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        //entering my used id
        driver.findElement(By.id("UserId")).sendKeys("3abba092-71ce-4d58-b552-c511d70b09b9");
        //clicking log in button
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        driver.findElement(By.xpath("//h1[normalize-space()='Latest alerts for Isaac Cutajar']")).isDisplayed();
        driver.close();
        driver.quit();
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        driver.get("http://www.marketalertum.com/Alerts/Login");
        //wrong user id
        driver.findElement(By.id("UserId")).sendKeys("noaccess");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        driver.findElement(By.xpath("//input[@type='submit']")).isDisplayed();
        driver.close();
        driver.quit();
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws IOException, InterruptedException {
        driver.findElement(By.id("UserId")).sendKeys("3abba092-71ce-4d58-b552-c511d70b09b9");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        request = new Request();
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);
        //purging alerts
        int delete = request.deleteRequest();
        System.out.println(delete);
        int response =0;
        JSONObject listing1 = new JSONObject();
        listing1.put("alertType", 6);
        listing1.put("heading", "iphone 14");
        listing1.put("description", "sample description re iphone 14");
        listing1.put("url", "https://www.amazon.co.uk/s?k=iphone+14&crid=A5NTM9RZIOLQ&sprefix=iphone+14%2Caps%2C118&ref=nb_sb_ss_pltr-ranker-1hour_3_9");
        listing1.put("imageUrl", "https://m.media-amazon.com/images/I/61cwywLZR-L._AC_SL1500_.jpg");
        listing1.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing1.put("priceInCents", 98793);
        response = request.postRequest(listing1.toString());
        System.out.println(response);
        JSONObject listing2 = new JSONObject();
        listing2.put("alertType", 5);
        listing2.put("heading", "playmobil");
        listing2.put("description", "sample description re playmobil");
        listing2.put("url", "https://www.amazon.co.uk/Playmobil-5567-City-Sunshine-Preschool/dp/B00IF1VVC2/ref=sr_1_1_sspa?crid=1NBY4OEXA8FRI&keywords=playmobil&qid=1667933830&sprefix=playmobil%2Caps%2C134&sr=8-1-spons&psc=1");
        listing2.put("imageUrl", "https://m.media-amazon.com/images/I/81qlPbKCJLL._AC_SL1500_.jpg");
        listing2.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing2.put("priceInCents", 3875);
        response = request.postRequest(listing2.toString());
        System.out.println(response);
        JSONObject listing3 = new JSONObject();
        listing3.put("alertType", 2);
        listing3.put("heading", "boat");
        listing3.put("description", "sample description re boat");
        listing3.put("url", "https://www.amazon.co.uk/Fayelong-Inflatable-Thicken-Foldable-2-person/dp/B0B1WLYFR9/ref=sr_1_1_sspa?crid=3EY0I8QQ5YYLD&keywords=boat&qid=1667933924&sprefix=boa%2Caps%2C137&sr=8-1-spons&psc=1");
        listing3.put("imageUrl", "https://m.media-amazon.com/images/I/61lB2OkoOzL._AC_SL1500_.jpg");
        listing3.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing3.put("priceInCents", 4500);
        response = request.postRequest(listing3.toString());
        System.out.println(response);
        //adding 3 listing alerts
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        driver.navigate().refresh();
        String xpath = "//h1[normalize-space()='Latest alerts for Isaac Cutajar']";
        driver.findElement(By.xpath(xpath)).isDisplayed();
    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        String xpath  ="//table//tbody//tr[1]//td//h4//img";
        List<WebElement> webElementList;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElementList = driver.findElements(By.xpath(xpath));

        //verify
        Assertions.assertEquals(3, webElementList.size());
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        String xpath = "//table//tbody//tr[1]//td//h4";
        List<String> s = new ArrayList<String>();
        List<WebElement> webElementList;
        webElementList = driver.findElements(By.xpath(xpath));
        for (WebElement i : webElementList) {
            s.add(i.getText());
        }
        //verify
        Assertions.assertEquals(3, s.size());
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        List<String> s = new ArrayList<String>();
        List<WebElement> webElementList;
        String xpath = "//table//tbody[1]//tr[3]//td";
        webElementList = driver.findElements(By.xpath(xpath));
        for (WebElement i : webElementList) {
            s.add(i.getText());
        }
        //verify
        Assertions.assertEquals(3, s.size());
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        String xpath = "//body[1]/div[1]/main[1]/table/tbody[1]/tr[2]/td[1]/img[1]";
        List<WebElement> webElementList;
        webElementList = driver.findElements(By.xpath(xpath));
        //verify
        Assertions.assertEquals(3, webElementList.size());
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        List<String> s = new ArrayList<String>();
        List<WebElement> webElementList;
        String xpath = "//table//tbody[1]//tr[4]//td";
        webElementList = driver.findElements(By.xpath(xpath));
        for (WebElement i : webElementList) {
            s.add(i.getText());
        }
        //verify
        Assertions.assertEquals(3, s.size());
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        List<String> s = new ArrayList<String>();
        List<WebElement> webElementList;
        String xpath = "//table//tbody[1]//tr[5]//td";
        webElementList = driver.findElements(By.xpath(xpath));
        for (WebElement i : webElementList) {
            s.add(i.getText());
        }
        //verify
        Assertions.assertEquals(3, s.size());
        //quit since last step of scenario
        driver.quit();
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws IOException, InterruptedException {
        driver.findElement(By.id("UserId")).sendKeys("3abba092-71ce-4d58-b552-c511d70b09b9");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        Request request = new Request();
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);
        //purging alerts
        int delete = request.deleteRequest();
        System.out.println(delete);
        int status =0;
        JSONObject listing1 = new JSONObject();
        listing1.put("alertType", 6);
        listing1.put("heading", "iphone 14");
        listing1.put("description", "sample description re iphone 14");
        listing1.put("url", "https://www.amazon.co.uk/s?k=iphone+14&crid=A5NTM9RZIOLQ&sprefix=iphone+14%2Caps%2C118&ref=nb_sb_ss_pltr-ranker-1hour_3_9");
        listing1.put("imageUrl", "https://m.media-amazon.com/images/I/61cwywLZR-L._AC_SL1500_.jpg");
        listing1.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing1.put("priceInCents", 98793);
        status = request.postRequest(listing1.toString());
        System.out.println(status);
        JSONObject listing2 = new JSONObject();
        listing2.put("alertType", 5);
        listing2.put("heading", "playmobil");
        listing2.put("description", "sample description re playmobil");
        listing2.put("url", "https://www.amazon.co.uk/Playmobil-5567-City-Sunshine-Preschool/dp/B00IF1VVC2/ref=sr_1_1_sspa?crid=1NBY4OEXA8FRI&keywords=playmobil&qid=1667933830&sprefix=playmobil%2Caps%2C134&sr=8-1-spons&psc=1");
        listing2.put("imageUrl", "https://m.media-amazon.com/images/I/81qlPbKCJLL._AC_SL1500_.jpg");
        listing2.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing2.put("priceInCents", 3875);
        int status2=request.postRequest(listing2.toString());
        System.out.println(status2);
        JSONObject listing3 = new JSONObject();
        listing3.put("alertType", 4);
        listing3.put("heading", "Rabat Townhouse (UCA with outdoor space)");
        listing3.put("description", "Well kept town house situated in a prime area in Rabat, facing a public garden. Property is located within an Urban Conservation Area (UCA) and has its own roof and airspace. Ground floor comprises an entrance hall, kitchen-dining area, internal yard, bathroom and a 40 square metre backyard / garden with a store room.");
        listing3.put("url", "https://www.maltapark.com/item/details/9521166");
        listing3.put("imageUrl", "https://www.maltapark.com/asset/itemphotos/9521343/9521343_5.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
        listing3.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing3.put("priceInCents", 58500000);
        request.postRequest(listing3.toString());

        JSONObject listing4 = new JSONObject();
        listing4.put("alertType", 3);
        listing4.put("heading", "Shop/Office for rent in Ibragg, Swieqi");
        listing4.put("description", "33SQM rental office/shop in Ibragg (Swieqi)\n" + "\n" + "Direct from Owner - call 99808959");
        listing4.put("url", "https://www.maltapark.com/item/details/9522054");
        listing4.put("imageUrl", "https://www.maltapark.com/asset/itemphotos/9522054/9522054_5.jpg?_ts=7");
        listing4.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing4.put("priceInCents", 500000);
        request.postRequest(listing4.toString());

        JSONObject listing5 = new JSONObject();
        listing5.put("alertType", 2);
        listing5.put("heading", "boat");
        listing5.put("description", "sample description re boat");
        listing5.put("url", "https://www.amazon.co.uk/Fayelong-Inflatable-Thicken-Foldable-2-person/dp/B0B1WLYFR9/ref=sr_1_1_sspa?crid=3EY0I8QQ5YYLD&keywords=boat&qid=1667933924&sprefix=boa%2Caps%2C137&sr=8-1-spons&psc=1");
        listing5.put("imageUrl", "https://m.media-amazon.com/images/I/61lB2OkoOzL._AC_SL1500_.jpg");
        listing5.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing5.put("priceInCents", 4500);
        request.postRequest(listing5.toString());
        //adding 5 listing alerts
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) throws IOException, InterruptedException {
        List<WebElement> webElementList;
        String xpath = "//table";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElementList = driver.findElements(By.xpath(xpath));
        System.out.println(arg0);
        //verify
        Assertions.assertEquals(arg0, webElementList.size());
    }

    @Given("I am an administrator of the website and I upload an alert of type {string}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(String arg0) throws IOException, InterruptedException {
        driver.findElement(By.id("UserId")).sendKeys("3abba092-71ce-4d58-b552-c511d70b09b9");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        //object
        Request request = new Request();

        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);
        //purging previous alerts
        request.deleteRequest();

        switch (arg0) {
            case "1":
                JSONObject listing6 = new JSONObject();
                listing6.put("alertType", 1);
                listing6.put("heading", "BMW E90 318d facelift");
                listing6.put("description", "Brand new: timing chain, clutch, pressure plate & flywheel changed just 5000 miles ago\n" +
                        "18d easy to insure\n" +
                        "Inside out impeccable condition\n" +
                        "160k miles\n" +
                        "Low license 339\n" +
                        "2010");
                listing6.put("url", "https://www.maltapark.com/item/details/9522749");
                listing6.put("imageUrl", "https://www.maltapark.com/asset/itemphotos/9522749/9522749_1.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=2");
                listing6.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing6.put("priceInCents", 640000);
                request.setApiService(apiService);
                request.postRequest(listing6.toString());
                break;
            case "2":
                JSONObject listing5 = new JSONObject();
                listing5.put("alertType", 2);
                listing5.put("heading", "boat");
                listing5.put("description", "sample description re boat");
                listing5.put("url", "https://www.amazon.co.uk/Fayelong-Inflatable-Thicken-Foldable-2-person/dp/B0B1WLYFR9/ref=sr_1_1_sspa?crid=3EY0I8QQ5YYLD&keywords=boat&qid=1667933924&sprefix=boa%2Caps%2C137&sr=8-1-spons&psc=1");
                listing5.put("imageUrl", "https://m.media-amazon.com/images/I/61lB2OkoOzL._AC_SL1500_.jpg");
                listing5.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing5.put("priceInCents", 4500);
                request.postRequest(listing5.toString());
                break;
            case "3":
                JSONObject listing4 = new JSONObject();
                listing4.put("alertType", 3);
                listing4.put("heading", "Shop/Office for rent in Ibragg, Swieqi");
                listing4.put("description", "33SQM rental office/shop in Ibragg (Swieqi)\n" +
                        "\n" +
                        "Direct from Owner - call 99808959");
                listing4.put("url", "https://www.maltapark.com/item/details/9522054");
                listing4.put("imageUrl", "https://www.maltapark.com/asset/itemphotos/9522054/9522054_5.jpg?_ts=7");
                listing4.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing4.put("priceInCents", 500000);
                request.postRequest(listing4.toString());
                break;
            case "4":
                JSONObject listing3 = new JSONObject();
                listing3.put("alertType", 4);
                listing3.put("heading", "Rabat Townhouse (UCA with outdoor space)");
                listing3.put("description", "Well kept town house situated in a prime area in Rabat, facing a public garden. Property is located within an Urban Conservation Area (UCA) and has its own roof and airspace. Ground floor comprises an entrance hall, kitchen-dining area, internal yard, bathroom and a 40 square metre backyard / garden with a store room.");
                listing3.put("url", "https://www.maltapark.com/item/details/9521166");
                listing3.put("imageUrl", "https://www.maltapark.com/asset/itemphotos/9521343/9521343_5.jpg/?x=TWF4Vz01NjMmTWF4SD00MjI=&_ts=10");
                listing3.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing3.put("priceInCents", 58500000);
                request.postRequest(listing3.toString());
                break;
            case "5":
                JSONObject listing2 = new JSONObject();
                listing2.put("alertType", 5);
                listing2.put("heading", "playmobil");
                listing2.put("description", "sample description re playmobil");
                listing2.put("url", "https://www.amazon.co.uk/Playmobil-5567-City-Sunshine-Preschool/dp/B00IF1VVC2/ref=sr_1_1_sspa?crid=1NBY4OEXA8FRI&keywords=playmobil&qid=1667933830&sprefix=playmobil%2Caps%2C134&sr=8-1-spons&psc=1");
                listing2.put("imageUrl", "https://m.media-amazon.com/images/I/81qlPbKCJLL._AC_SL1500_.jpg");
                listing2.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing2.put("priceInCents", 3875);
                request.postRequest(listing2.toString());
                break;
            case "6":
                JSONObject listing1 = new JSONObject();
                listing1.put("alertType", 6);
                listing1.put("heading", "iphone 14");
                listing1.put("description", "sample description re iphone 14");
                listing1.put("url", "https://www.amazon.co.uk/s?k=iphone+14&crid=A5NTM9RZIOLQ&sprefix=iphone+14%2Caps%2C118&ref=nb_sb_ss_pltr-ranker-1hour_3_9");
                listing1.put("imageUrl", "https://m.media-amazon.com/images/I/61cwywLZR-L._AC_SL1500_.jpg");
                listing1.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
                listing1.put("priceInCents", 98793);
                request.postRequest(listing1.toString());
                break;
        }
    }
    @And("the icon displayed should be {string}")
    public void theIconDisplayedShouldBe(String arg0) {
        String xpath = "//table//tbody//tr//td//h4//img";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        WebElement s = driver.findElement(By.xpath(xpath));
        String test = s.getAttribute("src");
        System.out.println(test);
        //Verify
        Assertions.assertEquals(arg0,test);
    }
}
