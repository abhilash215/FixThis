package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.abhiu.myapplication.Fragments.Garbage_frag;
import com.example.abhiu.myapplication.Fragments.Gen_frag;
import com.example.abhiu.myapplication.Fragments.Leak_frag;
import com.example.abhiu.myapplication.Fragments.Light_frag;
import com.example.abhiu.myapplication.Fragments.RecyclerView_frag;
import com.example.abhiu.myapplication.Fragments.Road_frag;
import com.example.abhiu.myapplication.R;

public class NewReq_Activity extends AppCompatActivity implements RecyclerView_frag.loadFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("New Requests");
/*
        Button b1=(Button)findViewById(R.id.button_road);
        Button b2=(Button)findViewById(R.id.button_light);
        Button b3=(Button)findViewById(R.id.button_leak);
        Button b4=(Button)findViewById(R.id.button_dump);
        Button b5=(Button)findViewById(R.id.button_other);

         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 getSupportFragmentManager().beginTransaction()
                     .replace(R.id.frame_replace, Road_frag.newInstance(R.id.road))
                     .commit();
                 }
         });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_replace, Light_frag.newInstance1(R.id.light))
                        .commit();
            }
        });

    b4.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, Garbage_frag.newInstance3(R.id.garbage))
                .commit();
    }
    });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, Leak_frag.newInstance4(R.id.leakage))
                        .commit();
            }
        });


        b5.setOnClickListener(new View.OnClickListener(){
            @Override
        public  void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, Gen_frag.newInstance5(R.id.general))
                        .commit();
            }
        });


*/
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag()).commit();
    }

    @Override
    public void loadComplaint(int type) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace,Road_frag.newInstance(type))
        .addToBackStack(null).commit();
    }
}
