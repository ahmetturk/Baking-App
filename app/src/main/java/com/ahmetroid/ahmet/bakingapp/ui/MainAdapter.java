package com.ahmetroid.ahmet.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.databinding.ItemMainBinding;
import com.ahmetroid.ahmet.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private List<Recipe> mList;

    private final ClickCallback<Recipe> mRecipeClickCallback;

    MainAdapter(ClickCallback<Recipe> clickCallback) {
        mRecipeClickCallback = clickCallback;
    }

    @NonNull
    @Override
    public MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMainBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_main, parent, false);
        binding.setCallback(mRecipeClickCallback);
        return new MainAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterViewHolder holder, int position) {
        Recipe recipe = mList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public void setList(List<Recipe> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class MainAdapterViewHolder extends RecyclerView.ViewHolder {
        final ItemMainBinding binding;

        MainAdapterViewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Recipe recipe) {
            binding.setRecipe(recipe);

            if (!TextUtils.isEmpty(recipe.getImage())) {
                Picasso.get().load(recipe.getImage())
                        .placeholder(R.drawable.placeholder)
                        .into(binding.recipeImage);
            } else {
                binding.recipeImage.setImageResource(R.drawable.placeholder);
            }
        }
    }
}
