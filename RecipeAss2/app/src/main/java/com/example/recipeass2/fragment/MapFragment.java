package com.example.recipeass2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.recipeass2.database.AppDatabase;
import com.example.recipeass2.databinding.FragmentMapBinding;
import com.example.recipeass2.map.NearShopListAdapter;
import com.example.recipeass2.map.Supermarket;
import com.example.recipeass2.user.User;
import com.example.recipeass2.user.UserDao;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * MapFragment is a Fragment that displays a Google Map showing the user's current location,
 * as well as nearby supermarkets. It uses the Google Maps API, and also the Google Places API
 * to search for nearby supermarkets.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    // Data binding
    private FragmentMapBinding binding;

    // Google Map
    private MapView mapView;
    private GoogleMap mMap;

    // Request code for location permission
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Progress bars to indicate loading
    private ProgressBar progressBarMap;
    private ProgressBar progressBarShop;

    // Location provider
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    // Constants
    private final String CURRENT_SCREEN_NAME = "Nearest Shop";
    private static final int PLACE_RADIUS = 1000; // 1km radius for place search
    private static final String PLACE_TYPE = "supermarket"; // Type of place to search for

    // User's address
    private String location = "";
    LatLng home;

    // Required empty public constructor
    public MapFragment() {}

    // Method to create a new instance of this fragment
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    // Inflate the layout for this fragment, set up the map view and load user's location
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false);

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        // Initialize progress bars and set them to visible
        progressBarMap = binding.mapProgressBar; //
        progressBarMap.setVisibility(View.VISIBLE); // Show progress bar
        progressBarShop = binding.shopListProgressBar;
        progressBarShop.setVisibility(View.VISIBLE);
        mapView.getMapAsync(this);


        setLocation();

        return binding.getRoot();
    }
    // Load user's location from shared preferences

    private void setLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info",
                Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("email", null);
        // Get the UserDao
        UserDao userDao = AppDatabase.getDatabase(getContext().getApplicationContext()).userDao();
        // Observe changes in the user's data
        LiveData<User> liveDataUser = userDao.getUserByEmail(email);




        liveDataUser.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user !=null){
                    location = user.getAddress().toString(); // Assuming User class has getLocation() method
                }
            }
        });
    }

    // Called when the map is ready to use
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Hide progress bar
        progressBarMap.setVisibility(View.GONE);
        requestLocationPermission();
    }
    // Get the LatLng from a location address
    private LatLng getLocationFromAddress() {
        Geocoder coder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(location, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
            Log.d("Geocoder Exception", "Could not get LatLng from Address");
        }

        return p1;
    }
    // Request location permission

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }
    // Handle result of location permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Get the current location of the device
    private void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {

                            // Get LatLng from address
                            home = getLocationFromAddress();

                            // Move camera to user's location
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));

                            // Stop location updates
                            fusedLocationClient.removeLocationUpdates(locationCallback);

                            // Search for nearby supermarkets
                            findNearbySupermarkets(home);

                            break;
                        }
                    }
                }
            };
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    // Find nearby supermarkets using the Google Places API
    private void findNearbySupermarkets(LatLng userLatLng) {
        // Clear existing markers
        mMap.clear();
        RecyclerView recyclerView = binding.shopLists;

        // Create the adapter and set it to the RecyclerView
        NearShopListAdapter adapter = new NearShopListAdapter();
        recyclerView.setAdapter(adapter);

        // Set the layout manager for the RecyclerView (e.g., LinearLayoutManager)
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Construct the URL for the nearby places API request
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + userLatLng.latitude + "," + userLatLng.longitude +
                "&radius=" + PLACE_RADIUS +
                "&type=" + PLACE_TYPE +
                "&key=" + "AIzaSyAp6y2XwKXUYOC56UVR7AtoCN0iTWvuJs0";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");

                        progressBarShop.setVisibility(View.GONE);
                        List<Supermarket> supermarkets = new ArrayList<>();

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            String name = result.getString("name");
                            String address = result.getString("vicinity");
                            String phone = "";

                            supermarkets.add(new Supermarket(name, address, phone));

                            // Get the LatLng for each supermarket
                            JSONObject geometry = result.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");
                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");
                            LatLng supermarketLatLng = new LatLng(lat, lng);

                            // Add a marker for each supermarket
                            mMap.addMarker(new MarkerOptions().position(supermarketLatLng).title(name));
                        }

                        // Creating a marker on the map
                        MarkerOptions options = new MarkerOptions().position(home).title("Home").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        Marker marker = mMap.addMarker(options);
                        marker.showInfoWindow();



                        // Update the adapter with the fetched data
                        adapter.updateData(supermarkets);

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    // Lifecycle methods to manage the MapView
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}