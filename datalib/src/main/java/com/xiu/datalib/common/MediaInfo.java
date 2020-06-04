package com.xiu.datalib.common;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaInfo implements Parcelable {
    private String title;
    private String album;
    private String artist;
    private String path;

    public MediaInfo() {
    }

    public MediaInfo(String title, String album, String artist, String path) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.path = path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.album);
        dest.writeString(this.artist);
        dest.writeString(this.path);
    }

    protected MediaInfo(Parcel in) {
        this.title = in.readString();
        this.album = in.readString();
        this.artist = in.readString();
        this.path = in.readString();
    }

    public static final Parcelable.Creator<MediaInfo> CREATOR = new Parcelable.Creator<MediaInfo>() {
        @Override
        public MediaInfo createFromParcel(Parcel source) {
            return new MediaInfo(source);
        }

        @Override
        public MediaInfo[] newArray(int size) {
            return new MediaInfo[size];
        }
    };
}
