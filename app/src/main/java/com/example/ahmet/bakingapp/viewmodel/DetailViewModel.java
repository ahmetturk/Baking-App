package com.example.ahmet.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.model.Step;
import com.example.ahmet.bakingapp.repository.Repository;
import com.example.ahmet.bakingapp.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {
    private final Repository repository;
    private LiveData<Resource<List<Recipe>>> recipes;
    private MutableLiveData<Step> step;

    private int recipeId;
    private MutableLiveData<Integer> stepId;

    @Inject
    DetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Step> getStep() {
        if (step == null) {
            setStepValue();
        }
        return step;
    }

    public LiveData<Resource<List<Recipe>>> getRecipes() {
        if (recipes == null) {
            recipes = repository.loadRecipes();
        }

        return recipes;
    }

    public LiveData<Integer> getStepId() {
        if (stepId == null) {
            stepId = new MutableLiveData<>();
            stepId.setValue(0);
        }

        return stepId;
    }

    public void setStepId(int id) {
        if (stepId == null) {
            stepId = new MutableLiveData<>();
        }

        this.stepId.setValue(id);
        setStepValue();
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTotalStepCount() {
        return recipes.getValue().data.get(recipeId).getSteps().size();
    }

    public void nextStepId() {
        stepId.setValue(getStepId().getValue() + 1);
        setStepValue();
    }

    public void previousStepId() {
        stepId.setValue(getStepId().getValue() - 1);
        setStepValue();
    }

    private void setStepValue() {
        if (step == null) {
            step = new MutableLiveData<>();
        }
        step.setValue(getRecipes().getValue().data.get(recipeId).getSteps().get(getStepId().getValue()));
    }
}
