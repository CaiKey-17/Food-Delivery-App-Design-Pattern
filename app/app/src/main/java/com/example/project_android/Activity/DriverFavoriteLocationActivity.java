package com.example.project_android.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_android.Interface.GeoapifyService;
import com.example.project_android.Model.GeoapifyResponse;
import com.example.project_android.R;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DriverFavoriteLocationActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteLocation;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_favorite_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        autoCompleteLocation = findViewById(R.id.autoCompleteLocation);

        setupAutoComplete();
    }

    private void setupAutoComplete() {
        autoCompleteLocation.setThreshold(1);

        autoCompleteLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    getGeoapifySuggestions(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getGeoapifySuggestions(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.geoapify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoapifyService geoapifyService = retrofit.create(GeoapifyService.class);
        String apiKey = "f9a0ed880c5a42d3a2fc9cd99c600b69";

        Call<GeoapifyResponse> call = geoapifyService.getAutocompleteSuggestions(query, apiKey);
        call.enqueue(new Callback<GeoapifyResponse>() {
            @Override
            public void onResponse(Call<GeoapifyResponse> call, Response<GeoapifyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> suggestions = new ArrayList<>();
                    Log.d("GeoapifyAPI", "Response: " + response.body().toString());

                    for (GeoapifyResponse.Feature feature : response.body().getFeatures()) {
                        String fullAddress = feature.getProperties().getFormatted();
                        suggestions.add(fullAddress);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DriverFavoriteLocationActivity.this, android.R.layout.simple_dropdown_item_1line, suggestions);
                    autoCompleteLocation.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("GeoapifyAPI", "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<GeoapifyResponse> call, Throwable t) {
                Log.e("GeoapifyAPI", "Error getting suggestions: ", t);
            }
        });
    }


}