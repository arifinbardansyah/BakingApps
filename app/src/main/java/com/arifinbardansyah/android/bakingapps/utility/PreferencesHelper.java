package com.arifinbardansyah.android.bakingapps.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.google.gson.Gson;

/**
 * Created by arifinbardansyah on 8/18/17.
 */

public class PreferencesHelper {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context mContext;

    public PreferencesHelper() {
    }

    public PreferencesHelper(Context context) {

        mContext = context;
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveRecipeToPref(Recipes recipe) {
        editor.putString(Constants.PREF_RECIPE, new Gson().toJson(recipe));
        editor.commit();
    }

    public Recipes getRecipe(){
        return new Gson().fromJson(pref.getString(Constants.PREF_RECIPE,null),Recipes.class);
    }

    public static Recipes getRecipe(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        String jsonRecipe = prefs.getString(Constants.PREF_RECIPE, null);
        return new Gson().fromJson(jsonRecipe,Recipes.class);
    }
}
