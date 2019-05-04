package com.vczyh.materialtest;

public class Fruit {

    private String name;

    private int imageId;

    private String imageUrl;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Fruit(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
