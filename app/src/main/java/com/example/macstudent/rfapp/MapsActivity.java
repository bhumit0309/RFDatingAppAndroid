
package com.example.macstudent.rfapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int position;
    private Double v, v1 = 0.0;
    Realm realm = Realm.getDefaultInstance();

    private RealmResults<AndroidVersion> data = realm.where(AndroidVersion.class).findAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!= null)
        {
            position = bundle.getInt("Position");
            v = data.get(position).getLatitude();
            v1 = data.get(position).getLongitude();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            final int REQUEST_LOCATION = 2;

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display UI and wait for user interaction
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMaxZoomPreference(10);
        mMap.setMinZoomPreference(13);
        // Add a marker in Sydney and move the camera
        for(int i = 0; i < DataAdapter.data.size(); i++)
        {
            LatLng place = new LatLng(data.get(i).getLatitude(),data.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(place).title(data.get(i).getDisplayName()));
            if(i == position)
            {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 13));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10));
                    }
                }, 5000);
            }
        }
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
