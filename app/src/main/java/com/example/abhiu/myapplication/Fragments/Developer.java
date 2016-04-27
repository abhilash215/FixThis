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
public class Developer extends Fragment {


    public Developer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developer, container, false);
    }


    private static final String ARG_SECTION_NUMBER="section_number";
    public static  final Developer  newInstancedev(int sectionNumber){
        Developer fragment=new Developer();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return  fragment;
    }
}
