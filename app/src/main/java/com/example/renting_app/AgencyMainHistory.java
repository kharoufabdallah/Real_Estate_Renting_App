package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class AgencyMainHistory extends AppCompatActivity {

    Button back;
    String emm;
    String choose;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main_history);


        emm = getIntent().getStringExtra("tenant_email");
        choose=getIntent().getStringExtra("agency_or_tenant");

        back = findViewById(R.id.back_tomain_histttttttttt);


        RecyclerView rv = (RecyclerView) findViewById(R.id.rec_view_hist_tenant);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<RelAgencyProp> po  = new ArrayList<RelAgencyProp>();//= getRelFromDB();
        po = getReservedProps(emm);
        po.add(new RelAgencyProp(1,8,"Poznan","Kharoufco"));

        back.setOnClickListener(v ->
        {
            Intent intent = new Intent(AgencyMainHistory.this, UI_Activity.class);
            intent.putExtra("tenant_email",getIntent().getStringExtra("tenant_email"));
            intent.putExtra("agency_or_tenant","1");
            startActivity(intent); // going to intro layout - REST
            finish();
            ////// TODO :  back to UI_activity =============>>>>> add it to tenant main history
        });
        // tenantHistoryAdapter = new TenantHistoryAdapter(po,this.getActivity());
        rv.setAdapter(new AgencyHistoryAdpater(po,this));
    }
    public ArrayList<RelAgencyProp> getReservedProps(String email)
    {
        ArrayList<RelAgencyProp> prop_list = new ArrayList<RelAgencyProp>();
        db = new DataBaseHelper(AgencyMainHistory.this);
        prop_list = db.store_recs_in_al_3rd_version(email);
        return prop_list;
    }
}