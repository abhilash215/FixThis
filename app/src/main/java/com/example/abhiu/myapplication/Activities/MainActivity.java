package com.example.abhiu.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.abhiu.myapplication.Fragments.User_Profile_frag;
import com.example.abhiu.myapplication.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


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


        ImageView new_img=(ImageView)findViewById(R.id.new_request_id);
        new_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1=new Intent(MainActivity.this,NewReq_Activity.class);
                startActivity(i1);

            }
        });

        ImageView user_img=(ImageView)findViewById(R.id.user_img);
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, User_Profile_frag.newInstanceuser(R.id.user_profile))
                        .commit();
            }
        });
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
            return  true;
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

        }
        else if (id==R.id.home)
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        }
        else if (id == R.id.nav_fav)
        {

        }
        else if (id == R.id.abt_app)
        {

        }
        else if (id == R.id.abt_me)
        {


            getSupportFragmentManager().beginTransaction()
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
