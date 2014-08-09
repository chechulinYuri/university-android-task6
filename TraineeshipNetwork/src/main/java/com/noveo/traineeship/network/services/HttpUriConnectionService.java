package com.noveo.traineeship.network.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noveo.traineeship.network.api.Api;
import com.noveo.traineeship.network.models.News;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpUriConnectionService extends IntentService{
    public static final String ACTION = "com.noveo.traineeship.network";
    public static final String RESULT_KEY = "result";
    public static final String INPUT_KEY = "input_number";
    public static final String OUTPUT_KEY = "output_number";

    public HttpUriConnectionService() {
        super("Number");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Integer value = (Integer) intent.getSerializableExtra(INPUT_KEY);
        ArrayList<News> news = new ArrayList<News>();
        try {
            news = testHttpUrlConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        publish(news);
    }

    private void publish(ArrayList<News> value) {
        Intent intent = new Intent(ACTION);
        //intent.putExtra(OUTPUT_KEY, value);
        intent.putParcelableArrayListExtra(OUTPUT_KEY, value);
        intent.putExtra(RESULT_KEY, Activity.RESULT_OK);
        sendBroadcast(intent);
    }

    public ArrayList<News> testHttpUrlConnection() throws Exception {
        Gson gson = new Gson();
        URL url = new URL(Api.END_POINT + Api.ALL_NEWS);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream stream = new BufferedInputStream(urlConnection.getInputStream());

        String json = inputStreamToString(stream);

        urlConnection.disconnect();
        return gson.fromJson(json, new TypeToken<ArrayList<News>>() {}.getType());
    }

    private static String inputStreamToString(final InputStream stream) {
        java.util.Scanner scanner = new java.util.Scanner(stream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
