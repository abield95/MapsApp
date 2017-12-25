package com.codebrain.minato.mapsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PointOfInterest;

public class PlacePickerActivity extends AppCompatActivity {//FragmentActivity implements LocationListener {

    private GoogleMap mMap;
    private LatLng placeLocation;
    AppCompatButton btnSetLocation;
    private Context activity;
    Marker locationMarker = null;
    PointOfInterest place;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        activity = this;

        Toolbar toolbar = findViewById(R.id.personal_toolbar);
        toolbar.setTitle("Pick a Place");
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }

        //define the complete button method
        btnSetLocation = findViewById(R.id.confirm_location);
        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("place_name", place.name);
                intent.putExtra("place_id", place.placeId);
                intent.putExtra("location_lat", place.latLng.latitude);//placeLocation.latitude);
                intent.putExtra("location_lon", place.latLng.longitude);//placeLocation.longitude);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //check google play services
        if (checkPlayServices())
        {
            SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

            //getting GoogleMap object from the fragment
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;

                    try {
                        mMap.setMyLocationEnabled(true);
                    }
                    catch (SecurityException e)
                    {
                        Log.d("Exception: ", e.getMessage());
                    }

                    mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                        @Override
                        public void onPoiClick(PointOfInterest pointOfInterest) {

                            placeLocation = pointOfInterest.latLng;
                            if (locationMarker != null) locationMarker.remove();
                            if (place != null) place = null;
                            place = pointOfInterest;
                            locationMarker = BaseMapsClass.addMarker(pointOfInterest.name, pointOfInterest.latLng,
                                    "Lat " + pointOfInterest.latLng.latitude + " long: " + pointOfInterest.latLng.longitude, mMap);
                        }
                    });

                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            if (locationMarker != null) locationMarker.remove();
                            if (place != null) place = null;
                            locationMarker = BaseMapsClass.addMarker("Street", latLng, "Strret", mMap);
                            place = new PointOfInterest(latLng, "Unknown id", "Unknown name");
                        }
                    });

                    //get the current location
                }
            });
        }
    }


    /**
     * checkPlayServices()
     * Used for checking if Google Play Services are available
     * @return
     */
    private boolean checkPlayServices()
    {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS)
        {
            if (googleApiAvailability.isUserResolvableError(result))
            {
                googleApiAvailability.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        Intent res = new Intent();
        setResult(RESULT_CANCELED, res);
        finish();
        return true;
    }

//    @Override
//    public void onLocationChanged(Location location)
//    {
//        //getting the lattitude of the current location
//        double latitude = location.getLatitude();
//
//        //getting the longitude of the current location
//        double longitude = location.getLongitude();
//
//        //creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//        placeLocation = latLng;
//
//        //showing the current location in google map
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        //zoom in the google map
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
//
//        mMap.getMaxZoomLevel();
//
//        //set latitude and longitude in the texview or other
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras)
//    {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider)
//    {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider)
//    {
//
//    }
}
