package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.abhiu.myapplication.Adapters.ComplaintAdapter;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecentActivity extends AppCompatActivity {
    private Firebase mRef;
    ListView listView;
   // HashMap<String, ?> complaintMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        final ArrayList<Complaint> complaintList = new ArrayList<Complaint>();
        listView = (ListView) findViewById(R.id.listView_id);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://fixthis.firebaseio.com");

//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1);

        final ComplaintAdapter adapter = new ComplaintAdapter(this,complaintList);
        listView.setAdapter(adapter);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    complaintMap  = (HashMap<String, ?>) postSnapshot.child("Chiranth Bs").getValue();
//                    complaintList .add(complaintMap);
//                    adapter.add(String.valueOf(complaintList));
                        Complaint complaint = new Complaint();
                        complaint.setReporter(postSnapshot.child("Chiranth Bs").
                                child("Road Complaint 1").child("reporter").getValue().toString());
                    complaint.setReporter(postSnapshot.child("Chiranth Bs").
                            child("Road Complaint 1").child("landmark").getValue().toString());
                        adapter.add(complaint);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}