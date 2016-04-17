package com.example.abhiu.myapplication.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.abhiu.myapplication.R;

public class NewReq_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("New Requests");

        ListView list =(ListView)findViewById(R.id.listView);
        String[] my={" Road" , " Light"};

      //  ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.activity_new_req_,android.R.id.text_list);
     //   list.setAdapter(adapter);





    }

}
