package com.example.abhiu.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.abhiu.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Developer extends Fragment {

ImageView abhi;
    public Developer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_developer, container, false);
        abhi=(ImageView)view.findViewById(R.id.imageView_abhi);
        RelativeLayout r=(RelativeLayout)view.findViewById(R.id.developers);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.combined));
            }
        });
        return view;
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
