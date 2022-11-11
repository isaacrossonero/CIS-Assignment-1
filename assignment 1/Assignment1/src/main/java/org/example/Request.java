package org.example;

import org.example.utils.Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class Request {
    //objects
    protected Api api;
    public void setApiService(Api apiService) {
        this.api = apiService;
    }
    public int postRequest(String json) throws IOException, InterruptedException {

        if (api.getApi() == Api.NOT_INITIALISED){
            return -1;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        return response.statusCode();
    }

    public int deleteRequest() throws IOException, InterruptedException {
        if (api.getApi() == Api.NOT_INITIALISED){
            return -1;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.marketalertum.com/Alert?userId=3abba092-71ce-4d58-b552-c511d70b09b9"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
