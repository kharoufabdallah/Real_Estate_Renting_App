package com.example.renting_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "SYS";

    // below int is our database version
    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
    }
  //  public DataBaseHelper(){super(this,DB_NAME,null,DB_VERSION);}
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

    public void insertAgency(Agency agency) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs = new ContentValues();

        vs.put("agency_email",agency.getAgency_email());
        vs.put("agency_name",agency.getAgency_name());
        vs.put("gender","NA");
        vs.put("agency_password",agency.getPassword());
        vs.put("country",agency.getCountry());
        vs.put("city",agency.getCity());
        vs.put("agency_phone",agency.getAgency_phone());
        db.insert("AGENCY", null, vs); // TENANT IS TABLE COLUMN
        db.close();
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

    public void insert_property_from_json_file(List<Property> props)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs= new ContentValues();
        for (int i=0;i<props.size();i++) {
            vs.put("city",props.get(i).getCity());
            vs.put("postal_address",props.get(i).getPostal_address());
            vs.put("surface_area",props.get(i).getSurface_area());
            vs.put("const_year", (props.get(i).getConst_year()));
            vs.put("bedroom_no",props.get(i).getBedroom_no());
            vs.put("rental_price",props.get(i).getRental_price());
            vs.put("status",props.get(i).getStatus());
            db.insert("PROPERTY", null, vs); // TENANT IS TABLE COLUMN
            db.close();
        }
    }

    public String get_emailfrom_data(String needed_to_search_email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TENANT WHERE tenant_email LIKE '" + "%" +needed_to_search_email+"%" +"'",null);
        if(cursor.moveToFirst())
            return cursor.getString(0);
        return null;
    }
    public String get_passfrom_data(String needed_to_search_pass,String found_email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM TENANT WHERE tenant_password IN " +
                "(SELECT tenant_password from TENANT WHERE tenant_email=?) " +
                "like '" +"%" + needed_to_search_pass+"%" +"'",new String [] {found_email});
        if(curs.moveToFirst())
            return curs.getString(0);
        return null;
        //return db.rawQuery("SELECT * FROM TENANT WHERE tenant_password MATCH " + needed_to_search_pass +";",null);
    }

    public DataBaseHelper open(SQLiteDatabase db) throws SQLException
    {
        db = getWritableDatabase();
        return this;
    }
}
