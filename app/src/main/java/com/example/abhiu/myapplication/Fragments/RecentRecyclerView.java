package com.example.abhiu.myapplication.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhiu.myapplication.Activities.LoginActivity;
import com.example.abhiu.myapplication.R;
import com.example.abhiu.myapplication.Utilities.Complaint;
import com.firebase.client.Firebase;

public class RecentRecyclerView extends Fragment {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
   //MyFirebaseRecylerViewAdapter myFirebaseRecylerViewAdapter;
    Firebase recycler_firebase = new Firebase(LoginActivity.getFIREBASEREF());
    public RecentRecyclerView() {
        // Required empty public constructor
    }
    public interface LoadRecentComplaint{

        public void loadRecentComplaints(int position,Complaint complaint);

    }
    public static RecentRecyclerView newInstance() {
        RecentRecyclerView fragment = new RecentRecyclerView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_recent_recycler_view, container, false);
        TextView textView = (TextView) view.findViewById(R.id.t);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleViewId_1);
        final LoadRecentComplaint loadRecentComplaint;
        loadRecentComplaint = (LoadRecentComplaint) getContext();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
       /* myFirebaseRecylerViewAdapter = new MyFirebaseRecylerViewAdapter(Complaint.class,R.layout.recent_cardview_layout,
                MyFirebaseRecylerViewAdapter.ComplaintViewHolder.class,recycler_firebase,getContext());
        mRecyclerView.setAdapter(myFirebaseRecylerViewAdapter);*/

        return view;
    }

}
