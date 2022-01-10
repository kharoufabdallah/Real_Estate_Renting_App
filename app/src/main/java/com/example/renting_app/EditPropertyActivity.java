package com.example.renting_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditPropertyActivity extends AppCompatActivity {

    ImageView prop_ic;
    TextView date;//
    TextView city; //
    TextView surface;//
    TextView postal;//
    TextView price;
    TextView status;
    TextView bed; //

    EditText emailMain;
    Button subEmail;

    EditText dataEdit;
    EditText cityEdit;
    EditText surfaceEdit;
    EditText postalEdit;
    EditText priceEdit;
    EditText statusEdit;
    EditText bedEdit;

    String tenant_or_agency="";

    DataBaseHelper db;
    Button back;
  //  Button edit;
    Button delete;


    AlertDialog alrtdialog_city;
    AlertDialog alrtdialog_postal;
    AlertDialog alrtdialog_surface;
    AlertDialog alrtdialog_const;
    AlertDialog alrtdialog_bed;
    AlertDialog alrtdialog_price;
    AlertDialog alrtdialog_status;


    String emailtocompare;

    Property property;
    RelAgencyProp agencyProp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        db = new DataBaseHelper(EditPropertyActivity.this);
        tenant_or_agency = getIntent().getStringExtra("agency_or_tenant");  // agency just 11111
        
        prop_ic = findViewById(R.id.porp_icAgency);
        date = findViewById(R.id.tvapp_constyearAgency);
        city = findViewById(R.id.tvapp_cityAgency);
        surface = findViewById(R.id.tvapp_surfaceareaAgency);
        postal = findViewById(R.id.tvapp_postalAgency);
        price = findViewById(R.id.tvapp_priceAgency);
        status = findViewById(R.id.ttvapp_statusAgency);
        bed= findViewById(R.id.tvapp_numbedAgency);

        back =findViewById(R.id.back_appAgency);
       // edit=findViewById(R.id.editAPPAgencyB);
        delete=findViewById(R.id.delete_appAgency);


        alrtdialog_city    = new AlertDialog.Builder(this).create();
        alrtdialog_postal     = new AlertDialog.Builder(this).create();
        alrtdialog_surface  = new AlertDialog.Builder(this).create();
        alrtdialog_price    = new AlertDialog.Builder(this).create();
        alrtdialog_const  = new AlertDialog.Builder(this).create();
        alrtdialog_bed  = new AlertDialog.Builder(this).create();
        alrtdialog_status  = new AlertDialog.Builder(this).create();

        dataEdit = new EditText(this); //const year
        surfaceEdit = new EditText(this);
        bedEdit = new EditText(this);
        cityEdit = new EditText(this);
        postalEdit = new EditText(this);
        priceEdit = new EditText(this);
        statusEdit = new EditText(this);

        emailMain = findViewById(R.id.emailAgEdit);
        subEmail = findViewById(R.id.submitEmail);

        property = new Property(getIntent().getStringExtra("prop_city"),
                getIntent().getStringExtra("prop_postal"),
                Double.parseDouble(getIntent().getStringExtra("prop_surface")),
                Integer.parseInt(getIntent().getStringExtra("prop_date"))
                ,Integer.parseInt(getIntent().getStringExtra("prop_bed")),
                Double.parseDouble(getIntent().getStringExtra("prop_price"))
                ,getIntent().getStringExtra("prop_status"));

        //todo: solve this returning agencyId=0
        int AgencyId = db.getID_fromEmail_agency(emailtocompare);
        int PropId   = db.getID_of_prop(property);

        agencyProp = new RelAgencyProp(AgencyId,PropId,property.getCity(),emailtocompare);

        date.setText(Integer.toString(property.getConst_year()));
        city.setText(property.getCity());
        bed.setText(Integer.toString(property.getBedroom_no())+" rooms");
        surface.setText(Double.toString(property.getSurface_area())+ " meter2");
        postal.setText("postal address "+ property.getPostal_address());
        status.setText("status is " + property.getStatus());
        price.setText(Double.toString(property.getRental_price()) + " $");


        delete.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
            emailMain.setError("Cannot be empty, enter agency email");
            return;
        }
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                DataBaseHelper db =new DataBaseHelper(EditPropertyActivity.this);
                db.delete_prop_from_db(property);
            }
            else {Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });
        subEmail.setOnClickListener(v -> {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            emailtocompare  = emailMain.getText().toString();
        });

        // todo: here solve nulling entering profile
        back.setOnClickListener(v ->
        {
            Intent intent=new Intent(EditPropertyActivity.this,UI_Activity.class);
            intent.putExtra("agency_or_tenant","1");
            intent.putExtra("tenant_email",emailtocompare); ////// solve in here
            startActivity(intent);
            finish();
        });

        city.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                cityEdit.setText(city.getText());
                alrtdialog_city.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_city.setTitle("Edit city");
        alrtdialog_city.setView(cityEdit);
        alrtdialog_city.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                city.setText(cityEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_city_in_agency(property,city.getText().toString());
            }
        });

        postal.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                postalEdit.setText(postal.getText());
                alrtdialog_postal.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_postal.setTitle("Edit postal address");
        alrtdialog_postal.setView(postalEdit);
        alrtdialog_postal.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postal.setText(postalEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_postal_in_property(property,postal.getText().toString());
            }
        });
        bed.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                bedEdit.setText(bed.getText());
                alrtdialog_bed.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_bed.setTitle("Edit bedroom number");
        alrtdialog_bed.setView(bedEdit);
        alrtdialog_bed.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bed.setText(bedEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_bed_in_property(property,bed.getText().toString());
            }
        });
        date.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                dataEdit.setText(date.getText());
                alrtdialog_const.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_const.setTitle("Edit construction year");
        alrtdialog_const.setView(dataEdit);
        alrtdialog_const.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.setText(dataEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_const_in_property(property,date.getText().toString());
            }
        });

        surface.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                surfaceEdit.setText(surface.getText());
                alrtdialog_surface.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_surface.setTitle("Edit surface area");
        alrtdialog_surface.setView(surfaceEdit);
        alrtdialog_surface.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.setText(surfaceEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_surface_in_property(property,surface.getText().toString());
            }
        });
        status.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }

            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                statusEdit.setText(status.getText());
                alrtdialog_status.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_status.setTitle("Edit status of the property");
        alrtdialog_status.setView(statusEdit);
        alrtdialog_status.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                status.setText(statusEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_status_in_property(property,status.getText().toString());
            }
        });
        price.setOnClickListener(v ->
        {
            if (emailMain.getText().toString().isEmpty()) {
                emailMain.setError("Cannot be empty, enter agency email");
                return;
            }
            //compare process -- a boolean
            boolean match = compare_if_match(AgencyId,PropId);
            if (match) {
                priceEdit.setText(price.getText());
                alrtdialog_price.show();
            }
            else {
                Toast.makeText(this,"You are not authorized to change data in this property",Toast.LENGTH_SHORT).show();
                emailMain.setError("Not authorized to perform action, you don't own this property "+AgencyId+" "+PropId);
            }
        });

        alrtdialog_price.setTitle("Edit price of the property");
        alrtdialog_price.setView(priceEdit);
        alrtdialog_price.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                price.setText(priceEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(EditPropertyActivity.this);
                db.update_price_in_property(property,price.getText().toString());
            }
        });
    }

    public boolean compare_if_match (int agency_id,int prop_id)
    {
        db = new DataBaseHelper(EditPropertyActivity.this);
        String tester;
        tester = db.testRel_ids(emailtocompare,prop_id);
        return tester != null;
    }
}