package com.example.agathe.tsgtest.dto;

/**
 * Created by koudm on 12/12/2016.
 */

public class ContactChallenge {
    public String challenge_name, challenge_duration, challenge_kilometers_average;

    public ContactChallenge(String challenge_name, String challenge_duration, String challenge_kilometers_average){
        this.challenge_name = challenge_name;
        this.challenge_duration = challenge_duration;
        this.challenge_kilometers_average = challenge_kilometers_average;
    }

    public void setChallengeName(String challenge_name){ this.challenge_name = challenge_name;}
    public String getChallengeName(){return challenge_name;}
    public void setChallengeDuration(String challenge_duration){ this.challenge_duration = challenge_duration;}
    public String getChallengeDuration(){return challenge_duration;}
    public void setChallengeKilometersAverage(String challenge_kilometers_average){ this.challenge_kilometers_average = challenge_kilometers_average;}
    public String getChallengeKilometersAverage(){return challenge_kilometers_average;}
}
