package com.ahmetroid.ahmet.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ahmetroid.ahmet.bakingapp.model.Ingredient;
import com.ahmetroid.ahmet.bakingapp.model.Recipe;
import com.ahmetroid.ahmet.bakingapp.model.Step;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    abstract public RecipeDao recipeDao();
}
