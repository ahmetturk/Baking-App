package com.example.ahmet.bakingapp.di;

import com.example.ahmet.bakingapp.ui.SelectStepFragment;
import com.example.ahmet.bakingapp.ui.ViewStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailFragmentsModule {
    @ContributesAndroidInjector
    abstract SelectStepFragment contributeSelectStepFragment();

    @ContributesAndroidInjector
    abstract ViewStepFragment contributeViewStepFragment();
}
