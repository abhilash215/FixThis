package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;


public class Light_frag extends Fragment {
    public Light_frag()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light_frag, container, false);
    }

    private static final String ARG_SECTION_NUMBER1="section_number";
    public static Light_frag newInstance1(int light)
    {

        Light_frag frag =new Light_frag();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER1,light);
        frag.setArguments(args);
        return frag;
    }
}