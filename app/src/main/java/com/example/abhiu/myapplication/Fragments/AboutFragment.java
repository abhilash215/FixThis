package com.example.abhiu.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    private static final String ARG_SECTION_NUMBER01="section_number";
    public static  final AboutFragment  newInstanceAbout(int aboutapp){
        AboutFragment fragment=new AboutFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER01, aboutapp);
        fragment.setArguments(args);
        return  fragment;
    }
}
