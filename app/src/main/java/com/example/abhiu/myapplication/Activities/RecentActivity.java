package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.abhiu.myapplication.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RecentActivity extends AppCompatActivity {
    private Firebase mRef;
    ListView listView;
    HashMap<String, ?> complaintMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        final ArrayList<HashMap<String, ?>> complaintList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView_id);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://fixthis.firebaseio.com");

        String[] values = new String[]{"Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    complaintMap  = (HashMap<String, ?>) postSnapshot.getValue();
                    complaintList .add(complaintMap);
                    adapter.add(String.valueOf(complaintList));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
//        new Firebase("https://fixthis.firebaseio.com")
//                .addChildEventListener(new ChildEventListener() {
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        adapter.add((String) dataSnapshot.child("Road Complaints").getValue());
//                    }
//
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//                        adapter.remove((String) dataSnapshot.child("text").getValue());
//                    }
//
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    }
//
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                    }
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {}
//
//                });
    }
}