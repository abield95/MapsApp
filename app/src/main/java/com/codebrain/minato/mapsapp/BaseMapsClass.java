package com.codebrain.minato.mapsapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by username on 12/21/2017.
 */

public abstract class BaseMapsClass {
    public static Marker addMarker(String title, LatLng position, String data, GoogleMap mMap) {
        if (mMap != null) {
            return mMap.addMarker(new MarkerOptions().title(title).position(position).snippet(data));
        }
        return null;
    }
}
