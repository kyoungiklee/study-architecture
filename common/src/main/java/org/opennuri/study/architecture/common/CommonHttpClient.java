package org.opennuri.study.architecture.common;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Component
public class CommonHttpClient {

    private final HttpClient httpClient;

    public CommonHttpClient() {
        this.httpClient = HttpClient.newBuilder()
                .build();
    }

    public HttpResponse<String> sendGetRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
