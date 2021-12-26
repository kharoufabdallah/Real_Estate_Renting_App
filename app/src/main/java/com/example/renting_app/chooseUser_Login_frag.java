package com.example.renting_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class chooseUser_Login_frag extends Fragment {


    public chooseUser_Login_frag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static chooseUser_Login_frag newInstance(String param1, String param2) {
        chooseUser_Login_frag fragment = new chooseUser_Login_frag();
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

        return inflater.inflate(R.layout.fragment_choose_user__login_frag, container, false);
    }
}