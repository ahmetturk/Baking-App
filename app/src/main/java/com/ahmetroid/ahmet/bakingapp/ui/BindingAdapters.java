package com.ahmetroid.ahmet.bakingapp.ui;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BindingAdapters {
    @BindingAdapter("decimal_text")
    public static void setDecimalText(TextView view, double value) {
        DecimalFormat formatter = new DecimalFormat("0.#");
        view.setText(formatter.format(value));
    }

    @BindingAdapter("visible_gone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
