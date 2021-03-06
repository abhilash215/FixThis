package com.example.abhiu.myapplication.Fragments;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.Activities.NewReq_Activity;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
import com.example.abhiu.myapplication.Utilities.GpsLocation;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Road_frag extends Fragment {
    View rootView;
    GpsLocation gpsLocation;
    String userAddress="";
    FragmentGoogleMap fragmentGoogleMap = new FragmentGoogleMap();
    Complaint cmp = new Complaint();
    public int cnt;
    int pagerCount=0;
    public String str = "";
    ImageView iv;
    Button b;
    Button bc;
    ActionBar actionBar;
    Toolbar mToolbar;
    EditText landmark, descr, reporter,locationAddress;
    private static final int REQUEST_CAMERA = 123, SELECT_FILE = 1; // integer request code for camera
    private LocationManager locationManager;
    // SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    Firebase road_firebase = new Firebase(LoginActivity.getFIREBASEREF()).child("RoadComplaints");
    /////////////////////////////////////////////////////////////////////////////////////////////////
    int[] mResources = {
            R.drawable.road,
            R.drawable.road2,R.drawable.road3,R.drawable.road4,R.drawable.road
    };
    ViewPager mViewPager;
    MyPagerAdapter myPagerAdapter;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public Road_frag() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            Firebase.setAndroidContext(getContext());
        rootView = inflater.inflate(R.layout.fragment_road, container, false);
        /////////////////////////////////////// viewpager //////////////////////////////////////
        myPagerAdapter = new MyPagerAdapter(getContext());
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_id);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setPageTransformer(true, new RotateUpTransformer());

         actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.hide();
        mToolbar = (Toolbar) rootView.findViewById(R.id.maintoolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ////////////timer //////////////
        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pagerCount <= 5) {
                           mViewPager.setCurrentItem(pagerCount);
                            pagerCount++;
                        } else {
                         pagerCount = 0;
                            mViewPager.setCurrentItem(pagerCount);
                        }
                    }
                });
                }
            }
        }, 500, 3000);

        iv = (ImageView) rootView.findViewById(R.id.camera_road);
        landmark = (EditText) rootView.findViewById(R.id.road_landmark);
        descr = (EditText) rootView.findViewById(R.id.road_desc);
        reporter = (EditText) rootView.findViewById(R.id.user_road);
       locationAddress = (EditText) rootView.findViewById(R.id.edit_loc_road);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
  //      String strAddr = sharedPreferences.getString("address",gpsLocation.getAddress());
   //   if(sharedPreferences!=null) locationAddress.setText();
        EditText editText = (EditText) rootView.findViewById(R.id.edit_loc_road);

        ///////////// collapsing toolbar ////////////////////////////////////////////////////////////
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle("Road");
        //ImageView imageView = (ImageView) rootView.findViewById(R.id.mainbackdrop);
        //imageView.setImageResource(R.drawable.road);
        ////////////////////////////////////////////////////////////////////////////////
       final EditText ph=(EditText) rootView.findViewById(R.id.phone_road_id);
        editText.setText("Location");

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//start new activity for image capture
                //getActivity().startActivityForResult(i, CAMERA_REQUEST);
                selectImage();
            }
        });

        b = (Button) rootView.findViewById(R.id.buttonroad);
//        cmp.setLandmark(landmark.getText().toString());
//        cmp.setDescription(descr.getText().toString());
//        cmp.setReporter(reporter.getText().toString());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Complaint successfully registered",Toast.LENGTH_LONG).show();
              //  Displayfun();
                // cnt = cmp.getCount();

                cnt++;
                cmp.setCount(cnt);
                str = "RoadComplaint " + cnt;
                //road_firebase.setValue(str);

                cmp.setLandmark(landmark.getText().toString());
                cmp.setDescription(descr.getText().toString());
                cmp.setReporter(reporter.getText().toString());
                //////////////////////////// current timestamp ////////////////////////////////////
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                String timeString = dateFormat.format(date);
                cmp.setCurrentTime(timeString);
                ///////////////////////////////////////////////////////////////////////////////////////////////////////
                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
               String   name = sharedPreferences1.getString("name", "Anonymous"+cnt);
                cmp.setuName(name);
               String  email = sharedPreferences1.getString("email","Anonymous"+cnt);
                String email1=email.replaceAll("[.]","")+ cnt;
                cmp.setuEmail(email);
                cmp.setPhoneNumber(ph.getText().toString());
                //////////////////////////////////////////////////////////////////////////////////

                AuthData authData= road_firebase.getAuth();
            //    String type = (String) authData.getProvider();
                String uid = authData.getUid();
              //  String uid = "something";
                cmp.setuId(uid);
                //if(type.matches("PASSWORD")) road_firebase.child(uid).child(str).setValue(cmp);
                //else
                    road_firebase.child(email1+cnt).child(str).setValue(cmp);
                Toast.makeText(getContext(),"Complaint submitted successfully with id "+uid,Toast.LENGTH_SHORT).show();
                sendSMSMessage(cmp.getPhoneNumber(),uid);
                Intent i=new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                //////////////////////////////////////////////////////////////////////////////////
            }
        });


        ImageView imvLoc = (ImageView) rootView.findViewById(R.id.road_gps_id);
        imvLoc.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          getActivity().getSupportFragmentManager()
                                                  .beginTransaction()
                                                  .replace(R.id.road_coordinator_id, new FragmentGoogleMap().newInstance(),"mapTAG")
                                                  .addToBackStack("mapTAG")
                                                  .commit();
                                      }
                                  });

                bc = (Button) rootView.findViewById(R.id.btncancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewReq_Activity.class);
                startActivityForResult(i,1);

            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences setting = getContext().getSharedPreferences("count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("count", cmp.getCount()).commit();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((NewReq_Activity) getActivity())
                .setActionBarTitle("Road/Potholes");
        SharedPreferences setting = getContext().getSharedPreferences("count", Context.MODE_PRIVATE);
        cnt = setting.getInt("count", cmp.getCount());

    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final Road_frag newInstance(int sectionNumber) {
        Road_frag fragment = new Road_frag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        iv.setImageBitmap(thumbnail);
        iv.setTransitionName("animationName");
        cmp.setBmp(thumbnail);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        iv.setImageBitmap(bm);
        cmp.setBmp(bm);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////


    void Displayfun() {
        Toast.makeText(getActivity(), "Complaint successfully registered", Toast.LENGTH_LONG).show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //////////// adapter class here ////////////////
    public class MyPagerAdapter extends PagerAdapter {
        int count;
        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(Context context) {
            super();
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.collapseImages);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }


    }// end of pager adapter class

    //method to set textview address
    public void updateLocation(String address){
        locationAddress.setText(address);
        cmp.setStreetAddress(locationAddress.getText().toString());
    }
    protected void sendSMSMessage(String num,String uid) {
        Log.i("Send SMS", "");


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null,"Your road complaint was successfully registered", null, null);
            Toast.makeText(getContext(), "check your phone", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}//end of main class
