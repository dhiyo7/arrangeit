package com.ydhnwb.arrangeit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class LecturerModel implements Parcelable {
    private String key;
    private String name;
    private String name_idiomatic;
    private String nipy;
    private String email;

    public LecturerModel() {
    }

    public LecturerModel(String key, String name, String name_idiomatic, String nipy, String email) {
        this.key = key;
        this.name = name;
        this.name_idiomatic = name_idiomatic;
        this.nipy = nipy;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_idiomatic() {
        return name_idiomatic;
    }

    public void setName_idiomatic(String name_idiomatic) {
        this.name_idiomatic = name_idiomatic;
    }

    public String getNipy() {
        return nipy;
    }

    public void setNipy(String nipy) {
        this.nipy = nipy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected LecturerModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        name_idiomatic = in.readString();
        nipy = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(name_idiomatic);
        dest.writeString(nipy);
        dest.writeString(email);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LecturerModel> CREATOR = new Parcelable.Creator<LecturerModel>() {
        @Override
        public LecturerModel createFromParcel(Parcel in) {
            return new LecturerModel(in);
        }

        @Override
        public LecturerModel[] newArray(int size) {
            return new LecturerModel[size];
        }
    };
}