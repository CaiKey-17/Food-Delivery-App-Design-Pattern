package com.example.project_android.Interface;

import com.example.project_android.Model.GeoapifyResponse;
import com.example.project_android.Model.RouteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoapifyService {
    @GET("v1/geocode/autocomplete")
    Call<GeoapifyResponse> getAutocompleteSuggestions(
            @Query("text") String query,
            @Query("apiKey") String apiKey
    );
        @GET("v1/routing")
        Call<RouteResponse> getRoute(
                @Query("waypoints") String waypoints,
                @Query("mode") String mode,
                @Query("apiKey") String apiKey);

}