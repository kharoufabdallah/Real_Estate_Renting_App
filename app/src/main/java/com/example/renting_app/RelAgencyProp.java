package com.example.renting_app;

public class RelAgencyProp {
    int agency_id;
    int prop_id;
    String prop_city;
    String agency_name;


    public RelAgencyProp(){}


    public RelAgencyProp(int agency_id, int prop_id, String prop_city, String agency_email) {
        this.agency_id = agency_id;
        this.prop_id = prop_id;
        this.prop_city = prop_city;
        this.agency_name = agency_email;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public int getProp_id() {
        return prop_id;
    }

    public void setProp_id(int prop_id) {
        this.prop_id = prop_id;
    }

    public String getProp_city() {
        return prop_city;
    }

    public void setProp_city(String prop_city) {
        this.prop_city = prop_city;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    @Override
    public String toString() {
        return "RelAgencyProp{" +
                "agency_id=" + agency_id +
                ", prop_id=" + prop_id +
                ", prop_city='" + prop_city + '\'' +
                ", agency_name='" + agency_name + '\'' +
                '}';
    }
}
