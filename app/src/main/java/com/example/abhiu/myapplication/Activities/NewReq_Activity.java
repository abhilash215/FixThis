package com.example.abhiu.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.abhiu.myapplication.Fragments.Garbage_frag;
import com.example.abhiu.myapplication.Fragments.Gen_frag;
import com.example.abhiu.myapplication.Fragments.Leak_frag;
import com.example.abhiu.myapplication.Fragments.Light_frag;
import com.example.abhiu.myapplication.Fragments.Road_frag;
import com.example.abhiu.myapplication.R;

public class NewReq_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_req_);
      //  getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag()).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("New Requests");

        Button b1=(Button)findViewById(R.id.button_road);
        Button b2=(Button)findViewById(R.id.button_light);
        Button b3=(Button)findViewById(R.id.button_leak);
        Button b4=(Button)findViewById(R.id.button_dump);
        Button b5=(Button)findViewById(R.id.button_other);
        TextView t1=(TextView)findViewById(R.id.user_road);

       /* t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.road, User_Profile_frag.newInstanceuser(R.id.user_profile))
                        .commit();
            }
        });*/

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




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.feedback)
        {
            return  true;
        }

        if(id==R.id.about)
        {
            return true;
        }

        if(id==R.id.home)
        {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
