package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import java.util.regex.Pattern;


/// TODO : PUSH DATA ON DB

public class Tenant_Sign_Up_activity extends AppCompatActivity {

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
                    ".{15}" +
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
        submit_and_sign_upB.setOnClickListener(v -> {
            boolean isEv = validateEmail();
            boolean isPv = validatePassword();
            boolean isNv = validateName();
            if (isEv && isPv && isNv) {
                Toast.makeText(this, "Welcome to System", Toast.LENGTH_LONG).show();
                push_into_db();
            }
        });
    }

    // this function inserts tenant into SQLite database
    void push_into_db() {

    }

    boolean validateName ()
    {
        String inputName  = firstName.getText().toString();
        String inputLName  = lastName.getText().toString();

        if(inputLName.length()<3) //  || inputName.length()<3)
        {
            lastName.setError("Name is too short");
            return false;
        }
        if(inputName.length()<3) //  || inputName.length()<3)
        {
            firstName.setError("Name is too short");
            return false;
        }
        if(inputName.length()>20) //  || inputName.length()<3)
        {
            firstName.setError("Name is too long");
            return false;
        }
        if(inputLName.length()>20) //  || inputName.length()<3)
        {
            lastName.setError("Name is too long");
            return false;
        }
        return true;
    }

    boolean validatePassword() {

        String input_pass = password.getText().toString();

        if (password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty()) {
            password.setError("Password cannot be empty");
            confirm_password.setError("ReWrite password to validate");
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(input_pass).matches()) {
            password.setError("Password too weak");
            return false;
        }
        if (!password.getText().toString().equals(confirm_password.getText().toString())) {
            confirm_password.setError("Passwords do not match - re-edit");
            password.setError("Passwords do not match - re-edit");
            return false;
        } else {
            password.setError(null);
            return true;

        }
    }

    boolean validateEmail() {
        String emailInput = emailAddressEditText.getText().toString();

        if (emailInput.isEmpty()) {
            emailAddressEditText.setError("Email Address cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailAddressEditText.setError("Please enter a valid email address");
            return false;
        } else {
            emailAddressEditText.setError(null);
            return true;
        }
    }
}