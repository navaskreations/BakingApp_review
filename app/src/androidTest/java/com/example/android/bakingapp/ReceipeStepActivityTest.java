package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.pojo.Receipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class ReceipeStepActivityTest {
        private Receipe receipe;

        @Rule
        public ActivityTestRule<ReceipeStepActivity> mainActivityActivityTestRule = new ActivityTestRule<>(ReceipeStepActivity.class);

        @Test
        public void TestAutoComplete() {
            Intent intent = new Intent();
            receipe = new Receipe(1,"Nutella Pie",8,"");
            intent.putExtra(MainActivity.SELECTED_RECEIPE, receipe);

            mainActivityActivityTestRule.launchActivity(intent);

        }
    }

