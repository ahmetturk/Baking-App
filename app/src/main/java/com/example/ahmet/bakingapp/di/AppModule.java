package com.example.ahmet.bakingapp.di;

import com.example.ahmet.bakingapp.api.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {
    @Singleton @Provides
    WebService provideWebService() {
        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }
}
