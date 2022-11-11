package test.cps3230.tests;

import org.example.AmazonPageObject;
import org.example.utils.Api;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.example.Request;

import java.io.IOException;

public class RequestTests {
    Api apiService;
    Request request;
    JSONObject listing = new JSONObject();
    @BeforeEach
    public void setup() {
        request = new Request();
        listing.put("alertType",6);
        listing.put("heading", "iphone 14");
        listing.put("description", "sample description re iphone 14");
        listing.put("url", "https://www.amazon.co.uk/s?k=iphone+14&crid=A5NTM9RZIOLQ&sprefix=iphone+14%2Caps%2C118&ref=nb_sb_ss_pltr-ranker-1hour_3_9");
        listing.put("imageUrl", "https://m.media-amazon.com/images/I/61cwywLZR-L._AC_SL1500_.jpg");
        listing.put("postedBy", "3abba092-71ce-4d58-b552-c511d70b09b9");
        listing.put("priceInCents", 98793);
    }
    @AfterEach
    public void teardown() {
        apiService = null;
    }

    @Test
    public void testPostRequestWithApiServiceInitialised() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.postRequest(listing.toString());

        // Verify
        Assertions.assertEquals(201, result);
    }

    @Test
    public void testPostRequestWithApiServiceNotInitialised() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.NOT_INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.postRequest(listing.toString());

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testPostRequestWithInCorrectJsonBody() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.NOT_INITIALISED);
        request.setApiService(apiService);

        // Exercise
        listing.put("priceInCents", "98793");
        int result = request.postRequest(listing.toString());

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testPostRequestWithCorrectJsonBody() throws IOException, InterruptedException {
        // Setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);

        // Exercise
        int result = request.postRequest(listing.toString());

        // Verify
        Assertions.assertEquals(201, result);
    }

    @Test
    public void testPurgingWithAPIServiceInitialised() throws IOException, InterruptedException {
        //setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.INITIALISED);
        request.setApiService(apiService);
        //purging alerts
        int response2 = request.deleteRequest();
        System.out.println(response2);
        //verify request
        Assertions.assertEquals(200, response2);
    }

    @Test
    public void testPurgingWithAPIServiceNotInitialised() throws IOException, InterruptedException {
        //setup
        apiService = Mockito.mock(Api.class);
        Mockito.when(apiService.getApi()).thenReturn(Api.NOT_INITIALISED);
        request.setApiService(apiService);
        //purging alerts
        int response2 = request.deleteRequest();
        System.out.println(response2);
        //verify request
        Assertions.assertEquals(-1, response2);
    }
}
