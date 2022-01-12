package com.example.renting_app;

public class RelTenantProp {
    int tenant_id;
    int prop_id;
    String prop_city;
    String tenant_name; /// email inside

    public RelTenantProp(int tenant_id, int prop_id, String prop_city, String tenant_name) {
        this.tenant_id = tenant_id;
        this.prop_id = prop_id;
        this.prop_city = prop_city;
        this.tenant_name = tenant_name;
    }

    public RelTenantProp() {
    }

    public int getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(int tenant_id) {
        this.tenant_id = tenant_id;
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

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    @Override
    public String toString() {
        return "RelTenantProp{" +
                "tenant_id=" + tenant_id +
                ", prop_id=" + prop_id +
                ", prop_city='" + prop_city + '\'' +
                ", tenant_name='" + tenant_name + '\'' +
                '}';
    }
}
