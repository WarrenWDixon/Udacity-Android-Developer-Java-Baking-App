package com.android.example.bakingapp15;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    private static ArrayList<Ingredients> ingredientsArray;
    static Integer mRecipeIndex;
    static void UpdateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int recipeIndex) {
        mRecipeIndex = recipeIndex;
        Log.d("WWD", "in UpdateRecipeWidgets");
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = JsonUtil.getRecipeName(mRecipeIndex);
        ingredientsArray = JsonUtil.getIngredientsForRecipe(mRecipeIndex);
        Log.d("WWD", "in updateAppWidget");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ingredientsArray.size(); i++) {
            sb.append(ingredientsArray.get(i).ingredient);
            sb.append("\r\n");
        }
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        String myStr = widgetText.toString();
        views.setTextViewText(R.id.appwidget_label, widgetText.toString());
        views.setTextViewText(R.id.appwidget_text, sb.toString());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}