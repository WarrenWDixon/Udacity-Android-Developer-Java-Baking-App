package com.android.example.bakingapp15;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static TextView mNoVideoAvailable;
    private static SimpleExoPlayer mExoPlayer;
    private static SimpleExoPlayerView mPlayerView;
    static private Integer mRecipeIndex;
    static private Integer mStepIndex = 0;
    private static Context mContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
        Log.d("WWD", "in VideoFragment constructor");
    }

    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("WWD", "VideoFragment onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String thumbnail = JsonUtil.getThumbnailURL(mRecipeIndex, mStepIndex);
        if (thumbnail.length() > 0) {
            Log.d("WWD", "the thumbnail is " + thumbnail);
        }
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        mNoVideoAvailable = (TextView) view.findViewById(R.id.fragment_no_video_available);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.fragment_playerView);

        Uri uri = Uri.parse(JsonUtil.getThumbnailURL(mRecipeIndex,mStepIndex));
        if (thumbnail.length() > 0) {
            Log.d("WWD", "the thumbnail is " + thumbnail);
        }
        if (thumbnail.length() == 0) {
            mPlayerView.setVisibility(View.INVISIBLE);
            mNoVideoAvailable.setVisibility(View.VISIBLE);
        } else {
            mNoVideoAvailable.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(mContext, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        return view;
    }

    public static void setReceipeIndex(Integer index){
        mRecipeIndex = index;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void setNewUri(Integer stepIndex) {
        mStepIndex = stepIndex;

        Uri uri = Uri.parse(JsonUtil.getThumbnailURL(mRecipeIndex,mStepIndex));
        String thumbnail = JsonUtil.getThumbnailURL(mRecipeIndex, mStepIndex);
        if (thumbnail.length() > 0) {
            Log.d("WWD", "the thumbnail is " + thumbnail);
        }
        if (thumbnail.length() == 0) {
            mPlayerView.setVisibility(View.INVISIBLE);
            mNoVideoAvailable.setVisibility(View.VISIBLE);
        } else {
            mNoVideoAvailable.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(mContext, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}