package com.example.android.bakingapp.IdlingResource;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Ingredients;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;

import java.util.ArrayList;

public class ReceipeDownloader {
    private static final int DELAY_MILLIS = 3000;

    // Create an ArrayList of mReceipe
    final static ArrayList<Receipe> mReceipe = new ArrayList<>();

    public interface DelayerCallback{
        void onDone(ArrayList<Receipe> receipes);
    }

    /**
     * This method is meant to simulate downloading a large image file which has a loading time
     * delay. This could be similar to downloading an image from the internet.
     * For simplicity, in this hypothetical situation, we've provided the image in
     * {@link drawable/order_activity_tea_image.jpg}.
     * We simulate a delay time of {@link #DELAY_MILLIS} and once the time
     * is up we return the image back to the calling activity via a {@link DelayerCallback}.
     * @param callback used to notify the caller asynchronously
     */
    public static void downloadReceipe(Context context, final DelayerCallback callback,
                              @Nullable final SimpleIdlingResource idlingResource) {

        /**
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        // Display a toast to let the user know the images are downloading
        String text = context.getString(R.string.loading_msg);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        ArrayList<Ingredients> ingredients = null;
        ArrayList<ReceipeSteps> steps = null;

        int id = 1;
        // Fill ArrayList with Tea objects
        mReceipe.add(new Receipe(1,"Nutella Pie",8,""));
        mReceipe.add(new Receipe(2,"Brownies",8,""));
        mReceipe.add(new Receipe(3,"Yellow Cake",8,""));
        mReceipe.add(new Receipe(4,"Cheesecake",8,""));




        /**
         * {@link postDelayed} allows the {@link Runnable} to be run after the specified amount of
         * time set in DELAY_MILLIS elapses. An object that implements the Runnable interface
         * creates a thread. When this thread starts, the object's run method is called.
         *
         * After the time elapses, if there is a callback we return the image resource ID and
         * set the idle state to true.
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(mReceipe);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
