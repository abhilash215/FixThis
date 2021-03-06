package com.example.abhiu.myapplication.Activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.AboutFragment;
import com.example.abhiu.myapplication.Fragments.FragmentGoogleMap;
import com.example.abhiu.myapplication.Fragments.Garbage_frag;
import com.example.abhiu.myapplication.Fragments.Gen_frag;
import com.example.abhiu.myapplication.Fragments.Leak_frag;
import com.example.abhiu.myapplication.Fragments.Light_frag;
import com.example.abhiu.myapplication.Fragments.RecyclerView_frag;
import com.example.abhiu.myapplication.Fragments.Road_frag;
import com.example.abhiu.myapplication.R;
import com.firebase.client.Firebase;

public class NewReq_Activity extends AppCompatActivity implements RecyclerView_frag.loadFragment,FragmentGoogleMap.OnFragmentInteractionListener
    {

    protected LocationManager locationManager;
    Button bl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);
        Firebase.setAndroidContext(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("New Requests");

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag(), "Complaints")
                .commit();
           }

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
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NewReq_Activity.this);
                Intent intent = new Intent(NewReq_Activity.this, Settings.class);
                startActivity(intent, options.toBundle());

            }

            if (id == R.id.feedback) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "feedbackfixthis@gmail.com"));
                startActivity(intent);
            }

            if (id == R.id.about) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_replace, AboutFragment.newInstanceAbout(R.id.aboutapp))
                        .commit();

            }

            if (id == R.id.home) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

            return super.onOptionsItemSelected(item);
        }


    @Override
    public void loadComplaint(int type,View sharedImage) {

        switch (type) {
            case 0:
                Road_frag road_frag = Road_frag.newInstance(type);
                road_frag.setSharedElementEnterTransition(new DetailsTransition());
                road_frag.setEnterTransition(new Fade());
                road_frag.setExitTransition(new Fade());
               road_frag.setSharedElementReturnTransition(new DetailsTransition());

               android.support.v4.app.FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.frame_replace, Road_frag.newInstance(type), "RoadTAG");
                     fragmentTransaction.addToBackStack("RoadTAG");
                fragmentTransaction.addSharedElement(sharedImage, sharedImage.getTransitionName());//does fragment animation
                        fragmentTransaction.commit();

                break;
            case 1:

                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.animr, 0, 0, R.anim.anim)
                        .replace(R.id.frame_replace, Light_frag.newInstance1(type),"LightTAG")
                        .addSharedElement(sharedImage, sharedImage.getTransitionName())//does fragment animation
                       .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in, 0, 0, R.anim.animr).replace(R.id.frame_replace, Leak_frag.newInstance4(type)
                        , "LeakTAG") .addSharedElement(sharedImage, sharedImage.getTransitionName())//does fragment animation
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in, 0, 0, R.anim.animr).replace(R.id.frame_replace, Garbage_frag.newInstance3(type)
                        , "GarbageTAG") .addSharedElement(sharedImage, sharedImage.getTransitionName())//does fragment animation
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.fade_in, 0, 0, R.anim.fade_out).replace(R.id.frame_replace, Gen_frag.newInstance5(type)
                        , "GeneralTAG") .addSharedElement(sharedImage, sharedImage.getTransitionName())//does fragment animation
                        .addToBackStack(null)
                        .commit();

            default:
                Toast.makeText(getApplicationContext(), "No complaint selected", Toast.LENGTH_LONG);
        }
    }

        @Override
        public void onFragmentInteraction(String str) {
            Road_frag road_frag = (Road_frag) getSupportFragmentManager().findFragmentByTag("RoadTAG");
            if(road_frag!=null)  road_frag.updateLocation(str);
            Light_frag light_frag = (Light_frag) getSupportFragmentManager().findFragmentByTag("LightTAG");
            if(light_frag!=null)light_frag.updateLocation(str);
            Leak_frag leak_frag = (Leak_frag) getSupportFragmentManager().findFragmentByTag("LeakTAG");
            if(leak_frag!=null) leak_frag.updateLocation(str);
            Garbage_frag garbage_frag = (Garbage_frag) getSupportFragmentManager().findFragmentByTag("GarbageTAG");
            if(garbage_frag!=null) garbage_frag.updateLocation(str);
            Gen_frag gen_frag = (Gen_frag) getSupportFragmentManager().findFragmentByTag("GeneralTAG");
            if(gen_frag!=null) gen_frag.updateLocation(str);
        }

        public  class DetailsTransition extends TransitionSet
        {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public  DetailsTransition()
            {
                setOrdering(ORDERING_TOGETHER);
                        addTransition(new ChangeBounds())
                                .addTransition(new ChangeTransform())
                                .addTransition(new ChangeImageTransform());
            }
        }

}
