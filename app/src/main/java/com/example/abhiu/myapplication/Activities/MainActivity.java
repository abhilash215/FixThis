package com.example.abhiu.myapplication.Activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.Recent_frag;
import com.example.abhiu.myapplication.Fragments.User_Profile_frag;
import com.example.abhiu.myapplication.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    Button bl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("FixThis");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ImageView new_img = (ImageView) findViewById(R.id.new_request_id);
        new_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, NewReq_Activity.class);
// activity animation//
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v, 0,
                        0, v.getWidth(), v.getHeight());
                startActivity(i1, options.toBundle());

            }
        });

        ImageView user_img = (ImageView) findViewById(R.id.user_img);
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, User_Profile_frag.newInstanceuser(R.id.user_profile))
                        .commit();
            }
        });

        ImageView recent_img = (ImageView) findViewById(R.id.recent_img);
        recent_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, Recent_frag.newInstancerecent(R.id.recent_frag))
                        .commit();
            }
        });

        bl = (Button) findViewById(R.id.btnlocation);
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "LOCATION CLICK Location Button ", Toast.LENGTH_SHORT).show();
                showCurrentLocation();
            }
        });


/////////////////////location /////////////////////////


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new MyLocationListener()
            );


            return;
        }
    }

    ////////////////////////////////location//////////////////////////////

    void showCurrentLocation() {
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
           *//* if (location != null) {
                String message = String.format(
                        "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                        location.getLongitude(), location.getLatitude()
                );
                Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
            }
            return;*//*


            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MINIMUM_TIME_BETWEEN_UPDATES,
                                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                        Log.d("Network", "Network Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MINIMUM_TIME_BETWEEN_UPDATES,
                                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                            Log.d("GPS", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;


        }
        return null;*/
    }





    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String message = String.format("New Location \n Longitude: %1$s \n Latitude: %2$s",location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "Provider status changed", Toast.LENGTH_LONG).show();
        }
        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this, "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }
    }
    /////////////////////////////////////location/////////////////////////////////

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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

        if(id==R.id.feedback)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);
        }

        if(id==R.id.about)
        {
            return true;
        }

        if(id==R.id.home)
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.req)
        {
            Intent i=new Intent(this,com.example.abhiu.myapplication.Activities.NewReq_Activity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_recent)
        {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.anim)
                    .replace(R.id.content_main, Recent_frag.newInstancerecent(R.id.recent_frag))
                    .commit();
        }
        else if (id==R.id.home)
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_fb)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);
        }
        else if (id == R.id.abt_app)
        {

        }
        else if (id == R.id.abt_me)
        {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in,0,0,R.anim.anim)
                    .replace(R.id.content_main, User_Profile_frag.newInstanceuser(R.id.user_profile))
                    .commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (getSupportFragmentManager().getBackStackEntryCount()>0) {
            outState.putInt("activity_main", 1);
        }
        super.onSaveInstanceState(outState);
    }
}
