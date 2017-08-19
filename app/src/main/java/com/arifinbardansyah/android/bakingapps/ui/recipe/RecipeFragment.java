package com.arifinbardansyah.android.bakingapps.ui.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.utility.Constants;

/**
 * Created by arifinbardansyah on 8/8/17.
 */

public class RecipeFragment extends Fragment {

    private LinearLayoutManager mLayoutManager;
    private StepsAdapter mAdapter;
    private Recipes mRecipes;
    private TextView tvIngredients;
    private RecyclerView rvSteps;

    private StepsAdapter.OnClickStepsListener mCallback;

    public RecipeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (StepsAdapter.OnClickStepsListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnClickStepsListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        if (getActivity().getIntent().hasExtra(Constants.EXTRA_RECIPE)) {
            mRecipes = getActivity().getIntent().getParcelableExtra(Constants.EXTRA_RECIPE);
        }

        tvIngredients = (TextView) rootView.findViewById(R.id.tv_ingredients);
        rvSteps = (RecyclerView) rootView.findViewById(R.id.rv_recipe_steps);

        String ingredients = "";
        if (mRecipes!=null) {
            if (mRecipes.getIngredients()!=null) {
                for (int i = 0; i < mRecipes.getIngredients().size(); i++) {
                    ingredients += (i + 1) + ".  " +
                            mRecipes.getIngredients().get(i).getQuantity() + " " +
                            mRecipes.getIngredients().get(i).getMeasure() + " of " +
                            mRecipes.getIngredients().get(i).getIngredient();
                    if (i != mRecipes.getIngredients().size() - 1) {
                        ingredients += "\n";
                    }
                }
            }
        }
        tvIngredients.setText(ingredients);

        mLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSteps.getContext(),
                mLayoutManager.getOrientation());
        rvSteps.addItemDecoration(dividerItemDecoration);
        mAdapter = new StepsAdapter(getContext(), mRecipes.getSteps(), mCallback);
        rvSteps.setLayoutManager(mLayoutManager);
        rvSteps.setAdapter(mAdapter);

        return rootView;
    }

}
