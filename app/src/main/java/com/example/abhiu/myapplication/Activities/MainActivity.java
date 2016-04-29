package com.example.abhiu.myapplication.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.Recent_frag;
import com.example.abhiu.myapplication.Fragments.User_Profile_frag;
import com.example.abhiu.myapplication.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
///////////////////////////////////////////////////////////////////////////////////////////
//    private static final int RC_SIGN_IN = 0;
//    // Logcat tag
//    private static final String TAG = "MainActivity";
//    // Profile pic image size in pixels
//    private static final int PROFILE_PIC_SIZE = 400;
//
//    // Google client to interact with Google API
//    private GoogleApiClient mGoogleApiClient;
//
//    /**
//     * A flag indicating that a PendingIntent is in progress and prevents us
//     * from starting further intents.
//     */
//    private boolean mIntentInProgress;
//
//    private boolean mSignInClicked;
//
//    private ConnectionResult mConnectionResult;

    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    String name,email;
      Uri profilePicUrl;
    String strProfilePic;
   public SharedPreferences sharedPreferences ;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////////////Google login/////////////////////////////////////////////////////

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

       // profilePicUrl =
       // imgProfilePic.setImageURI(profilePicUrl);
        strProfilePic = sharedPreferences.getString("profile_pic","No Image found");
        name = sharedPreferences.getString("name","No Name found");
        email = sharedPreferences.getString("email","No Email found");

        ///////////////////////////////////////////////////////////////////////////////////////////////////
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
        View headerView = navigationView.getHeaderView(0); // to set header values
        ////////////////////////////////setting navigation drawer profile details////////////////////////////////////////////////
        imgProfilePic = (ImageView) headerView.findViewById(R.id.imageView_h);
        txtName = (TextView) headerView.findViewById(R.id.user_name_id);
        txtEmail = (TextView) headerView.findViewById(R.id.textView);
        txtName.setText(name);
        txtEmail.setText(email);
        Picasso.with(this).load(strProfilePic).into(imgProfilePic);
        ///////////////////////////////////////////////////////////////////////////////////
        ImageView new_img = (ImageView) findViewById(R.id.new_request_id);
        new_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NewReq_Activity.class);
                // Pass data object in the bundle and populate details activity.
                ActivityOptionsCompat optionstry = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this,v , "newrequest");
                startActivity(intent, optionstry.toBundle());
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
                Intent i2 = new Intent(MainActivity.this, RecentActivity.class);
                startActivity(i2);
            }
        });

        ImageView emg_img = (ImageView) findViewById(R.id.fav_img);
        emg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, Emergency.class);
        /////////////////////////////////////////////////// activity animation/////////////////////
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(v, 0,
                        0, v.getWidth(), v.getHeight());
                startActivity(i1, options.toBundle());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


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
            /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);*/

            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "feedbackfixthis@gmail.com"));
            startActivity(intent);

        }

        else if (id == R.id.abt_me)
        {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in,0,0,R.anim.anim)
                    .replace(R.id.content_main, User_Profile_frag.newInstanceuser(R.id.user_profile))
                    .commit();


        }

        else if(id==R.id.settings)
        {

            Intent i=new Intent(this,com.example.abhiu.myapplication.Activities.Settings.class);
            startActivity(i);
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

    ///////////////////////////////////////////////////////////////////////////////////////
//    /**
//     * Background Async task to load user profile picture from url
//     * */
//    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public LoadProfileImage(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
}
