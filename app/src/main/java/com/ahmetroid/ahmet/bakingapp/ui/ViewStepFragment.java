package com.ahmetroid.ahmet.bakingapp.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.databinding.FragmentViewStepBinding;
import com.ahmetroid.ahmet.bakingapp.model.Step;
import com.ahmetroid.ahmet.bakingapp.viewmodel.DetailViewModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ViewStepFragment extends Fragment {

    private static final String CURRENT_POSITION = "current_position";
    private static final String PLAY_STATE = "play_state";
    private static final String STEP_NUMBER = "step_number";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentViewStepBinding binding;
    private DetailViewModel viewModel;
    private SimpleExoPlayer exoPlayer;
    private int stepNumber;
    private long previousPosition;
    private boolean playState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_view_step, container, false);
        binding.setCallback(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stepNumber = -1;

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CURRENT_POSITION)) {
                previousPosition = savedInstanceState.getLong(CURRENT_POSITION);
            }

            if (savedInstanceState.containsKey(PLAY_STATE)) {
                playState = savedInstanceState.getBoolean(PLAY_STATE);
            }

            if (savedInstanceState.containsKey(STEP_NUMBER)) {
                stepNumber = savedInstanceState.getInt(STEP_NUMBER);
            }
        }

        if (isPhoneAndLandscape()) {
            // hide the action bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            // activate full screen mode
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);

        viewModel.getStep().observe(this, this::populateViews);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CURRENT_POSITION, previousPosition);
        outState.putBoolean(PLAY_STATE, playState);
        outState.putInt(STEP_NUMBER, stepNumber);
    }


    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNext() {
        resetVideo();
        viewModel.nextStepId();
    }

    public void onClickPrevious() {
        resetVideo();
        viewModel.previousStepId();
    }

    private void resetVideo() {
        playState = true;
        previousPosition = 0;
        if (exoPlayer != null) {
            exoPlayer.stop();
        }
    }

    private void populateViews(Step step) {
        if (stepNumber == -1) {
            resetVideo();
            stepNumber = step.getId();
        } else if (stepNumber != step.getId()) {
            resetVideo();
            stepNumber = step.getId();
        }

        binding.setTotalStepCount(viewModel.getTotalStepCount());
        binding.setStep(step);

        String videoUrl = step.getVideoURL();
        if (!videoUrl.isEmpty()) {
            binding.imageViewPlayer.setVisibility(View.GONE);
            binding.playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));
        } else {
            binding.imageViewPlayer.setVisibility(View.VISIBLE);
            binding.playerView.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(step.getThumbnailURL())) {
                Picasso.get().load(step.getThumbnailURL())
                        .placeholder(R.drawable.placeholder)
                        .into(binding.imageViewPlayer);
            } else {
                binding.imageViewPlayer.setImageResource(R.drawable.placeholder);
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            binding.playerView.setPlayer(exoPlayer);
        }

        // Prepare the MediaSource.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);
        exoPlayer.prepare(mediaSource);

        exoPlayer.seekTo(previousPosition);
        exoPlayer.setPlayWhenReady(playState);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            previousPosition = exoPlayer.getCurrentPosition();
            playState = exoPlayer.getPlayWhenReady();

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private boolean isPhoneAndLandscape() {
        return getContext().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE
                && getContext().getResources().getConfiguration().smallestScreenWidthDp < 600;
    }
}
