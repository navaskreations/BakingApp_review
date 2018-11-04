package com.example.android.bakingapp.DetailFragment;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Activity.ReceipeStepActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.pojo.Receipe;
import com.example.android.bakingapp.pojo.ReceipeSteps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceipeVideoFragment extends Fragment implements ExoPlayer.EventListener {

    // Tag for logging
    private static final String TAG = ReceipeNavigationFragment.class.getSimpleName();

    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";
    private static final String KEY_WINDOW = "window";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private boolean startAutoPlay;
    private long startPosition;
    private int startWindow;

    TextView video;

    public ReceipeVideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_receipe_video, container, false);

        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
        } else {
            startAutoPlay = true;
            startPosition = 0;
            startWindow = C.INDEX_UNSET;
             //setRetainInstance(true);

        }
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        // Initialize the Media Session.
        initializeMediaSession();
        initializePlayer();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {

            Uri mediaUri = null;
            if (getArguments() != null) {
                ReceipeSteps steps = (ReceipeSteps) getArguments().get(ReceipeStepActivity.SELECTED_RECEIPE_STEP);

                if (steps != null) {
                    Log.i(TAG, "you choose " + steps.getDescription());
                    // Initialize the player.
                    if (steps.getVideoURL().isEmpty())
                        // Load the question mark as the background image until the user answers the question.
                        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                                (getResources(), R.drawable.novideo));
                    mediaUri = Uri.parse(steps.getVideoURL());
                }
            }

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.setPlayWhenReady(startAutoPlay);

            boolean haveStartPosition = false;
            if(startWindow != C.INDEX_UNSET)
                haveStartPosition = true;

            if (haveStartPosition) {
                mExoPlayer.seekTo(startWindow, startPosition);
            }

            mExoPlayer.prepare(mediaSource,!haveStartPosition,false);


        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer != null)
            releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        // mMediaSession.setActive(false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(KEY_POSITION,startPosition);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
                startAutoPlay = mExoPlayer.getPlayWhenReady();
                startPosition = mExoPlayer.getCurrentPosition();
                startWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.stop();
            mExoPlayer.release();

            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onResume() {
        super.onResume();
       // hideSystemUi();
        if (Util.SDK_INT <= 23 || mExoPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

}
