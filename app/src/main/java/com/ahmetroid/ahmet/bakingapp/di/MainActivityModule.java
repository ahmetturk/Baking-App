package com.ahmetroid.ahmet.bakingapp.di;

import com.ahmetroid.ahmet.bakingapp.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
