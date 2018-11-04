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
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReceipeDetailActivity extends AppCompatActivity implements ReceipeNavigationFragment.OnButtonClickListener {

    private static final String TAG = ReceipeDetailActivity.class.getSimpleName();
    public static final String POSITION = "position";
    private static final String SELECTED_RECEIPESTEP = "selected_receipestep";
    public static final String RECEIPE_STEP_LIST = " receipestep_list";
    private static final String RECEIPENAME = " receipename";

    ReceipeSteps selectedReceipeStep;
    ArrayList<ReceipeSteps> steps;
    int mposition = 0;
    String rName;
    boolean updatecontineronorienchange = false;

    /*This Method displays the detailsFragment by calling description,video and navigation fragment*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            mposition = savedInstanceState.getInt(POSITION);
            selectedReceipeStep = (ReceipeSteps) savedInstanceState.getSerializable(SELECTED_RECEIPESTEP);
            steps = (ArrayList<ReceipeSteps>) savedInstanceState.getSerializable(RECEIPE_STEP_LIST);
            rName = savedInstanceState.getString(RECEIPENAME);
            setTitle(rName);
            updatecontineronorienchange = true;
            callfragment(selectedReceipeStep);
        }

        if (savedInstanceState == null) {
            Intent intentThatStartedThisActivity = getIntent();

            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.getExtras() != null) {
                    if (intentThatStartedThisActivity.getSerializableExtra(ReceipeMasterFragment.SELECTED_RECEIPESTEP_MASTER) != null) {
                        rName = intentThatStartedThisActivity.getStringExtra(ReceipeMasterFragment.RECEIPENAME);
                        setTitle(rName);
                        steps = (ArrayList<ReceipeSteps>) intentThatStartedThisActivity.getSerializableExtra(ReceipeMasterFragment.RECEIPESTEP_LIST_MASTER);
                        mposition = intentThatStartedThisActivity.getIntExtra(ReceipeMasterFragment.MASTER_ADAPTER_POSITION, 1);
                        selectedReceipeStep = steps.get(mposition);
                        if (selectedReceipeStep != null) {
                            //Toast.makeText(this, " you choose " + selectedReceipe.getDescription(), Toast.LENGTH_LONG).show();
                            Log.i(TAG, "you choose " + selectedReceipeStep.getDescription());

                            Bundle arguments = new Bundle();
                            arguments.putSerializable((String) ReceipeStepActivity.SELECTED_RECEIPE_STEP, selectedReceipeStep);
                            arguments.putSerializable(ReceipeStepActivity.RECEIPE_STEP_POSITION, mposition);
                            arguments.putSerializable(ReceipeStepActivity.RECEIPESTEP_LISTSIZE, steps.size());

                            FragmentManager fragmentManager = getSupportFragmentManager();

                            if (findViewById(R.id.video_container) != null) {
                                ReceipeVideoFragment videoFragment = new ReceipeVideoFragment();
                                videoFragment.setArguments(arguments);
                                fragmentManager.beginTransaction()
                                        .add(R.id.video_container, videoFragment)
                                        //.addToBackStack(null)
                                        .commit();
                            }

                            if (findViewById(R.id.step_detail_container) != null) {
                                ReceipeDetailStepFragment detailStepFragment = new ReceipeDetailStepFragment();
                                detailStepFragment.setArguments(arguments);
                                fragmentManager.beginTransaction()
                                        .add(R.id.step_detail_container, detailStepFragment)
                                       // .addToBackStack(null)
                                        .commit();
                            }

                            if (findViewById(R.id.navigation_container) != null) {
                                ReceipeNavigationFragment navigationFragment = new ReceipeNavigationFragment();
                                navigationFragment.setArguments(arguments);
                                fragmentManager.beginTransaction()
                                        .add(R.id.navigation_container, navigationFragment)
                                       // .addToBackStack(null)
                                        .commit();
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void onPButtonSelected(int position) {
        Toast.makeText(this, "you choose Previous Button", Toast.LENGTH_LONG).show();
        mposition = position - 1;
        selectedReceipeStep = steps.get(mposition);
        updatecontineronorienchange = false;
        callfragment(selectedReceipeStep);
    }

    @Override
    public void onNButtonSelected(int position) {
        Toast.makeText(this, "you choose Next Button", Toast.LENGTH_LONG).show();
        mposition = position + 1;
        selectedReceipeStep = steps.get(mposition);
        updatecontineronorienchange = false;
        callfragment(selectedReceipeStep);
    }

    /* Replace the detail fragment in master/detail when navigation button is pressed*/
    public void callfragment(ReceipeSteps selectedReceipe) {
        if (selectedReceipe != null) {
            //Toast.makeText(this, "you choose " + selectedReceipe.getDescription(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "you choose " + selectedReceipe.getDescription());

            Bundle arguments = new Bundle();
            arguments.putSerializable(ReceipeStepActivity.SELECTED_RECEIPE_STEP, selectedReceipe);
            arguments.putSerializable(ReceipeStepActivity.RECEIPE_STEP_POSITION, mposition);
            arguments.putSerializable(ReceipeStepActivity.RECEIPESTEP_LISTSIZE, steps.size());

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (findViewById(R.id.video_container) != null && !updatecontineronorienchange) {
                ReceipeVideoFragment videoFragment = new ReceipeVideoFragment();
                videoFragment.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.video_container, videoFragment)
                        .commit();
            }

            if (findViewById(R.id.step_detail_container) != null) {
                ReceipeDetailStepFragment detailStepFragment = new ReceipeDetailStepFragment();
                detailStepFragment.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.step_detail_container, detailStepFragment)
                        .commit();
            }

            if (findViewById(R.id.navigation_container) != null) {
                ReceipeNavigationFragment navigationFragment = new ReceipeNavigationFragment();
                navigationFragment.setArguments(arguments);
                fragmentManager.beginTransaction()
                        .replace(R.id.navigation_container, navigationFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putInt(POSITION, mposition);
        currentState.putSerializable(SELECTED_RECEIPESTEP, steps.get(mposition));
        currentState.putSerializable(RECEIPE_STEP_LIST, steps);
        currentState.putString(RECEIPENAME, rName);
        super.onSaveInstanceState(currentState);
    }

}