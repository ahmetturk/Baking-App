package com.example.ahmet.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.model.Step;
import com.example.ahmet.bakingapp.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {
    private final Repository repository;
    private LiveData<List<Recipe>> recipes;
    private MutableLiveData<Step> step;

    private int recipeId;
    private int stepId;

    @Inject
    DetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Step> getStep() {
        return step;
    }

    public LiveData<List<Recipe>> getRecipes() {
        if (recipes == null) {
            recipes = repository.getRecipes();
        }

        return recipes;
    }

    public void setRecipeId(int recipeId){
        this.recipeId = recipeId;
    }

    public void setStepId(int stepId){
        this.stepId = stepId;
        setStepValue();
    }

    public int getTotalStepCount(){
        return recipes.getValue().get(recipeId).getSteps().size();
    }

    public void nextStepId() {
        stepId++;
        setStepValue();
    }

    public void previousStepId() {
        stepId--;
        setStepValue();
    }

    private void setStepValue() {
        if (step == null) {
            step = new MutableLiveData<>();
        }
        step.setValue(getRecipes().getValue().get(recipeId).getSteps().get(stepId));
    }
}
