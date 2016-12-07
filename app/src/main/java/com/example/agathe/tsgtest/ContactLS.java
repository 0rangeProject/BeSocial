package com.example.agathe.tsgtest;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactLS {
    private String ls_name;
    private String location;

    public ContactLS(String ls_name, String location){
        this.ls_name = ls_name;
        this.location = location;
    }

    public void setLSName(String ls_name){
        this.ls_name = ls_name;
    }

    public String getLSName(){
        return ls_name;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

}
