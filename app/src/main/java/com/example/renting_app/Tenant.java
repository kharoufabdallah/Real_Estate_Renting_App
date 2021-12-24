package com.example.renting_app;

public class Tenant {
    String tenant_email;
    String first_name;
    String last_name;
    String gender;
    String tenant_password;
    String nationality;
    double GMS;
    String occupation;
    int fam_size;
    String residence_country;
    String city;
    String phone;

    public Tenant() {
    }

    public Tenant(String tenant_email, String first_name, String last_name, String gender, String tenant_password, String nationality, double GMS, String occupation, int fam_size, String residence_country, String city, String phone) {
        this.tenant_email = tenant_email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.tenant_password = tenant_password;
        this.nationality = nationality;
        this.GMS = GMS;
        this.occupation = occupation;
        this.fam_size = fam_size;
        this.residence_country = residence_country;
        this.city = city;
        this.phone = phone;
    }


    public String getTenant_email() {
        return tenant_email;
    }

    public void setTenant_email(String tenant_email) {
        this.tenant_email = tenant_email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTenant_password() {
        return tenant_password;
    }

    public void setTenant_password(String tenant_password) {
        this.tenant_password = tenant_password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getGMS() {
        return GMS;
    }

    public void setGMS(double GMS) {
        this.GMS = GMS;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getFam_size() {
        return fam_size;
    }

    public void setFam_size(int fam_size) {
        this.fam_size = fam_size;
    }

    public String getResidence_country() {
        return residence_country;
    }

    public void setResidence_country(String residence_country) {
        this.residence_country = residence_country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenant_email='" + tenant_email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", tenant_password='" + tenant_password + '\'' +
                ", nationality='" + nationality + '\'' +
                ", GMS=" + GMS +
                ", occupation='" + occupation + '\'' +
                ", fam_size=" + fam_size +
                ", residence_country='" + residence_country + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
