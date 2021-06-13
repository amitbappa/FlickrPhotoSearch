package com.demo.flickr.ui.photo.search.model;

public enum PhotoSize {

    THUMBNAIL,
    SMALL,
    MEDIUM,
    LARGE;

    public String getValue() {
        switch (this) {
            case THUMBNAIL:
                return "t";
            case SMALL:
                return "s";
            case LARGE:
                return "h";
            default:
                return "z";
        }
    }
}