package com.example.ahmet.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ahmet.bakingapp.R;
import com.example.ahmet.bakingapp.databinding.ItemStepBinding;
import com.example.ahmet.bakingapp.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private List<Step> mList;

    @NonNull
    @Override
    public StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_step, parent, false);
        return new StepAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapterViewHolder holder, int position) {
        Step step = mList.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public void setList(List<Step> list) {
        mList = list;
        notifyDataSetChanged();
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder {
        final ItemStepBinding binding;

        StepAdapterViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Step step) {
            binding.setStep(step);
        }
    }
}
