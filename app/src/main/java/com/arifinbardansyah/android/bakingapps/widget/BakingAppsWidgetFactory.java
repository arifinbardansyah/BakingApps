package com.arifinbardansyah.android.bakingapps.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Ingredients;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.utility.PreferencesHelper;

/**
 * Created by arifinbardansyah on 8/18/17.
 */

public class BakingAppsWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private PreferencesHelper preferencesHelper;
    private Recipes mRecipes;

    public BakingAppsWidgetFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getRecipe();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipes.getIngredients() == null ? 0 : mRecipes.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredients ingredient = mRecipes.getIngredients().get(position);

        String ingredientAndQuantity = ingredient.getQuantity() + " " + ingredient.getMeasure() +
                " of " + ingredient.getIngredient();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        views.setTextViewText(R.id.widget_ingredient, ingredientAndQuantity);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void getRecipe() {
        preferencesHelper = new PreferencesHelper(mContext);
        mRecipes = preferencesHelper.getRecipe();
    }
}
