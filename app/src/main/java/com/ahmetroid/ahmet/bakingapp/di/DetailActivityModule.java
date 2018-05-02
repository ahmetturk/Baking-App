package com.ahmetroid.ahmet.bakingapp.di;

import com.ahmetroid.ahmet.bakingapp.ui.DetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailActivityModule {
    @ContributesAndroidInjector(modules = DetailFragmentsModule.class)
    abstract DetailActivity contributeDetailActivity();
}
