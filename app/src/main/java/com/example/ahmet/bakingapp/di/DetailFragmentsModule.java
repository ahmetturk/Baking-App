package com.example.ahmet.bakingapp.di;

import com.example.ahmet.bakingapp.ui.SelectStepFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class DetailFragmentsModule {
    @ContributesAndroidInjector
    abstract SelectStepFragment contributeSelectStepFragment();
}
