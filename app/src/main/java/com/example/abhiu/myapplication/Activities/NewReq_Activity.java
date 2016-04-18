package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.abhiu.myapplication.Fragments.road;
import com.example.abhiu.myapplication.R;

public class NewReq_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("New Requests");

        Button b1=(Button)findViewById(R.id.button_road);
        Button b2=(Button)findViewById(R.id.button_light);
        Button b3=(Button)findViewById(R.id.button_leak);
        Button b4=(Button)findViewById(R.id.button_dump);
        Button b5=(Button)findViewById(R.id.button_other);

         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 getSupportFragmentManager().beginTransaction()
                     .replace(R.id.new_content_activity,road.newInstance(R.id.road))
                     .commit();
                 }
         });


       /* b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.new_content_activity,road.newInstance1(R.id.light))
                        .commit();
            }
        });*/




    }

}
