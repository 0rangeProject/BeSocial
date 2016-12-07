package com.example.agathe.tsgtest.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by agathe on 07/12/16.
 */

public class User implements Parcelable {
    public String name;
    public String relation;
    public String phoneNumber;

    public User(String name, String relation, String phoneNumber) {
        this.name = name;
        this.relation = relation;
        this.phoneNumber = phoneNumber;
    }

    protected User(Parcel in) {
        name = in.readString();
        relation = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(relation);
        parcel.writeString(phoneNumber);
    }
}
