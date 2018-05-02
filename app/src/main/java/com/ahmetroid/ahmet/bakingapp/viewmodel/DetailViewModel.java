package com.ahmetroid.ahmet.bakingapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ahmetroid.ahmet.bakingapp.model.Recipe;
import com.ahmetroid.ahmet.bakingapp.model.Step;
import com.ahmetroid.ahmet.bakingapp.repository.Repository;
import com.ahmetroid.ahmet.bakingapp.repository.Resource;

import java.util.List;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {
    private final Repository repository;
    private LiveData<Resource<List<Recipe>>> recipes;
    private MediatorLiveData<Step> step;

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

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getTotalStepCount() {
        return recipes.getValue().data.get(recipeId).getSteps().size();
    }

    public void nextStepId() {
        stepId.setValue(getStepId().getValue() + 1);
    }

    public void previousStepId() {
        stepId.setValue(getStepId().getValue() - 1);
    }

    private void setStepValue() {
        if (step == null) {
            step = new MediatorLiveData<>();
        }
        LiveData<Resource<List<Recipe>>> resourceLiveData = getRecipes();
        LiveData<Integer> stepIdLiveData = getStepId();

        step.addSource(resourceLiveData, resources -> {
            if (resources.data != null) {
                step.setValue(resources.data.get(recipeId).getSteps().get(getStepId().getValue()));
            }
        });

        step.addSource(stepIdLiveData, stepId -> {
            Resource<List<Recipe>> resources = getRecipes().getValue();
            if (stepId != null && resources.data != null) {
                step.setValue(resources.data.get(recipeId).getSteps().get(stepId));
            }
        });
    }
}
