package com.shxy.datasharedplatform.utils;

import java.io.File;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.shxy.datasharedplatform.utils.MainConfig.MAIN_URL;

/**
 * Created by shxy on 2018/12/4.
 */

public class OkHttpUtils {
    private static final String baseUrl = MAIN_URL;

    public static void basePostAsync(String url, Map<String, String> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        String realUrl = baseUrl + url;
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(realUrl)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public static void baseGetAsync(String url, Map<String, String> params, Callback callback) {
        StringBuilder realUrl = new StringBuilder();
        realUrl.append(baseUrl).append(url).append('?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            realUrl.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        realUrl = realUrl.deleteCharAt(realUrl.length() - 1);

        Request request = new Request.Builder()
                .url(realUrl.toString())
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public static void filePostAsync(String url, Map<String, String> params,
                                     Map<String, File> fileParams, Callback callback) {

        MultipartBody.Builder mBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : params.entrySet()) {
//            mBuilder = mBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            mBuilder.addFormDataPart(entry.getKey(),entry.getValue());
        }
//        String TYPE = "application/octet-stream";
        for (Map.Entry<String, File> entry : fileParams.entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("image/png"), entry.getValue());
            mBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), body);
        }

        RequestBody body = mBuilder.build();
        String realUrl = baseUrl + url;
        Request request = new Request.Builder()
                .url(realUrl)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

}
