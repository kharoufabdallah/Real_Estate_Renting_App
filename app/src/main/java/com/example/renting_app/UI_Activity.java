package com.example.renting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


/// TODO : THIS IS TENANT UI.

public class UI_Activity extends AppCompatActivity {

    TextView tv1;
    String name_of_tenant;
    String email_of_tenant;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav_view;
    DataBaseHelper db; // databased helper to get data and out in in UI

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final RentalHistoryPopUpFragment cuf2 = new RentalHistoryPopUpFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        // to get name from email and print hi , ......
        email_of_tenant = getIntent().getStringExtra("tenant_email");

        db = new DataBaseHelper(UI_Activity.this);
        tv1 = (TextView)findViewById(R.id.tvUI_tenant);
        name_of_tenant =db.getNameFromEmail(email_of_tenant);
        tv1.setText("Hi, "  + name_of_tenant + "!");
        // now --> email of tenant is know in the UI -- > we can access the name and other information
        // from the database depending on email_of_tenant.


        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nav_view=  (NavigationView)findViewById(R.id.nnnnn);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(UI_Activity.this,"HOEME ME",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_search:
                        break;
                        //return true;
                    case R.id.nav_account:
                        call_profile_show_activity(); // not dialog to enable passing data from/to UI and child activities!
//                        return true;
                        break;
                    case R.id.nav_history:
                        call_rental_history_popup();
                     //   return true;
                        break;
                    case R.id.nav_logout:
                        Intent intent=new Intent(UI_Activity.this,SigningActivity.class);
                        startActivity(intent); // going to intro layout - REST
                        finish();
                        break;
                    case R.id.nav_settings:
                       break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            switch (item.getItemId())
            {
                case R.id.nav_home:
                    Toast.makeText(UI_Activity.this,"HOEME ME ",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_search:
                    return true;
                case R.id.nav_account:
                    return true;
                case R.id.nav_history:
                    return true;
                case R.id.nav_logout:
                    Intent intent=new Intent(UI_Activity.this,SigningActivity.class);
                    startActivity(intent); // going to intro layout - REST
                    finish();
                    return true;
                case R.id.nav_settings:
                    return true;
              //  default: return super.onOptionsItemSelected(item);
            }
       }
        return super.onOptionsItemSelected(item);
    }

    void call_profile_show_activity() {
        Intent intent=new Intent(UI_Activity.this,TenantProfile.class);
        intent.putExtra("tenant_name",name_of_tenant);
        intent.putExtra("tenant_email",email_of_tenant);
        startActivity(intent); // going to intro layout - REST
        finish();
    }
    void call_rental_history_popup(){
//        DialogFragment dialogFragment= new DialogFragment();
//        dialogFragment.show(getSupportFragmentManager(),"RentalHistoryPopupFragment");


        //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.my_drawer_layout, cuf2, "choose user frag");
//        fragmentTransaction.commit();


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        final Dialog dialog = new Dialog(UI_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout((6 * width)/7, DrawerLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.fragment_rental_history_pop_up);
        dialog.show();
    }
}