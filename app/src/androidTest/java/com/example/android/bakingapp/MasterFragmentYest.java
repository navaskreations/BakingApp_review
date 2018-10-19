package com.example.android.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.util.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MasterFragmentYest {

       public final ActivityTestRule<ReceipeStepActivity> mActivityTestRule =
                new ActivityTestRule<>(ReceipeStepActivity.class, false, false);

        private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), ReceipeStepActivity.class);

        @Before
        public void setup() {
           mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
        }

        @Test
        public void checkTextDisplayedInDynamicallyCreatedFragment() {
            ReceipeMasterFragment fragment = new ReceipeMasterFragment();
            mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_container, fragment).commit();
            String itemElementText = mActivityTestRule.getActivity().getResources()
                    .getString(R.string.ingredients);

            onView(ViewMatchers.withId(R.id.tv_receipe)).check(matches(withText(itemElementText)));


        }

    }