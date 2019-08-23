package com.example.why_not_android.data.Models;

public class MemberData {
    private String _id;
    private String name;
    private String image;

    public MemberData(String _id, String name, String image) {
        this._id = _id;
        this.name = name;
        this.image = image;
    }

    public MemberData() {
    }

    public String get_id() {
        return _id;
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