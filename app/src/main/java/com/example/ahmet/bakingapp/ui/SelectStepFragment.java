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
import com.example.ahmet.bakingapp.model.Step;
import com.example.ahmet.bakingapp.viewmodel.DetailViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SelectStepFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "recipe_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentSelectStepBinding mBinding;

    private IngredientAdapter ingredientAdapter;
    private StepAdapter stepAdapter;

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
        mBinding.ingredientsList.setNestedScrollingEnabled(false);

        mBinding.stepsList.setHasFixedSize(true);
        mBinding.stepsList.setNestedScrollingEnabled(false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int recipeId = getArguments().getInt(KEY_RECIPE_ID) - 1;

        ingredientAdapter = new IngredientAdapter();
        mBinding.ingredientsList.setAdapter(ingredientAdapter);

        stepAdapter = new StepAdapter((ClickCallback<Step>) getActivity());
        mBinding.stepsList.setAdapter(stepAdapter);

        DetailViewModel viewModel =
                ViewModelProviders.of(getActivity(), viewModelFactory).get(DetailViewModel.class);

        viewModel.setRecipeId(recipeId);

        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) {
                    populateViews(recipes.get(recipeId));
                }
            }
        });
    }

    private void populateViews(Recipe recipe) {
        getActivity().setTitle(recipe.getName());

        ingredientAdapter.setList(recipe.getIngredients());
        stepAdapter.setList(recipe.getSteps());
    }
}
