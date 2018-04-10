package com.example.ahmet.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.databinding.ActivityMainBinding;
import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.utils.VerticalItemDecoration;
import com.example.ahmet.bakingapp.viewmodel.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recipesRecyclerView.setLayoutManager(layoutManager);
        binding.recipesRecyclerView.setHasFixedSize(true);
        binding.recipesRecyclerView.addItemDecoration(new VerticalItemDecoration(this));

        mAdapter = new MainAdapter();
        binding.recipesRecyclerView.setAdapter(mAdapter);

        MainViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mAdapter.setList(recipes);
            }
        });
    }
}
