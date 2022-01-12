package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class AgencyMainHistory extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main_history);


        back = findViewById(R.id.back_tomain_histttttttttt);

        back.setOnClickListener(v ->
        {
            ////// TODO :  back to UI_activity =============>>>>> add it to tenant main history
        });
        RecyclerView rv = (RecyclerView) findViewById(R.id.rec_view_hist_tenant);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<RelAgencyProp> po  = new ArrayList<RelAgencyProp>();//= getRelFromDB();
        po.add(new RelAgencyProp(1,8,"Poznan","Kharoufco"));

        // tenantHistoryAdapter = new TenantHistoryAdapter(po,this.getActivity());
        rv.setAdapter(new AgencyHistoryAdpater(po,this));
    }
}