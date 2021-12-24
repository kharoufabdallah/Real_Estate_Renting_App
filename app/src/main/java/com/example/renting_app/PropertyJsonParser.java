package com.example.renting_app;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


//this class will convert the Json
//object we got from the REST API to Array List of the type Student

public class PropertyJsonParser {
    public static List<Property> getObjectFromJason(String jason) {
        List<Property> properties;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            properties = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Property property = new Property();
                property.setCity(jsonObject.getString("city"));
                property.setBedroom_no(jsonObject.getInt("bedroom_no"));
                property.setPostal_address(jsonObject.getString("postal_address"));
                property.setConst_year(jsonObject.getInt("const_year"));
                property.setRental_price(jsonObject.getDouble("rental_price"));
                property.setStatus(jsonObject.getString("status"));
                property.setSurface_area(jsonObject.getDouble("surface_area"));
                properties.add(property);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
