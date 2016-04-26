package com.example.abhiu.myapplication.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.Garbage_frag;
import com.example.abhiu.myapplication.Fragments.Gen_frag;
import com.example.abhiu.myapplication.Fragments.Leak_frag;
import com.example.abhiu.myapplication.Fragments.Light_frag;
import com.example.abhiu.myapplication.Fragments.RecyclerView_frag;
import com.example.abhiu.myapplication.Fragments.Road_frag;
import com.example.abhiu.myapplication.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;

public class NewReq_Activity extends AppCompatActivity implements RecyclerView_frag.loadFragment
      /*  OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener */ {

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    Button bl;

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("New Requests");

     /*   ////////////////////view pager////////////
        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
       *//* MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager(), new MovieData());
        viewPager.setAdapter(adapter);*//*
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ////////////////////view pager////////////*/
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag())
                .addToBackStack(null)
                .commit();

/*///////////////google maps///////////////////
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }

/////////////////////////////////////google maps///////////////*/


        bl = (Button) findViewById(R.id.btnlocation);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new MyLocationListener()
            );
            bl.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {
                    showCurrentLocation();
                }
            });

            return;
        }
           }

    ////////////////////////////////location//////////////////////////////

    void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                String message = String.format(
                        "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                        location.getLongitude(), location.getLatitude()
                );
                Toast.makeText(NewReq_Activity.this, message,Toast.LENGTH_LONG).show();
            }
            return;
        }
    }
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String message = String.format("New Location \n Longitude: %1$s \n Latitude: %2$s",location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(NewReq_Activity.this, message, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(NewReq_Activity.this, "Provider status changed", Toast.LENGTH_LONG).show();
        }
        public void onProviderDisabled(String s) {
            Toast.makeText(NewReq_Activity.this, "Provider disabled by the user. GPS turned off",

                    Toast.LENGTH_LONG).show();

        }


        public void onProviderEnabled(String s) {

            Toast.makeText(NewReq_Activity.this,

                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }
    }
/////////////////////////////////////location/////////////////////////////////

    // to set the title in fragments
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    // for menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.feedback) {
            return true;
        }

        if (id == R.id.about) {
            return true;
        }

        if (id == R.id.home) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void loadComplaint(int type) {

        switch (type) {
            case 0:
               android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();

                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frame_replace, Road_frag.newInstance(type));
                     fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.animr, 0, 0, R.anim.anim)
                        .replace(R.id.frame_replace, Light_frag.newInstance1(type))
                       .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.anim, 0, 0, R.anim.animr).replace(R.id.frame_replace, Leak_frag.newInstance4(type))

                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in, 0, 0, R.anim.animr).replace(R.id.frame_replace, Garbage_frag.newInstance3(type))
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.fade_in,0,0,R.anim.fade_out).replace(R.id.frame_replace, Gen_frag.newInstance5(type))
                        .addToBackStack(null)
                        .commit();

            default:
                Toast.makeText(getApplicationContext(), "No complaint selected", Toast.LENGTH_LONG);
        }
    }

/*
    ///////////////////////////////////google maps///////////////////////////////////////

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationRequest mLocationRequest = createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("onConnectionFailed ", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
    LatLng latLng_Prev = null;

    @Override
    public void onLocationChanged(Location location) {

        //move camera when location changed
        LatLng latLng_Now = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng_Now)      // Sets the center of the map to LatLng (refer to previous snippet)
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if(latLng_Prev == null){
            latLng_Prev = latLng_Now;
        }
        //draw line between two locations:
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(latLng_Prev, latLng_Now)
                .width(5)
                .color(Color.RED));
        latLng_Prev=latLng_Now;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng lat) {
                Toast.makeText(getApplicationContext(), "Latitude: " + lat.latitude + "\nLongitude: " + lat.longitude, Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng lat) {
                final Marker marker = mMap.addMarker(new MarkerOptions()
                        .title("self defined marker")
                        .snippet("Hello!")
                        .position(lat).visible(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))//.icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
                );
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                Toast.makeText(getApplicationContext(), marker.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
    /////////////////////////////////////////////////////google maps////////////////////////////////////////////
    */
}
