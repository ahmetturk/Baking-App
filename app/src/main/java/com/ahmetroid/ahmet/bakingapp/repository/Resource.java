package com.ahmetroid.ahmet.bakingapp.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.ahmetroid.ahmet.bakingapp.repository.Status.ERROR;
import static com.ahmetroid.ahmet.bakingapp.repository.Status.LOADING;
import static com.ahmetroid.ahmet.bakingapp.repository.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
