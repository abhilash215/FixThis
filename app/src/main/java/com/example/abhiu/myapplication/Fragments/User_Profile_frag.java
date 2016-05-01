package com.example.abhiu.myapplication.Fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;



public class User_Profile_frag extends Fragment {
    LoginActivity loginActivity;
    User user = new User();
    Button buttonSubmit;
    EditText fName,lName,phNo,email;
    Firebase user_ref = new Firebase(loginActivity.getFIREBASEREF()).child("UserList");
    public User_Profile_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_user__profile_frag, container, false);
        EditText editText = (EditText) rootView.findViewById(R.id.editText);
        String strGmail = null;
        try {
            Account[] accounts = AccountManager.get(getActivity()).getAccounts();
            Log.e("PIKLOG", "Size: " + accounts.length);
            for (Account account : accounts) {

                String possibleEmail = account.name;
                String type = account.type;

                if (type.equals("com.google")) {

                    strGmail = possibleEmail;
                    Log.e("PIKLOG", "Emails: " + strGmail);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            strGmail = null;
        }
        AuthData authData= user_ref.getAuth();
       final String uid = authData.getUid();
        fName = (EditText) rootView.findViewById(R.id.editText2);
        lName = (EditText) rootView.findViewById(R.id.editText4);
        phNo = (EditText) rootView.findViewById(R.id.editText3);
        email = (EditText) rootView.findViewById(R.id.editText);
        buttonSubmit = (Button) rootView.findViewById(R.id.button2);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     user.setfName(fName.getText().toString());
                    user.setlName(lName.getText().toString());
                user.setPhoneNumber(phNo.getText().toString());
                user.setUserEmail(email.getText().toString());
                user_ref.child(uid).setValue(user);
                Toast.makeText(getContext(),"Details Submitted Successfully",Toast.LENGTH_SHORT).show();
                sendSMSMessage(user.getPhoneNumber(),uid);
            }
        });
        return  rootView;
    }

    private static final String ARG_SECTION_NUMBERUSER="section_number";
    public static Fragment newInstanceuser(int user_profile) {

        User_Profile_frag fragment=new User_Profile_frag();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBERUSER, user_profile);
        fragment.setArguments(args);
        return  fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle("User Profile");
    }
    protected void sendSMSMessage(String num,String uid) {
        Log.i("Send SMS", "");


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num, null,"your complaint id is "+uid, null, null);
            Toast.makeText(getContext(), "check your phone", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
