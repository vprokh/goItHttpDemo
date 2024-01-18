package org.example.client;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class MyOkHttpClient {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://google.com/search").newBuilder();
        urlBuilder.addQueryParameter("q", "hello world");
        String url = urlBuilder.build().toString();
        System.out.println(url);

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/todos/1")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());
        response.close();
    }

    private static void postRequest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("attr1", "test")
                .add("attr2", "test1")
                .build();

        Request request = new Request.Builder()
                .url("some-url/test")
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        System.out.println((response.code()));
    }
}
