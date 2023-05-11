package com.example.recipeass2.map;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeass2.R;
import com.example.recipeass2.databinding.ActivityNearestShopBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NearestShopActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityNearestShopBinding binding;

    private MapView mapView;

    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;

    private final String CURRENT_SCREEN_NAME = "Nearest Shop";

    private static final int PLACE_RADIUS = 1000; // 1km radius
    private static final String PLACE_TYPE = "supermarket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNearestShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.topBar.currentScreenName.setText(CURRENT_SCREEN_NAME);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        goBackPrevScreen();
    }
    public void goBackPrevScreen(){
        binding.topBar.goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        Toast.makeText(NearestShopActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {

                            /**
                             * hard code location
                             * **/
                            LatLng startingLatLng = new LatLng(-37.810111, 144.964333); // Latitude and longitude for 5 Little La Trobe St, Melbourne
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLatLng, 15));
                            mMap.addMarker(new MarkerOptions().position(startingLatLng).title("Starting Location"));
                            /**
                             * hard code location
                             * **/

                            fusedLocationClient.removeLocationUpdates(locationCallback);

                            // Search for nearby supermarkets
//                            findNearbySupermarkets(userLatLng);
                            findNearbySupermarkets(startingLatLng);

                            break;
                        }
                    }
                }
            };
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }


    private void findNearbySupermarkets(LatLng userLatLng) {
        // Clear existing markers
        mMap.clear();
        RecyclerView recyclerView = findViewById(R.id.shop_lists);

        // Create the adapter and set it to the RecyclerView
        NearShopListAdapter adapter = new NearShopListAdapter();
        recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView (e.g., LinearLayoutManager)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Construct the URL for the nearby places API request
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + userLatLng.latitude + "," + userLatLng.longitude +
                "&radius=" + PLACE_RADIUS +
                "&type=" + PLACE_TYPE +
                "&key=" + "AIzaSyAp6y2XwKXUYOC56UVR7AtoCN0iTWvuJs0";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            List<Supermarket> supermarkets = new ArrayList<>();

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                String name = result.getString("name");
                                String address = result.getString("vicinity");
                                String phone = ""; // You can retrieve the phone number if available

                                supermarkets.add(new Supermarket(name, address, phone));
                            }

                            // Update the adapter with the fetched data
                            adapter.updateData(supermarkets);

                            // Notify the adapter that the data has changed
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(NearestShopActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}