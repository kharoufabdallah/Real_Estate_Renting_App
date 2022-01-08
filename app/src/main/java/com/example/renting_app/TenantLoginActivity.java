package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

// we will use shared preferences for rememberMe feature - checkbox
public class TenantLoginActivity extends AppCompatActivity {

    CheckBox rememberme;
    EditText emailEdit;
    EditText passwordEdit;
    SharedPrefManager sharedPrefManager;
    Button loginB;

    SharedPreferences loginPref;
    SharedPreferences.Editor loginPrefEditor;
    boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);

        rememberme = findViewById(R.id.checkBoxLoginTenant);
        loginB = findViewById(R.id.loginB_sp);
        emailEdit = findViewById(R.id.emailTenantEditLogn);
        passwordEdit = findViewById(R.id.EDTpassLoginTen);

        sharedPrefManager =SharedPrefManager.getInstance(this);


        loginPref= getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();


        isSaved= loginPref.getBoolean("saveLogin",false);

        // when email and password are chekced ---> next login should be remembered
        if (isSaved){
            emailEdit.setText(loginPref.getString("email",""));
            passwordEdit.setText(loginPref.getString("password",""));
            rememberme.setChecked(true);
        }

        loginB.setOnClickListener(v ->
        {
            if(emailEdit.getText().toString().isEmpty()) emailEdit.setError("Field cannot be empty"); //when clicking on login button
            if(passwordEdit.getText().toString().isEmpty()) passwordEdit.setError("Field cannot be empty");

            boolean email_exists =  check_if_email_exist_in_db(emailEdit.getText().toString()) ;
            boolean pass_exists=false; // default - email is not found

            if (email_exists) pass_exists= check_if_password_exist_in_db(passwordEdit.getText().toString(),emailEdit.getText().toString()) ; // related with email also
            // if pass_exist == false ==> returned null from the database --> query search is wrong

            /// TODO: edit pass_exist in if statement
            if (email_exists) {   /////
                Toast.makeText(TenantLoginActivity.this, "email and password found",
                        Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(TenantLoginActivity.this,UI_Activity.class);
                intent.putExtra("tenant_email",emailEdit.getText().toString());
                intent.putExtra("agency_or_tenant","0");
                startActivity(intent); // going to intro layout - REST
                finish();
            } else {
                emailEdit.setError("Email provided not registered");
                Toast.makeText(TenantLoginActivity.this, "either email or password incorrect ",
                        Toast.LENGTH_SHORT).show();
            }

            if(rememberme.isChecked()) {  // write email and password on sp

                loginPrefEditor.putBoolean("saveLogin",true);
                loginPrefEditor.putString("email",emailEdit.getText().toString());
                loginPrefEditor.putString("password",passwordEdit.getText().toString());
                loginPrefEditor.commit();
                Toast.makeText(TenantLoginActivity.this, "User saved",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                loginPrefEditor.clear();
                loginPrefEditor.commit();
            }
         //   do_next(email_exists,pass_exists);
        });
    }
    void do_next(boolean ee, boolean pe) {
        if (ee && pe) {
            login_into_app();
        }
        else Toast.makeText(TenantLoginActivity.this, "Error in email or password - retry",
                Toast.LENGTH_LONG).show();
    }

    void login_into_app() {Toast.makeText(TenantLoginActivity.this, "MAIN MENU !",
            Toast.LENGTH_LONG).show();}

    boolean check_if_password_exist_in_db(String pass,String found_email)
    {
        DataBaseHelper db = new DataBaseHelper(TenantLoginActivity.this);
        String pass_returned = db.get_Agency_passfrom_data(pass,found_email);
        if(pass_returned==null) return false;
        return true;
       // if (db.get_passfrom_data(pass,found_email).equals(pass)) return true;
       // return false;
    }
     boolean check_if_email_exist_in_db(String email) {
         DataBaseHelper db = new DataBaseHelper(TenantLoginActivity.this);
         String email_returned = db.get_emailfrom_data(email);
         if (email_returned==null) return false;
         return true;
         //if (db.get_emailfrom_data(email).equals(email)) return true; // without toString(); by default returns a STRING
         //return false;
    }
}