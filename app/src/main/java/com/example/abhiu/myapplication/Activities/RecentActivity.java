package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhiu.myapplication.Fragments.RecentRecyclerView;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

public class RecentActivity extends AppCompatActivity implements RecentRecyclerView.LoadRecentComplaint{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

    /*    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Recent requests");*/

// getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace_1, new RecentRecyclerView())
// .commit();

// Use Firebase to populate the list.
        Firebase.setAndroidContext(this);
        final TextView textView = (TextView ) findViewById(R.id.listText);
        Firebase recent_firebase_ref = new Firebase(LoginActivity.getFIREBASEREF());
        recent_firebase_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
// for(DataSnapshot snapshot:dataSnapshot.getChildren())
// {
                HashMap<String,?> complaint = (HashMap<String, ?>) dataSnapshot.child("Road Complaints").child("Chiranth Bs")
                        .child("Road Complaint 1").getValue();

                textView.setText(complaint.get("landmark").toString());
// }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(),"Firebase cancelled",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void loadRecentComplaints(int position, Complaint complaint) {
        Toast.makeText(getApplicationContext(),"loading recent complaint",Toast.LENGTH_SHORT);
    }
}