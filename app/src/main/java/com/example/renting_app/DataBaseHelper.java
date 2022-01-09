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
    private static final int DB_VERSION = 7;

    public DataBaseHelper(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);
    }

    public DataBaseHelper(Context context, @Nullable String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // CREATION OF MAIN TABLES IN DATABASE SYSTEM
        onUpgrade(db,0,DB_VERSION);
        //6
        db.execSQL("CREATE TABLE PROPERTY(property_id integer primary key autoincrement,city TEXT, postal_address TEXT ,surface_area DOUBLE,const_year INT ,bedroom_no INT," +
                "rental_price DOUBLE , status TEXT)");

        db.execSQL("CREATE TABLE TENANT(tenant_id integer primary key autoincrement, tenant_email TEXT , first_name TEXT, last_name TEXT ,  gender TEXT, tenant_password TEXT, nationality TEXT , GMS DOUBLE, " +
                " occupation TEXT , fam_size INT, residence_country TEXT, city TEXT ,phone TEXT)");

        db.execSQL("CREATE TABLE AGENCY(agency_id INTEGER PRIMARY KEY AUTOINCREMENT, agency_email TEXT, agency_name TEXT, gender TEXT, agency_password TEXT, country TEXT , city TEXT, " +
                "agency_phone TEXT)");

        // 7
        db.execSQL(" CREATE TABLE PROP_AGENCY (prop_id integer, agency_id integer, agency_name TEXT, property_city,FOREIGN KEY(prop_id) REFERENCES PROPERTY(prop_id),FOREIGN KEY(agency_id) REFERENCES AGENCY(agency_id))");
        db.execSQL(" CREATE TABLE PROP_TENANT (prop_id integer, TENANT_id integer, TENANT_name TEXT, property_city,FOREIGN KEY(prop_id) REFERENCES PROPERTY(prop_id),FOREIGN KEY(tenant_id) REFERENCES TENANT(tenant_id))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) // new user{
        {

        }
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    public void insert_rel_agency_prop(RelAgencyProp relAgencyProp)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs = new ContentValues();
        vs.put("prop_id",relAgencyProp.getProp_id());
        vs.put("agency_id",relAgencyProp.getAgency_id());
        vs.put("agency_name",relAgencyProp.getAgency_name());
        vs.put("property_city",relAgencyProp.getProp_city());
        db.insert("PROP_AGENCY",null,vs);
        db.close();
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

    public void insert_into_prop_agency(List<Property> props) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs = new ContentValues();
//        Cursor cursor;
//        int ids[];
//        for (int i = 0; i < props.size(); i++) {
//
//            ids[i]=
//
//        }

        for (int i = 0; i < props.size(); i++) {
          //  vs.put("prop_id",props.get(i).);
            vs.put("agency_id",999);
            vs.put("agency_name","serverAgent");
            vs.put("property_city",props.get(i).getCity());
            db.insert("PROP_AGENCY", null, vs); // TENANT IS TABLE COLUMN
            db.close();
        }
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
    public String get_Agency_emailfrom_data(String needed_to_search_email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AGENCY WHERE agency_email LIKE '" + "%" +needed_to_search_email+"%" +"'",null);
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
    public String get_Agency_passfrom_data(String needed_to_search_pass,String found_email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM AGENCY WHERE agency_password IN " +
                "(SELECT agency_password from AGENCY WHERE agency_email=?) " +
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

    public String getNameFromEmail_agency(String email)
    {
        String name = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select agency_name from AGENCY WHERE agency_email LIKE '"+"%"+email+"%"+"'",null);
        if (curs.moveToFirst())name  = curs.getString(0);
        return name;
    }

    public int getID_fromEmail_agency(String email){
        int id=1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select agency_id from AGENCY WHERE agency_email LIKE '"+"%"+email+"%"+"'",null);
        if (curs.moveToFirst()) id = curs.getInt(0);
        //   return  id;
        //    }
        //    catch (SQLException e) {

        ///  }
        return id;
    }
    public int getID_of_prop (Property property)
    {
        int id = 0;
       // try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor curs = db.rawQuery("Select property_id from PROPERTY where city like '" +  property.getCity() + "' and surface_area='" +  property.getSurface_area() + "'", null);
            if (curs.moveToFirst()) id = curs.getInt(0);
         //   return  id;
    //    }
    //    catch (SQLException e) {

      ///  }
        return id;
    }
    public String testRel_ids(int agencyid,int propid)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select property_city from PROP_AGENCY where agency_id like '" +  agencyid + "' and prop_id='" +  propid + "'", null);
        if (cursor.moveToFirst()) return cursor.getString(0);
        else return null;
    }
    public String getNameFromEmail(String email)
    {
        String name = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select first_name from TENANT WHERE tenant_email LIKE '"+"%"+email+"%"+"'",null);
        if (curs.moveToFirst())name  = curs.getString(0);
        return name;
    }
    public String getLastName_emailKey(String email){
        String last = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select last_name from TENANT WHERE tenant_email LIKE '"+"%"+email+"%"+"'",null);
        if (curs.moveToFirst())last  = curs.getString(0);
        return last;
    }
    public String getNat_emailKey(String email){
        String nat = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select nationality from TENANT WHERE tenant_email LIKE '"+"%"+email+"%"+"'",null);
        if (curs.moveToFirst())nat  = curs.getString(0);
        return nat;
    }
    public void update_nat_in_profile(String email,String new_nat)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update TENANT set nationality = '" + new_nat  + "'" +"where tenant_email LIKE '"+"%"+email+"%"+"'");
    }
    public void update_city_in_agency(Property property,String new_city)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set city = '" + new_city  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void update_postal_in_property(Property property,String new_postal)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set postal_address = '" + new_postal  + "'" +"where rental_price LIKE '"+"%"+property.getRental_price()+"%"+"'");
    }
    public void update_bed_in_property(Property property,String new_bed)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set bedroom_no = '" + new_bed  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void update_const_in_property(Property property,String new_year)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set const_year = '" + new_year  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void update_surface_in_property(Property property,String new_surf)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set surface_area = '" + new_surf  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void update_price_in_property(Property property,String new_price)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set rental_price = '" + new_price  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void update_status_in_property(Property property,String new_status)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update PROPERTY set status = '" + new_status  + "'" +"where postal_address LIKE '"+"%"+property.getPostal_address()+"%"+"'");
    }
    public void insertPropMan(Property property){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vs = new ContentValues();

        vs.put("city",property.getCity());
        vs.put("postal_address",property.getPostal_address());
        vs.put("surface_area",property.getSurface_area());
        vs.put("const_year",property.getConst_year());
        vs.put("bedroom_no",property.getBedroom_no());
        vs.put("rental_price",property.getRental_price());
        vs.put("status",property.getStatus());

        db.insert("PROPERTY",null,vs);
        db.close();
    }


    public String getPhoneAgency(String name, String email)
    { String nat = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor curs = db.rawQuery("Select agency_phone from AGENCY WHERE agency_email LIKE '"+"%"+email+"%"+"' and agency_name LIKE '"+"%"+name+"%"+"'",null);
        if (curs.moveToFirst())nat  = curs.getString(0);
        return nat;
    }

    public ArrayList<Property> store_recs_in_al () {
      //  String [] columns = new String[] {"city","postal_address","surface_area","const_year","bedroom_no","rental_price","status"};

        ArrayList<Property> prop_list = new ArrayList<Property>();
      //  Property temp ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from PROPERTY GROUP BY surface_area having count(*) >= 1",null);
       // return Cursor;
        while(cursor.moveToNext())
        {
            prop_list.add(new Property(cursor.getString(1),cursor.getString(2),cursor.getDouble(3),
                    cursor.getInt(4),cursor.getInt(5),cursor.getDouble(6),cursor.getString(7),R.drawable.flag_canada));
        }
//        int i=0;
//        int icity = cursor.getColumnIndex("city");
//        int ipost = cursor.getColumnIndex("postal_address");
//        int isurface = cursor.getColumnIndex("surface_area");
//        int iyear = cursor.getColumnIndex("const_year");
//        int ibed = cursor.getColumnIndex("bedroom_no");
//        int irental= cursor.getColumnIndex("rental_price");
//        int istat = cursor.getColumnIndex("status");

//        for (cursor.moveToFirst();!cursor.moveToLast();cursor.moveToNext())
//        {
//            temp = new Property();
////            temp.setCity(cursor.getString(icity));
////            temp.setPostal_address(cursor.getString(ipost));
////            temp.setSurface_area(cursor.getDouble(isurface));
////            temp.setConst_year(cursor.getInt(iyear));
////            temp.setBedroom_no(cursor.getInt(ibed));
////            temp.setRental_price(cursor.getDouble(irental));
////            temp.setStatus(cursor.getString(istat));
//            prop_list.add(temp);
//         //   i++;
//           // Toast.makeText(DataBaseHelper.this,prop_list.get(0).getStatus(),Toast.LENGTH_SHORT).show();
//        }
        return prop_list;
    }
}