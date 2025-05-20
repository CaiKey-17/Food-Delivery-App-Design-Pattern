package com.example.project_android.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_android.Interface.GeoapifyService;
import com.example.project_android.Model.RouteResponse;
import com.example.project_android.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private LatLng mStartLocation = new LatLng(10.762622, 106.660172);
    private LatLng mEndLocation = new LatLng(10.823099, 106.629664);
    private Polyline currentPolyline;
    private boolean bottomShow = true;
    private final String geoapifyApiKey = "a6c10ae7ee0d40e3aff65fa09068f5da";


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.getUiSettings().setRotateGesturesEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);

            updateRoute();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void updateRoute() {
        if (mStartLocation != null && mEndLocation != null) {
            mMap.clear();
            addMarkers();
            fetchRoute();
        }
    }

    private void addMarkers() {
        mMap.addMarker(new MarkerOptions().position(mStartLocation).title("Điểm xuất phát"));
        mMap.addMarker(new MarkerOptions().position(mEndLocation).title("Điểm đến"));
    }

    private void fetchRoute() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.geoapify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoapifyService geoapifyService = retrofit.create(GeoapifyService.class);
        String waypoints = String.format("%s,%s|%s,%s", mStartLocation.latitude, mStartLocation.longitude, mEndLocation.latitude, mEndLocation.longitude);

        Call<RouteResponse> call = geoapifyService.getRoute(waypoints, "drive", geoapifyApiKey);
        call.enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LatLng> routePoints = new ArrayList<>();
                    if (response.body().getFeatures() != null) {
                        for (List<List<Double>> point : response.body().getFeatures().get(0).getGeometry().getCoordinates()) {
                            for (List<Double> coords : point) {
                                routePoints.add(new LatLng(coords.get(1), coords.get(0)));
                            }
                        }
                        if (!routePoints.isEmpty()) {
                            drawRoute(routePoints);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy tuyến đường", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Lấy tuyến đường thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Không thể kết nối tới API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void drawRoute(List<LatLng> routePoints) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        if (routePoints.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .color(ContextCompat.getColor(getActivity(), R.color.purple_500))
                    .width(12)
                    .addAll(routePoints);
            currentPolyline = mMap.addPolyline(polylineOptions);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng point : routePoints) {
                builder.include(point);
            }
            LatLngBounds bounds = builder.build();

            int padding = 113;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        }
    }
}
