package com.example.renting_app;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class choose_user_Frag extends Fragment {


    Button choose_agency;
    Button  choose_tenant;


    public choose_user_Frag() {
        // Required empty public constructor
    }


    public static choose_user_Frag newInstance(String param1, String param2) {
        choose_user_Frag fragment = new choose_user_Frag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.choose_agency= (Button) getActivity().findViewById(R.id.AgencyB_frag);
//        choose_agency.setOnClickListener(v -> {
//            // move into sign in/up intent
//        } );
        return inflater.inflate(R.layout.fragment_choose_user, container, false);
    }
}