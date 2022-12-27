package com.android.example.bakingapp15;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ListItemClickListener {
    RecyclerView recyclerView;
    TextView mTextView;
    Adapter adapter;
    Integer mRecipeIndex = 0;
    ArrayList<String> items;
    Context context;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeSearchQuery();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        context = this;

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        Integer orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(this, items, this);
        recyclerView.setAdapter(adapter);

        DoWidgetUpdates();
    }

    public void DoWidgetUpdates() {
        Log.d("WWD", "in DoWidgetUpdates");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.UpdateRecipeWidgets(context, appWidgetManager, appWidgetIds, mRecipeIndex);
    }

    private void recipeSearchQuery() {
        URL recipeUrl;
        recipeUrl= NetworkUtils.buildRecipeUrl();
        new RecipeQueryTask().execute(recipeUrl);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        mRecipeIndex = clickedItemIndex;
        DoWidgetUpdates();
        Intent intent = new Intent(MainActivity.this, IngredientStepDetail.class);
        intent.putExtra("RECIPE_INDEX", clickedItemIndex);
        startActivity(intent);
    }

    public void showErrorMessage() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.INVISIBLE);

        mTextView = (TextView) findViewById(R.id.tv_error_message);
        mTextView.setVisibility(View.VISIBLE);
    }

    public void showRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);

        mTextView = (TextView) findViewById(R.id.tv_error_message);
        mTextView.setVisibility(View.INVISIBLE);
    }


    public class RecipeQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String recipeResults = null;
            try {
                recipeResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipeResults;
        }

        @Override
        protected void onPostExecute(String recipeSearchResults) {
            if (NetworkUtils.getNetworkConnected()) {
                showRecyclerView();
                if (recipeSearchResults != null && !recipeSearchResults.equals("")) {
                    JsonUtil.parseRecipeJson(recipeSearchResults);
                    adapter.notifyDataSetChanged();
                }
            } else {
                showErrorMessage();
            }
        }

    }

}
