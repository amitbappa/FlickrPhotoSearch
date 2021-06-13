package com.demo.flickr.ui.photo.search.model;

public class PhotoSearchResponse {

    private String stat;
    private String code;
    private String message;
    private Photos photos;

    public String getStat() {
        return stat;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public Photos getPhotos() {
        return photos;
    }
}