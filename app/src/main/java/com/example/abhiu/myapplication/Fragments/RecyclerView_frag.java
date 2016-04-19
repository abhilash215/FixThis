package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhiu.myapplication.Adapters.MyRecyclerViewAdapter;
import com.example.abhiu.myapplication.R;


public class RecyclerView_frag extends Fragment
{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
   // private onListItemSelectedListener mListener;


    public RecyclerView_frag() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_recycler_view_frag, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootview;
    }



}
