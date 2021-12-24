package com.example.renting_app;
//import org.apache.commons.validator.routines.EmailValidator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    public static boolean isValidEmail(String email) {
//        // create the EmailValidator instance
//        EmailValidator validator = EmailValidator.getInstance();
//
//        // check for valid email addresses using isValid method
//        return validator.isValid(email);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }
}