package com.ahmetroid.ahmet.bakingapp.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.databinding.ItemVerticalStepperBinding;
import com.ahmetroid.ahmet.bakingapp.model.Step;

import java.util.List;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;

public class StepAdapter implements IStepperAdapter {

    private final List<Step> steps;
    private final DetailActivity activity;
    private final SelectStepFragment fragment;

    public StepAdapter(List<Step> steps, DetailActivity activity, SelectStepFragment fragment) {
        this.steps = steps;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CharSequence getTitle(int i) {
        return steps.get(i).getShortDescription();
    }

    @Nullable
    @Override
    public CharSequence getSummary(int i) {
        return steps.get(i).getDescription();
    }

    @Override
    public int size() {
        return steps.size();
    }

    @Override
    public View onCreateCustomView(int i, Context context, VerticalStepperItemView parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemVerticalStepperBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_vertical_stepper, parent, false);

        binding.setWatchCallback(activity);
        binding.setNextCallback(fragment);
        binding.setStep(steps.get(i));
        binding.setTotalStepCount(steps.size());

        return binding.getRoot();
    }

    @Override
    public void onShow(int i) {

    }

    @Override
    public void onHide(int i) {

    }
}
