package com.guitarshack;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Request {
    private final String baseURL;

    public Request(String baseURL) {
        this.baseURL = baseURL;
    }

    String get(Map<String, Object> params) {
        String paramString = "?";

        for (String key : params.keySet()) {
            paramString += key + "=" + params.get(key).toString() + "&";
        }
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(baseURL + paramString))
                .build();
        String result = "";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
