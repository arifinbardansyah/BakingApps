package com.arifinbardansyah.android.bakingapps.ui.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Recipes;
import com.arifinbardansyah.android.bakingapps.model.Steps;
import com.arifinbardansyah.android.bakingapps.ui.step.StepActivity;
import com.arifinbardansyah.android.bakingapps.ui.step.StepFragment;
import com.arifinbardansyah.android.bakingapps.utility.Constants;
import com.arifinbardansyah.android.bakingapps.utility.PreferencesHelper;
import com.arifinbardansyah.android.bakingapps.widget.BakingAppsWidgetService;

public class RecipeActivity extends AppCompatActivity implements StepsAdapter.OnClickStepsListener {

    private boolean mTwoPane;
    private Recipes mRecipes;

    private FragmentManager fragmentManager;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        preferencesHelper = new PreferencesHelper(this);

        if (getIntent().hasExtra(Constants.EXTRA_RECIPE)) {
            mRecipes = getIntent().getParcelableExtra(Constants.EXTRA_RECIPE);
        }

        getSupportActionBar().setTitle(mRecipes.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.step_container)!=null){
            mTwoPane = true;

            fragmentManager = getSupportFragmentManager();
        } else {
            mTwoPane = false;
        }
    }

    private void setFragment(Steps step, int position) {
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, StepFragment.newInstance(step, position))
                .commit();
    }

    public static void start(Context context, Recipes recipes) {
        Intent starter = new Intent(context, RecipeActivity.class);
        starter.putExtra(Constants.EXTRA_RECIPE, recipes);
        context.startActivity(starter);
    }

    @Override
    public void onClickStep(Steps step, int position) {
        if (mTwoPane){
            setFragment(step, position);
        } else {
            StepActivity.start(this, mRecipes.getName(), mRecipes.getSteps(),position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_widget,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_add_to_widget:
                preferencesHelper.saveRecipeToPref(mRecipes);
                BakingAppsWidgetService.startUpdatePlantWidgets(this);
                Toast.makeText(this,"This recipe added to widget", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
