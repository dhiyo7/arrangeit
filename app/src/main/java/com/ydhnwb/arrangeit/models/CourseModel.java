package com.ydhnwb.arrangeit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseModel implements Parcelable {
    private String key;
    private String name;
    private String name_idiomatic;
    private String code;

    public CourseModel() {
    }

    public CourseModel(String key, String name, String name_idiomatic, String code) {
        this.key = key;
        this.name = name;
        this.name_idiomatic = name_idiomatic;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected CourseModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        name_idiomatic = in.readString();
        code = in.readString();
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
        dest.writeString(code);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CourseModel> CREATOR = new Parcelable.Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };
}