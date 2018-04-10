package com.example.ahmet.bakingapp.api;

import com.example.ahmet.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface  WebService {
    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();
}
