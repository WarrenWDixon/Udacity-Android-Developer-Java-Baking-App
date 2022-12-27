package com.android.example.bakingapp15;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetail extends AppCompatActivity {

    private TextView mStepDescription;
    private TextView mNoVideoAvailable;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Integer mRecipeIndex;
    private Integer mStepIndex;
    private Integer mNumberSteps;
    private Button mPreviousButton;
    private Button mNextButton;
    private Long   mPausePosition = 0L;
    private Boolean mPlayState;

    private final Integer PHONE_WIDTH = 600;
    private final String KEY_EXO_POSITION = "POSITION";
    private final String KEY_EXO_PLAYREADY = "PLAYSTATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation;
        orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.d("WWD", "in StepDetail.java onCreate");
        Intent intent = getIntent();
        if (intent.hasExtra("RECIPE_INDEX")) {
            mRecipeIndex = intent.getIntExtra("RECIPE_INDEX", 0);
        }

        if (intent.hasExtra("STEP_INDEX")) {
            mStepIndex = intent.getIntExtra("STEP_INDEX", 0);
        }

        if (savedInstanceState != null) {
            mPausePosition = savedInstanceState.getLong(KEY_EXO_POSITION);
            mPlayState     = savedInstanceState.getBoolean(KEY_EXO_PLAYREADY);
        }
        else {
            mPausePosition = 0L;
            mPlayState = true;
        }

        if ((orientation == Configuration.ORIENTATION_LANDSCAPE) && (width < PHONE_WIDTH)) {
            setContentView(R.layout.phone_detail_landscape);
            mPlayerView = (SimpleExoPlayerView) findViewById(R.id.phone_playerView);
            mNoVideoAvailable = (TextView) findViewById(R.id.phone_no_video_available);
            displayVideo();
            Log.d("WWD", "in StepDetail.java onCreate LANDSCAPE");

        } else {
            setContentView(R.layout.step_detail_view);
            Log.d("WWD", "in StepDetail.java onCreate PORTRAIT");
            mNoVideoAvailable = (TextView) findViewById(R.id.no_video_available);
            mPlayerView = (SimpleExoPlayerView) findViewById(R.id.playerView);
            mPreviousButton = (Button) findViewById(R.id.prev_button);
            mNextButton = (Button) findViewById(R.id.next_button);
            mStepDescription = (TextView) findViewById(R.id.description);

            mNumberSteps = JsonUtil.getNumberOfSteps(mRecipeIndex);
            displayVideoAndText();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
        mExoPlayer = null;
        Log.d("WWD", "in StepDetail.java onStop");
    }

    @Override
    public void onPause() {
        if (mExoPlayer != null) {
            mPausePosition = mExoPlayer.getCurrentPosition();
            mPlayState = (mExoPlayer.getPlayWhenReady());
        }
        super.onPause();
        Log.d("WWD", "in StepDetail.java onPause");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_EXO_POSITION, mPausePosition);
        outState.putBoolean(KEY_EXO_PLAYREADY, mPlayState);
    }



    public void prevButton(View view) {
        if (mStepIndex > 0) {
            mStepIndex--;
        }
        displayVideoAndText();
    }

    public void nextButton(View view) {
        if (mStepIndex < (mNumberSteps - 1)) {
            mStepIndex++;
        }
        displayVideoAndText();
    }

    public void displayVideo() {
        String thumbnail = JsonUtil.getThumbnailURL(mRecipeIndex, mStepIndex);
        Uri uri = Uri.parse(JsonUtil.getThumbnailURL(mRecipeIndex,mStepIndex));
        Log.d("WWD", "in StepDetail.java disaplayVideo stepIndex is" + mStepIndex);
        if (thumbnail.length() == 0) {
            mPlayerView.setVisibility(View.INVISIBLE);
            mNoVideoAvailable.setVisibility(View.VISIBLE);
        } else {
            mNoVideoAvailable.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPausePosition);
            mExoPlayer.setPlayWhenReady(mPlayState);
        }
    }

    public void displayVideoAndText() {
        Log.d("WWD", "in StepDetail.java disaplayVideoAndText stepIndex is" + mStepIndex);
        if (mStepIndex == 0) {
            mPreviousButton.setEnabled(false);
            mPreviousButton.setAlpha(.5F);
        } else {
            mPreviousButton.setEnabled(true);
            mPreviousButton.setAlpha(1.0F);
        }

        if (mStepIndex >= (mNumberSteps - 1)) {
            mNextButton.setEnabled(false);
            mNextButton.setAlpha(.5F);
        } else {
            mNextButton.setEnabled(true);
            mNextButton.setAlpha(1.0F);
        }

        mStepDescription.setText(JsonUtil.getStepDescription(mRecipeIndex,mStepIndex));

        String description = JsonUtil.getStepDescription(mRecipeIndex,mStepIndex);
        String thumbnail = JsonUtil.getThumbnailURL(mRecipeIndex, mStepIndex);

        Uri uri = Uri.parse(JsonUtil.getThumbnailURL(mRecipeIndex,mStepIndex));

        if (thumbnail.length() == 0) {
            mPlayerView.setVisibility(View.INVISIBLE);
            mNoVideoAvailable.setVisibility(View.VISIBLE);
        } else {
            mNoVideoAvailable.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPausePosition);
            mExoPlayer.setPlayWhenReady(mPlayState);
        }
    }
}

