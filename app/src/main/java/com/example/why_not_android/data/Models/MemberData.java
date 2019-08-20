package com.example.why_not_android.data.Models;

public class MemberData {
    private String name;
    private String image;

    public MemberData(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}