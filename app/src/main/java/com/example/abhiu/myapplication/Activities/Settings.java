package com.example.abhiu.myapplication.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.abhiu.myapplication.Fragments.AboutFragment;
import com.example.abhiu.myapplication.Fragments.Developer;
import com.example.abhiu.myapplication.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbars = (Toolbar) findViewById(R.id.toolbarsettings);
        setSupportActionBar(toolbars);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Settings");
////////////////////////////////////////////////////////
        Transition enterTrans = new Explode();
        getWindow().setEnterTransition(enterTrans);

        Transition returnTrans = new Slide();
        getWindow().setReturnTransition(returnTrans);
        ///////////////////////////////////////////////

        LinearLayout lmain = (LinearLayout) findViewById(R.id.layout1);
        lmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "feedbackfixthis@gmail.com"));
                startActivity(intent);
            }
        });


        LinearLayout llay2 = (LinearLayout) findViewById(R.id.layout2);
        llay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.new_content_activity, Developer.newInstancedev(R.id.developers))
                        .commit();
            }
        });
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
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Settings.this);
            Intent intent = new Intent(Settings.this, Settings.class);
            startActivity(intent, options.toBundle());

        }

        if (id == R.id.feedback) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "feedbackfixthis@gmail.com"));
            startActivity(intent);
        }

        if (id == R.id.about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.new_content_activity, AboutFragment.newInstanceAbout(R.id.aboutapp))
                    .commit();

        }

        if (id == R.id.home) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
