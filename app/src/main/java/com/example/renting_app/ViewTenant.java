package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTenant extends AppCompatActivity {
    TextView fn,ln,oc,gen,nat,phone,month,fam;
    EditText agem,agpas;

    Button acc,rej;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenant);

        String  emaily = getIntent().getStringExtra("emailyy");
        String cityy = getIntent().getStringExtra("cityy");

        Toast.makeText(this,emaily,Toast.LENGTH_SHORT).show();

        acc = findViewById(R.id.acceptapp);
        rej = findViewById(R.id.rejectapp);

        fn  = findViewById(R.id.tfname);
        ln  = findViewById(R.id.tlname);
        oc  = findViewById(R.id.tocc);
        gen  = findViewById(R.id.tgencder);
        nat  = findViewById(R.id.tnat);
        phone  = findViewById(R.id.tphone);
        month  = findViewById(R.id.tgms);
        fam  = findViewById(R.id.tfam);


        agem = findViewById(R.id.agem);
        agpas = findViewById(R.id.agepas);
        agem.setVisibility(View.INVISIBLE);
        agpas.setVisibility(View.INVISIBLE);

        submit = findViewById(R.id.subbbinnn);
        submit.setVisibility(View.INVISIBLE);

        Tenant ten = get_attrsfrom_email("dsd",emaily);
        fn.setText(ten.getFirst_name());
        ln.setText(ten.getLast_name());
        oc.setText(ten.getOccupation());
        gen.setText(ten.getGender());
        nat.setText(ten.getNationality());
        phone.setText(ten.getPhone());
        month.setText(Double.toString(ten.getGMS()));
        fam.setText(Integer.toString(ten.getFam_size()));


        acc.setOnClickListener(v -> {
            agem.setVisibility(View.VISIBLE);
            agpas.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            acc.setVisibility(View.INVISIBLE);
            rej.setVisibility(View.INVISIBLE);
        });

        submit.setOnClickListener(v -> {
            if(agem.getText().toString().isEmpty() || agpas.getText().toString().isEmpty())
            {
                agem.setError("Fill this field");
                agpas.setError("Fill this field");
            }
            //     public String testRel_ids(String agencyEm,int propid)
            else {
                boolean match = check_em_pass_match();
                boolean isOwned = check_if_it_is_owned();

                if (match && isOwned) Toast.makeText(this, "SUCCCCCCCEESSSSSSS",Toast.LENGTH_LONG).show();
                else {
                    agem.setError("You do not own this property");

                    Toast.makeText(this, "You do not own this property",Toast.LENGTH_LONG).show();
                }
            }
        });
        rej.setOnClickListener(v -> {
            Intent gmailIntent =new Intent();
            gmailIntent.setAction(Intent.ACTION_SENDTO);
            gmailIntent.setType("message/rfc822");
            gmailIntent.setData(Uri.parse("mailto:"));
            gmailIntent.putExtra(Intent.EXTRA_EMAIL,emaily);
            gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Renting Rejection Apology");
            gmailIntent.putExtra(Intent.EXTRA_TEXT,"Dear Tenant,\n" +
                            "We are sorry that your application to rent property in " + cityy + " has been rejected\n" +
                    "We are looking forward to deal with you later on");
            startActivity(gmailIntent);
        });

    }
    boolean check_em_pass_match()
    {
        DataBaseHelper db = new DataBaseHelper(this);
        return db.matched_email_pass_agency(agem.getText().toString(),agpas.getText().toString());
    }
    boolean check_if_it_is_owned(){
        DataBaseHelper db = new DataBaseHelper(this);
        String n = db.testRel_ids(agem.getText().toString(),db.getID_of_prop( new Property (
                getIntent().getStringExtra("prop_city"),
                getIntent().getStringExtra("prop_postal"),
                Double.parseDouble(getIntent().getStringExtra("prop_surface")),
                Integer.parseInt(getIntent().getStringExtra("prop_date"))
                ,Integer.parseInt(getIntent().getStringExtra("prop_bed")),
                Double.parseDouble(getIntent().getStringExtra("prop_price"))
                ,getIntent().getStringExtra("prop_status")))); //String agencyEm,int propid
        if(n==null) return false;
        return true;
    }
    Tenant get_attrsfrom_email(String type, String email) {
        DataBaseHelper bs = new DataBaseHelper(this);
        return bs.getFeatures(type,email);
    }
}