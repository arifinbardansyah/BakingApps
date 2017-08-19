package com.arifinbardansyah.android.bakingapps.api;

import com.arifinbardansyah.android.bakingapps.model.Recipes;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by arifinbardansyah on 8/8/17.
 */

public interface ApiInterface {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipes>> getRecipes();
}
