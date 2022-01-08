package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AgencyProfile extends AppCompatActivity {

    TextView ag_email;
    TextView ag_phone;
    TextView ag_name;
    Button back;

    String agency_email;
    String agency_name;
    String agency_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_profile);

        ag_email  = findViewById(R.id.tv_AgencyEmail);
        ag_phone  = findViewById(R.id.tvAgencyPhone);
        ag_name  = findViewById(R.id.tvAgency_name);
        back     = findViewById(R.id.buttonBack_fromAgency);

        agency_email = getIntent().getStringExtra("agency_email_to_prof");
        agency_name= getIntent().getStringExtra("agency_name");


        ag_email.setText(agency_email);
        ag_name.setText(agency_name);

        agency_phone = getphonefromdatabase(agency_name,agency_email);
        ag_phone.setText(agency_phone);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(AgencyProfile.this, UI_Activity.class);
            intent.putExtra("agency_name", agency_name);
            intent.putExtra("tenant_email", agency_email);
            intent.putExtra("agency_or_tenant","1");
            startActivity(intent);
            finish();
        });
    }

   public String getphonefromdatabase(String name, String email)
    {
        String phone = "";
        DataBaseHelper db = new DataBaseHelper(this);
        phone = db.getPhoneAgency(name,email);
        return phone;
    }
}