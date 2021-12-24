package com.example.renting_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

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

}
