package com.example.abhiu.myapplication.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.abhiu.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link New_Request.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link New_Request#newInstance} factory method to
 * create an instance of this fragment.
 */
public class New_Request extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;//remove this later
    private Button testButton; // remove this later

    private OnFragmentInteractionListener mListener;

    public New_Request() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new__request, container, false);
        testButton = (Button) view.findViewById(R.id.test_button_id);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.req_fragment,PostRequest.newInstance(mParam1,mParam2)).addToBackStack(null).commit();
            }
        });
        return view;
    }





    private static final String ARG_SECTION_NUMBER = "section_number";

    public static final New_Request newInstance(int sectionNumber)
    {
        New_Request fragment = new New_Request();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
