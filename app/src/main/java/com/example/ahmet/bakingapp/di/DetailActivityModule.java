package com.example.ahmet.bakingapp.di;

import com.example.ahmet.bakingapp.ui.DetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailActivityModule {
    @ContributesAndroidInjector(modules = DetailFragmentsModule.class)
    abstract DetailActivity contributeDetailActivity();
}
