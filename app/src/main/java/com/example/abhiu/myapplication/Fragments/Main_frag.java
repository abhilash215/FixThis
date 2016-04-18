package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.R;


public class Main_frag extends Fragment {

    public Main_frag() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_frag, container, false);
    }

    private static final String ARG_SECTION_NUMBER2="section_number";
    public static Main_frag newInstance3(int main_frag) {
        Main_frag frag =new Main_frag();
        Bundle args=new Bundle();
        args.putInt(ARG_SECTION_NUMBER2,main_frag);
        frag.setArguments(args);
        return frag;

    }
}
