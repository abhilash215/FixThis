package com.example.abhiu.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.abhiu.myapplication.R;

public class LaunchScreen extends AppCompatActivity {

    Animation animation;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        //should add launch screen animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LaunchScreen.this, LoginActivity.class);
                LaunchScreen.this.startActivity(i);
                LaunchScreen.this.finish();
            }
        }, 2000); //adds a delay of 2 seconds

        animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        textView=(TextView)findViewById(R.id.screentxt);
        textView.startAnimation(animation);
        animation.start();
        animation.setDuration(2000);

    }
}
