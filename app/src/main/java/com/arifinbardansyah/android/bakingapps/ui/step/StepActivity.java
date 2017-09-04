package com.arifinbardansyah.android.bakingapps.ui.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Steps;
import com.arifinbardansyah.android.bakingapps.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class StepActivity extends AppCompatActivity {

    public static final String CURRENT_FRAGMENT_POSITION = "currentfragmentposition";

    private FragmentManager fragmentManager;
    private Fragment fragment;

    private List<Steps> mSteps;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        String recipeName = "Baking Apps";
        if (getIntent().hasExtra(Constants.EXTRA_RECIPE_NAME)) {
            recipeName = getIntent().getStringExtra(Constants.EXTRA_RECIPE_NAME);
        }
        if (getIntent().hasExtra(Constants.EXTRA_STEP)) {
            mSteps = getIntent().getParcelableArrayListExtra(Constants.EXTRA_STEP);
        }
        if (getIntent().hasExtra(Constants.EXTRA_POSITION)) {
            position = getIntent().getIntExtra(Constants.EXTRA_POSITION, 0);
        }

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            setFragment(position);
        } else {
            position = savedInstanceState.getInt(CURRENT_FRAGMENT_POSITION);
        }

        getSupportActionBar().setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFragment(int position) {
        if (fragment==null) {
            fragment = StepFragment.newInstance(mSteps.get(position));

            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, fragment)
                    .commit();
        }
    }

    public static void start(Context context, String recipeName, ArrayList<Steps> steps, int
            position) {
        Intent starter = new Intent(context, StepActivity.class);
        starter.putExtra(Constants.EXTRA_RECIPE_NAME, recipeName);
        starter.putParcelableArrayListExtra(Constants.EXTRA_STEP, steps);
        starter.putExtra(Constants.EXTRA_POSITION, position);
        context.startActivity(starter);
    }

    public void onClickPrevious(View view) {
        position--;
        if (position >= 0) {
            setFragment(position);
        } else {
            position = 0;
            Toast.makeText(this, "This is the first step", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNext(View view) {
        position++;
        if (position < mSteps.size()) {
            setFragment(position);
        } else {
            position = mSteps.size() - 1;
            Toast.makeText(this, "This is the last step", Toast
                    .LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT_POSITION, position);
        super.onSaveInstanceState(outState);
    }
}
