package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;


public class Road_frag extends Fragment {
    public Road_frag()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_road, container, false);




    }

    private static final String ARG_SECTION_NUMBER="section_number";

    public static  final Road_frag newInstance(int sectionNumber){
        Road_frag fragment=new Road_frag();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return  fragment;
    }



}
