package com.example.renting_app;

import androidx.appcompat.app.AlertDialog;
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
    String  emaily;
    Property property;

    AlertDialog alrtdialog;
    AlertDialog alrtdialog2;
    AlertDialog alrtdialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tenant);

        DataBaseHelper db = new DataBaseHelper(this);
        emaily = getIntent().getStringExtra("emailyy");
        String cityy = getIntent().getStringExtra("cityy");
        int iddddddd = getIntent().getIntExtra("prop_idd",9999);


        property = new Property(
                getIntent().getStringExtra("cityy"),
                getIntent().getStringExtra("prop_postal"),
                Double.parseDouble(getIntent().getStringExtra("prop_surface")),
                Integer.parseInt(getIntent().getStringExtra("prop_date"))
                ,Integer.parseInt(getIntent().getStringExtra("prop_bed")),
                Double.parseDouble(getIntent().getStringExtra("prop_price"))
                ,getIntent().getStringExtra("prop_status")); //String agencyEm,int propid)

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


        alrtdialog3  = new AlertDialog.Builder(this).create();

        alrtdialog3.setTitle("Error");
        alrtdialog3.setIcon(R.drawable.ic_baseline_close_24);
        alrtdialog3.setMessage("Sorry, This property does not belong to the entered agency  in"+property.getCity());

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

                if (match && isOwned) {Toast.makeText(this, "SUCCESS",Toast.LENGTH_LONG).show();
                get_things_done(property,iddddddd);}
                if(!match) {
                    agem.setError("Entries are not matched, retry");
                    return;
                }
                if(!isOwned){
                    alrtdialog3.show();
                    return;
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

        alrtdialog  = new AlertDialog.Builder(this).create();
        alrtdialog2  = new AlertDialog.Builder(this).create();

        alrtdialog2.setTitle("Success Message");
        alrtdialog2.setIcon(R.drawable.ic_baseline_check_24);
        alrtdialog2.setMessage("Congrats, you have rented the property in "+property.getCity());

        alrtdialog.setTitle("Fail Message");
        alrtdialog.setIcon(R.drawable.ic_baseline_close_24);
        alrtdialog.setMessage("Property no. "+ iddddddd + " in " + property.getCity() +" is already rented, we will give you a call when it becomes availables");

    }
    void get_things_done (Property property, int prop_id)
    {
        DataBaseHelper db = new DataBaseHelper(this);
        boolean isRented = db.CheckIfRented(property.getCity(),prop_id);
        if (isRented) tell_user_itis_rented();
        else {
            push_tenant_prop_db(property,prop_id);
            tell_itisnot();
        }
    }
    void tell_itisnot() {
        agem.setVisibility(View.INVISIBLE);
        agpas.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        alrtdialog2.show();
    }
    void tell_user_itis_rented(){
        agem.setVisibility(View.INVISIBLE);
        agpas.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        alrtdialog.show();
    }
    public void push_tenant_prop_db(Property property, int prop_id)
    {
        DataBaseHelper db = new DataBaseHelper(this);
        int tenant_id = db.getIDfromEmail_tenant(emaily);
        DataBaseHelper ls = new DataBaseHelper(ViewTenant.this);
        ls.applicant_insert(property,emaily,tenant_id,prop_id);
    }
    boolean check_em_pass_match()
    {
        Toast.makeText(this,agem.getText().toString()+agpas.getText().toString(),Toast.LENGTH_SHORT).show();
        DataBaseHelper db = new DataBaseHelper(this);
        return db.matched_email_pass_agency(agpas.getText().toString(),agem.getText().toString());
    }
    boolean check_if_it_is_owned(){

        DataBaseHelper db = new DataBaseHelper(this);
        String n = db.testRel_ids(agem.getText().toString(),getIntent().getIntExtra("prop_idd",9999));
        Toast.makeText(this,"n = " +n, Toast.LENGTH_SHORT).show();

        if(n==null) return false;
        return true;


    }
    Tenant get_attrsfrom_email(String type, String email) {
        DataBaseHelper bs = new DataBaseHelper(this);
        return bs.getFeatures(type,email);
    }
}