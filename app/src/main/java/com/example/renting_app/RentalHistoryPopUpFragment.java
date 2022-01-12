package com.example.renting_app;

// this is tenant history of renting

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RentalHistoryPopUpFragment extends DialogFragment {
    TenantHistoryAdapter tenantHistoryAdapter;
    RecyclerView rv;

    Button tenB;
    Button agenB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental_history_pop_up, container, false);

        tenB = view.findViewById(R.id.buttonChoose_Ten);
        agenB = view.findViewById(R.id.buttonBack_fromAgency);

        tenB.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),TenantMainHistory.class);
            startActivity(intent); // going to intro layout - REST
        });
        agenB.setOnClickListener(v->{

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    ArrayList<RelTenantProp> getRelFromDB(){
        ArrayList<RelTenantProp> po = null;
        return  po;
    }
}