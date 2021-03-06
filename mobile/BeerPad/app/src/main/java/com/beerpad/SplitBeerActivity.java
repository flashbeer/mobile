package com.beerpad;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.SplitAdapter;
import adapters.SplitHolder;
import models.Person;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.AppHelper;

/**
 * Created by luisalfonsobejaranosanchez on 5/28/17.
 */
public class SplitBeerActivity extends Activity implements SplitHolder.SplitListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SplitAdapter splitAdapter;

    private List<Person> persons = new ArrayList<>();

    private OkHttpClient client;
    public String paymentSplitUrl = "http://10.105.168.180:8000/api/payment/?phone=7778899&bar=moes&table=01&beer=1000&split=7778899,3136064315";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        persons.add(new Person("Luis", "3136078321"));

        client = new OkHttpClient();

        splitAdapter = new SplitAdapter(persons, this);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(splitAdapter);

        OkHttpSplitHandler okHttpSplitHandler = new OkHttpSplitHandler();
        okHttpSplitHandler.execute(paymentSplitUrl);

    }

    @Override
    public void isSplitClicked() {
        AppHelper.screenManager.showSearchBeerScreen(this);
    }

    public class OkHttpSplitHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }

}
