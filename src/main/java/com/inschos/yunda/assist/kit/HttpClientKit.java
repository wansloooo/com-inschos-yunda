package com.inschos.yunda.assist.kit;

import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpClientKit {

    private static final Logger logger = Logger.getLogger(HttpClientKit.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client;

    public static String post(String url, String json) throws IOException {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client
                .newCall(request)
                .execute();
        String result = "";
        if (response.body() != null) {
            result = response.body().string();
        }
        return result;
    }
}
