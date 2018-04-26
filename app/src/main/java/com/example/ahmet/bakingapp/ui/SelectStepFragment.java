package com.example.ahmet.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.databinding.FragmentSelectStepBinding;
import com.example.ahmet.bakingapp.model.Recipe;
import com.example.ahmet.bakingapp.repository.Resource;
import com.example.ahmet.bakingapp.viewmodel.DetailViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SelectStepFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "recipe_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentSelectStepBinding mBinding;
    private DetailViewModel viewModel;
    private IngredientAdapter ingredientAdapter;

    public static SelectStepFragment forRecipe(int recipeId) {
        SelectStepFragment fragment = new SelectStepFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_select_step, container, false);

        mBinding.ingredientsList.setHasFixedSize(true);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int recipeId = getArguments().getInt(KEY_RECIPE_ID) - 1;

        ingredientAdapter = new IngredientAdapter();
        mBinding.ingredientsList.setAdapter(ingredientAdapter);

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);
        viewModel.setRecipeId(recipeId);

        viewModel.getRecipes().observe(this, new Observer<Resource<List<Recipe>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Recipe>> listResource) {
                if (listResource.data != null) {
                    populateViews(listResource.data.get(recipeId));
                }
            }
        });
    }

    public void onClickNext() {
        int current = mBinding.verticalStepperView.getCurrentStep();
        mBinding.verticalStepperView.setCurrentStep(current + 1);

        viewModel.setStepId(current + 1);
    }

    private void populateViews(Recipe recipe) {
        getActivity().setTitle(recipe.getName());

        ingredientAdapter.setList(recipe.getIngredients());

        StepAdapter stepAdapter = new StepAdapter(
                recipe.getSteps(),
                (DetailActivity) getActivity(),
                this);
        mBinding.verticalStepperView.setStepperAdapter(stepAdapter);

        viewModel.getStepId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer stepId) {
                mBinding.verticalStepperView.setCurrentStep(stepId);
            }
        });
    }
}
