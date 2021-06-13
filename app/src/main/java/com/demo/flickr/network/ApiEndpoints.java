package com.demo.flickr.network;

public enum ApiEndpoints {


    RELEASE("Release", "https://api.flickr.com/");

    public final String name;
    public final String url;

    ApiEndpoints(String name, String url) {
        this.name = name;
        this.url = url;
    }

}