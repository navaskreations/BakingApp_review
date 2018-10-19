package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeDetailActivity;
import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ReceipeDetailActivityTest {
    private Receipe receipe;

        @Rule
        public ActivityTestRule<ReceipeDetailActivity> receipeDetailActivityActivityTestRule = new ActivityTestRule<>(ReceipeDetailActivity.class);

        @Test
        public void TestAutoComplete() {
            Intent intent = new Intent();

            ArrayList<ReceipeSteps> stepsArray = new ArrayList<>();
            ReceipeSteps steps = new ReceipeSteps();
            steps.setId(0);
            steps.setDescription("Recipe Introduction");
            steps.setShortDescription("Recipe Introduction");
            steps.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
            steps.setThumbnailURL("");
            stepsArray.add(steps);
            steps.setId(1);
            steps.setDescription("\"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\"");
            steps.setShortDescription("\"Starting prep\"");
            steps.setVideoURL("");
            steps.setThumbnailURL("");
            stepsArray.add(steps);

            intent.putExtra(ReceipeMasterFragment.RECEIPESTEP_LIST_MASTER, stepsArray);
            intent.putExtra(ReceipeMasterFragment.MASTER_ADAPTER_POSITION, 1);
            intent.putExtra(ReceipeDetailActivity.RECEIPE_STEP_LIST, stepsArray);
            receipeDetailActivityActivityTestRule.launchActivity(intent);

        }
    }

