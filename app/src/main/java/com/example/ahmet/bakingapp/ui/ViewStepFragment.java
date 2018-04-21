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
import com.example.ahmet.bakingapp.model.Recipe;
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

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ViewStepFragment extends Fragment {

    private static final String KEY_STEP_ID = "step_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentViewStepBinding mBinding;
    private SimpleExoPlayer mExoPlayer;

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
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_view_step, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int stepId = getArguments().getInt(KEY_STEP_ID);

        DetailViewModel viewModel =
                ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);

        final int recipeId = viewModel.getRecipeId();

        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                populateViews(recipes.get(recipeId).getSteps().get(stepId));
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void populateViews(Step step) {
        String videoUrl = step.getVideoURL();
        if (videoUrl.isEmpty()) {
            videoUrl = step.getThumbnailURL();
        }

        if (!videoUrl.isEmpty()) {
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mBinding.playerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "bakingapp"));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
