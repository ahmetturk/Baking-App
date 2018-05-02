package com.ahmetroid.ahmet.bakingapp.di;

import com.ahmetroid.ahmet.bakingapp.ui.SelectStepFragment;
import com.ahmetroid.ahmet.bakingapp.ui.ViewStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailFragmentsModule {
    @ContributesAndroidInjector
    abstract SelectStepFragment contributeSelectStepFragment();

    @ContributesAndroidInjector
    abstract ViewStepFragment contributeViewStepFragment();
}
