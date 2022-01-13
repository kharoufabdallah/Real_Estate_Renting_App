package com.example.renting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/// TODO : email_tenant from login

public class UI_Activity extends AppCompatActivity {

    String tenant_agency="";

    Button minsurf;
    Button maxsurf;
    Button minbed;
    Button maxbed;
    Button minprice;

    TextView tv1;
    String name_of_tenant;
    String name_of_agency;
    String email_of_tenant;
    String email_of_agency;

    PropertyAdpter propertyAdpter;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav_view;
    DataBaseHelper db; // databased helper to get data and out in in UI

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final RentalHistoryPopUpFragment cuf2 = new RentalHistoryPopUpFragment();

    AlertDialog historyChooseDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);


        /// either 1 for agency or 0 for tenant
        tenant_agency = getIntent().getStringExtra("agency_or_tenant");
        email_of_tenant = getIntent().getStringExtra("tenant_email");// email of both


        if(tenant_agency.equals("0")) { //tenant getIntent().getStringExtra("agency_or_tenant")
            db = new DataBaseHelper(UI_Activity.this);
            name_of_tenant = db.getNameFromEmail(email_of_tenant);
        }
        else if(tenant_agency.equals("1")){ // 1 -> agency
            db = new DataBaseHelper(UI_Activity.this);
            name_of_agency = db.getNameFromEmail_agency(email_of_tenant);
        }



        RecyclerView rv = (RecyclerView) findViewById(R.id.rec_view_home);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        minsurf = (Button)findViewById(R.id.min_surfaceB);
        maxsurf = (Button)findViewById(R.id.max_surfB);
        minbed = (Button)findViewById(R.id.min_bedB);
        maxbed = (Button)findViewById(R.id.max_bedB);
        minprice = (Button)findViewById(R.id.min_rentalB);

        // Adapter of the recycler view
        ArrayList<Property> po = getAllprops(); // getting all properties from database

        po.add( new Property("NY", "42342342", 120.5, 2019, 3, 1250.5, "Much better", R.drawable.flag_albania));
        po.add( new Property("CALIFORNIA", "4555", 1223.5, 2020, 7, 2280.5, "Fantastic", R.drawable.flag_united_states_of_america));

        propertyAdpter = new PropertyAdpter(po, UI_Activity.this,tenant_agency);
        rv.setAdapter(propertyAdpter);

        historyChooseDialog = new AlertDialog.Builder(this).create();

        minbed.setOnClickListener(v -> {
            Collections.sort(po,Property.sortBedroom);
            propertyAdpter = new PropertyAdpter(po,UI_Activity.this,tenant_agency);
            rv.setAdapter(propertyAdpter);
        });
        maxbed.setOnClickListener(v -> {
            Collections.sort(po,Property.sortBedroom.reversed());
            propertyAdpter = new PropertyAdpter(po,UI_Activity.this,tenant_agency);
            rv.setAdapter(propertyAdpter);
        });
        minprice.setOnClickListener(v -> {
            Collections.sort(po,Property.sortprice);
            propertyAdpter = new PropertyAdpter(po,UI_Activity.this,tenant_agency);
            rv.setAdapter(propertyAdpter);
        });
        minsurf.setOnClickListener(v -> {
            Collections.sort(po,Property.sortsurface);
            propertyAdpter = new PropertyAdpter(po,UI_Activity.this,tenant_agency);
            rv.setAdapter(propertyAdpter);
        });
        maxsurf.setOnClickListener(v -> {
            Collections.sort(po,Property.sortsurface.reversed());
            propertyAdpter = new PropertyAdpter(po,UI_Activity.this,tenant_agency);
            rv.setAdapter(propertyAdpter);
        });

        historyChooseDialog.setTitle("Choose Type of History");
        historyChooseDialog.setIcon(R.drawable.ic_baseline_view_compact_24);
        historyChooseDialog.setButton( "Tenant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getIntent().getStringExtra("agency_or_tenant").equals("0")) {
                    Intent intent = new Intent(UI_Activity.this, TenantMainHistory.class);
                    intent.putExtra("tenant_email",getIntent().getStringExtra("tenant_email").toString());
                    intent.putExtra("agency_or_tenant",getIntent().getStringExtra("agency_or_tenant"));
                    startActivity(intent); // going to intro layout - REST
                    finish();
                }else   if(getIntent().getStringExtra("agency_or_tenant").equals("1")) {
                    Toast.makeText(UI_Activity.this, "You are logged in as an agency", Toast.LENGTH_SHORT).show();
                }
                else  Toast.makeText(UI_Activity.this, "You are logged in as a guest", Toast.LENGTH_SHORT).show();


            }
        });
        historyChooseDialog.setButton2("Agency", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getIntent().getStringExtra("agency_or_tenant").equals("1")) {
                    Intent intent = new Intent(UI_Activity.this, AgencyMainHistory.class);
                    intent.putExtra("tenant_email",getIntent().getStringExtra("tenant_email").toString());
                    intent.putExtra("agency_or_tenant",getIntent().getStringExtra("agency_or_tenant"));
                    startActivity(intent); // going to intro layout - REST
                    finish();
                }
                else if (getIntent().getStringExtra("agency_or_tenant").equals("0")) {
                    Toast.makeText(UI_Activity.this, "You are logged in as a tenant", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(UI_Activity.this, "You are logged in as a guest", Toast.LENGTH_SHORT).show();
            }
        });
        // to get name from email and print hi , ......

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nav_view = (NavigationView) findViewById(R.id.nnnnn);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(UI_Activity.this, "You are in home page", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_search:
                        Toast.makeText(UI_Activity.this, "Search is done on the top", Toast.LENGTH_SHORT).show();
                        break;
                    //return true;
                    case R.id.nav_agency_acc:
                        if(getIntent().getStringExtra("agency_or_tenant").equals("1"))
                            call_agency_profile();
                        else Toast.makeText(UI_Activity.this, "Entered as tenant/guest", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_account:
                        if(getIntent().getStringExtra("agency_or_tenant").equals("0"))
                            call_profile_show_activity(); // not dialog to enable passing data from/to UI and child activities!
                        else Toast.makeText(UI_Activity.this, "Entered as agency/guest", Toast.LENGTH_SHORT).show();
//                        return true;
                        break;
                    case R.id.nav_history:
                        if(getIntent().getStringExtra("agency_or_tenant").equals("0") ||getIntent().getStringExtra("agency_or_tenant").equals("1"))
                             call_rental_history_popup();
                        else Toast.makeText(UI_Activity.this, "No History for guest, please sign up", Toast.LENGTH_SHORT).show();
                        //   return true;
                        break;
                    case R.id.nav_logout:
                        Intent intent = new Intent(UI_Activity.this, SigningActivity.class);
                        intent.putExtra("tenant_agency","0");
                        startActivity(intent); // going to intro layout - REST
                        finish();
                        break;
                    case R.id.nav_settings:
                        break;
                    case R.id.nav_add_prop:
                        if(tenant_agency.equals("1"))
                             add_property_by_agency();
                        else Toast.makeText(UI_Activity.this, "cannot perform action, entered as guest/tenant", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_edit_prop:
                        Toast.makeText(UI_Activity.this, "Editing is done for agencies, click on the property you need to edit", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        MenuItem item = menu.findItem(R.id.nav_search);

        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                propertyAdpter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(UI_Activity.this, "HOEME ME ", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_search:
                    return true;
                case R.id.nav_account:
                    return true;
                case R.id.nav_history:
                    return true;
                case R.id.nav_logout:
                    Intent intent = new Intent(UI_Activity.this, SigningActivity.class);
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
        Intent intent = new Intent(UI_Activity.this, TenantProfile.class);
        intent.putExtra("tenant_name", name_of_tenant);
        intent.putExtra("tenant_email", email_of_tenant);
        startActivity(intent);
        finish();
    }

    void call_agency_profile()
    {
        Intent intent = new Intent(UI_Activity.this, AgencyProfile.class);
        intent.putExtra("agency_name", name_of_agency);
        intent.putExtra("agency_email_to_prof", email_of_tenant);
        startActivity(intent);
        finish();
//        Intent intent = new Intent(UI_Activity.this, TenantProfile.class);
//        intent.putExtra("agency_name", name_of_tenant);
//        intent.putExtra("agency_email", email_of_tenant);
//        startActivity(intent); // going to intro layout - REST
//        finish();
    }

    void call_rental_history_popup() {
//        DialogFragment dialogFragment= new DialogFragment();
//        dialogFragment.show(getSupportFragmentManager(),"RentalHistoryPopupFragment");
// FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.my_drawer_layout, cuf2, "choose user frag");
//        fragmentTransaction.commit();

        historyChooseDialog.show();

        //// TODO: ALERT DAILOG 
//        // Alert Dialog

//        if(getIntent().getStringExtra("agency_or_tenant").equals("0"))
//        else Toast.makeText(UI_Activity.this, "Entered as tenant", Toast.LENGTH_SHORT).show();


//        final Dialog dialog = new Dialog(UI_Activity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//    //    dialog.getWindow().setLayout((6 * width) / 7, DrawerLayout.LayoutParams.WRAP_CONTENT);
//        dialog.setContentView(R.layout.fragment_rental_history_pop_up);
//        dialog.show();
    }

    void add_property_by_agency() {
        Intent intent = new Intent(UI_Activity.this, AddPropActivity.class);
        intent.putExtra("tenant_email", email_of_tenant); // this is agency email
        startActivity(intent); // going to intro layout - REST
        finish();
    }

    public ArrayList<Property> getAllprops() {
        ArrayList<Property> prop_list = new ArrayList<Property>();
        db = new DataBaseHelper(UI_Activity.this);
        prop_list = db.store_recs_in_al();
        return prop_list;
    }
}
