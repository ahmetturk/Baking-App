package com.example.ahmet.bakingapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.databinding.ActivityMainBinding;
import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.test.SimpleIdlingResource;
import com.example.ahmet.bakingapp.utils.MainItemDecoration;
import com.example.ahmet.bakingapp.viewmodel.MainViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.example.ahmet.bakingapp.ui.DetailActivity.INTENT_RECIPE_ID;

public class MainActivity extends AppCompatActivity implements ClickCallback<Recipe> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainAdapter mAdapter;
    private IdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recipesList.setHasFixedSize(true);
        binding.recipesList.addItemDecoration(new MainItemDecoration(this));

        mAdapter = new MainAdapter(this);
        binding.recipesList.setAdapter(mAdapter);

        MainViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, listResource -> {
            mAdapter.setList(listResource.data);
            mIdlingResource.isIdleNow();
        });

        getIdlingResource();
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
