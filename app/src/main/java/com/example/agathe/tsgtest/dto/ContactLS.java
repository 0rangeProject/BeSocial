package com.example.agathe.tsgtest.dto;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactLS {
    public String ls_name;
    public String relation_strength;

    public ContactLS(String ls_name, String relation_strength){
        this.ls_name = ls_name;
        this.relation_strength = relation_strength;
    }

    public void setLSName(String ls_name){
        this.ls_name = ls_name;
    }

    public String getLSName(){
        return ls_name;
    }

    public void setRelationStrength(String relation_strength){
        this.relation_strength = relation_strength;
    }

    public String getRelationStrength(){
        return relation_strength;
    }

}
