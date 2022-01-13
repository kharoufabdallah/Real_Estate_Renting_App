package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TenantMainHistory extends AppCompatActivity {

    Button btnback;
    String email ;
    String choose;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main_history);

        email=getIntent().getStringExtra("tenant_email");
        choose=getIntent().getStringExtra("agency_or_tenant");

        btnback=findViewById(R.id.back_tomain_histttttttttt);
        btnback.setOnClickListener(v -> {
            Intent intent = new Intent(TenantMainHistory.this, UI_Activity.class);
            intent.putExtra("tenant_email",email);

            intent.putExtra("agency_or_tenant","0");
            startActivity(intent); // going to intro layout - REST
            finish();
        });
        RecyclerView rv = (RecyclerView) findViewById(R.id.rec_view_hist_tenant);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this,"email"+email,Toast.LENGTH_SHORT).show();
        ArrayList<RelTenantProp> po  = new ArrayList<RelTenantProp>();//= getRelFromDB();
        po = getReservedProps(email);
//        po.add(new RelTenantProp(4,6,"Haifa","Murad Ismail"));
//        po.add(new RelTenantProp(2   ,3,"Akkaa","Murad Ismail"));
//        po.add(new RelTenantProp(1,32,"Yafa","Murad Ismail"));
        po.add(new RelTenantProp(4432,624,"Beirut","Murad Ismail"));
//        po.add(new RelTenantProp(442342,6576,"Damscus","Murad Ismail"));
//        po.add(new RelTenantProp(43345,534,"Jerusalem","Murad Ismail"));
//        po.add(new RelTenantProp(435,534,"Hebron","Murad Ismail"));

        // tenantHistoryAdapter = new TenantHistoryAdapter(po,this.getActivity());
        rv.setAdapter(new TenantHistoryAdapter(po,this));

    }

    public ArrayList<RelTenantProp> getReservedProps(String email)
    {
        ArrayList<RelTenantProp> prop_list = new ArrayList<RelTenantProp>();
        db = new DataBaseHelper(TenantMainHistory.this);
        prop_list = db.store_recs_in_al_2nd_version(email);
        return prop_list;
    }
}