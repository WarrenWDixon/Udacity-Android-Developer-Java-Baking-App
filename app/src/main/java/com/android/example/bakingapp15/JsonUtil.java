package com.android.example.bakingapp15;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {
    public static ArrayList<Recipe> mRecipeArrayList;
    public static ArrayList<Ingredients> ingredientsRecipeArray;
    public static ArrayList<Steps>  stepsRecipeArray;
    public static Boolean dataRead = false;
    public static JSONObject jOBJ;
    public static JSONObject recipe;
    public static JSONObject ingredientObject;
    public static JSONObject stepObject;
    public static JSONArray  jsonReceipeArray;
    public static JSONArray  ingredientsArray;
    public static JSONArray  stepsArray;

    //Recipe fields
    public static Integer id;
    public static String name;
    public static Integer servings;
    public static String imageURL;

    // ingredient fields
    public static Double quantity;
    public static String measure;
    public static String ingredient;

    // steps fields
    public static  Integer stepId;
    public static  String shortDescription;
    public static  String description;
    public static  String videoURL;
    public static  String thumbnailURL;


    public static Boolean parseJSONObject(String json) {
        String jsonString = "{ \"recipes\" :" + json + "}";
        jOBJ = null;
        try {
            jOBJ = new JSONObject(jsonString);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseJSONRecipeArray() {
        jsonReceipeArray = new JSONArray();
        try {
            jsonReceipeArray = jOBJ.getJSONArray("recipes");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseRecipeFromArray(Integer i) {
        // first parse recipe object from array of recipes
        try {
            recipe = jsonReceipeArray.getJSONObject(i);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseRecipeID() {
        try {
            id = (Integer) recipe.get("id");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseRecipeName() {
        try {
            name = recipe.get("name").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseRecipeServings() {
        // next parse servings
        try {
            servings = (Integer) recipe.get("servings");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseRecipeImageURL() {
        // next parse image
        try {
            imageURL = recipe.get("image").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseIngredientsArray() {
        ingredientsArray = new JSONArray();
        try {
            ingredientsArray = recipe.getJSONArray("ingredients");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseIngredientObjectFromArray(Integer j) {
        // parse Ingredient Object from Ingredients Array
        try {
            ingredientObject = ingredientsArray.getJSONObject(j);
            return true;
        } catch (JSONException e) {
            dataRead = false;
            return false;
        }
    }

    public static Boolean parseQuantityFromRecipe() {
        // next quantity
        try {
            quantity = ((Number)ingredientObject.get("quantity")).doubleValue();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseMeasureFromRecipe() {
        // next measure
        try {
            measure = ingredientObject.get("measure").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseIngredientFromRecipe() {
        // next ingredient
        try {
            ingredient = ingredientObject.get("ingredient").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseStepsArray() {
        stepsArray = new JSONArray();
        try {
            stepsArray = recipe.getJSONArray("steps");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseStepFromStepsArray(Integer j) {
        try {
            stepObject = stepsArray.getJSONObject(j);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseStepIdFromStep() {
        try {
            stepId = (Integer) stepObject.get("id");
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseShortDescriptionFromStep() {
        // next measure
        try {
            shortDescription = stepObject.get("shortDescription").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseDescriptionFromStep() {
        // next measure
        try {
            description = stepObject.get("description").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseVideoURLFromStep() {
        // next measure
        try {
            videoURL = stepObject.get("videoURL").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Boolean parseThumbnailURLFromStep() {
        // next measure
        try {
            thumbnailURL = stepObject.get("videoURL").toString();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static Integer getRecipeArraySize() {
        Integer size;
        if (!dataRead) {
            return 4;
        }
        if (mRecipeArrayList != null) {
            size = mRecipeArrayList.size();
        }
        else {
            return 4;
        }
        return mRecipeArrayList.size();
    }

    public static Integer getRecipeServings(Integer index) {
        if (!dataRead)
            return 0;
        Integer size = mRecipeArrayList.size();
        if (index < size ) {
            return mRecipeArrayList.get(index).servings;
        } else {
            return 0;
        }
    }

    public static String getRecipeName(Integer index) {
        if (!dataRead)
            return "";
        Integer size = mRecipeArrayList.size();
        if (index < size ) {
            return mRecipeArrayList.get(index).name;
        } else {
            return "No Recipe available";
        }
    }

    public static String getRecipeImage(Integer index) {
        if (!dataRead)
            return "";
        Integer size = mRecipeArrayList.size();
        if (index < size ) {
            return mRecipeArrayList.get(index).image;
        } else {
            return "";
        }
    }

    public static Boolean parseRecipeJson(String json) {
        mRecipeArrayList       = new ArrayList<>();

        dataRead = false;

        if (!parseJSONObject(json))
            return false;

        if (!parseJSONRecipeArray())
            return false;

        Integer numRecipes = jsonReceipeArray.length();

        for (int i = 0; i < numRecipes; i++) {
            ingredientsRecipeArray = new ArrayList();
            stepsRecipeArray       = new ArrayList<>();

            if (!parseRecipeFromArray(i))
                continue;

            if (!parseRecipeID())
                continue;

            if (!parseRecipeName())
                continue;

            if (!parseRecipeServings())
                continue;

            if (!parseRecipeImageURL())
                continue;

            if (!parseIngredientsArray())
                continue;

            Integer ingredientsLen = ingredientsArray.length();

            Ingredients ing;
            if (!ingredientsRecipeArray.isEmpty())
                ingredientsRecipeArray.clear();
            // logic to parse elements of Ingredient array
            for (int j = 0; j < ingredientsLen; j++) {
                if (!parseIngredientObjectFromArray(j))
                    continue;

                if (!parseIngredientFromRecipe())
                    continue;

                if (!parseQuantityFromRecipe())
                    continue;

                if (!parseMeasureFromRecipe())
                    continue;
                ing = new Ingredients(quantity, measure, ingredient);
                ingredientsRecipeArray.add(ing);
            }

            if (!parseStepsArray())
                continue;

            Integer stepsLen = stepsArray.length();
            // logic to parse elements of Steps array
            if (!stepsRecipeArray.isEmpty())
                stepsRecipeArray.clear();
            for (int j = 0; j < stepsLen; j++) {

                if (!parseStepFromStepsArray(j))
                    continue;

                if(!parseShortDescriptionFromStep())
                    continue;

                if (!parseDescriptionFromStep())
                    continue;

                if (!parseVideoURLFromStep())
                    continue;

                if(!parseThumbnailURLFromStep())
                    continue;
                Steps step = new  Steps(stepId, shortDescription, description, videoURL, thumbnailURL);
                stepsRecipeArray.add(step);
            }
            Recipe recipe = new Recipe(id, name, servings, imageURL, ingredientsRecipeArray, stepsRecipeArray);
            mRecipeArrayList.add(recipe);
        }
        dataRead = true;
        return true;
    }

    public static ArrayList<Ingredients> getIngredientsForRecipe(int index) {
        return mRecipeArrayList.get(index).mIngredientsArrayList;
    }

    public static ArrayList<Steps> getStepsForRecipe(int index) {
        return mRecipeArrayList.get(index).mStepsArrayList;
    }

    public static String getStepDescription(Integer recipeIndex, Integer stepIndex) {
        return mRecipeArrayList.get(recipeIndex).mStepsArrayList.get(stepIndex).description;
    }

    public static String getThumbnailURL(Integer recipeIndex, Integer stepIndex) {
        return mRecipeArrayList.get(recipeIndex).mStepsArrayList.get(stepIndex).thumbnailURL;
    }

    public static Integer getNumberOfSteps(Integer recipeIndex) {
        return mRecipeArrayList.get(recipeIndex).mStepsArrayList.size();
    }
}
