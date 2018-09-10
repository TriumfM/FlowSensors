package com.example.triumf.flow_sensor_app.Entiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("uid")
    private Integer uid;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("email")
    @Expose
    private String password;

    public Integer getUid() {
        return uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public Users(Integer uid, String first_name, String last_name, String password) {
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }
}
