package com.example.renting_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/// this is for signup form

public class choose_user_Frag extends Fragment {


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

        View view  = inflater.inflate(R.layout.fragment_choose_user, container, false);
        Button choose_tenantB = (Button) view.findViewById(R.id.TenantB_frag2);
        choose_tenantB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent  intent1 = new Intent(getActivity(),Tenant_Sign_Up_activity.class);
                startActivity(intent1);
            }
        } );
        return view;
    }
}