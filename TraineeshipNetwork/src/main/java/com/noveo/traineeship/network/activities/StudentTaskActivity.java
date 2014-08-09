package com.noveo.traineeship.network.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.noveo.traineeship.network.NewsListAdapter;
import com.noveo.traineeship.network.R;
import com.noveo.traineeship.network.api.Api;
import com.noveo.traineeship.network.models.News;
import com.noveo.traineeship.network.services.HttpUriConnectionService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StudentTaskActivity extends ListActivity {
    private ProgressBar progressBar;
    private ArrayAdapter<News> adapter;
    private List<News> news = new ArrayList<News>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_task);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        adapter = new NewsListAdapter(this, news);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(HttpUriConnectionService.ACTION));

        startServiceListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.getInt(HttpUriConnectionService.RESULT_KEY, 0) == Activity.RESULT_OK) {
                ArrayList<News> asd = bundle.getParcelableArrayList(HttpUriConnectionService.OUTPUT_KEY);
                news.addAll(asd);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }
        }
    };

    private void startServiceListener() {
        Intent intent = new Intent(this, HttpUriConnectionService.class);
        intent.putExtra(HttpUriConnectionService.INPUT_KEY, 1);
        startService(intent);
    }
}
