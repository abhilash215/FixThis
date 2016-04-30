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
    LinearLayoutManager mLinearLayoutManager;
    //RecyclerView.LayoutManager mLayoutManager;
    MyRecyclerViewAdapter mRecyclerViewAdapter;
   // private onListItemSelectedListener mListener;


    public RecyclerView_frag() {
        // Required empty public constructor
    }

    //interface to handle loading of fragment from recycler view
    public interface loadFragment{
        public void loadComplaint(int type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootview = inflater.inflate(R.layout.fragment_recycler_view_frag, container, false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycleViewId);
        mRecyclerView.setHasFixedSize(true);
        /////---------------///////////////////////////////////////////////
        //mLayoutManager = new LinearLayoutManager(getActivity());
       // mRecyclerView.setLayoutManager(mLayoutManager);
        //////////////////*********************--------------------////////////
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        ///////////////////////////////////////////////////////////////////////////////////////////
        final loadFragment loadFragmentInterface;

        loadFragmentInterface = (loadFragment) getContext();

        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                loadFragmentInterface.loadComplaint(position);
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return rootview;
    }



}
