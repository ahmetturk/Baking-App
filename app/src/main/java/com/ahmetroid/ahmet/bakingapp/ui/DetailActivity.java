package com.ahmetroid.ahmet.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ahmetroid.ahmet.bakingapp.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {
    public static final String INTENT_RECIPE_ID = "recipe_id";

    private boolean isTablet;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            isTablet = findViewById(R.id.view_fragment_container) != null;

            int recipeId = getIntent().getIntExtra(INTENT_RECIPE_ID, 0);

            SelectStepFragment selectStepFragment = SelectStepFragment.forRecipe(recipeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.select_fragment_container, selectStepFragment)
                    .commit();

            if (isTablet) {
                ViewStepFragment viewStepFragment = new ViewStepFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.view_fragment_container, viewStepFragment)
                        .commit();
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public void onClickWatch() {
        if (!isTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.select_fragment_container, new ViewStepFragment())
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
