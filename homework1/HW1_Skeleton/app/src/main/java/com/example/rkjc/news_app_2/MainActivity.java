package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.rkjc.news_app_2.NetworkUtils;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ArrayList<NewsItem> newsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAdapter = new NewsAdapter(this, newsList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsQueryTask task = new NewsQueryTask();
        task.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = NetworkUtils.buildURL();
            NewsQueryTask task = new NewsQueryTask();
            task.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class NewsQueryTask extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            String jSONResp = null;
            try {
                jSONResp = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildURL());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jSONResp;
        }

        @Override
        protected void onPostExecute(String jSONResp) {
            mProgressBar.setVisibility(View.GONE);
            populateRecyclerView(jSONResp);
        }

        public void populateRecyclerView(String searchResults){
            newsList = JsonUtils.parseNews(searchResults);
            mAdapter.itemList.addAll(newsList);
            mAdapter.notifyDataSetChanged();
        }


    }



}





