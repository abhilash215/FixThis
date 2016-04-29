package com.example.abhiu.myapplication.Fragments;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.R;
import com.firebase.client.Firebase;



public class User_Profile_frag extends Fragment {
    LoginActivity loginActivity;
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
        editText.setText(strGmail);
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

}
