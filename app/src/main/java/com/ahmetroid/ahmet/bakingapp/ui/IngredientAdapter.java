package com.ahmetroid.ahmet.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.databinding.ItemIngredientBinding;
import com.ahmetroid.ahmet.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private List<Ingredient> mList;

    @NonNull
    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false);
        return new IngredientAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapterViewHolder holder, int position) {
        Ingredient ingredient = mList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public void setList(List<Ingredient> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        final ItemIngredientBinding binding;

        IngredientAdapterViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Ingredient ingredient) {
            binding.setIngredient(ingredient);
        }
    }
}
