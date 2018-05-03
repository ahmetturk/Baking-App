package com.ahmetroid.ahmet.bakingapp.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.ahmetroid.ahmet.bakingapp.R;

public final class PrefsUtil {
    private PrefsUtil() {
    }

    public static void setWidgetRecipeId(Context context, int recipeId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(context.getString(R.string.pref_widget_key), recipeId)
                .apply();
    }

    public static int getWidgetRecipeId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.pref_widget_key), -1);
    }

    public static void setWidgetTitle(Context context, String title) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(context.getString(R.string.pref_title_key), title)
                .apply();
    }

    public static String getWidgetTitle(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_title_key),
                        context.getString(R.string.widget_text));
    }
}
