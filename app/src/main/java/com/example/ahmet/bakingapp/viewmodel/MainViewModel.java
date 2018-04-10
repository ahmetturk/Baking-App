package com.example.ahmet.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ahmet.bakingapp.repository.Repository;
import com.example.ahmet.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipes;
    private Repository repository;

    @Inject
    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (recipes == null) {
            recipes = repository.getRecipes();
        }

        return recipes;
    }
}
