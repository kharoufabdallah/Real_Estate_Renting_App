package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.util.regex.Pattern;


/// TODO : setError when two emails are the same in database

public class Tenant_Sign_Up_activity extends AppCompatActivity {

    boolean ee,pe,fe,le,cpe,gme,oce,fse,cie,phe;
    EditText emailAddressEditText;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText confirm_password;

    Spinner gender_spin;
    Spinner nat_spin;
    Spinner country_res_spin;

    EditText GMS_edit;
    EditText occ_edit;
    EditText city_edit;
    EditText fam_size_edit;
    EditText phone_edit;
    CountryCodePicker cpp;

    Button submit_and_sign_upB;
    Button backB;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
//                    ".{15}" +
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant__sign__up);

        emailAddressEditText = findViewById(R.id.editTextTextEmailAddress);


        firstName = findViewById(R.id.editTextfirstPersonName);
        lastName = findViewById(R.id.editlastTextPersonName2);

        password = findViewById(R.id.passwordTenant);
        confirm_password = findViewById(R.id.passwordTenant2);

        gender_spin = findViewById(R.id.spinnerTenantGender);
        nat_spin = findViewById(R.id.spinnerNat);
        country_res_spin = findViewById(R.id.spinnerNat2);

        GMS_edit= findViewById(R.id.editTextNumberDecimal2);
        occ_edit= findViewById(R.id.occ_edit);
        city_edit = findViewById(R.id.city_edit);
        fam_size_edit = findViewById(R.id.fam_num);
        phone_edit = findViewById(R.id.editTextPhoneTenant);

        cpp = (CountryCodePicker) findViewById(R.id.countryCode_picker);

        backB  = findViewById(R.id.back_to_log_inB);
        submit_and_sign_upB = findViewById(R.id.Submit_DataB);

        // main functionality (i.e. validation and insertion) ;

        submit_and_sign_upB.setOnClickListener(v -> {
//                Tenant to_be_inserted;// = setTenantAttrs (); // validation of attrs are done here
                Tenant tenant =setTenantAttrs();
                if(ee&&pe&&cpe&&fe&&le&&gme&&oce&&fse&&cie&&phe) {
                    push_into_db(tenant);
                    Toast.makeText(this, "Welcome to System", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(this, "some fields are empty - refill", Toast.LENGTH_SHORT).show();
        });

        backB.setOnClickListener(v ->
        {
            Toast.makeText(this, "Back to Login menu", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Tenant_Sign_Up_activity.this,SigningActivity.class);
            startActivity(intent); // going to intro layout - REST
            finish();
        });
    }

    public Tenant setTenantAttrs() {
        Tenant tnt = new Tenant();
         if(validateEmail()) tnt.setTenant_email(emailAddressEditText.getText().toString());
         if(validatePassword()) tnt.setTenant_password( password.getText().toString());
         if(validateName()) {
             tnt.setFirst_name(firstName.getText().toString());
             tnt.setLast_name(lastName.getText().toString());
         }
         tnt.setGender(gender_spin.getSelectedItem().toString());
         tnt.setNationality(nat_spin.getSelectedItem().toString());

        if (phone_edit.getText().toString().isEmpty()) {
            phe=false;
            phone_edit.setError("Cannot be empty");
          //  setTenantAttrs();
        } else {
            phe=true;
            tnt.setPhone(phone_edit.getText().toString());
        }
        if (fam_size_edit.getText().toString().isEmpty()) {
            fse=false;
            fam_size_edit.setError("Cannot be empty");
         //   setTenantAttrs();
        } else {
            fse=true;
            tnt.setFam_size(Integer.parseInt(fam_size_edit.getText().toString()));
        }

        if (GMS_edit.getText().toString().isEmpty()) {
            gme=false;
            GMS_edit.setError("Cannot be Empty");
          //  setTenantAttrs();
        } else {
            gme=true;
            tnt.setGMS(Double.parseDouble(GMS_edit.getText().toString()));
        }

        tnt.setResidence_country(country_res_spin.getSelectedItem().toString());

        if (city_edit.getText().toString().isEmpty()) {
            cie=false;
            city_edit.setError("Cannot be empty");
         //   setTenantAttrs();
        } else {
            cie=true;
            tnt.setCity(city_edit.getText().toString());
        }

        if (occ_edit.getText().toString().isEmpty()) {
            oce=false;
            occ_edit.setError("Cannot be Empty");
         //   setTenantAttrs();
        } else {
            oce=true;
            tnt.setOccupation(occ_edit.getText().toString());
        }

        return tnt;
    }

    // this function inserts tenant into SQLite database
    public  void push_into_db(Tenant tenant) {
        DataBaseHelper sqLiteDatabase = new DataBaseHelper(Tenant_Sign_Up_activity.this);
        sqLiteDatabase.insertTenant(tenant);
    }

    boolean validateName ()
    {
        String inputName  = firstName.getText().toString();
        String inputLName  = lastName.getText().toString();

        if(inputLName.length()<3) //  || inputName.length()<3)
        {
            le=false;
            lastName.setError("Name is too short");
            return false;
        }
        if(inputName.length()<3) //  || inputName.length()<3)
        {
            fe=false;
            firstName.setError("Name is too short");
            return false;
        }
        if(inputName.length()>20) //  || inputName.length()<3)
        {
            fe=false;
            firstName.setError("Name is too long");
            return false;
        }
        if(inputLName.length()>20) //  || inputName.length()<3)
        {
            le=false;
            lastName.setError("Name is too long");
            return false;
        }
        le=true;
        fe=true;
        return true;
    }

    boolean validatePassword() {

        String input_pass = password.getText().toString();

        if (password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty()) {
            password.setError("Password cannot be empty");
            confirm_password.setError("ReWrite password to validate");
            pe=false;
            cpe=false;
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(input_pass).matches()) {
            password.setError("Password too weak");
            pe=false;
            cpe=false;
            return false;
        }
        if (!password.getText().toString().equals(confirm_password.getText().toString())) {
            confirm_password.setError("Passwords do not match - re-edit");
            password.setError("Passwords do not match - re-edit");
            pe=false;
            cpe=false;
            return false;
        } else {
            pe=true;
            cpe=true;
            password.setError(null);
            return true;

        }
    }

    boolean validateEmail() {
        String emailInput = emailAddressEditText.getText().toString();

        if (emailInput.isEmpty()) {
            ee=false;
            emailAddressEditText.setError("Email Address cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ee=false;
            emailAddressEditText.setError("Please enter a valid email address");
            return false;
        } else {
            ee=true;
            emailAddressEditText.setError(null);
            return true;
        }
    }
}