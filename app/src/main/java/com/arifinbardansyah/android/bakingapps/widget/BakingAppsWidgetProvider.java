package com.arifinbardansyah.android.bakingapps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.ui.main.MainActivity;
import com.arifinbardansyah.android.bakingapps.ui.recipe.RecipeActivity;
import com.arifinbardansyah.android.bakingapps.utility.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipes recipes, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_apps_widget_provider);

        if (recipes!=null) {
            views.setTextViewText(R.id.widget_recipe, recipes.getName());

            Intent service = new Intent(context, ListIngredientsWidgetService.class);
            views.setRemoteAdapter(R.id.widget_list, service);
            views.setTextViewText(R.id.text_ingredient, "Ingredients :");
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra(Constants.EXTRA_RECIPE, recipes);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);
        } else {
            views.setTextViewText(R.id.widget_recipe, "Baking Apps");
            views.setTextViewText(R.id.text_ingredient, "Click and select recipe first");
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingAppsWidgetService.startUpdatePlantWidgets(context);
    }

    public static void onRecipeUpdate(Context context, AppWidgetManager appWidgetManager, Recipes recipes, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipes, appWidgetId);
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

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        BakingAppsWidgetService.startUpdatePlantWidgets(context);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

