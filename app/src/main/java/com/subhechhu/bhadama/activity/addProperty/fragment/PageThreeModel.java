package com.subhechhu.bhadama.activity.addProperty.fragment;

public class PageThreeModel {
    String imagePath,  id;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PageThreeModel{" +
                "imagePath='" + imagePath + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
