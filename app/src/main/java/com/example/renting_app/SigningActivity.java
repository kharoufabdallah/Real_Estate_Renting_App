package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

// this activity is generated by intro_activity
// it began once connection to server REST is successsful


// To add a fragment dynamically you will use fragment manager to make a fragment
//transaction to add a new fragment and after you finish you should commit the transaction
//you made.



public class SigningActivity extends AppCompatActivity {
    final choose_user_Frag cuf1  = new choose_user_Frag();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    Button sign_in_b;
    Button sign_up_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);

//        Intent intent1 = getIntent();
//        String txt=intent1.getStringExtra("Android");


        sign_in_b= (Button)findViewById(R.id.sign_inB);
        sign_in_b.setOnClickListener(v -> {
            // move into choose user fragement
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.add(R.id.fragment_in_signing, cuf1, "chooseFrag").commit();

        });

        sign_up_b= (Button)findViewById(R.id.sign_inB);
        sign_up_b.setOnClickListener(v -> {
            // move into choose user fragement
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.add(R.id.fragment_in_signing, cuf1, "chooseFrag").commit();

        });


    }
}