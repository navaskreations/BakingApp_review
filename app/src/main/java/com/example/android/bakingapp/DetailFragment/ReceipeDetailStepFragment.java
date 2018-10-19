package com.example.android.bakingapp.DetailFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.Adapter.ReceipeMasterAdapter;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.util.ArrayList;

/* Update the TextView with receipe description*/
public class ReceipeDetailStepFragment extends Fragment {

    // Tag for logging
    private static final String TAG = ReceipeDetailStepFragment.class.getSimpleName();
    TextView detail;

    public ReceipeDetailStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receipe_step_detail, container, false);
        detail = (TextView) rootView.findViewById(R.id.tv_receipedetail);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            ReceipeSteps steps = (ReceipeSteps) getArguments().get(ReceipeStepActivity.SELECTED_RECEIPE_STEP);

            if (steps != null) {
                Log.i(TAG, "you choose " + steps.getDescription());
                detail.setText(steps.getDescription());
            }
        }
    }
}
