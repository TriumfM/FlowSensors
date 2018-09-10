package com.example.triumf.flow_sensor_app.Entiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensorData {

    @SerializedName("did")
    @Expose
    private Integer did;

    @SerializedName("dataTime")
    @Expose
    private String dataTime;

    @SerializedName("literMin")
    @Expose
    private Double literMin;

    @SerializedName("sid")
    @Expose
    private Integer sid;

    @SerializedName("total")
    @Expose
    private Double total;


    public Integer getDid() {
        return did;
    }

    public String getDataTime() {
        return dataTime;
    }

    public Double getLiterMin() {
        return literMin;
    }

    public Integer getSid() {
        return sid;
    }

    public Double getTotal() {
        return total;
    }

    public SensorData(Integer did, String dataTime, Double literMin, Integer sid, Double total) {
        this.did = did;
        this.dataTime = dataTime;
        this.literMin = literMin;
        this.sid = sid;
        this.total = total;
    }
}
