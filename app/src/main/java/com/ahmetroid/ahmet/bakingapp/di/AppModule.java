package com.ahmetroid.ahmet.bakingapp.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ahmetroid.ahmet.bakingapp.api.LiveDataCallAdapterFactory;
import com.ahmetroid.ahmet.bakingapp.api.WebService;
import com.ahmetroid.ahmet.bakingapp.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {
    @Singleton
    @Provides
    WebService provideWebService() {
        return new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(WebService.class);
    }

    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "bakingapp.db").build();
    }
}
