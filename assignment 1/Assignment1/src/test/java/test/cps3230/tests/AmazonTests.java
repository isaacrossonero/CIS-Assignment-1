package test.cps3230.tests;

import org.example.utils.Api;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.example.AmazonPageObject;
import org.example.Request;
import java.io.IOException;

public class AmazonTests {
    //objects
    AmazonPageObject amazon;
    Request requests;
    Api apiService;

    @BeforeEach
    public void setup() {
        requests = new Request();
        System.setProperty("webdriver.chrome.driver", "E:\\UM\\Yr 3\\CPS3230 - Fundamentals of Software Testing\\assignment 1\\chromedriver.exe");
        amazon = new AmazonPageObject();
    }

    @AfterEach
    public void teardown() {
        apiService = null;
    }

    @Test
    public void testOpenAmazonSuccessful(){
        //exercise
        boolean result= amazon.openAmazon("https://www.amazon.co.uk/");
        amazon.closeDriver();
        //verify
        Assertions.assertTrue(result);
    }
    @Test
    public void testOpenAmazonUnSuccessful(){
        //exercise - by opening ebay instead of amazon
        boolean result= amazon.openAmazon("https://www.ebay.com/");
        amazon.closeDriver();
        //verify
        Assertions.assertFalse(result);
    }
    @Test
    public void testScrapeAmazonSuccessfully()throws IOException, InterruptedException{
        //setup
        apiService = Mockito.mock(Api.class);
        amazon.setApiService(apiService);
        String search = "Apple iPhone 14";
        //exercise
        int statuscode = amazon.scrapeAmazon(search, "http://www.amazon.co.uk");
        //verify
        Assertions.assertEquals(201, statuscode);
    }
    @Test
    public void testScrapeAmazonUnSuccessfully()throws IOException, InterruptedException{
        //setup
        apiService = Mockito.mock(Api.class);
        amazon.setApiService(apiService);
        String search = "Electronics";
        //exercise
        int statuscode = amazon.scrapeAmazon(search, "http://www.ebay.co.uk");
        //verify
        Assertions.assertEquals(-1, statuscode);
    }
}
