package com.example.agathe.tsgtest.dto;

/**
 * Created by koudm on 31/01/2017.
 */

public class RecommendationLS {
    public String contact_name;
    public String recommendation_type;
    public String location;

    public RecommendationLS(String contact_name, String recommendation_type,  String location){
        this.contact_name = contact_name;
        this.recommendation_type = recommendation_type;
        this.location = location;
    }

    public void setContactName(String contact_name){
        this.contact_name = contact_name;
    }

    public String getContactName(){
        return contact_name;
    }

    public void setRecommendationType(String recommendation_type){
        this.recommendation_type = recommendation_type;
    }

    public String getRecommendationType(){
        return recommendation_type;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
}
