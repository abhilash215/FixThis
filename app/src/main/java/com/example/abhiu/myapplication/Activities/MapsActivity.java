package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhiu.myapplication.Fragments.FragmentGoogleMap;
import com.example.abhiu.myapplication.Fragments.Road_frag;
import com.example.abhiu.myapplication.R;

public class MapsActivity extends AppCompatActivity implements FragmentGoogleMap.OnFragmentInteractionListener{

    //private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);//content_main2
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      //  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
       //         .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerMap, new FragmentGoogleMap().newInstance(),"MapTAG")
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(String str) {
        Road_frag road_frag = (Road_frag) getSupportFragmentManager().findFragmentByTag("RoadTAG");
     if(road_frag.isAdded())    road_frag.updateLocation(str);
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
/*    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
}
