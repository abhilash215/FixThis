package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;


public class Garbage_frag extends Fragment {


    public Garbage_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garbage_frag, container, false);
    }
    private static final String ARG_SECTION_NUMBER4 = "section_number";
    public static Fragment newInstance3(int garbage) {

            Garbage_frag frag = new Garbage_frag();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER4, garbage);
            frag.setArguments(args);
            return frag;
        }
    }

