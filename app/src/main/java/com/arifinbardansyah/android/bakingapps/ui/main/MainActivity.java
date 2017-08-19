package com.arifinbardansyah.android.bakingapps.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arifinbardansyah.android.bakingapps.IdlingResource.SimpleIdlingResource;
import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.api.ApiClient;
import com.arifinbardansyah.android.bakingapps.api.ApiInterface;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.ui.recipe.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements RecipesAdapter
        .OnClickRecipeListener, SwipeRefreshLayout.OnRefreshListener{

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public static final String SAVE_INSTANCE_RECIPES = "saveinstancerecipes";

    private RecyclerView rvRecipes;
    private LinearLayout layoutRecipesTablet;
    private RecipesAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Recipes> recipesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();

        rvRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        layoutRecipesTablet = (LinearLayout) findViewById(R.id.layout_recipes_tablet);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        mAdapter = new RecipesAdapter(this, recipesList, this);
        if (layoutRecipesTablet != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rvRecipes.setLayoutManager(layoutManager);
            rvRecipes.setAdapter(mAdapter);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvRecipes.setLayoutManager(layoutManager);
            rvRecipes.setAdapter(mAdapter);
        }

        if (savedInstanceState != null) {
            ArrayList<Recipes> recipes = savedInstanceState.getParcelableArrayList
                    (SAVE_INSTANCE_RECIPES);
            if (recipes != null) {
                refreshAdapter(recipes);
            }
        } else {
            getRecipes();
        }

    }

    private void getRecipes() {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        ApiInterface apiInterface = ApiClient.provideApiInterface();
        apiInterface.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Recipes>>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);

                        if (mIdlingResource != null) {
                            mIdlingResource.setIdleState(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Recipes> recipes) {
                        refreshAdapter(recipes);
                    }
                });

    }

    public void showToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void refreshAdapter(List<Recipes> recipes){
        recipesList.clear();
        recipesList.addAll(recipes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_INSTANCE_RECIPES, recipesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClickRecipe(int position) {
        RecipeActivity.start(this,recipesList.get(position));
    }

    @Override
    public void onRefresh() {
        getRecipes();
    }
}
