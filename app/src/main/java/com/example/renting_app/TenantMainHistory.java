package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TenantMainHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main_history);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rec_view_hist_tenant);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<RelTenantProp> po  = new ArrayList<RelTenantProp>();//= getRelFromDB();
        po.add(new RelTenantProp(4,6,"Haifa","Murad Ismail"));

        // tenantHistoryAdapter = new TenantHistoryAdapter(po,this.getActivity());
        rv.setAdapter(new TenantHistoryAdapter(po,this));

    }
}