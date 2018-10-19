package com.example.android.bakingapp.bakingwidget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.MasterFragment.ReceipeMasterFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.network.APIInterface;
import com.example.android.bakingapp.network.RetrofitClientInstance;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.provider.IngredientContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.provider.IngredientContract.BASE_CONTENT_URI;
import static com.example.android.bakingapp.provider.IngredientContract.PATH_RECEIPE;

public class GridWidgetServices extends RemoteViewsService {

    private static final String TAG = GridWidgetServices.class.getSimpleName();

    List<Receipe> receipeList = new ArrayList<>();
    Context mContext;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory");
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand");
        // Reaches the view on widget and displays the number
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_views);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        ComponentName theWidget = new ComponentName(this, ReceipeIngredientWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, views);


        return super.onStartCommand(intent, flags, startId);
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context mContext;
        Cursor mCursor;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
        }

        //called on start and when notifyAppWidgetViewDataChanged is called
        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
            // Get all receipe info
            Uri RECEIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECEIPE).build();
            if (mCursor != null) mCursor.close();
            mCursor = mContext.getContentResolver().query(
                    RECEIPE_URI,
                    null,
                    null,
                    null,
                    null
            );

//Print the cursor value
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
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");
            mCursor.close();
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount");
            return mCursor.getCount();
        }

        /**
         * This method acts like the onBindViewHolder method in an Adapter
         *
         * @param position The current position of the item in the GridView to be displayed
         * @return The RemoteViews object to display for the provided postion
         */
        @Override
        public RemoteViews getViewAt(int position) {

            if (mCursor == null || mCursor.getCount() == 0) return null;
            mCursor.moveToPosition(position);
            int rName = mCursor.getColumnIndex(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_NAME);
            int rId = mCursor.getColumnIndex(IngredientContract.ReceipeEntry.COLUMN_RECEIPE_ID);
            int rIngredient = mCursor.getColumnIndex(IngredientContract.ReceipeEntry.COLUMN_INGREDEINT);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.receipe_ingredient_widget_provider);
            views.setTextViewText(R.id.tv_receipewidget, mCursor.getString(rName));

            // Fill in the onClick PendingIntent Template using the specific receipe Id for each item individually
            Bundle extras = new Bundle();
            extras.putString(ReceipeIngredientActivity.RECEIPEID, mCursor.getString(rId));
            String temp = mCursor.getString(rIngredient);
            if (temp.isEmpty()) {
                temp = "Ingredients Missing in DB, Pls Check DB.";
                extras.putString(ReceipeIngredientActivity.RECEIPEINGREDIENTWIDGET, temp);
            }else
            extras.putString(ReceipeIngredientActivity.RECEIPEINGREDIENTWIDGET, mCursor.getString(rIngredient));
            extras.putString(ReceipeMasterFragment.RECEIPENAME, mCursor.getString(rName));

            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.appwidget_image, fillInIntent);

            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            Log.d(TAG, "getLoadingView");
            return null;
        }

        @Override
        public int getViewTypeCount() {
            Log.d(TAG, "getViewTypeCount");
            return 1; // Treat all items in the GridView the same
        }

        @Override
        public long getItemId(int i) {
            Log.d(TAG, "getItemId");
            return i;
        }

        @Override
        public boolean hasStableIds() {
            Log.d(TAG, "hasStableIds");
            return true;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return super.onBind(intent);

    }
}

