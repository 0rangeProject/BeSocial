package com.example.agathe.tsgtest.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agathe on 07/12/16.
 */

public class CommonTravel implements Parcelable {
    public String departure;
    public String destination;
    public LatLng departurePosition;
    public LatLng destinationPosition;
    public List<User> users;

    public CommonTravel(String departure, String destination, LatLng departurePosition, LatLng destinationPosition, List<User> users) {
        this.departure = departure;
        this.destination = destination;
        this.departurePosition = departurePosition;
        this.destinationPosition = destinationPosition;
        this.users = users;
    }

    protected CommonTravel(Parcel in) {
        departure = in.readString();
        destination = in.readString();
        departurePosition = in.readParcelable(LatLng.class.getClassLoader());
        destinationPosition = in.readParcelable(LatLng.class.getClassLoader());
        users = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<CommonTravel> CREATOR = new Creator<CommonTravel>() {
        @Override
        public CommonTravel createFromParcel(Parcel in) {
            return new CommonTravel(in);
        }

        @Override
        public CommonTravel[] newArray(int size) {
            return new CommonTravel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(departure);
        parcel.writeString(destination);
        parcel.writeParcelable(departurePosition, i);
        parcel.writeParcelable(destinationPosition, i);
        parcel.writeTypedList(users);
    }
}

