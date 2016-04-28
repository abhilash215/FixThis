package com.example.abhiu.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.RecentRecyclerView;
import com.example.abhiu.myapplication.Fragments.RecyclerView_frag;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;

public class RecentActivity extends AppCompatActivity implements RecentRecyclerView.LoadRecentComplaint{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Recent requests");
        Log.d("before", "recent actvirty: ");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace_1, new RecentRecyclerView())
                .commit();
        Log.d("after","after");
    }

    @Override
    public void loadRecentComplaints(int position, Complaint complaint) {
        Toast.makeText(getApplicationContext(),"loading recent complaint",Toast.LENGTH_SHORT);
    }
}
