package com.example.ankit_pc.bakingappudacity;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by ANKIT_PC on 08-03-2018.
 */

public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, RecipeWidgetService.class);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        rv.setRemoteAdapter(R.id.recipe_widget_GridView, intent);
        rv.setEmptyView(R.id.recipe_widget_GridView, R.id.recipes_empty_TextView);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    static public void onUpdateRecipes(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}
