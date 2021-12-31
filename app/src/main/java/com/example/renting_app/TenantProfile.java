package com.example.renting_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class TenantProfile extends AppCompatActivity {

    TextView nameTv;
    TextView emailTv;
    TextView NatTv;
    TextView hiTv;
    ImageView avatar;

    static final int PICK_IMAGE = 100;
    Uri imageURI;
    //for alertDialog

    Button change_pp;
    EditText natEdit;
    EditText emailEdit;
    AlertDialog alrtdialog; // does not change in database ---> primary key
    AlertDialog alrtdialog2; // changes on database !!

    AlertDialog imgdialog;



    Button back_main_menu;

    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

            setContentView(R.layout.tenant_profile);


            nameTv    = (TextView) findViewById(R.id.name_tvprofile);
            hiTv      = (TextView) findViewById(R.id.tv1hi_profile);
            emailTv   = (TextView) findViewById(R.id.email_tv_profile);
            NatTv     = (TextView) findViewById(R.id.nat_tvProfile);
            avatar    = (ImageView) findViewById(R.id.avatar);
            back_main_menu =(Button)findViewById(R.id.back_main_menub);
            change_pp = (Button)findViewById(R.id.changepp_B);

        alrtdialog  = new AlertDialog.Builder(this).create();
        alrtdialog2  = new AlertDialog.Builder(this).create();
        emailEdit   = new EditText(this);
        natEdit     = new EditText(this);

        alrtdialog.setTitle("Edit data of profile");
        alrtdialog.setView(emailEdit);
        alrtdialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emailTv.setText(emailEdit.getText().toString());
            }
        });

       emailTv.setOnClickListener(v ->
        {
            emailEdit.setText(emailTv.getText());
            alrtdialog.show();
        });


        alrtdialog2.setTitle("Edit data of profile");
        alrtdialog2.setView(natEdit);
        alrtdialog2.setButton(DialogInterface.BUTTON_POSITIVE, "Save data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NatTv.setText(natEdit.getText().toString());

                // updates on database record
                db =new DataBaseHelper(TenantProfile.this);
                db.update_nat_in_profile(emailTv.getText().toString(),NatTv.getText().toString());
            }
        });

        NatTv.setOnClickListener(v ->
        {
            natEdit.setText(NatTv.getText());
            alrtdialog2.show();
        });

            String emailfromUI = getIntent().getStringExtra("tenant_email").toString();
            String namefromUI = getIntent().getStringExtra("tenant_name").toString();

            db = new DataBaseHelper(TenantProfile.this);
            String last_name = db.getLastName_emailKey(emailfromUI);
            String nat = db.getNat_emailKey(emailfromUI);



            avatar.setImageResource(R.drawable.flag_palestine);

            ///settings
            emailTv.setText(emailfromUI);
            hiTv.setText("Hi " +namefromUI+ "!");
            nameTv.setText(namefromUI+"  "+last_name);
            NatTv.setText(nat);


            back_main_menu.setOnClickListener(v-> {
                Intent intent=new Intent(TenantProfile.this,UI_Activity.class);
                intent.putExtra("tenant_name",namefromUI);
                intent.putExtra("tenant_email",emailfromUI);
                startActivity(intent); // going to intro layout - REST
                finish();
            });

            change_pp.setOnClickListener(v ->
            {
                openGallery();
            });
        }

    public void openGallery() {
        Intent gellery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gellery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageURI = data.getData();
            avatar.setImageURI(imageURI);
        }

    }
}