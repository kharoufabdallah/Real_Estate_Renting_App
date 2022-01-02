package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddPropActivity extends AppCompatActivity {
    EditText city;
    EditText postal;
    EditText surface;
    EditText nobed;
    EditText constyear;
    EditText rentalp;
    EditText status;
    ImageView imgview;
    Button back;
    Button add_photo;
    Button all;

    Property mainProp;


    static final int PICK_IMAGE = 100;
    Uri imageURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prop);

        city = findViewById(R.id.addProp_city);
        postal=findViewById(R.id.addProp_postal);
        surface =findViewById(R.id.addProp_surface);
        nobed=findViewById(R.id.addProp_noBed);
        constyear=findViewById(R.id.addProp_const);
        rentalp=findViewById(R.id.addProp_rentalPrice);
        status=findViewById(R.id.addProp_Status);
        imgview=findViewById(R.id.addPRop_img);
        back=findViewById(R.id.back_mainBAdd_Prop);
        add_photo=findViewById(R.id.UploadPhotoB);
        all=findViewById(R.id.addallProp);


        all.setOnClickListener(v ->
        {
            mainProp = setAttrs();
            push_into_db(mainProp);
            Toast.makeText(AddPropActivity.this,"Property Added to System",Toast.LENGTH_SHORT).show();

        });

        back.setOnClickListener(v->
        {
            Intent intent=new Intent(AddPropActivity.this,UI_Activity.class);
    //        intent.putExtra("tenant_email",email_of_tenant); // this may be tenant email or agency email
            startActivity(intent); // going to intro layout - REST
            finish();

        });
        add_photo.setOnClickListener(v ->
        {
            openGallery();
        });
    }

    public void push_into_db(Property property)
    {
        DataBaseHelper sqLiteDatabase = new DataBaseHelper(AddPropActivity.this);
        sqLiteDatabase.insertPropMan(property);
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
            imgview.setImageURI(imageURI);
        }

    }

    Property setAttrs(){
        Property propp = new Property();

        propp.setStatus(status.getText().toString());
        propp.setSurface_area(Double.parseDouble(surface.getText().toString()));
        propp.setRental_price(Double.parseDouble(rentalp.getText().toString()));
        propp.setCity(city.getText().toString());
        propp.setPostal_address(postal.getText().toString());
        propp.setBedroom_no(Integer.parseInt(nobed.getText().toString()));
        propp.setConst_year(Integer.parseInt(constyear.getText().toString()));
     //   propp.setImage_view(imgview.setImageResource(););

        return propp;
    }
}