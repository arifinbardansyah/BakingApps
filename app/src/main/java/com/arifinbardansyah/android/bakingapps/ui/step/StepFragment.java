package com.arifinbardansyah.android.bakingapps.ui.step;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arifinbardansyah.android.bakingapps.R;
import com.arifinbardansyah.android.bakingapps.model.Steps;
import com.arifinbardansyah.android.bakingapps.utility.Constants;
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
 * Created by arifinbardansyah on 8/12/17.
 */

public class StepFragment extends Fragment {

    public static final String SAVE_CURRENT_VIDEO_POSITION = "currentvideoposition";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private LinearLayout layoutNotAvailable;

    private Steps mStep;

    private long currentVideoPosition;

    public static StepFragment newInstance(Steps step) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_STEP,step);
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if (getArguments().getParcelable(Constants.EXTRA_STEP)!=null){
            mStep = getArguments().getParcelable(Constants.EXTRA_STEP);
        }

        if (savedInstanceState!=null){
            currentVideoPosition = savedInstanceState.getLong(SAVE_CURRENT_VIDEO_POSITION,0);
        }
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        TextView tvDescription = (TextView) rootView.findViewById(R.id.tv_description);
        layoutNotAvailable = (LinearLayout) rootView.findViewById(R.id.layout_video_not_available);

//        initializePlayer(mStep.getVideoURL());

        tvDescription.setText(mStep.getDescription());

        return rootView;
    }

    private void initializePlayer(String stringUrl) {

        if (mExoPlayer == null) {
            if (!stringUrl.isEmpty()) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                String userAgent = Util.getUserAgent(getContext(), "BakingApps");
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(stringUrl), new
                        DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);

                mExoPlayer.seekTo(currentVideoPosition);
            } else {
                mPlayerView.setVisibility(View.GONE);
                layoutNotAvailable.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseAndNullPlayer();
    }

    private void releasePlayer() {
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            currentVideoPosition = mExoPlayer.getCurrentPosition();
        }
    }

    private void releaseAndNullPlayer() {
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void playPlayer(){
        if (mExoPlayer!=null) {
            mExoPlayer.getPlayWhenReady();
            mExoPlayer.seekTo(currentVideoPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mExoPlayer!=null) {
            outState.putLong(SAVE_CURRENT_VIDEO_POSITION, mExoPlayer.getCurrentPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseAndNullPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(mStep.getVideoURL());
    }
}
