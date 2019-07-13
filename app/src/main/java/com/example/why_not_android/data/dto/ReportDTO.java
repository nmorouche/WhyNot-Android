package com.example.why_not_android.data.dto;

import com.google.gson.annotations.SerializedName;

public class ReportDTO {

    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("idReporter")
    private String idReporter;
    @SerializedName("idReported")
    private String idReported;

    public ReportDTO(String type, String description, String idReporter, String idReported) {
        this.type = type;
        this.description = description;
        this.idReporter = idReporter;
        this.idReported = idReported;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdReporter() {
        return idReporter;
    }

    public void setIdReporter(String idReporter) {
        this.idReporter = idReporter;
    }

    public String getIdReported() {
        return idReported;
    }

    public void setIdReported(String idReported) {
        this.idReported = idReported;
    }
}
