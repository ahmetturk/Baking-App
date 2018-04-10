package com.example.ahmet.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.ahmet.bakingapp.api.WebService;
import com.example.ahmet.bakingapp.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {

    private final WebService webService;

    @Inject
    Repository(WebService webService) {
        this.webService = webService;
    }

    public LiveData<List<Recipe>> getRecipes() {

        final MutableLiveData<List<Recipe>> data = new MutableLiveData<>();

        webService.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                data.setValue(response.body());
                Log.d("ahmetnormal", response.message());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // TODO Failure
                Log.d("ahmethata", t.getMessage());
            }
        });

        return data;
    }

}
