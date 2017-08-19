package com.arifinbardansyah.android.bakingapps;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.arifinbardansyah.android.bakingapps.ui.main.MainActivity;
import com.arifinbardansyah.android.bakingapps.utility.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by arifinbardansyah on 8/19/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    public static final String RECIPE_NAME = "Nutella Pie";
    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new
            IntentsTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeItem_OpensRecipeActivity_clickStepItem_OpenStepActivity() {
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));

        onView(withId(R.id.text_ingredients)).check(matches(withText("Ingredients :")));
        onView(withId(R.id.text_steps)).check(matches(withText("Steps :")));

        onView(withId(R.id.rv_recipe_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));

        onView(withId(R.id.text_description)).check(matches(withText("Description :")));
        intended(hasExtra(Constants.EXTRA_RECIPE_NAME, RECIPE_NAME));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
