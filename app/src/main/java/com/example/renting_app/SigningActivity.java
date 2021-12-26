package com.example.renting_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class SigningActivity extends AppCompatActivity {

    final choose_user_Frag cuf1  = new choose_user_Frag();
    final chooseUser_Login_frag cuf2 = new chooseUser_Login_frag();

    final FragmentManager fragmentManager = getSupportFragmentManager();


    Button sign_in_b;
    Button sign_up_b;
    Button cont_guestB; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing);

        sign_in_b= (Button)findViewById(R.id.sign_inB);  // cuf2
        sign_in_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cuf2.isAdded())
                {
                    return; //or return false/true, based on where you are calling from
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.Root_IN, cuf2, "choose user frag");
                fragmentTransaction.commit();
            }
        } );

        sign_up_b= (Button)findViewById(R.id.sign_upB); // cuf1
        sign_up_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cuf1.isAdded())
                {
                    return; //or return false/true, based on where you are calling from
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.root2, cuf1, "choose user frag2");
                fragmentTransaction.commit();
            }
        } );
        cont_guestB= (Button)findViewById(R.id.guestContB);
        cont_guestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                }
        }); 
    }
}