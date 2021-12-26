package com.example.renting_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "SYS";

    // below int is our database version
    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
    }
    public DataBaseHelper(Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // CREATION OF MAIN TABLES IN DATABASE SYSTEM
        db.execSQL("CREATE TABLE PROPERTY(city TEXT, postal_address TEXT ,surface_area DOUBLE,const_year INT ,bedroom_no INT," +
                       "rental_price DOUBLE , status TEXT)");

        db.execSQL("CREATE TABLE TENANT(tenant_email TEXT PRIMARY KEY, first_name TEXT, last_name TEXT ,  gender TEXT, tenant_password TEXT, nationality TEXT , GMS DOUBLE, " +
                " occupation TEXT , fam_size INT, residence_country TEXT, city TEXT ,phone TEXT)");

        db.execSQL("CREATE TABLE AGENCY(agency_email TEXT PRIMARY KEY, agency_name TEXT, gender TEXT, agency_password TEXT, country TEXT , city TEXT, "+
                "agency_phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    public void insertTenant (Tenant tenant) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs = new ContentValues();
        vs.put("tenant_email", tenant.getTenant_email());
        vs.put("first_name", tenant.getFirst_name());
        vs.put("last_name", tenant.getLast_name());
        vs.put("gender", tenant.getGender());
        vs.put("tenant_password", tenant.getTenant_password());
        vs.put("nationality", tenant.getNationality());
        vs.put("GMS", tenant.getGMS());
        vs.put("occupation", tenant.getOccupation());
        vs.put("fam_size", tenant.getFam_size());
        vs.put("residence_country", tenant.getResidence_country());
        vs.put("city", tenant.getCity());
        vs.put("phone", tenant.getPhone());

        db.insert("TENANT", null, vs); // TENANT IS TABLE COLUMN
        db.close();
    }
}
