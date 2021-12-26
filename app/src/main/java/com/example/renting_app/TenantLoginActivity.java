package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;

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
            if(emailEdit.getText().toString().isEmpty()) emailEdit.setError("Field cannot be empty");
            if(passwordEdit.getText().toString().isEmpty()) passwordEdit.setError("Field cannot be empty");

//           boolean email_exists =  check_if_email_exist_in_db(emailEdit.getText().toString()) ;
//           boolean pass_exists =   check_if_password_exist_in_db(passwordEdit.getText().toString()) ;


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
       //     do_next(email_exists,pass_exists);
        });
    }
    void do_next(boolean ee, boolean pe) {
        if (ee && pe) {
            login_into_app();
        }
        else Toast.makeText(TenantLoginActivity.this, "Error in email or password - retry",
                Toast.LENGTH_LONG).show();
    }

    void login_into_app() {}

    boolean check_if_password_exist_in_db(String pass)
    {
        DataBaseHelper db = new DataBaseHelper(TenantLoginActivity.this);
        if (db.get_passfrom_data(pass).toString().equals(pass)) return true;
        return false;
    }
     boolean check_if_email_exist_in_db(String email) {
         DataBaseHelper db = new DataBaseHelper(TenantLoginActivity.this);
         if (db.get_emailfrom_data(email).toString().equals(email)) return true;
         return false;
    }
}