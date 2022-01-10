package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.DoubleBuffer;

//
public class ApplicationForm extends AppCompatActivity {
    ImageView prop_ic;
    TextView date;
    TextView city;
    TextView surface;
    TextView postal;
    TextView price;
    TextView status;
    TextView bed;

    String to_send_email;

    EditText getEmail;
    Button subEmail;
    String tenant_or_agency="";

    DataBaseHelper db;

    Button back;
    Button apply;

    int tenant_id;
    int prop_id;
    Property property;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        db =new DataBaseHelper(ApplicationForm.this);

        prop_ic = findViewById(R.id.porp_ic);
        date = findViewById(R.id.tvapp_constyear);
        city = findViewById(R.id.tvapp_city);
        surface = findViewById(R.id.tvapp_surfacearea);
        postal = findViewById(R.id.tvapp_postal);
        price = findViewById(R.id.tvapp_price);
        status = findViewById(R.id.ttvapp_status);
        bed= findViewById(R.id.tvapp_numbed);

        back =findViewById(R.id.back_app);
        apply=findViewById(R.id.apply);

        prop_ic.setImageResource(R.drawable.flag_belgium);

        subEmail=findViewById(R.id.submit_app_emSec);
        getEmail=findViewById(R.id.emailAPP_sec);

     //   tenant_or_agency= getIntent().getStringExtra("tenant_or_agency").toString();

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


        to_send_email  = getEmail.getText().toString();


        subEmail.setOnClickListener(v -> {
            if (getEmail.getText().toString().isEmpty()) {
                getEmail.setError("Cannot be empty, enter agency email");
                return;
            }
            to_send_email  = getEmail.getText().toString();
        });

        if(to_send_email!=null || !to_send_email.equals(""))
            tenant_id = db.getIDfromEmail_tenant(to_send_email);
        prop_id = db.getID_of_prop(property);

        back.setOnClickListener(v -> {
            Intent intent=new Intent(ApplicationForm.this,UI_Activity.class);
            intent.putExtra("agency_or_tenant","0");
            startActivity(intent);
            finish();
        });

        apply.setOnClickListener(v -> {
            if (getEmail.getText().toString().isEmpty()) {
                getEmail.setError("Cannot be empty, enter tenant email");
                return;
            }
            popNotification();
            Toast.makeText(this,"notification sent",Toast.LENGTH_SHORT).show();
            push_tenant_prop_db();
        });
    }
    public void popNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle("A tenant needs to rent your property")
                .setContentText("press to see details");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    public void push_tenant_prop_db()
    {
        tenant_id = db.getIDfromEmail_tenant(to_send_email);
        DataBaseHelper db = new DataBaseHelper(ApplicationForm.this);
        if(to_send_email==null || to_send_email.equals("")) getEmail.setError("Email is not submitted");
        else db.applicant_insert(property,to_send_email,tenant_id,prop_id);
    }
}