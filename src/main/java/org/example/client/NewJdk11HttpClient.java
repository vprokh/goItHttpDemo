package org.example.client;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.time.temporal.ChronoUnit.SECONDS;


public class NewJdk11HttpClient {
    public static void main(String[] args) throws URISyntaxException, InterruptedException, ExecutionException {
//        HttpRequest.newBuilder(new URI("https://postman-echo.com/get"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .POST(HttpRequest.BodyPublishers.ofString("Sample request body"))
                // by default
                // client will fall back to, e.g., HTTP/1.1 if HTTP/2 isn’t supported
                .version(HttpClient.Version.HTTP_2)
                .headers("key1", "value1", "key2", "value2")
                .timeout(Duration.of(5, SECONDS))
                .build();

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();


//        CookieManager cookieManager = (CookieManager) client.cookieHandler().get();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<HttpResponse<String>> responseAsync = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        final HttpResponse<String> stringResponse = responseAsync.get();

        System.out.println(stringResponse.body());
    }
}
