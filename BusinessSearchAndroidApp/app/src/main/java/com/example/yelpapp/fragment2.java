package com.example.yelpapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class fragment2 extends Fragment {

    String lat;
    String lng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_fragment2, container, false);


        View view=inflater.inflate(R.layout.fragment_fragment2, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        Bundle dataf2 = getArguments();
        lat = dataf2.getString("blat");
        lng = dataf2.getString("blng");

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                        // When clicked on map
                        // Initialize marker options
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                        // Set title of marker
//                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        // Remove all marker
//                        googleMap.clear();
                        // Animating to zoom the marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                        CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)),14);
                        googleMap.moveCamera(center);
                        googleMap.moveCamera(zoom);

//                        googleMap.animateCamera(center);
//                        googleMap.animateCamera(zoom);


                    }
                });






        // Return view
        return view;







    }
}