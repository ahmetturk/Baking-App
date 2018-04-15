package com.example.ahmet.bakingapp.ui;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BindingAdapters {
    @BindingAdapter("decimal_text")
    public static void setDecimalText(TextView view, double value) {
        DecimalFormat formatter = new DecimalFormat("0.#");
        view.setText(formatter.format(value));
    }
}