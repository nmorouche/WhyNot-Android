package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class MatchDTO {
    @SerializedName("match")
    private Boolean match;
    @SerializedName("error")
    private String error;

    public Boolean getMatch() {
        return match;
    }

    public void setMatch(Boolean match) {
        this.match = match;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "match='" + match + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}