package com.xiu.datalib.image;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Image implements Parcelable {

    private String name = null;

    private String path = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(path, image.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
