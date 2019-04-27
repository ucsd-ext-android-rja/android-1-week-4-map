package com.ucsdextandroid1.snapmap;

import android.util.Log;
import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by rjaylward on 2019-04-27
 */
public class DataSources {

    private static final String TAG = DataSources.class.getSimpleName();

    private static DataSources instance;

    private DataApi dataApi;

    public DataSources() {
        this.dataApi = new Retrofit.Builder()
                .baseUrl("https://ucsd-ext-android-rja-1.firebaseio.com/apps/snap/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DataApi.class);
    }


    public static DataSources getInstance() {
        if(instance == null)
            instance = new DataSources();

        return instance;
    }

    public void getStaticUserLocations(Callback<List<UserLocationData>> callback) {
        dataApi.getStaticUserLocations().enqueue(new retrofit2.Callback<List<UserLocationData>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserLocationData>> call, @NonNull Response<List<UserLocationData>> response) {
                if(response.isSuccessful())
                    callback.onDataFetched(response.body());
                else
                    callback.onDataFetched(Collections.emptyList());
            }

            @Override
            public void onFailure(@NonNull Call<List<UserLocationData>> call, @NonNull Throwable t) {
                Log.e(TAG, "DataApi error", t);
                callback.onDataFetched(Collections.emptyList());
            }
        });
    }

    public interface Callback<T> {
        void onDataFetched(T data);
    }

    private interface DataApi {
        @GET("static_user_locations.json")
        Call<List<UserLocationData>> getStaticUserLocations();
    }
}
