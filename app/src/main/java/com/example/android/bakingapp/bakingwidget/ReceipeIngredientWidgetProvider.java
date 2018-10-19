package com.example.android.bakingapp.bakingwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Activity.ReceipeIngredientActivity;
import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class ReceipeIngredientWidgetProvider extends AppWidgetProvider {

    private static final String TAG = ReceipeIngredientWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = getReceipeGridRemoteView(context);
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
    public static RemoteViews getReceipeGridRemoteView(Context context) {

        Log.d(TAG,"getReceipeGridRemoteView");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_views);
        // GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetServices.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // Set the ReceipeIngredientActivity intent to launch when clicked
        Intent appIntent = new Intent(context, ReceipeIngredientActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
                // Handle empty receipe
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
       /* AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = mgr.getAppWidgetIds(new ComponentName(context, ReceipeIngredientWidgetProvider.class));
        mgr.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_grid_view);*/
        return views;
        }
}

