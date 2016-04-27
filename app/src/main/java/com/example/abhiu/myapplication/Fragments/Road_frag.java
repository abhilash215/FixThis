package com.example.abhiu.myapplication.Fragments;

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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.Activities.MapsActivity;
import com.example.abhiu.myapplication.Activities.NewReq_Activity;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Road_frag extends Fragment {

    FragmentGoogleMap fragmentGoogleMap=new FragmentGoogleMap();
    Complaint cmp = new Complaint();
   public int cnt;
    public String str="";
    ImageView iv;
    Button b;
    Button bc;
    EditText landmark,descr,reporter;
    private static final int REQUEST_CAMERA = 123, SELECT_FILE=1; // integer request code for camera
    private LocationManager locationManager;
   // SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

    Firebase road_firebase = new Firebase(LoginActivity.getFIREBASEREF()).child("Road Complaints");
    /////////////////////////////////////////////////////////////////////////////////////////////////
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
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public Road_frag()
    {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View rootView =  inflater.inflate(R.layout.fragment_road, container, false);

        iv =(ImageView) rootView.findViewById(R.id.camera_road);
        landmark = (EditText) rootView.findViewById(R.id.road_landmark);
        descr = (EditText) rootView.findViewById(R.id.road_desc);
        reporter = (EditText) rootView.findViewById(R.id.user_road);
        EditText editText=(EditText)rootView.findViewById(R.id.edit_loc_road);
        editText.setText("Location");

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//start new activity for image capture
                        //getActivity().startActivityForResult(i, CAMERA_REQUEST);
                        selectImage();
                    }
                });

        b=(Button)rootView.findViewById(R.id.buttonroad);
//        cmp.setLandmark(landmark.getText().toString());
//        cmp.setDescription(descr.getText().toString());
//        cmp.setReporter(reporter.getText().toString());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Complaint successfully registered",Toast.LENGTH_LONG).show();
            Displayfun();
                // cnt = cmp.getCount();

                cnt++;
                cmp.setCount(cnt);
                str = "Road Complaint " + cnt ;
                //road_firebase.setValue(str);

                cmp.setLandmark(landmark.getText().toString());
                cmp.setDescription(descr.getText().toString());
                cmp.setReporter(reporter.getText().toString());
                road_firebase.child(str).setValue(cmp);
            }
        });
        Button bl;
        bl = (Button)rootView.findViewById(R.id.locationroad);
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
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
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences setting = getContext().getSharedPreferences("count", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("count",cmp.getCount()).commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((NewReq_Activity) getActivity())
                .setActionBarTitle("Road/Potholes");
        SharedPreferences setting = getContext().getSharedPreferences("count", Context.MODE_PRIVATE);
        cnt = setting.getInt("count",cmp.getCount());


    }

    private static final String ARG_SECTION_NUMBER="section_number";
    public static  final Road_frag  newInstance(int sectionNumber){
        Road_frag fragment=new Road_frag();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return  fragment;
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
        cmp.setBmp(thumbnail);

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
        cmp.setBmp(bm);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////


    void Displayfun()
    {
        Toast.makeText(getActivity(),"Complaint successfully registered",Toast.LENGTH_LONG).show();
    }
}
