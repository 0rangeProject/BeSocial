package com.example.agathe.tsgtest;

/**
 * Created by koudm on 06/12/2016.
 */

public class ContactChallenge {
    private String name;
    private String duration;
    private String kilometers_average;

    public ContactChallenge(String name, String duration, String kilometers_average){
        this.name = name;
        this.duration = duration;
        this.kilometers_average = kilometers_average;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public String getDuration(){
        return duration;
    }

    public void setKilometers_average(String kilometers_average){this.kilometers_average = kilometers_average;}

    public String getKilometers_average(){return kilometers_average;}
}
