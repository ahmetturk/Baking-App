package com.example.ahmet.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.model.Step;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, ClickCallback<Step> {
    public static final String INTENT_RECIPE_ID = "recipe_id";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            int recipeId = getIntent().getIntExtra(INTENT_RECIPE_ID, 0);

            SelectStepFragment fragment = SelectStepFragment.forRecipe(recipeId);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onClick(Step step) {
        ViewStepFragment stepFragment = ViewStepFragment.forStep(step.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("recipe")
                .replace(R.id.fragment_container, stepFragment)
                .commit();
    }
}
