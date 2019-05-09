package com.ucsdextandroid1.snapmap;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rjaylward on 2019-04-27
 */
public class DataSources {

    private static final String TAG = DataSources.class.getSimpleName();

    private static DataSources instance;

    private DataApi dataApi;

    public DataSources() {

        //TODO: Added in class 5
        //Gson factory which allows us to have custom json parsing logic for specific classes
        Gson gson = new GsonBuilder()
                //type adapter is custom instructions on how to parse certain classes
                .registerTypeAdapter(ActiveUserLocationsResponse.class, new ActiveUserLocationsResponseDeserializer())
                //exclusion strategy allows us to remove things from the json that we don't want
                //so this one says when turing classes into json skip any fields with the annotation
                //@RemoveFromJson
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(RemoveFromJson.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        //TODO: Added in class 4
        this.dataApi = new Retrofit.Builder()
                .baseUrl("https://ucsd-ext-android-rja-1.firebaseio.com/apps/snap/")
                .addConverterFactory(GsonConverterFactory.create(gson)) //in class 5 we added our custom gson here
                .build()
                .create(DataApi.class);
    }


    public static DataSources getInstance() {
        if(instance == null)
            instance = new DataSources();

        return instance;
    }

    //TODO: Added in class 4
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

    //TODO: Added in class 4
    public void getAppName(Callback<String> callback) {
        dataApi.getAppName().enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    callback.onDataFetched(response.body());
                }
                else {
                    callback.onDataFetched("Failure");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onDataFetched("Failure");
            }
        });
    }

    /**
     * This method fetches all the active user locations.
     *
     * @see DataApi#getActiveUserLocations(Callback)
     *
     * @param callback either returns locations or an empty list in the case of an error
     */
    //TODO: Added in class 5
    public void getActiveUserLocations(Callback<List<UserLocationData>> callback) {
        dataApi.getActiveUserLocations().enqueue(new retrofit2.Callback<ActiveUserLocationsResponse>() {
            @Override
            public void onResponse(Call<ActiveUserLocationsResponse> call, Response<ActiveUserLocationsResponse> response) {
                if(response.isSuccessful()) {
                    callback.onDataFetched(response.body().getUserLocations());
                }
                else {
                    Log.e("DataSource", "response was not successful");
                    callback.onDataFetched(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<ActiveUserLocationsResponse> call, Throwable t) {
                Log.e("DataSource", "response failed", t);
                callback.onDataFetched(Collections.emptyList());
            }
        });
    }

    /**
     * TODO added in class 5
     * This method calls the server with an updated location for the user.
     *
     * @see DataApi#updateUser(String, UserLocationData, Callback)
     *
     * @param userId user_## for the user
     * @param locationData description of the data we want to update on the server
     * @param callback callback gives us the result or null if the call failed
     */
    public void updateUser(
            String userId,
            UserLocationData locationData,
            Callback<UserLocationData> callback
    ) {
        dataApi.updateUserLocation(userId, locationData).enqueue(new retrofit2.Callback<UserLocationData>() {
            @Override
            public void onResponse(Call<UserLocationData> call, Response<UserLocationData> response) {
                if(response.isSuccessful()) {
                    callback.onDataFetched(response.body());
                }
                else {
                    callback.onDataFetched(null);
                }
            }

            @Override
            public void onFailure(Call<UserLocationData> call, Throwable t) {
                Log.e("DataSource", "response failed", t);
                callback.onDataFetched(null);
            }
        });
    }

    public interface Callback<T> {
        void onDataFetched(T data);
    }

    //TODO: Added in class 4
    private interface DataApi {

        @GET("app_name.json")
        Call<String> getAppName();

        @GET("static_user_locations.json")
        Call<List<UserLocationData>> getStaticUserLocations();

        /**
         * TODO added in class 5
         * This call is very similar to the {@link #getStaticUserLocations()} call but we can call
         * {@link #updateUserLocation(String, UserLocationData)} to update each location in this list.
         * It needs a custom deserializer to change it from a giant json object to an array so we use
         * {@link ActiveUserLocationsResponseDeserializer}
         *
         * @return wrapper class around a list of user locations
         */
        @GET("active_user_locations.json")
        Call<ActiveUserLocationsResponse> getActiveUserLocations();

        /**
         * TODO added in class 5
         * Here we use a PATCH request to update data on the server. A patch request says take what
         * we send and update whatever is already on the server with this data. The @Path param is
         * inserted into the url path and the @Body param is the data we want the server to update
         *
         * @param userId user_## for the user
         * @param userLocationData description of the data we want to update on the server
         * @return a retrofit call that will make the api request
         */
        @PATCH("active_user_locations/{user_id}.json")
        Call<UserLocationData> updateUserLocation(
                @Path("user_id") String userId,
                @Body UserLocationData userLocationData
        );

    }

}
