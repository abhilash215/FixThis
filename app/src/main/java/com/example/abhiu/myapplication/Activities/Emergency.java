package com.example.abhiu.myapplication.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.abhiu.myapplication.R;

public class Emergency extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent, options.toBundle());
            }
        });

        Button bh=(Button)findViewById(R.id.button_hosp);
        bh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Emergency.this,Test.class);
                startActivity(i);
            }
        });
    }


/*
    public class ShrinkBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>
    {

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent,FloatingActionButton child, View dependency)
        {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependencyViewChanged(CoordinatorLayout parent,FloatingActionButton child, View dependency)
        {
            float translationY=getFabTranslationYForSnackBar(parent,child);
            float percentComplete=-translationY/dependency.getHeight();
            float scaleFactor=1-percentComplete;

            child.setScaleX(scaleFactor);
            child.setScaleY(scaleFactor);
            return false;
        }

        private float getFabTranslationYForSnackBar(CoordinatorLayout parent, FloatingActionButton child) {
            return 0;
        }
    }*/

}
