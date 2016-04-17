package com.example.abhiu.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.abhiu.myapplication.R;

public class LaunchScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        //should add launch screen animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchScreen.this, MainActivity.class);
                LaunchScreen.this.startActivity(i);
                LaunchScreen.this.finish();
            }
        }, 2000); //delay of 2 seconds

    }
}
