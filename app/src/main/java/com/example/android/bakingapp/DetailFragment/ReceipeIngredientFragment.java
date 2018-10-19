package com.example.android.bakingapp.DetailFragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReceipeIngredientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReceipeIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/* Update the TextView with receipe ingredients*/
public class ReceipeIngredientFragment extends Fragment {

    // Tag for logging
    private static final String TAG = ReceipeIngredientFragment.class.getSimpleName();
    TextView detail;
    View rootView;

    public ReceipeIngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_receipe_ingredient, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Ingredients> ingredientsList = new ArrayList<Ingredients>();

        // Check if call is from activity or widget and dispaly ngredients accordingly
        if (getArguments() != null) {
            ingredientsList = (List<Ingredients>) getArguments().get(ReceipeStepActivity.RECEIPE_INGREDEINTS);

            if (ingredientsList != null && ingredientsList.size() != 0) {
                TextView ingredientTV = rootView.findViewById(R.id.tv_ingre);
                String temp;
                String tempHeading = " INGREDIENTS \n\n";
                temp = tempHeading;
                for (int i = 0; i < ingredientsList.size(); i++) {
                    String quantity;
                    String measure;
                    String ing;
                    quantity = String.valueOf(ingredientsList.get(i).getQuantity());
                    measure = String.valueOf(ingredientsList.get(i).getMeasure());
                    ing = String.valueOf(ingredientsList.get(i).getIngredient());
                    temp = temp + quantity + " " + measure + "  " + ing + "\n";
                }
                ingredientTV.setText(temp);
            } else {
                TextView ingredientTV = rootView.findViewById(R.id.tv_ingre);
                String IngredientsTxt = getArguments().getString(ReceipeIngredientActivity.RECEIPEINGREDIENTWIDGET);
                ingredientTV.setText(IngredientsTxt);

            }
        }
    }
}
