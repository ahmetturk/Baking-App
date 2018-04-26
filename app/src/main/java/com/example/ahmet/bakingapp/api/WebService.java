package com.example.ahmet.bakingapp.api;

import android.arch.lifecycle.LiveData;

import com.example.ahmet.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.http.GET;

public interface WebService {
    @GET("baking.json")
    LiveData<ApiResponse<List<Recipe>>> getRecipes();
}
