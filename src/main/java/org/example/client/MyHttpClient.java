package org.example.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


// problems of HttpUrlConnection

//  URLConnection API was designed with multiple protocols that are now no longer functioning (FTP, gopher, etc.).
//  The API predates HTTP/1.1 and is too abstract.
//  It works in blocking mode only (i.e., one thread per request/response).
//  It is very hard to maintain.

public class MyHttpClient {
    public static void main(String[] args) {
        try {
            URI uri = new URI("https", "jsonplaceholder.typicode.com", "/todos/1", null);

            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

//            String cookiesHeader = connection.getHeaderField("Set-Cookie");
//            List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);


            // in case of error response
//            BufferedReader errorStreamReader = new InputStreamReader(connection.getErrorStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            System.out.println("Response content: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}