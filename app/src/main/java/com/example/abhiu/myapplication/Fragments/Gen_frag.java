package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;


public class Gen_frag extends Fragment {


    public Gen_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gen_frag, container, false);
    }


    private static final String ARG_SECTION_NUMBER5 = "section_number";

    public static Gen_frag newInstance5(int general) {
        Gen_frag frag = new Gen_frag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER5, general);
        frag.setArguments(args);
        return frag;
    }

}
