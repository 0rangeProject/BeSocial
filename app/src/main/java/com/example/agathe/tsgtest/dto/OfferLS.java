package com.example.agathe.tsgtest.dto;

/**
 * Created by koudm on 26/01/2017.
 */

public class OfferLS {
    public String offer_title;
    public String offer_type;
    public String offer_description;
    public String location;

    public OfferLS(String offer_title, String offer_type, String offer_description, String location){
        this.offer_title = offer_title;
        this.offer_type = offer_type;
        this.offer_description = offer_description;
        this.location = location;
    }

    public void setOfferTitle(String offer_title){
        this.offer_title = offer_title;
    }

    public String getOfferTitle(){
        return offer_title;
    }

    public void setOfferType(String offer_type){
        this.offer_type = offer_type;
    }

    public String getOfferType(){
        return offer_type;
    }

    public void setOfferDescription(String offer_description){ this.offer_description = offer_description;}

    public String getOfferDescription(){ return offer_description;}

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
