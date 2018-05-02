package com.ahmetroid.ahmet.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ahmetroid.ahmet.bakingapp.AppExecutors;
import com.ahmetroid.ahmet.bakingapp.api.ApiResponse;
import com.ahmetroid.ahmet.bakingapp.api.WebService;
import com.ahmetroid.ahmet.bakingapp.db.AppDatabase;
import com.ahmetroid.ahmet.bakingapp.model.Recipe;
import com.ahmetroid.ahmet.bakingapp.model.RecipeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {

    private final AppExecutors appExecutors;
    private final WebService webService;
    private final AppDatabase appDatabase;

    private MediatorLiveData<List<Recipe>> recipesLiveData;

    @Inject
    Repository(AppExecutors appExecutors, WebService webService, AppDatabase appDatabase) {
        this.appExecutors = appExecutors;
        this.webService = webService;
        this.appDatabase = appDatabase;
    }

    public LiveData<Resource<List<Recipe>>> loadRecipes() {
        return new NetworkBoundResource<List<Recipe>, List<Recipe>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<Recipe> item) {
                appDatabase.recipeDao().insertRecipes(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return data == null || data.size() == 0;
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                LiveData<List<RecipeView>> recipeViewsLiveData = appDatabase.recipeDao().getRecipes();
                recipesLiveData = new MediatorLiveData<>();
                recipesLiveData.addSource(recipeViewsLiveData, recipeViews -> {
                    List<Recipe> recipes = new ArrayList<>();

                    for (RecipeView recipeView : recipeViews) {
                        recipeView.recipe.setIngredients(recipeView.ingredients);
                        recipeView.recipe.setSteps(recipeView.steps);
                        recipes.add(recipeView.recipe);
                    }

                    recipesLiveData.setValue(recipes);
                });
                return recipesLiveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {
                return webService.getRecipes();
            }
        }.asLiveData();
    }
}
