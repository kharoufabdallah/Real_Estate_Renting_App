package com.example.renting_app;
//import org.apache.commons.validator.routines.EmailValidator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //    public static boolean isValidEmail(String email) {
//        // create the EmailValidator instance
//        EmailValidator validator = EmailValidator.getInstance();
//
//        // check for valid email addresses using isValid method
//        return validator.isValid(email);
//    }
    Button connection_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

//        connection_button= (Button) findViewById(R.id.button11);
//        connection_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast toast = Toast.makeText(MainActivity.this,
//                        "Unsuccessful Connection - Retry", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
    }
}