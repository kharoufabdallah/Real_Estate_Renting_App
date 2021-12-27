package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Agency_sign_up_Activity extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText pass;
    EditText con_pass;
    EditText phone;
    Spinner country;
    EditText city;

    Button sign_up;
    Button back_main;


    boolean ee,ne,pe,cpe,phe,cie;

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
        setContentView(R.layout.activity_agency_sign_up_);

        email = findViewById(R.id.agency_email_edit_signup);
        pass = findViewById(R.id.agency_pass_edit_signup);
        con_pass= findViewById(R.id.agency_pass_edit_signup_confirm);
        name = findViewById(R.id.agency_name_edit_signup);
        country= findViewById(R.id.spinner_signup_country_agency);
        phone= findViewById(R.id.phone_agency_sign_up);
        city=findViewById(R.id.city_agency_edit_signup);

        sign_up =(Button)findViewById(R.id.sing_up_agency_button);
        back_main=(Button)findViewById(R.id.back_lgoin_agency);

        sign_up.setOnClickListener(v ->
        {
            Agency agnc = set_agency_attrs();
            if(ee&&pe&&cpe&&cie&&phe&&ne) {
                push_into_db(agnc);
                Toast.makeText(this, "Agency Signed up Successfully!", Toast.LENGTH_SHORT).show();
            }
          else Toast.makeText(this, "some fields are empty - refill", Toast.LENGTH_SHORT).show();
        });

        back_main.setOnClickListener(v ->
        {
            Intent intent=new Intent(Agency_sign_up_Activity.this,SigningActivity.class);
            startActivity(intent); // going to intro layout - REST
            finish();
        });
    }

    void push_into_db(Agency agency){
        DataBaseHelper sqLiteDatabase = new DataBaseHelper(Agency_sign_up_Activity.this);
        sqLiteDatabase.insertAgency(agency);
    }
    public Agency set_agency_attrs(){
        Agency agency= new Agency();
        if (validateEmail())agency.setAgency_email(email.getText().toString());
        if(validatePassword()) agency.setPassword(pass.getText().toString());
        if(validateName()) agency.setAgency_name(name.getText().toString());

        agency.setCountry(country.getSelectedItem().toString());

        if(phone.getText().toString().isEmpty()) {phe =false; phone.setError("Cannot be empty");}
        else {agency.setAgency_phone(phone.getText().toString()); phe=true;}
        if(city.getText().toString().isEmpty()) {cie =false; city.setError("Cannot be empty");}
        else {agency.setCity(city.getText().toString()); cie=true;}

        return agency;
    }

    boolean validateName ()
    {
        String inputName  = name.getText().toString();


        if(inputName.length()<3) //  || inputName.length()<3)
        {
            ne=false;
            name.setError("Name is too short");
            return false;
        }
        if(inputName.length()>20) //  || inputName.length()<3)
        {
            ne=false;
            name.setError("Name is too long");
            return false;
        }

        ne=true;
        return true;
    }

    boolean validateEmail() {
        String emailInput = email.getText().toString();

        if (emailInput.isEmpty()) {
            ee=false;
            email.setError("Email Address cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            ee=false;
            email.setError("Please enter a valid email address");
            return false;
        } else {
            ee=true;
            email.setError(null);
            return true;
        }
    }
    boolean validatePassword() {

        String input_pass = pass.getText().toString();

        if (pass.getText().toString().isEmpty() || con_pass.getText().toString().isEmpty()) {
            pass.setError("Password cannot be empty");
            con_pass.setError("ReWrite password to validate");
            pe=false;
            cpe=false;
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(input_pass).matches()) {
            pass.setError("Password too weak");
            pe=false;
            cpe=false;
            return false;
        }
        if (!pass.getText().toString().equals(con_pass.getText().toString())) {
            con_pass.setError("Passwords do not match - re-edit");
            pass.setError("Passwords do not match - re-edit");
            pe=false;
            cpe=false;
            return false;
        } else {
            pe=true;
            cpe=true;
            pass.setError(null);
            return true;

        }
    }

}