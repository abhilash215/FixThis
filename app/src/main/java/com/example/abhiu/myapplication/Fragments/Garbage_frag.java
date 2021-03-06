package com.example.abhiu.myapplication.Fragments;

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
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
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


public class Garbage_frag extends Fragment {

    ImageView iv;
    Button bc;
    int pagerCount=0;

    private static final int REQUEST_CAMERA = 123, SELECT_FILE=1; // integer request code for camera
    int[] mResources = {
            R.drawable.garbagedumping,
            R.drawable.garbage2,R.drawable.garbage3,R.drawable.garbage3 };
    ViewPager mViewPager;
    MyPagerAdapter myPagerAdapter;
    Complaint cmp = new Complaint();
    public int garbageComplaintCount;
    EditText reporter,descr;
    EditText locationAddress;
    Spinner garbageSpinner;
    public String str = "";
    Firebase garbage_firebase = new Firebase(LoginActivity.getFIREBASEREF()).child("GarbageComplaints");
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

    public Garbage_frag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((NewReq_Activity) getActivity())
                .setActionBarTitle("Garbage ");
        SharedPreferences setting = getContext().getSharedPreferences("garcount", Context.MODE_PRIVATE);
        garbageComplaintCount = setting.getInt("garcount", cmp.getCount());
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences setting = getContext().getSharedPreferences("garcount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("garcount", cmp.getCount()).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_garbage_frag, container, false);
        ////////////////////////////// find layout fields ///////////////////////////////////////////
        reporter = (EditText) view.findViewById(R.id.user_garbage);
        locationAddress =  (EditText) view.findViewById(R.id.edit_loc_garbage);;
        descr = (EditText) view.findViewById(R.id.editText5);
        final EditText phonegsr=(EditText)view.findViewById(R.id.phone_gar);
        //////////////////////////////////////////////////////////////////////////////////
       final Spinner spinner=(Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.dumpings,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       Button b3=(Button)view.findViewById(R.id.buttongarbage);
        iv =(ImageView) view.findViewById(R.id.camera_garbage);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//start new activity for image capture
                //getActivity().startActivityForResult(i, CAMERA_REQUEST);
                selectImage();
            }
        });


        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)view.findViewById(R.id.maincollapsinggarbage);
        collapsingToolbarLayout.setTitle("Garbage");
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////////////////////////////////////////////////////
               garbageComplaintCount++;
                cmp.setCount(garbageComplaintCount);
                str = "GarbageComplaint " + garbageComplaintCount;
                //road_firebase.setValue(str);
                ////////////////////////////////find layout params////////////////////////////////
                cmp.setDescription(descr.getText().toString());
                cmp.setReporter(reporter.getText().toString());

                //////////////////////////// current timestamp ////////////////////////////////////
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                String timeString = dateFormat.format(date);
                cmp.setCurrentTime(timeString);
                cmp.setPhoneNumber(phonegsr.getText().toString());
                /////////////////////////////////////////////////////////////////////////////////
                SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("address", Context.MODE_PRIVATE);
                locationAddress.setText(sharedPreferences2.getString("address", "some address"));
                //////////////////////////////////spinner value ////////////////////////////////////
                cmp.setLightTypeComplaint(spinner.getSelectedItem().toString());
                //////////////////////////////get username and mail from sharedpref//////////////////////////
                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                String   name = sharedPreferences1.getString("name", "Anonymous"+garbageComplaintCount);
                cmp.setuName(name);
                String  email = sharedPreferences1.getString("email","Anonymous"+garbageComplaintCount);
                String email2=email.replaceAll("[.]","");
                cmp.setuEmail(email);
                //////////////////////////get firebase uid //////////////////////////////////
                AuthData authData= garbage_firebase.getAuth();
                String type = (String) authData.getProvider();
                String uid = authData.getUid();
                cmp.setuId(uid);
                if(type.matches("PASSWORD") && type != null) garbage_firebase.child(uid).child(str).setValue(cmp);
                else garbage_firebase.child(email2+garbageComplaintCount).child(str).setValue(cmp);
                sendSMSMessage(cmp.getPhoneNumber(),uid);
                Toast.makeText(getContext(),"Complaint submitted successfully with complaint id"+uid,Toast.LENGTH_LONG).show();
                ////////////////////////// go to main page on submit /////////////////////////////////
                Intent i=new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                //////////////////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        });


        bc=(Button) view.findViewById(R.id.btncancel);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),NewReq_Activity.class);
                startActivity(i);
            }
        });

        /////////////////////////////////////// viewpager //////////////////////////////////////
        myPagerAdapter = new MyPagerAdapter(getContext());
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_id);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setPageTransformer(true, new RotateDownTransformer());
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


        ///////////////////////////////Location google map///////////////////////////////////////
       ImageView imvLocation = (ImageView) view.findViewById(R.id.garbage_gps_id);
        imvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.garbage_coord_id,
                        FragmentGoogleMap.newInstance())
                        .addToBackStack("mapTAG").
                        commit();
            }
        });


        return  view;
    }

    private static final String ARG_SECTION_NUMBER4 = "section_number";
    public static Fragment newInstance3(int garbage) {

            Garbage_frag frag = new Garbage_frag();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER4, garbage);
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

    public void updateLocation(String address){
        locationAddress.setText(address);
        cmp.setStreetAddress(locationAddress.getText().toString());
    }
    protected void sendSMSMessage(String num,String uid) {
        Log.i("Send SMS", "");


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null,"your garbage complaint was successfully  registered ", null, null);
            Toast.makeText(getContext(), "check your phone", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}

