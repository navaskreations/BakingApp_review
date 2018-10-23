package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.DetailFragment.ReceipeDetailStepFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeIngredientFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeNavigationFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeVideoFragment;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReceipeIngredientActivity extends AppCompatActivity {

    private static final String TAG = ReceipeDetailActivity.class.getSimpleName();
    public static final String RECEIPEID = "ReceipeID";
    public static final String RECEIPEINGREDIENTWIDGET = "IngredientFromWidget";

    /* Display Ingredient in detail fragment view*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_ingredient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.getExtras() != null) {
                setTitle(intentThatStartedThisActivity.getStringExtra(ReceipeMasterFragment.RECEIPENAME));
                Bundle arguments = new Bundle();
                List<Ingredients> ingredients = new ArrayList<Ingredients>();
                //Check if it is from widget or activity
                if (intentThatStartedThisActivity.hasExtra(RECEIPEID)) {
                    String receipeID = intentThatStartedThisActivity.getStringExtra(RECEIPEID);
                    String receipeIngredeint = intentThatStartedThisActivity.getStringExtra(RECEIPEINGREDIENTWIDGET);
                    arguments.putString(RECEIPEID, receipeID);
                    arguments.putString(RECEIPEINGREDIENTWIDGET, receipeIngredeint);
                } else {
                    ingredients = (List<Ingredients>) intentThatStartedThisActivity.getSerializableExtra(ReceipeMasterFragment.INGREDIENTLIST_MASTER);
                    if (ingredients.size() != 0) {
                        //Toast.makeText(this, "you choose " + ingredients.get(0).getIngredient(), Toast.LENGTH_LONG).show();
                        Log.i(TAG, "you choose " + ingredients.get(0).getIngredient());

                        arguments.putSerializable(ReceipeStepActivity.RECEIPE_INGREDEINTS, (Serializable) ingredients);
                    }
                }
                FragmentManager fragmentManager = getSupportFragmentManager();

                ReceipeIngredientFragment ingredientFragment = new ReceipeIngredientFragment();
                ingredientFragment.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .add(R.id.ingredient_container, ingredientFragment)
                        //.addToBackStack(null)
                        .commit();
            }
        }
    }

}