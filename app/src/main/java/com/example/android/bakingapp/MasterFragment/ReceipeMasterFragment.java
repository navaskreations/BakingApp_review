package com.example.android.bakingapp.MasterFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeDetailActivity;
import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.Adapter.ReceipeAdapter;
import com.example.android.bakingapp.Adapter.ReceipeMasterAdapter;
import com.example.android.bakingapp.DetailFragment.ReceipeIngredientFragment;
import com.example.android.bakingapp.DetailFragment.ReceipeNavigationFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceipeMasterFragment extends Fragment implements ReceipeMasterAdapter.ReceipeMasterAdapterOnClickHandler {

    private static final String TAG = ReceipeMasterFragment.class.getSimpleName();
    public static final String SELECTED_RECEIPESTEP_MASTER = "selected_receipesteps_master_fragment";
    public static final String RECEIPESTEP_LIST_MASTER = "listof_receipestep_master";
    public static final String RECEIPENAME = "receipeName";
    public static final String MASTER_ADAPTER_POSITION = "adapterposition";
    public static final String INGREDIENTLIST_MASTER = "receipeingredient_master_fragment";

    private ReceipeMasterAdapter mAdapter;
    Receipe receipe;
    ArrayList<ReceipeSteps> steps = new ArrayList<ReceipeSteps>();
    public boolean isTwoPane = false;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public ReceipeMasterFragment() {
    }

    //interface OnStepClickListener triggers a callback in the host activity
    ReceipeMasterFragment.OnStepClickListener mCallback;

    // OnStepClickListener interface, calls a method in the host activity
    public interface OnStepClickListener {
        void onStepSelected(int position);
        void OnIngreSelected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getActivity().findViewById(R.id.receipe_step_twopane) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            isTwoPane = true;
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            isTwoPane = false;
        }

        // binding view
        ButterKnife.bind(getActivity());

        final View rootView = inflater.inflate(R.layout.fragment_master_receipe, container, false);

        // Set Layout and Adapter to Recycler View
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ReceipeMasterAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // Update UI/Adapter
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            steps = (ArrayList<ReceipeSteps>) getArguments().get(ReceipeStepActivity.RECEIPE_STEPS);
            receipe = (Receipe) getArguments().get(MainActivity.SELECTED_RECEIPE);
            List<Ingredients> ingredients = (ArrayList<Ingredients>) receipe.getIngredients();

            if (steps != null) {
               // Log.i(TAG, " you choose " + ingredients.get(0).getIngredient());
                mAdapter.setReceipeSteps(steps);
                mAdapter.setReceipeIngredients(ingredients);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
                 } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListerner");
        }
    }

    /*
     *This Method instantiate detailactivity in case of phone view
     * in case of TwoPane/Tablet View display the detail fragments side by side to master fragment
     */
    @Override
    public void onClick(ReceipeSteps receipeSteps, int position) {
        if(isTwoPane)
            mCallback.onStepSelected(position);
        else {
            Context context = getActivity().getApplicationContext();
            Intent intent = new Intent(context, ReceipeDetailActivity.class);
            intent.putExtra(SELECTED_RECEIPESTEP_MASTER, receipeSteps);
            intent.putExtra(RECEIPESTEP_LIST_MASTER, steps);
            intent.putExtra(RECEIPENAME, receipe.getName());
            intent.putExtra(MASTER_ADAPTER_POSITION, position);
            startActivity(intent);
        }
    }

    /*
     *This Method instantiate ingredient activity in case of phone view
     * in case of TwoPane/Tablet View display the ingredient fragments side by side to master fragment
     */
    @Override
    public void onClick(List<Ingredients> ingredients) {
        if(isTwoPane)
            mCallback.OnIngreSelected();
        else {
        Context context = getActivity().getApplicationContext();
        Intent intent = new Intent(context, ReceipeIngredientActivity.class);
        intent.putExtra(RECEIPENAME, receipe.getName());
        intent.putExtra(INGREDIENTLIST_MASTER, (Serializable) ingredients);
        startActivity(intent);
    }
}
}
