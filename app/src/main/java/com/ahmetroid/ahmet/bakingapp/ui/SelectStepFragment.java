package com.ahmetroid.ahmet.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.databinding.FragmentSelectStepBinding;
import com.ahmetroid.ahmet.bakingapp.model.Recipe;
import com.ahmetroid.ahmet.bakingapp.utils.PrefsUtil;
import com.ahmetroid.ahmet.bakingapp.viewmodel.DetailViewModel;
import com.ahmetroid.ahmet.bakingapp.widget.BakingAppWidget;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SelectStepFragment extends Fragment {

    private static final String KEY_RECIPE_ID = "recipe_id";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FragmentSelectStepBinding mBinding;
    private DetailViewModel viewModel;
    private IngredientAdapter ingredientAdapter;
    private Recipe recipe;

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
        mBinding.setCallback(this);

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

        viewModel.getRecipes().observe(this, listResource -> {
            if (listResource.data != null) {
                recipe = listResource.data.get(recipeId);
                populateViews();
            }
        });
    }

    public void onClickNext() {
        int current = mBinding.verticalStepperView.getCurrentStep();
        mBinding.verticalStepperView.setCurrentStep(current + 1);

        viewModel.nextStepId();
    }

    public void onClickAddWidget() {
        PrefsUtil.setWidgetTitle(getContext(), recipe.getName());
        PrefsUtil.setWidgetRecipeId(getContext(), recipe.getId());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(getContext(), BakingAppWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview_ingredients);

        BakingAppWidget.updateAppWidget(getContext(), appWidgetManager, appWidgetIds);
    }

    private void populateViews() {
        getActivity().setTitle(recipe.getName());

        ingredientAdapter.setList(recipe.getIngredients());

        StepAdapter stepAdapter = new StepAdapter(
                recipe.getSteps(),
                (DetailActivity) getActivity(),
                this);
        mBinding.verticalStepperView.setStepperAdapter(stepAdapter);

        viewModel.getStepId().observe(this, stepId ->
            mBinding.verticalStepperView.setCurrentStep(stepId)
        );
    }
}
