package com.example.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class IdilingResourceMainActivityTest {

       /**
         * The ActivityTestRule is a rule provided by Android used for functional testing of a single
         * activity. The activity that will be tested, MenuActivity in this case, will be launched
         * before each test that's annotated with @Test and before methods annotated with @Before.
         *
         * The activity will be terminated after the test and methods annotated with @After are
         * complete. This rule allows you to directly access the activity during the test.
         */
        @Rule
        public ActivityTestRule<MainActivity> mActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        private IdlingResource mIdlingResource;


        // Registers any resource that needs to be synchronized with Espresso before the test is run.
        @Before
        public void registerIdlingResource() {
            mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
            // To prove that the test fails, omit this call:
            //Espresso.registerIdlingResources(mIdlingResource);
            IdlingRegistry.getInstance().register(mIdlingResource);
        }

        @Test
        public void idlingResourceTest() {
            //onData(anything()).inAdapterView(withId(R.id.rv_receipe)).atPosition(0).perform(click());
            onView(ViewMatchers.withId((R.id.rv_receipe))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

            // Match the text in an item below the fold and check that it's displayed.
            String itemElementText = mActivityTestRule.getActivity().getResources()
                    .getString(R.string.NutellaPie);
            onView(withText(itemElementText)).check(matches(isDisplayed()));

        }

        // Remember to unregister resources when not needed to avoid malfunction.
        @After
        public void unregisterIdlingResource() {
            if (mIdlingResource != null) {
                //Espresso.unregisterIdlingResources(mIdlingResource);
                IdlingRegistry.getInstance().unregister(mIdlingResource);
            }
        }

    }
