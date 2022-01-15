package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

    Button snd_mesg;

    EditText getEmail;
    Button subEmail;
    String tenant_or_agency="";

    SharedPreferences shared;
    SharedPreferences.Editor edit_shared;
    String always_email;

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
        snd_mesg = findViewById(R.id.sendmsssg);

        prop_ic.setImageResource(R.drawable.flag_belgium);

        subEmail=findViewById(R.id.submit_app_emSec);
        getEmail=findViewById(R.id.emailAPP_sec);

        tenant_or_agency= getIntent().getStringExtra("agency_or_tenant");

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

        shared = getSharedPreferences("email_of_user_shared",MODE_PRIVATE);
        always_email = shared.getString("email_to_use","");


        to_send_email  = getEmail.getText().toString();


        subEmail.setOnClickListener(v -> {
            if(getIntent().getStringExtra("agency_or_tenant").equals("2")){
                getEmail.setError("Entered as guest, cannot apply to rent properties");
                return;
            }
            else if (getEmail.getText().toString().isEmpty()) {
                getEmail.setError("Cannot be empty, enter tenant email");
                return;
            }
            else if (!getEmail.getText().toString().equals(always_email)) {
                getEmail.setError("email does not match with user's");
                return;}
            else  to_send_email  = getEmail.getText().toString();
        });


        tenant_id = db.getIDfromEmail_tenant(always_email);
//        if(to_send_email!=null || !to_send_email.equals(""))
//            tenant_id = db.getIDfromEmail_tenant(to_send_email);
        prop_id = db.getID_of_prop(property);

        back.setOnClickListener(v -> {
            Intent intent=new Intent(ApplicationForm.this,UI_Activity.class);
            intent.putExtra("agency_or_tenant",getIntent().getStringExtra("agency_or_tenant"));
            intent.putExtra("tenant_email",always_email);
            startActivity(intent);
            finish();
        });

        apply.setOnClickListener(v -> {
            if(getIntent().getStringExtra("agency_or_tenant").equals("2")) {
                Toast.makeText(this, "please sign up to get feature of renting", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ApplicationForm.this,SigningActivity.class);
             //   intent.putExtra("agency_or_tenant",getIntent().getStringExtra("agency_or_tenant"));
                startActivity(intent);
                finish();
                return;
            }
            else if (getEmail.getText().toString().isEmpty()) {
                getEmail.setError("Cannot be empty, enter tenant email");
                return;
            }
            else if (!getEmail.getText().toString().equals(always_email)) {
                getEmail.setError("email does not match with user's");
                return;}
            else {
                /// send motification to agency
                createNotification("Renting request from " + always_email,
                        "User "+always_email+" wants to rent property no."+prop_id);
                Toast.makeText(this, "notification sent", Toast.LENGTH_SHORT).show();
//                push_tenant_prop_db();
            }
        });

        snd_mesg.setOnClickListener(v -> {
            Intent gmailIntent =new Intent();
            gmailIntent.setAction(Intent.ACTION_SENDTO);
            gmailIntent.setType("message/rfc822");
            gmailIntent.setData(Uri.parse("mailto:"));
            gmailIntent.putExtra(Intent.EXTRA_EMAIL,get_email_from_id_rel(prop_id,city.getText().toString()));
            gmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Property No. "+ prop_id + " Rental Inquiry");
            gmailIntent.putExtra(Intent.EXTRA_TEXT,"Dear agency,\nI would like to ask about property of " + prop_id + " where it is in "+ city.getText().toString());
            startActivity(gmailIntent);
        });
    }

    public String get_email_from_id_rel(int prop_id,String city){
        DataBaseHelper db = new DataBaseHelper(this);
        return db.get_agency_formRel(prop_id,city);
    }
    public void popNotification() {
       NotificationManager manager = (NotificationManager)  getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
       Uri uir = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

       NotificationCompat.Builder builder = new NotificationCompat.Builder( ApplicationForm.this, getString(R.string.app_name));
       builder.setContentTitle("Renting Request");
       builder.setContentText("User "+always_email+" wants to rent property no."+prop_id);
       builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setSound(uir);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.build();
        manager.notify(1,builder.build());
    }
    private static final int NOTIFICATION_ID = 123;
    private static final String NOTIFICATION_TITLE = "Notification Title";
    private static final String NOTIFICATION_BODY = "This is the body of my notification";
    public void createNotification(String title, String body) {
        Uri uir = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, ViewTenant.class);
        intent.putExtra("emailyy",always_email);
        intent.putExtra("cityy",property.getCity());
        intent.putExtra("prop_date",Integer.toString(property.getConst_year()));
        intent.putExtra("prop_surface",Double.toString(property.getSurface_area()));
        intent.putExtra("prop_bed",Integer.toString(property.getBedroom_no()));
        intent.putExtra("prop_status",property.getStatus());
        intent.putExtra("prop_price",Double.toString(property.getRental_price()));
        intent.putExtra("prop_postal",property.getPostal_address());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                MY_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .addAction(R.drawable.ic_launcher_foreground,"View Tenant",pendingIntent)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        builder.setSound(uir);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private static final String MY_CHANNEL_ID = "my_chanel_1";
    private static final String MY_CHANNEL_NAME = "My channel";

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                MY_CHANNEL_NAME, importance);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void push_tenant_prop_db()
    {
        tenant_id = db.getIDfromEmail_tenant(always_email);
        DataBaseHelper db = new DataBaseHelper(ApplicationForm.this);
        if(always_email==null || always_email.equals("")) getEmail.setError("Email is not submitted");
        else db.applicant_insert(property,always_email,tenant_id,prop_id);
    }
}