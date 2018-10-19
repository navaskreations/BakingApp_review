package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeDetailActivity;
import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.DetailFragment.ReceipeIngredientFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeNavigationFragment;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ReceipeNavigationFragmentTest {

    public final ActivityTestRule<ReceipeDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(ReceipeDetailActivity.class, false, false);

    private static final Intent MY_ACTIVITY_INTENT = new Intent(InstrumentationRegistry.getTargetContext(), ReceipeDetailActivity.class);

    @Before
    public void setup() {
        mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
        ActivityTestRule<MainActivity> mainActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);


    }

    @Test
    public void checkDisplayedInDynamicallyCreatedFragment() {
        ReceipeNavigationFragment fragment = new ReceipeNavigationFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.navigation_container, fragment).commit();
        //onView(ViewMatchers.withId(R.id.playerView)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.b_next)).check(matches((isClickable())));
        onView(ViewMatchers.withId(R.id.b_previous)).check(matches((isClickable())));
    }

}
