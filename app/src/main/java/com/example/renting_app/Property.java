package com.example.renting_app;


/// This class is the main data to be parsed from JSON type into data in arrayList

import java.util.ArrayList;
import java.util.Comparator;

public class Property implements Comparable<Property>{

    String city;
    String postal_address;
    double surface_area;
    int const_year;
    int bedroom_no;
    double rental_price;
    String status;

    Integer image_view;

    public Property(String city, String postal_address, double surface_area, int const_year, int bedroom_no, double rental_price, String status, Integer image_view) {
        this.city = city;
        this.postal_address = postal_address;
        this.surface_area = surface_area;
        this.const_year = const_year;
        this.bedroom_no = bedroom_no;
        this.rental_price = rental_price;
        this.status = status;
        this.image_view = image_view;
    }

    public Property() {
    }

    public Property(String city, String postal_address, double surface_area, int const_year, int bedroom_no, double rental_price, String status) {
        this.city = city;
        this.postal_address = postal_address;
        this.surface_area = surface_area;
        this.const_year = const_year;
        this.bedroom_no = bedroom_no;
        this.rental_price = rental_price;
        this.status = status;
    }

    public Integer getImage_view() {
        return image_view;
    }

    public void setImage_view(Integer image_view) {
        this.image_view = image_view;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public double getSurface_area() {
        return surface_area;
    }

    public void setSurface_area(double surface_area) {
        this.surface_area = surface_area;
    }

    public int getConst_year() {
        return const_year;
    }

    public void setConst_year(int const_year) {
        this.const_year = const_year;
    }

    public int getBedroom_no() {
        return bedroom_no;
    }

    public void setBedroom_no(int bedroom_no) {
        this.bedroom_no = bedroom_no;
    }

    public double getRental_price() {
        return rental_price;
    }

    public void setRental_price(double rental_price) {
        this.rental_price = rental_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Property{" +
                "city='" + city + '\'' +
                ", postal_address='" + postal_address + '\'' +
                ", surface_area='" + surface_area + '\'' +
                ", const_year=" + const_year +
                ", bedroom_no=" + bedroom_no +
                ", rental_price=" + rental_price +
                ", status='" + status + '\'' +
                '}';
    }

    public static Comparator<Property> sortBedroom = new Comparator<Property>()
    {

        @Override
        public int compare(Property o1, Property o2) {
//            return o1.getCity().compareTo(o2.getCity());
            return o1.getBedroom_no() - o2.getBedroom_no();
        }
    };

    public static Comparator<Property> sortprice = new Comparator<Property>()
    {

        @Override
        public int compare(Property o1, Property o2) {
            return (int) o1.getRental_price() - (int) o2.getRental_price() ;
        }
    };

    public static Comparator<Property> sortsurface = new Comparator<Property>()
    {

        @Override
        public int compare(Property o1, Property o2) {
            return (int) o1.getSurface_area() - (int) o2.getSurface_area() ;
        }
    };
    @Override
    public int compareTo(Property o) {
      return this.bedroom_no = getBedroom_no();
        //  return Integer.parseInt(Double.toString(this.surface_area))=Integer.parseInt(Double.toString(o.getSurface_area()));
    }
}
