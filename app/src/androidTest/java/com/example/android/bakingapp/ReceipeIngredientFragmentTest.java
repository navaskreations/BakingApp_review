package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeDetailActivity;
import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.DetailFragment.ReceipeDetailStepFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeIngredientFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class ReceipeIngredientFragmentTest {

        public final ActivityTestRule<ReceipeIngredientActivity> mActivityTestRule =
                new ActivityTestRule<>(ReceipeIngredientActivity.class, false, false);

        private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), ReceipeIngredientActivity.class);

        @Before
        public void setup() {
            mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
            ActivityTestRule<MainActivity> mainActivityTestRule =
                    new ActivityTestRule<>(MainActivity.class);


        }

        @Test
        public void checkDisplayedInDynamicallyCreatedFragment() {
            ReceipeIngredientFragment fragment = new ReceipeIngredientFragment();
            mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_container, fragment).commit();
            //onView(ViewMatchers.withId(R.id.playerView)).check(matches(not(isDisplayed())));
            onView(ViewMatchers.withId(R.id.tv_ingre)).check(matches((isDisplayed())));
        }

    }


