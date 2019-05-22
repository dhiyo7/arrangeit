package com.ydhnwb.arrangeit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomModel implements Parcelable {
    private String key;
    private String name;
    private String name_idiomatic;
    private String description;

    public RoomModel() { }

    public RoomModel(String key, String name, String name_idiomatic, String description) {
        this.key = key;
        this.name = name;
        this.name_idiomatic = name_idiomatic;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected RoomModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        name_idiomatic = in.readString();
        description = in.readString();
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
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RoomModel> CREATOR = new Parcelable.Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };
}