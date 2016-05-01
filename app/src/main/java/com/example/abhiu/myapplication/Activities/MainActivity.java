package com.example.abhiu.myapplication.Activities;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.abhiu.myapplication.Fragments.AboutFragment;
import com.example.abhiu.myapplication.Fragments.User_Profile_frag;
import com.example.abhiu.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_CONTACTS=1;
    private static String[] PERMISSIONS ={Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SEND_SMS};
    private ImageView imgProfilePic;
       private TextView txtName, txtEmail;
        String name,email;
          Uri profilePicUrl;
        String strProfilePic;
     SharedPreferences sharedPreferences ;
    int pagerCount=0;

    int[] mResources = {
            R.drawable.campusaerialview,
            R.drawable.syr,R.drawable.syr1, R.drawable.syr2,R.drawable.syr3
    };
    ViewPager mViewPager;
    MyPagerAdapter myPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///////////////////////////////////////////////////////////////////////////////////////////////////

               ///////////////////////////////Google login/////////////////////////////////////////////////////

     sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

           strProfilePic = sharedPreferences.getString("profile_pic","No Image found");
                name = sharedPreferences.getString("name","No Name found");
               email = sharedPreferences.getString("email","No Email found");

 //////////////////////////////////////////////////////////////////////////////////////////////////
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
        ////////////////////////////////setting navigation drawer profile details////////////////
                imgProfilePic = (ImageView) headerView.findViewById(R.id.imageView_h);
                txtName = (TextView) headerView.findViewById(R.id.user_name_id);
                txtEmail = (TextView) headerView.findViewById(R.id.textView);
                txtName.setText(name);
                txtEmail.setText(email);
                Picasso.with(this).load(strProfilePic).into(imgProfilePic);
                ///////////////////////////////////////////////////////////////////////////////////
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.reqid);
        RelativeLayout r1=(RelativeLayout)findViewById(R.id.recentid);
        RelativeLayout r2=(RelativeLayout)findViewById(R.id.emerid);
        RelativeLayout r3=(RelativeLayout)findViewById(R.id.userid);
//        ImageView new_img = (ImageView) findViewById(R.id.new_request_id);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NewReq_Activity.class);
// Pass data object in the bundle and populate details activity.
                ActivityOptionsCompat optionstry = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, v, "newrequest");
                startActivity(intent, optionstry.toBundle());
            }
        });

//        ImageView user_img = (ImageView) findViewById(R.id.user_img);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, User_Profile_frag.newInstanceuser(R.id.user_profile))
                        .commit();
            }
        });

//        ImageView recent_img = (ImageView) findViewById(R.id.recent_img);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, HealthCareAcivity.class);
                startActivity(i2);
            }
        });


//        ImageView emg_img = (ImageView) findViewById(R.id.fav_img);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Emergency.class);
// Pass data object in the bundle and populate details activity.
                ActivityOptionsCompat optionstry = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, v, "emergency");
                startActivity(intent, optionstry.toBundle());
            }
        });

        myPagerAdapter = new MyPagerAdapter(MainActivity.this);
        mViewPager = (ViewPager)findViewById(R.id.viewpager_id);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setPageTransformer(true, new RotateUpTransformer());
        ////////////timer //////////////
        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (MainActivity.this != null) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pagerCount <= 5) {
                                mViewPager.setCurrentItem(pagerCount);
                                pagerCount++;
                            } else {
                                pagerCount = 0;
                                mViewPager.setCurrentItem(pagerCount);
                            }
                        }
                    });
                }
            }
        }, 500, 3000);


    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Exit app?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);}
        else {
           // super.onBackPressed();
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
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
            Intent intent = new Intent(MainActivity.this,Settings.class);
            startActivity(intent, options.toBundle());

        }

        if(id==R.id.feedback)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "feedbackfixthis@gmail.com"));
            startActivity(intent);
        }

        if(id==R.id.about)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, AboutFragment.newInstanceAbout(R.id.aboutapp))
                    .commit();

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

        else if (id == R.id.abt_me) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //////////// adapter class here ////////////////
    public class MyPagerAdapter extends PagerAdapter {
        int count;
        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(Context context) {
            super();
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.collapseImages);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }


    }// end of pager adapter class

}
