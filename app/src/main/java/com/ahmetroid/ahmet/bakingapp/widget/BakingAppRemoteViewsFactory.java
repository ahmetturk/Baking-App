package com.ahmetroid.ahmet.bakingapp.widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ahmetroid.ahmet.bakingapp.R;
import com.ahmetroid.ahmet.bakingapp.db.AppDatabase;
import com.ahmetroid.ahmet.bakingapp.model.Ingredient;
import com.ahmetroid.ahmet.bakingapp.model.RecipeView;
import com.ahmetroid.ahmet.bakingapp.utils.PrefsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BakingAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<String> ingredients;

    private AppDatabase appDatabase;

    public BakingAppRemoteViewsFactory(Context context) {
        this.context = context;
        appDatabase =  Room.databaseBuilder(context, AppDatabase.class, "bakingapp.db").build();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        int recipeId = PrefsUtil.getWidgetRecipeId(context);

        if (recipeId != -1) {
            ingredients = new ArrayList<>();

            RecipeView recipeView =
                    appDatabase.recipeDao().getRecipe(recipeId);


            if (recipeView != null) {
                for (Ingredient ingredient : recipeView.ingredients) {
                    ingredients.add(String.format(Locale.getDefault(), "%.1f %s %s",
                            ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
                }
            }
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredients == null) {
            return 0;
        }
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || ingredients == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widget_item_textview, ingredients.get(position));

        Intent fillInIntent = new Intent();
        rv.setOnClickFillInIntent(R.id.widget_item_textview, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
