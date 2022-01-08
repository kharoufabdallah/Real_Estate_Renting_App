package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Agency_login_activity extends AppCompatActivity {
    EditText email;
    EditText pass;
    CheckBox rememberAgency;
    Button agency_login;

    SharedPreferences loginPref;
    SharedPreferences.Editor loginPrefEditor;
    boolean isSaved;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_login);

        email = (EditText) findViewById(R.id.agency_login_email);
        pass  = (EditText) findViewById(R.id.agency_login_pasw);
        rememberAgency =(CheckBox)findViewById(R.id.agency_rememberMe);
        agency_login = (Button) findViewById(R.id.login_agencyB);


        sharedPrefManager =SharedPrefManager.getInstance(this);


        loginPref= getSharedPreferences("Agency_loginPrefs",MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();


        isSaved= loginPref.getBoolean("saveLoginAgency",false);

        // when email and password are checked ---> next login should be remembered
        if (isSaved){
            email.setText(loginPref.getString("Agency_email",""));
            pass.setText(loginPref.getString("Agency_password",""));
            rememberAgency.setChecked(true);
        }


        agency_login.setOnClickListener(v ->
        {
            if(email.getText().toString().isEmpty()) email.setError("Field cannot be empty"); //when clicking on login button
            if(pass.getText().toString().isEmpty()) pass.setError("Field cannot be empty");

            boolean email_exists =  check_if_email_exist_in_db(email.getText().toString()) ;
            boolean pass_exists=false; // default - email is not found

            if (email_exists) pass_exists= check_if_password_exist_in_db(pass.getText().toString(),email.getText().toString()) ; // related with email also
            // if pass_exist == false ==> returned null from the database --> query search is wrong

            /// TODO: edit pass_exist in if statement
            if (email_exists) {   /////
                Toast.makeText(Agency_login_activity.this, "email and password found",
                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Agency_login_activity.this,UI_Activity.class);
                intent.putExtra("tenant_email",email.getText().toString());
                intent.putExtra("agency_or_tenant","1");
                startActivity(intent); // going to intro layout - REST
                finish();
            } else {
                email.setError("Email provided not registered");
                Toast.makeText(Agency_login_activity.this, "either email or password incorrect ",
                        Toast.LENGTH_SHORT).show();
            }

            if(rememberAgency.isChecked()) {  // write email and password on sp

                loginPrefEditor.putBoolean("saveLoginAgency",true);
                loginPrefEditor.putString("Agency_email",email.getText().toString());
                loginPrefEditor.putString("Agency_password",pass.getText().toString());
                loginPrefEditor.commit();
                Toast.makeText(Agency_login_activity.this, "User saved",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                loginPrefEditor.clear();
                loginPrefEditor.commit();
            }
            //   do_next(email_exists,pass_exists);
        });
    }

    boolean check_if_password_exist_in_db(String pass,String found_email)
    {
        DataBaseHelper db = new DataBaseHelper(Agency_login_activity.this);
        String pass_returned = db.get_passfrom_data(pass,found_email);
        if(pass_returned==null) return false;
        return true;
    }
    boolean check_if_email_exist_in_db(String email) {
        DataBaseHelper db = new DataBaseHelper(Agency_login_activity.this);
        String email_returned = db.get_Agency_emailfrom_data(email);
        if (email_returned==null) return false;
        return true;
    }
}