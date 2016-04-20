package com.example.abhiu.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, new RecyclerView_frag())
                .addToBackStack(null)
               .commit();

    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
        }

        return super.onOptionsItemSelected(item);
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
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_replace, Gen_frag.newInstance5(type))
                        .addToBackStack(null)
                        .commit();

            default:
                Toast.makeText(getApplicationContext(),"No complaint selected",Toast.LENGTH_LONG);
        }



    }
}
