package com.android.example.bakingapp15;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IngredientStepDetail extends AppCompatActivity implements StepAdapter.ListItemClickListener{

    private IngredientAdapter mIngredientAdapter;
    private StepAdapter mStepAdapter;
    private RecyclerView mIngredientRV;
    private RecyclerView mStepRV;
    private Toast mToast;
    private static final int NUM_LIST_ITEMS = 10;
    private Integer mRecipeIndex = 0;
    private Integer mStepIndex   = 0;
    private TextView tvStepDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation;
        Log.d("WWD", "in IngredientStepDetail.java onCreate");
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            VideoFragment.setReceipeIndex(mRecipeIndex);
            VideoFragment.setContext(this);
        }
        setContentView(R.layout.ingredient_step_detail);
        mIngredientRV = (RecyclerView) findViewById(R.id.ingredientRecyclerView);
        mStepRV       = (RecyclerView) findViewById(R.id.stepRecyclerView);
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvStepDescription = (TextView) findViewById(R.id.tv_step_description2);
            String description = JsonUtil.getStepDescription(mRecipeIndex, mStepIndex);
            tvStepDescription.setText("Receipe Introduction");
        }

        Intent intent = getIntent();

        if (intent.hasExtra("RECIPE_INDEX")) {
            mRecipeIndex = intent.getIntExtra("RECIPE_INDEX", 0);
        }

        LinearLayoutManager ingredientLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager stepLayoutManager       = new LinearLayoutManager(this);

        mIngredientRV.setLayoutManager(ingredientLayoutManager);
        mStepRV.setLayoutManager(stepLayoutManager);

        mIngredientRV.setHasFixedSize(true);
        mStepRV.setHasFixedSize(true);
        mIngredientAdapter = new IngredientAdapter(mRecipeIndex);
        mIngredientRV.setAdapter(mIngredientAdapter);

        mStepAdapter = new StepAdapter(this, mRecipeIndex);
        mStepRV.setAdapter(mStepAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        mStepIndex = clickedItemIndex;
        int orientation;
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            VideoFragment.setContext(this);
            VideoFragment.setNewUri(mStepIndex);
            tvStepDescription.setText(JsonUtil.getStepDescription(mRecipeIndex, mStepIndex));
        } else {
            Intent intent = new Intent(IngredientStepDetail.this, StepDetail.class);
            intent.putExtra("RECIPE_INDEX", mRecipeIndex);
            intent.putExtra("STEP_INDEX", clickedItemIndex);
            startActivity(intent);
        }
    }

    public Integer getRecipeIndex() {
        return mRecipeIndex;
    }
}
