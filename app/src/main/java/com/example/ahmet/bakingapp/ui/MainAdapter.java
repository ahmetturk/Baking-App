package com.example.ahmet.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ahmet.bakingapp.databinding.ItemMainBinding;
import com.example.ahmet.bakingapp.model.Recipe;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {

    private List<Recipe> mList;

    @NonNull
    @Override
    public MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMainBinding binding = ItemMainBinding.inflate(layoutInflater, parent, false);
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
        ItemMainBinding binding;

        public MainAdapterViewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
        }
    }
}
