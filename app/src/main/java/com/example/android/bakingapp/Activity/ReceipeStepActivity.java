package com.example.android.bakingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.bakingapp.Adapter.ReceipeMasterAdapter;
import com.example.android.bakingapp.DetailFragment.ReceipeDetailStepFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeIngredientFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeVideoFragment;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReceipeStepActivity extends AppCompatActivity implements ReceipeMasterFragment.OnStepClickListener {

    private static final String TAG = ReceipeStepActivity.class.getSimpleName();
    public static final String RECEIPE_STEPS = "listof_receipesteps";
    public static final String SELECTED_RECEIPE_STEP = "selected_receipestep";
    public static final String RECEIPE_STEP_POSITION = "receipestep_position";
    public static final String RECEIPESTEP_LISTSIZE = "stepsarraysize";
    public static final String RECEIPE_INGREDEINTS = "listof_ingredients";
    private static final String TWOPANE = "isTwoPane";

    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;
    public Receipe selectedReceipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_step);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.receipe_step_twopane) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }

        /*
         *Enable Master Fragment with list of Receipe Steps
         *and in case of TwoPane display intial receipestep/Detail Fragment
         */
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.getExtras() != null) {
                selectedReceipe = (Receipe) intentThatStartedThisActivity.getSerializableExtra(MainActivity.SELECTED_RECEIPE);
                if (selectedReceipe != null) {
                    setTitle(selectedReceipe.getName());
                    // Toast.makeText(this, "Receipe Selected : " + selectedReceipe.getName(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Receipe Selected : " + selectedReceipe.getName());
                    Bundle arguments = new Bundle();
                    List<ReceipeSteps> steps = new ArrayList<ReceipeSteps>();
                    steps = selectedReceipe.getSteps();
                    arguments.putSerializable(RECEIPE_STEPS, (Serializable) steps);
                    arguments.putSerializable(MainActivity.SELECTED_RECEIPE, selectedReceipe);

                    /*MasterFragment*/
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    ReceipeMasterFragment masterFragment = new ReceipeMasterFragment();
                    masterFragment.setArguments(arguments);
                    if (masterFragment.isAdded())
                    fragmentManager.beginTransaction()
                            .replace(R.id.steps_container, masterFragment)
                            //.addToBackStack(null)
                            .commit();
                    else
                        fragmentManager.beginTransaction()
                                .add(R.id.steps_container, masterFragment)
                                //.addToBackStack(null)
                                .commit();



                    if (savedInstanceState == null && mTwoPane) {
                        // In two-pane mode, add initial receipestep fragment to the screen
                        arguments = new Bundle();

                        arguments.putSerializable(SELECTED_RECEIPE_STEP, selectedReceipe.getSteps().get(0));
                        arguments.putSerializable(RECEIPE_STEP_POSITION, 0);
                        arguments.putSerializable(RECEIPESTEP_LISTSIZE, steps.size());

                        ReceipeVideoFragment videoFragment = new ReceipeVideoFragment();
                        videoFragment.setArguments(arguments);
                        fragmentManager.beginTransaction()
                                .add(R.id.video_container, videoFragment)
                               // .addToBackStack(null)
                                .commit();

                        ReceipeDetailStepFragment detailStepFragment = new ReceipeDetailStepFragment();
                        detailStepFragment.setArguments(arguments);
                        fragmentManager.beginTransaction()
                                .add(R.id.step_detail_container, detailStepFragment)
                               // .addToBackStack(null)
                                .commit();
                    }
                }
            }
        }
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        if (mTwoPane)
            currentState.putBoolean(TWOPANE, true);
        else
            currentState.putBoolean(TWOPANE, false);
    }

    /*
     *This Method displays the ReceipeStep detailsFragment by calling description fragment and video fragment
     *and hides the ingredient fragment
     */
    @Override
    public void onStepSelected(int position) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(SELECTED_RECEIPE_STEP, selectedReceipe.getSteps().get(position));
        arguments.putSerializable(RECEIPE_STEP_POSITION, position);
        arguments.putSerializable(RECEIPESTEP_LISTSIZE, selectedReceipe.getSteps().size());

        FragmentManager fragmentManager = getSupportFragmentManager();
        ReceipeVideoFragment videoFragment = new ReceipeVideoFragment();
        if (videoFragment.isAdded())
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(videoFragment)
                    .commit();

        ReceipeDetailStepFragment detailStepFragment = new ReceipeDetailStepFragment();
        if (detailStepFragment.isAdded())
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(detailStepFragment)
                    .commit();

        videoFragment.setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(R.id.video_container, videoFragment)
                .commit();

        detailStepFragment.setArguments(arguments);
        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_container, detailStepFragment)
                .commit();

        ReceipeIngredientFragment ingredientFragment = (ReceipeIngredientFragment) fragmentManager.findFragmentById(R.id.ingredient_container);
        if (ingredientFragment != null)
            fragmentManager.beginTransaction()
                    .remove(ingredientFragment)
                    .commit();
    }

    /*
     *This Method displays the ReceipeIngredients detailsFragment by calling ingredient fragment
     *and hides the video and description fragment
     */
    @Override
    public void OnIngreSelected() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(RECEIPE_INGREDEINTS, (Serializable) selectedReceipe.getIngredients());

        FragmentManager fragmentManager = getSupportFragmentManager();

        ReceipeVideoFragment videoFragment = (ReceipeVideoFragment) fragmentManager.findFragmentById(R.id.video_container);
        if (videoFragment != null)
            fragmentManager.beginTransaction()
                    .remove(videoFragment)
                    .commit();

        ReceipeDetailStepFragment detailStepFragment = (ReceipeDetailStepFragment) fragmentManager.findFragmentById((R.id.step_detail_container));
        if (detailStepFragment != null)
            fragmentManager.beginTransaction()
                    .remove(detailStepFragment)
                    .commit();

        ReceipeIngredientFragment ingredientFragment = new ReceipeIngredientFragment();
        ingredientFragment.setArguments(arguments);
        fragmentManager.beginTransaction()
                .add(R.id.ingredient_container, ingredientFragment)
                //.addToBackStack(null)
                .commit();
    }

}


