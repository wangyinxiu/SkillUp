package com.xiu.datalib.common;

public class MediaInfo {
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
}
