package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.R;


public class User_Profile_frag extends Fragment {

    public User_Profile_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user__profile_frag, container, false);
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
