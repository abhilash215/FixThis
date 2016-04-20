package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.Garbage_frag;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag())
                .addToBackStack(null)
               .commit();



    }

    @Override
    public void loadComplaint(int type) {

        switch (type)
        {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace,Road_frag.newInstance(type))
                        .addToBackStack(null)
                       .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace,Light_frag.newInstance1(type))
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace,Leak_frag.newInstance4(type))
                       .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace,Garbage_frag.newInstance3(type))
                       .addToBackStack(null)
                       .commit();
                break;
            default:
                Toast.makeText(getApplicationContext(),"No complaint selected",Toast.LENGTH_LONG);
        }



    }
}
