package com.example.android.bakingapp.Activity;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.IdlingResource.ReceipeDownloader;

import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Adapter.ReceipeAdapter;
import com.example.android.bakingapp.bakingwidget.ReceipeIngredientWidgetProvider;
import com.example.android.bakingapp.network.APIInterface;
import com.example.android.bakingapp.network.RetrofitClientInstance;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.provider.IngredientContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.provider.IngredientContract.BASE_CONTENT_URI;
import static com.example.android.bakingapp.provider.IngredientContract.PATH_RECEIPE;

public class MainActivity extends AppCompatActivity implements ReceipeAdapter.ReceipeAdapterOnClickHandler, ReceipeDownloader.DelayerCallback  {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SELECTED_RECEIPE = "selectedreceipe";

    private ReceipeAdapter mAdapter;
    @BindView(R.id.rv_receipe)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar mprogressDialog;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the IdlingResource instance
        getIdlingResource();
        /*Binding View*/
        ButterKnife.bind(this);

        /*ProgressBar*/
        // SetupLoader
        mprogressDialog = (ProgressBar) findViewById(R.id.progressbar);
       mprogressDialog.setVisibility(View.VISIBLE);
        /*Create handle for the RetrofitInstance interface to  extract Receipe Information from the URL*/
        APIInterface apiInterface = RetrofitClientInstance.getRetrofitInstance().create(APIInterface.class);
        Call<List<Receipe>> call = apiInterface.getAllReceipe();
        call.enqueue(new Callback<List<Receipe>>() {
            @Override
            public void onResponse(Call<List<Receipe>> call, Response<List<Receipe>> response) {
                mprogressDialog.setVisibility(View.INVISIBLE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Receipe>> call, Throwable t) {
                mprogressDialog.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of Receipe data using RecyclerView with custom adapter*/
    private void generateDataList(List<Receipe> receipeList) {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_receipe);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ReceipeAdapter(this, receipeList);
        mRecyclerView.setAdapter(mAdapter);
        updateDB(receipeList);
    }

    /*Method to instantiate Receipe Steps for the selected Receipe*/
    @Override
    public void onClick(Receipe receipe) {
        Context context = this;
        Intent intent = new Intent(context, ReceipeStepActivity.class);
        intent.putExtra(SELECTED_RECEIPE, receipe);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ReceipeDownloader.downloadReceipe(this, MainActivity.this, mIdlingResource);
    }

    /*Method to store the ingredients details in local DB for widget Purpose*/
    public void updateDB(List<Receipe> receipeList) {

        if (receipeList != null) {
            if (receipeList.size() >= 0) {
                for (int j = 0; j <= receipeList.size() - 1; j++) {
                    Cursor mCursor = null;
                    Integer receipeId = receipeList.get(j).getId();
                    Uri RECEIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECEIPE).build();
                    if (mCursor != null) mCursor.close();
                    mCursor = this.getContentResolver().query(
                            RECEIPE_URI,
                            null,
                            "receipeId=" + String.valueOf(receipeId),
                            null,
                            null,
                            null
                    );
//Print Cursor values
                /*if (mCursor.moveToFirst()) {
                    do {
                        StringBuilder sb = new StringBuilder();
                        int columnsQty = mCursor.getColumnCount();
                        for (int idx=0; idx<columnsQty; ++idx) {
                            sb.append(mCursor.getString(idx));
                            if (idx < columnsQty - 1)
                                sb.append("; ");
                        }
                        Log.v(TAG, String.format("Row: %d, Values: %s", mCursor.getPosition(),
                                sb.toString()));
                    } while (mCursor.moveToNext());
                }*/
                    String receipeName = receipeList.get(j).getName();
                    String temp = "";
                    List<Ingredients> ingredientsList = receipeList.get(j).getIngredients();
                    //Frame the Ingredients to display
                    if (ingredientsList != null) {
                    if (ingredientsList.size() != 0) {
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
                    }
                    }
                    if (mCursor != null && mCursor.moveToFirst()) {
                        // Update the receipe into DB
                        ContentValues contentValues = new ContentValues();
                        //Log.i(TAG, "Table " + receipeId + ":" + receipeName + ":" + temp);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_ID, receipeId);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_NAME, receipeName);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_INGREDEINT, temp);
                        int result = getContentResolver().update(IngredientContract.ReceipeEntry.CONTENT_URI, contentValues, "receipeId=" + receipeId, null);
                        Log.i(TAG, "Table updated for receipe name" + receipeName);

                    } else {
                        // Insert the new receipe into DB
                        ContentValues contentValues = new ContentValues();
                        //   Log.i(TAG, "Table " + receipeId + ":" + receipeName + ":" + temp);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_ID, receipeId);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_NAME, receipeName);
                        contentValues.put(IngredientContract.ReceipeEntry.COLUMN_INGREDEINT, temp);
                        getContentResolver().insert(IngredientContract.ReceipeEntry.CONTENT_URI, contentValues);
                        Log.i(TAG, "Table inserted");
                    }
                }
            }
        }
    }

    @Override
    public void onDone(ArrayList<Receipe> receipes) {
        //generateDataList(receipes);
    }
}

