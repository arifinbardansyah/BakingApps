package com.arifinbardansyah.android.bakingapps.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.utility.Constants;
import com.arifinbardansyah.android.bakingapps.utility.PreferencesHelper;

/**
 * Created by arifinbardansyah on 8/18/17.
 */

public class BakingAppsWidgetService extends IntentService {

    public BakingAppsWidgetService() {
        super("BakingAppsWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(Constants.WIDGET_ACTION_UPDATE_RECIPE)) {
                handleActionUpdateRecipeWidget();
            }
        }
    }

    private void handleActionUpdateRecipeWidget() {
        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        Recipes recipe = preferencesHelper.getRecipe();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                BakingAppsWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
        BakingAppsWidgetProvider.onRecipeUpdate(this, appWidgetManager, recipe, appWidgetIds);
    }

    public static void startUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, BakingAppsWidgetService.class);
        intent.setAction(Constants.WIDGET_ACTION_UPDATE_RECIPE);
        context.startService(intent);
    }
}
