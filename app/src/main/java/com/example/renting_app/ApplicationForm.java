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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.DoubleBuffer;

public class ApplicationForm extends AppCompatActivity {
    ImageView prop_ic;
    TextView date;
    TextView city;
    TextView surface;
    TextView postal;
    TextView price;
    TextView status;
    TextView bed;


    Button back;
    Button apply;

    Property property;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

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

        back.setOnClickListener(v -> {
            Intent intent=new Intent(ApplicationForm.this,UI_Activity.class);

            startActivity(intent);
            finish();
        });

        apply.setOnClickListener(v -> {
            popNotification();
            Toast.makeText(this,"SDFSDF",Toast.LENGTH_SHORT).show();
        });
    }
    public void popNotification() {
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle("John's Android Studio Tutorials")
                .setContentText("A video has just arrived!");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}