package com.example.project_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_android.Fragment.InfoBottomSheetDialogFragment.ResetMapListener;
import com.example.project_android.Interface.ActiveStatusViewModel;
import com.example.project_android.Interface.CustomerStatusViewModel;
import com.example.project_android.Interface.GeoapifyService;
import com.example.project_android.Interface.RunningStatusViewModel;
import com.example.project_android.Model.RouteResponse;
import com.example.project_android.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsDirectionFragment extends Fragment implements ResetMapListener {
    private ActiveStatusViewModel viewModel;
    private RunningStatusViewModel viewModel1;
    private CustomerStatusViewModel viewModel2;

    private GoogleMap mMap;
    private LatLng mStartLocation;
    private LatLng mStrartStreetLocation;
    private LatLng mEndLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private final String geoapifyApiKey = "a6c10ae7ee0d40e3aff65fa09068f5da";
    private boolean isFirstLocationUpdate = true;
    private Polyline currentPolyline;
    private Marker arrowMarker;
    private boolean bottomShow = true;
    private DatabaseReference locationRef;
    private final List<Marker> currentMarkers = new ArrayList<>();
    private boolean active = false;
    private Map<String, LatLng> index_customer = new HashMap<>();
    private boolean running = false;
    private String order = "";


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("orders", MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (sharedPreferences.contains("orderId")) {
                String orderId = sharedPreferences.getString("orderId", null);
                if (orderId != null) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders").child(orderId);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                editor.remove("orderId");
                                editor.apply();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("FirebaseError", "Không thể kiểm tra orderId: " + databaseError.getMessage());
                        }
                    });
                }
            }

            mMap = googleMap;
            checkLocationPermissions();
            startLocationUpdates();

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (!marker.getTitle().equals("Vị trí của tôi")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Toast.makeText(getActivity(), marker.getTitle() + "", Toast.LENGTH_SHORT).show();

                        if (sharedPreferences.contains("orderId")) {
                            editor.remove("orderId");
                            editor.remove("restaurantName");
                            editor.remove("restaurantAddress");
                            editor.remove("billName");
                            editor.remove("paymentStatus");
                            editor.remove("totalPrice");
                            editor.remove("customerName");
                            editor.remove("customerPhone");
                            editor.remove("customerAddress");
                            editor.remove("customerOrder");
                            editor.remove("customerTotal");
                            editor.remove("customerPayment");

                        }

                        editor.putString("orderId", marker.getTitle());
                        editor.apply();

                        order = sharedPreferences.getString("orderId", "");

                        mMap.clear();

                        mEndLocation = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                        updateRoute();

                        return true;
                    }
                    return false;
                }
            });
        }
    };

    private void listenForOrders() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!active) return;
                if (mMap == null) return;

                mMap.clear();

                if (mStartLocation != null) {
                    LatLng rightNow = new LatLng(mStartLocation.latitude, mStartLocation.longitude);
                    Marker right = mMap.addMarker(new MarkerOptions()
                            .position(rightNow)
                            .title("Vị trí của tôi"));
                    currentMarkers.add(right);

                }
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Double latitudeRes = orderSnapshot.child("latitude_res").getValue(Double.class);
                    Double longitudeRes = orderSnapshot.child("longitude_res").getValue(Double.class);
                    Double latitudeCus = orderSnapshot.child("latitude_cus").getValue(Double.class);
                    Double longitudeCus = orderSnapshot.child("longitude_cus").getValue(Double.class);
                    String status = orderSnapshot.child("status").getValue(String.class);

                    if (!status.equals("0")) continue;

                    if (latitudeRes != null && longitudeRes != null && latitudeCus != null && longitudeCus != null) {
                        LatLng restaurantLocation = new LatLng(latitudeRes, longitudeRes);
                        Marker restaurantMarker = mMap.addMarker(new MarkerOptions()
                                .position(restaurantLocation)
                                .title(orderSnapshot.getKey()));
                        currentMarkers.add(restaurantMarker);

                        index_customer.put(orderSnapshot.getKey(), new LatLng(latitudeCus, longitudeCus));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Lỗi khi lấy dữ liệu từ Firebase: " + error.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        viewModel = new ViewModelProvider(requireActivity()).get(ActiveStatusViewModel.class);

        viewModel.getActiveStatus().observe(getViewLifecycleOwner(), isActive -> {
            if (isActive) {
                listenForOrders();
                active = true;
            } else {
                active = false;
                if (currentMarkers != null) {
                    currentMarkers.clear();
                }
                if (mMap != null) {
                    mMap.clear();
                }
                if (mStartLocation != null) {
                    LatLng rightNow = new LatLng(mStartLocation.latitude, mStartLocation.longitude);
                    Marker right = mMap.addMarker(new MarkerOptions()
                            .position(rightNow)
                            .title("Vị trí của tôi"));
                    currentMarkers.add(right);
                }

            }
        });
        viewModel1 = new ViewModelProvider(requireActivity()).get(RunningStatusViewModel.class);

        viewModel1.getRunningStatus().observe(getViewLifecycleOwner(), isRunning -> {
            if (isRunning) {
                running = true;
                active = false;

                if (currentMarkers != null) {
                    currentMarkers.clear();
                }
                if (mMap != null) {
                    mMap.clear();
                }
                fetchRoute();
            } else if (running) {
                running = false;
                active = true;
            }
        });


        viewModel2 = new ViewModelProvider(requireActivity()).get(CustomerStatusViewModel.class);

        viewModel2.getCustomerStatus().observe(getViewLifecycleOwner(), isCustomer -> {
            if (isCustomer) {
                mEndLocation = index_customer.get(order);
                updateRoute();
            } else if (mEndLocation != null) {
                mEndLocation = null;
                active = true;
                listenForOrders();
                if (currentMarkers != null) {
                    currentMarkers.clear();
                }
                if (mMap != null) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(mStartLocation).title("Vị trí của tôi"));
                }
            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        locationRef = database.getReference("drivermap").child(userId + "");

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

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(15000)
                .setFastestInterval(10000);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location newLocation = locationResult.getLastLocation();
                    if (newLocation != null) {
                        if (mStartLocation == null || calculateDistance(mStartLocation, newLocation) > 10) {
                            mStartLocation = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());

                            if (mMap != null && isFirstLocationUpdate) {
                                isFirstLocationUpdate = false;
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mStartLocation, 15));
                            }

                            updateRoute();
                            updateLocationOnFirebase(newLocation);
                        }
                    }
                }
            }
        }, Looper.getMainLooper());
    }

    private double calculateDistance(LatLng startLocation, Location newLocation) {
        Location startLoc = new Location("");
        startLoc.setLatitude(startLocation.latitude);
        startLoc.setLongitude(startLocation.longitude);
        return startLoc.distanceTo(newLocation);
    }

    private void updateRoute() {
        if (mStartLocation != null && mEndLocation != null) {
            mMap.clear();
            addMarkers();
            fetchRoute();
        }
    }

    private void addMarkers() {
        if (mEndLocation != null) {
            mMap.addMarker(new MarkerOptions().position(mEndLocation).title("Vị trí đích"));
        }
    }

    private void fetchRoute() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.geoapify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeoapifyService geoapifyService = retrofit.create(GeoapifyService.class);
        String waypoints = String.format("%s,%s|%s,%s", mStartLocation.latitude, mStartLocation.longitude, mEndLocation.latitude, mEndLocation.longitude);

        Call<RouteResponse> call = geoapifyService.getRoute(waypoints, "bicycle", geoapifyApiKey);
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
                            mStrartStreetLocation = routePoints.get(0);
                            drawRoute(routePoints);

                            double distance = response.body().getFeatures().get(0).getProperties().getDistance() / 1000; // Chuyển đổi từ mét sang km
                            double duration = response.body().getFeatures().get(0).getProperties().getTime() / 60; // Dự đoán thời gian (giây)

                            if (distance != 0.0 && duration != 0.0) {
                                showBottomSheet(String.format("%.2f km", distance), String.format("%.2f phút", duration));
                            }

                            Log.e("route", String.valueOf(distance));
                            Log.e("route", String.valueOf(duration));
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
                Log.e("MapsFragment", "onFailure: " + t.getMessage());
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
                    .width(10)
                    .addAll(routePoints);
            currentPolyline = mMap.addPolyline(polylineOptions);

            rotateArrowMarker(routePoints);

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(mStartLocation);
            builder.include(mEndLocation);
            LatLngBounds bounds = builder.build();

            LatLng point1 = routePoints.get(0);
            LatLng point2 = routePoints.get(1);

            double angle = calculateBearing(point1, point2);
            double adjustedAngle = Math.toDegrees(angle) + 180;

            Log.e("Map", String.valueOf(calculateBearing(point1, point2)));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mStartLocation)
                    .zoom(19)
                    .tilt(45)
                    .bearing((float) angle)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
        }
    }

    public static double calculateBearing(LatLng point1, LatLng point2) {
        double lat1 = Math.toRadians(point1.latitude);
        double lon1 = Math.toRadians(point1.longitude);
        double lat2 = Math.toRadians(point2.latitude);
        double lon2 = Math.toRadians(point2.longitude);

        double deltaLon = lon2 - lon1;

        double x = Math.sin(deltaLon) * Math.cos(lat2);
        double y = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLon);

        double bearing = Math.atan2(x, y);
        bearing = Math.toDegrees(bearing);

        bearing = (bearing + 360) % 360;

        return bearing;
    }

    private void rotateArrowMarker(List<LatLng> routePoints) {
        if (routePoints == null || routePoints.size() < 2) {
            Log.e("MapsFragment", "Insufficient points to calculate direction");
            return;
        }

        LatLng point1 = routePoints.get(0);
        LatLng point2 = routePoints.get(1);
        double angle = calculateBearing(point1, point2);
        double adjustedAngle = Math.toDegrees(angle) + 180;

        Bitmap arrowBitmap = getBitmapFromVectorDrawable(R.drawable.arrow_icon, 75, 75);

        LatLng startPosition = point1;

        if (arrowMarker != null) {
            arrowMarker.remove();
        }
        if (arrowBitmap != null) {
            arrowMarker = mMap.addMarker(new MarkerOptions()
                    .position(startPosition)
                    .icon(BitmapDescriptorFactory.fromBitmap(arrowBitmap))
                    .anchor(0.5f, 0.5f)
                    .rotation((float) angle + 90)
                    .flat(true)
            );
        } else {
            Log.e("MapsFragment", "Failed to create bitmap for arrow icon");
        }
    }

    private Bitmap getBitmapFromVectorDrawable(int drawableId, int width, int height) {
        Drawable vectorDrawable = ContextCompat.getDrawable(requireContext(), drawableId);
        if (vectorDrawable == null) return null;

        vectorDrawable.setBounds(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return bitmap;
    }


    private void updateLocationOnFirebase(Location location) {
        Map<String, Object> locationData = new HashMap<>();
        locationData.put("latitude", location.getLatitude());
        locationData.put("longitude", location.getLongitude());
        locationData.put("timestamp", System.currentTimeMillis());

        locationRef.setValue(locationData)
                .addOnSuccessListener(aVoid -> Log.d("MapsFragment", "Location updated on Firebase"))
                .addOnFailureListener(e -> Log.e("MapsFragment", "Failed to update location on Firebase", e));
    }

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void showBottomSheet(String distance, String duration) {
        if (bottomShow && !running) {
            bottomShow = false;
            InfoBottomSheetDialogFragment bottomSheetDialogFragment = new InfoBottomSheetDialogFragment(distance, duration);

            bottomSheetDialogFragment.setDismissListener(() -> bottomShow = true);
            bottomSheetDialogFragment.setResetMapListener(this);

            bottomSheetDialogFragment.show(getChildFragmentManager(), "InfoBottomSheetDialogFragment");
        }
    }

    @Override
    public void onResetMap() {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mStartLocation, 15));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mStartLocation)
                    .zoom(15)
                    .bearing(0)
                    .tilt(0)
                    .build();
            if (currentMarkers != null) {
                currentMarkers.clear();
            }
            if (arrowMarker != null) {
                arrowMarker.remove();
            }
            if (currentPolyline != null) {
                currentPolyline.remove();
            }
            if (arrowMarker != null) {
                arrowMarker.remove();
            }
            mMap.clear();
            listenForOrders();
        }
    }
}
