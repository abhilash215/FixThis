package com.example.abhiu.myapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhiu.myapplication.Adapters.HealthAdapter;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Util.APIUtil;
import com.example.abhiu.myapplication.Utilities.ConnectionDetector;
import com.example.abhiu.myapplication.entity.FactualResponse;
import com.example.abhiu.myapplication.entity.HealthCare;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HealthCareAcivity extends AppCompatActivity implements Callback<FactualResponse>, SearchView.OnQueryTextListener,AdapterView.OnItemClickListener {

private SearchView searchView;
    private ProgressDialog progressDialog;
    private MenuItem actionSearch;
    private RecyclerView recyclerView;
    private HealthAdapter healthAdapter;
    private TextView cityTitle;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    List<HealthCare> restaurantList = new ArrayList<>();

    private RelativeLayout frontpage;
    private final String SEARCH_CRITERIA = "Search term should be 3 or more characters";
    private final String SEARCH_RESULTS = "Displaying search results";
    private final String ERROR = "An error occured";
    private final String SEARCH = "Searching healthcare providers in ";
    private final String HEALTH = "HealthCare Providers in ";
    private final String INTERNET_ERROR = "Error:No Internet Connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("HealthCare Finder");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_acivity);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        healthAdapter = new HealthAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(healthAdapter);
        cityTitle = (TextView) findViewById(R.id.cityTitle);
        cityTitle.setText("");
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        frontpage = (RelativeLayout) findViewById(R.id.layout1);
        recyclerView.setVisibility(GONE);
        frontpage.setVisibility(RelativeLayout.VISIBLE);
        fancyAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        actionSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) actionSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    //Function to execute the Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
//        if (id == R.id.about_me) {
//            //Calling Fragment
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.HealthCareActivity, AboutMeFragment.newInstance(R.id.about_me))
//                    .commit();
//            Toast.makeText(getApplicationContext(), "Loading 'About Me' fragment", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        if (id == R.id.mainactivity) {
            //Launching Main Activity
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
           // Toast.makeText(getApplicationContext(), "Launching HealthCareActivity", Toast.LENGTH_SHORT).show();
            return true;
//        }

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResponse(Call<FactualResponse> call, Response<FactualResponse> response) {
        progressDialog.dismiss();
        if (response != null && response.body() != null) {
            List<HealthCare> restaurantList = response.body().getResponse().getData();
            healthAdapter.setRestaurantList(restaurantList);
            frontpage.setVisibility(RelativeLayout.GONE);
            // Delay
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setVisibility(VISIBLE);
                }
            }, 300);
            Toast.makeText(this, SEARCH_RESULTS, Toast.LENGTH_SHORT).show();
        }
    }

   /* @Override
    public void onFailure(Call<FactualResponse> call, Throwable t) {

    }*/


  /*  healthAdapter.setOnItemClickListener(new HealthAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
            mListener.onListItemSelected(position, movie);
            //v.setOnClickListener();
            //onItemClick(v, position);
        }*/


    @Override
    public void onFailure(Call<FactualResponse> call, Throwable t) {
        Log.e("TNAPP", "API Error");
        progressDialog.dismiss();
        Toast.makeText(this, ERROR, Toast.LENGTH_SHORT).show();
    }

    // search function
    @Override
    public boolean onQueryTextSubmit(final String query) {
        if (isInternetPresent) {

            if (query.length() >= 3) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(SEARCH + query);
                progressDialog.show();
                APIUtil.getHealthCaresByLocality(query, HealthCareAcivity.this);
                actionSearch.collapseActionView();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cityTitle.setText(HEALTH + query);
                    }
                }, 900);
            } else {
                Toast.makeText(this, SEARCH_CRITERIA, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, INTERNET_ERROR, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //@Override
    public void onListItemSelected(int position, List<HealthCare> restaurantList) {
//        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.HealthCareActivity, DisplayFragment.newInstance(restaurantList.get(position)));
//        transaction.commit();

    }

    /*  @Override
      public boolean onQueryTextSubmit(String query) {
          return false;
      }
  */
    @Override
    public boolean onQueryTextChange(String s) {
        // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
        return false;
    }

    //Animation for RecyclerView
    private void fancyAnimation() {
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(healthAdapter);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaInAnimationAdapter));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface OnItemSelectedListener {
    }
}
