package com.android.example.bakingapp15;

import android.util.Log;

import java.util.ArrayList;

public class Recipe {
    public Integer id;
    public String name;
    public Integer servings;
    public String image;
    public ArrayList<Ingredients> mIngredientsArrayList;
    public ArrayList<Steps> mStepsArrayList;

    Recipe(Integer id, String name, Integer servings, String image, ArrayList<Ingredients> ingredientArray, ArrayList<Steps> stepArray) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.mIngredientsArrayList = ingredientArray;
        this.mStepsArrayList = stepArray;
    }
}
