package com.example.why_not_android.data.Models;

public class MemberData {
    private String _id;
    private String name;

    public MemberData(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public MemberData() {
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "MemberData{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}