package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ReceipeIngredientActivityTest {
        private Receipe receipe;

        @Rule
        public ActivityTestRule<ReceipeIngredientActivity> receipeIngActivityActivityTestRule = new ActivityTestRule<>(ReceipeIngredientActivity.class);

        @Test
        public void TestAutoComplete() {
            Intent intent = new Intent();
            ArrayList<Ingredients> ingArray = new ArrayList<>();
            Ingredients ing = new Ingredients();
            ing.setQuantity(2.0);
            ing.setIngredient("Graham Cracker crumbs");
            ing.setMeasure("CUP");
            ingArray.add(ing);

            intent.putExtra(ReceipeMasterFragment.INGREDIENTLIST_MASTER, ingArray);

            receipeIngActivityActivityTestRule.launchActivity(intent);

        }
    }

