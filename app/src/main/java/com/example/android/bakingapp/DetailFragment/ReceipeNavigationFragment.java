package com.example.android.bakingapp.DetailFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.util.ArrayList;
import java.util.List;

/* Handle navigation Button in detail fragment as we navigate through receipesteps */
public class ReceipeNavigationFragment extends Fragment {

    // Tag for logging
    private static final String TAG = ReceipeNavigationFragment.class.getSimpleName();
    int position;
    int stepsArraySize;

    public ReceipeNavigationFragment() {
    }

    // interface OnButtonClickListener that triggers a callback in the host activity
    OnButtonClickListener mCallback;

    // OnButtonClickListener interface, calls a method in the host activity
    public interface OnButtonClickListener {
        void onPButtonSelected(int position);

        void onNButtonSelected(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receipe_navigation, container, false);

        if (getArguments() != null) {
            position = (int) getArguments().get(ReceipeStepActivity.RECEIPE_STEP_POSITION);
            stepsArraySize = (int) getArguments().get(ReceipeStepActivity.RECEIPESTEP_LISTSIZE);
        }

        Button previous_button = (Button) rootView.findViewById(R.id.b_previous);
        previous_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getActivity(), "Previous called", Toast.LENGTH_LONG).show();
                if (position <= 0)
                    Toast.makeText(getActivity(), "You Reached Initial Step.No more Steps to navigate backward", Toast.LENGTH_LONG).show();
                else
                    mCallback.onPButtonSelected(position);
            }
        });

        Button next_button = (Button) rootView.findViewById(R.id.b_next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "Next called", Toast.LENGTH_LONG).show();
                if (position >= stepsArraySize - 1)
                    Toast.makeText(getActivity(), "You Reached Final Step.No More Steps to Navigate forward.", Toast.LENGTH_LONG).show();
                else
                    mCallback.onNButtonSelected(position);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement mCallbackPrevious");
        }
    }
}


