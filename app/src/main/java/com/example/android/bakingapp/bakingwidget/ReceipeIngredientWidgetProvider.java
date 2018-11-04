package com.example.android.bakingapp.bakingwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.provider.IngredientContract;

import static com.example.android.bakingapp.provider.IngredientContract.BASE_CONTENT_URI;
import static com.example.android.bakingapp.provider.IngredientContract.PATH_RECEIPE;

/**
 * Implementation of App Widget functionality.
 */
public class ReceipeIngredientWidgetProvider extends AppWidgetProvider {

    private static final String TAG = ReceipeIngredientWidgetProvider.class.getSimpleName();
Cursor cursor;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
      RemoteViews views = getReceipeRemoteView(context);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Log.d(TAG,"updateAppWidget");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        Log.d(TAG,"onUpdate");
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG,"onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG,"onDisabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG,"onReceive");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d(TAG,"onRestored");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG,"onDeleted");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG,"onAppWidgetOptionsChanged");
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    public static RemoteViews getReceipeRemoteView(Context mContext) {
        Cursor cursor = null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.receipe_ingredient_widget_provider);
        Uri RECEIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECEIPE).build();

        cursor = mContext.getContentResolver().query(
                RECEIPE_URI,
                null,
                "receipeName=" + "\'lastseenreceipe\'",
                null,
                null
        );
        if (cursor != null && cursor.getCount() >    0) {
            cursor.moveToPosition(0);
            int rIngredient = cursor.getColumnIndex(IngredientContract.ReceipeEntry.COLUMN_INGREDEINT);
            views.setTextViewText(R.id.tv_receipewidget, cursor.getString(rIngredient));
        }
        return  views;
    }
}

