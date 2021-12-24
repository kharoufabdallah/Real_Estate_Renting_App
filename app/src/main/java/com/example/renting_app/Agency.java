package com.example.renting_app;

public class Agency {
    String agency_email;
    String agency_name;
    String password;
    String country;
    String city;
    String agency_phone;

    public Agency() {
    }

    public Agency(String agency_email, String agency_name, String password, String country, String city, String agency_phone) {
        this.agency_email = agency_email;
        this.agency_name = agency_name;
        this.password = password;
        this.country = country;
        this.city = city;
        this.agency_phone = agency_phone;
    }

    public String getAgency_email() {
        return agency_email;
    }

    public void setAgency_email(String agency_email) {
        this.agency_email = agency_email;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAgency_phone() {
        return agency_phone;
    }

    public void setAgency_phone(String agency_phone) {
        this.agency_phone = agency_phone;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "agency_email='" + agency_email + '\'' +
                ", agency_name='" + agency_name + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", agency_phone='" + agency_phone + '\'' +
                '}';
    }
}
