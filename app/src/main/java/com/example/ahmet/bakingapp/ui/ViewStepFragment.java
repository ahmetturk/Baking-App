package com.example.ahmet.bakingapp.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.databinding.FragmentViewStepBinding;
import com.example.ahmet.bakingapp.model.Step;
import com.example.ahmet.bakingapp.viewmodel.DetailViewModel;
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

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ViewStepFragment extends Fragment {

    private static final String KEY_STEP_ID = "step_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentViewStepBinding binding;
    private DetailViewModel viewModel;
    private SimpleExoPlayer exoPlayer;

    public static ViewStepFragment forStep(int stepId) {
        ViewStepFragment fragment = new ViewStepFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_STEP_ID, stepId);
        fragment.setArguments(args);
        return fragment;
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
        final int stepId = getArguments().getInt(KEY_STEP_ID);

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);
        viewModel.setStepId(stepId);

        binding.setTotalStepCount(viewModel.getTotalStepCount());

        viewModel.getStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                populateViews(step);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    public void onClickNext() {
        viewModel.nextStepId();
    }

    public void onClickPrevious() {
        viewModel.previousStepId();
    }

    private void populateViews(Step step) {
        binding.setStep(step);

        String videoUrl = step.getVideoURL();
        if (videoUrl.isEmpty()) {
            videoUrl = step.getThumbnailURL();
        }

        if (!videoUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl));
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
                Util.getUserAgent(getContext(), "bakingapp"));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
