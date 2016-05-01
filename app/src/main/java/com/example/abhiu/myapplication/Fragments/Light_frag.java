package com.example.abhiu.myapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.Activities.NewReq_Activity;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
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


public class Light_frag extends Fragment {

ImageView iv;
    Button b1;
    Button bc;
    int pagerCount=0;
    ImageView imvLocation;
    private static final int REQUEST_CAMERA = 123, SELECT_FILE=1; // integer request code for camera
    int[] mResources = {
            R.drawable.light,
            R.drawable.light2,R.drawable.light3 };
    ViewPager mViewPager;
    MyPagerAdapter myPagerAdapter;
    Complaint cmp = new Complaint();
    public int lightComplaintCount;
    EditText reporter,poleNumber,descr;
    EditText locationAddress;
    Spinner lightSpinner;
    public String str = "";
    ////////////////////////////////////////////////////////////////////////////////////////////////
    Firebase light_firebase = new Firebase(LoginActivity.getFIREBASEREF()).child("LightComplaints");
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
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

    public Light_frag() {

    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences setting = getContext().getSharedPreferences("lightcount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("lightcount", cmp.getCount()).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  if(requestCode==CAMERA_REQUEST && resultCode== getActivity().RESULT_OK)
        {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            iv.setImageBitmap(bitmap);
            Toast.makeText(getContext(), "Image Saved in Gallery", Toast.LENGTH_SHORT).show();
        }*/
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((NewReq_Activity) getActivity())
                .setActionBarTitle("Lights");
        SharedPreferences setting = getContext().getSharedPreferences("lightcount", Context.MODE_PRIVATE);
      lightComplaintCount = setting.getInt("lightcount", cmp.getCount());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView=  inflater.inflate(R.layout.fragment_light_frag, container, false);
        ////////////////////////////// find layout fields ///////////////////////////////////////////
        reporter = (EditText) rootView.findViewById(R.id.user_light);
        locationAddress =  (EditText) rootView.findViewById(R.id.edit_loc_light);
        poleNumber = (EditText) rootView.findViewById(R.id.pole_number);
        descr = (EditText) rootView.findViewById(R.id.road_desc);
        final EditText phonelight=(EditText)rootView.findViewById(R.id.phone_light);
        ///////////////////////////////////////////////////////////////////////////////////////////
        final Spinner spinner=(Spinner)rootView.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.light_defects,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)rootView.findViewById(R.id.maincollapsinglight);
        collapsingToolbarLayout.setTitle("Light");
            /////////////////////////////////////// viewpager //////////////////////////////////////
        myPagerAdapter = new MyPagerAdapter(getContext());
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_id);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setPageTransformer(true, new RotateUpTransformer());
        ////////////timer //////////////
        Timer timer  = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
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
        ///////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////



        b1=(Button)rootView.findViewById(R.id.buttonlight);
        iv =(ImageView) rootView.findViewById(R.id.camera_light);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//start new activity for image capture
                //getActivity().startActivityForResult(i, CAMERA_REQUEST);
                selectImage();
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ////////////////////////////////////////////////////////////////////////////////////////////////////
                lightComplaintCount++;
                cmp.setCount(lightComplaintCount);
                str = "LightComplaint " + lightComplaintCount;
                //road_firebase.setValue(str);
                ////////////////////////////////find layout params////////////////////////////////
                cmp.setDescription(descr.getText().toString());
                cmp.setReporter(reporter.getText().toString());
                cmp.setPoleNumber(poleNumber.getText().toString());
                //////////////////////////// current timestamp ////////////////////////////////////
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                String timeString = dateFormat.format(date);
                cmp.setCurrentTime(timeString);
                cmp.setPhoneNumber(phonelight.getText().toString());
                /////////////////////////////////////////////////////////////////////////////////
                SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("address", Context.MODE_PRIVATE);
                locationAddress.setText(sharedPreferences2.getString("address", "some address"));
                //////////////////////////////////spinner value ////////////////////////////////////
                cmp.setLightTypeComplaint(spinner.getSelectedItem().toString());
                //////////////////////////////get username and mail from sharedpref//////////////////////////
                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                String   name = sharedPreferences1.getString("name", "Anonymous"+lightComplaintCount);
                cmp.setuName(name);
                String  email = sharedPreferences1.getString("email","Anonymous"+lightComplaintCount);
                String email2=email.replaceAll("[.]","");
                cmp.setuEmail(email);
                //////////////////////////get firebase uid //////////////////////////////////
                AuthData authData= light_firebase.getAuth();
                    String type = (String) authData.getProvider();
                    String uid = authData.getUid();
                cmp.setuId(uid);
                if(type.matches("PASSWORD") && type != null) light_firebase.child(uid).child(str).setValue(cmp);
                else light_firebase.child(email2+lightComplaintCount).child(str).setValue(cmp);
                sendSMSMessage(cmp.getPhoneNumber(),uid);
                Toast.makeText(getContext(),"Complaint submitted successfully with complaint id"+uid,Toast.LENGTH_LONG).show();
                ////////////////////////// go to main page on submit /////////////////////////////////
                Intent i=new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                //////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        });


        bc=(Button)rootView.findViewById(R.id.btncancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),NewReq_Activity.class);
                startActivity(i);
            }
        });
        ///////////////////////////////Location google map///////////////////////////////////////
        imvLocation = (ImageView) rootView.findViewById(R.id.gpsId);
        imvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.light_coordinator_id,
                        FragmentGoogleMap.newInstance())
                        .addToBackStack("mapTAG").
                        commit();
            }
        });
                ///////////////////////////////////////////////////////////////////////////////////////////
        return rootView;
    }

    private static final String ARG_SECTION_NUMBER1 = "section_number";

    public static Light_frag newInstance1(int light) {

        Light_frag frag = new Light_frag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER1, light);
        frag.setArguments(args);
        return frag;
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
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
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
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////

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
            container.removeView((LinearLayout) object);
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
    }

    //method to set textview address
    public void updateLocation(String address){
        locationAddress.setText(address);
        cmp.setStreetAddress(locationAddress.getText().toString());
    }

    protected void sendSMSMessage(String num,String uid) {
        Log.i("Send SMS", "");


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null," ight complaint has been successfully registered ", null, null);
            Toast.makeText(getContext(), "check your phone", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}