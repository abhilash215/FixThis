package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.Activities.MainActivity;
import com.example.abhiu.myapplication.R;


public class Recent_frag extends Fragment {

    public Recent_frag() {
        // Required empty public constructor
    }
    private static final String ARG_SECTION_NUMBER10 = "section_number";
    public static Recent_frag newInstancerecent(int recent) {
        Recent_frag fragment = new Recent_frag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER10, recent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity())
                .setActionBarTitle("Recent Requests ");
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_frag, container, false);
    }


}
