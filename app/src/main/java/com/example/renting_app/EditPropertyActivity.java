package com.example.renting_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditPropertyActivity extends AppCompatActivity {

    ImageView prop_ic;
    TextView date;
    TextView city;
    TextView surface;
    TextView postal;
    TextView price;
    TextView status;
    TextView bed;


    String tenant_or_agency="";


    Button back;
    Button edit;
    Button delete;


    AlertDialog alrtdialog_city;
    AlertDialog alrtdialog_postal;
    AlertDialog alrtdialog_surface;
    AlertDialog alrtdialog_const;
    AlertDialog alrtdialog_bed;
    AlertDialog alrtdialog_price;
    AlertDialog alrtdialog_status;


    Property property;
    RelAgencyProp agencyProp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);


        prop_ic = findViewById(R.id.porp_icAgency);
        date = findViewById(R.id.tvapp_constyearAgency);
        city = findViewById(R.id.tvapp_cityAgency);
        surface = findViewById(R.id.tvapp_surfaceareaAgency);
        postal = findViewById(R.id.tvapp_postalAgency);
        price = findViewById(R.id.tvapp_priceAgency);
        status = findViewById(R.id.ttvapp_statusAgency);
        bed= findViewById(R.id.tvapp_numbedAgency);

        back =findViewById(R.id.back_appAgency);
        edit=findViewById(R.id.editAPPAgencyB);
        delete=findViewById(R.id.delete_appAgency);

        alrtdialog_city  = new AlertDialog.Builder(this).create();
        alrtdialog_postal  = new AlertDialog.Builder(this).create();
        alrtdialog_surface  = new AlertDialog.Builder(this).create();
        alrtdialog_price  = new AlertDialog.Builder(this).create();
        alrtdialog_const  = new AlertDialog.Builder(this).create();
        alrtdialog_bed  = new AlertDialog.Builder(this).create();
        alrtdialog_status  = new AlertDialog.Builder(this).create();

        property = new Property(getIntent().getStringExtra("prop_city"),
                getIntent().getStringExtra("prop_postal"),
                Double.parseDouble(getIntent().getStringExtra("prop_surface")),
                Integer.parseInt(getIntent().getStringExtra("prop_date"))
                ,Integer.parseInt(getIntent().getStringExtra("prop_bed")),
                Double.parseDouble(getIntent().getStringExtra("prop_price"))
                ,getIntent().getStringExtra("prop_status"));

        date.setText(Integer.toString(property.getConst_year()));
        city.setText(property.getCity());
        bed.setText(Integer.toString(property.getBedroom_no())+" rooms");
        surface.setText(Double.toString(property.getSurface_area())+ " meter2");
        postal.setText("postal address "+ property.getPostal_address());
        status.setText("status is " + property.getStatus());
        price.setText(Double.toString(property.getRental_price()) + " $");


//        alrtdialog_city.setTitle("Edit data of profile");
//        alrtdialog_city.setView(emailEdit);
//        alrtdialog_city.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                emailTv_.setText(emailEdit.getText().toString());
//            }
//        });


    }
}