package com.example.ahmet.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {
    private final Repository repository;
    private LiveData<List<Recipe>> recipes;

    @Inject
    DetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (recipes == null) {
            recipes = repository.getRecipes();
        }

        return recipes;
    }
}
