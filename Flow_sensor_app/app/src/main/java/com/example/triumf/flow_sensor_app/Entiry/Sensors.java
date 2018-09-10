package com.example.triumf.flow_sensor_app.Entiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sensors {

    @SerializedName("sid")
    private Integer Sensorid;

    @SerializedName("input")
    @Expose
    private Integer InputPin;

    @SerializedName("output")
    @Expose
    private Integer OutputPin;

    @SerializedName("status")
    @Expose
    private Integer StatusPin;

    @SerializedName("name")
    @Expose
    private String Name;

    @SerializedName("type")
    @Expose
    private String Type;

    @SerializedName("diameter")
    @Expose
    private String Diameter;

    @SerializedName("description")
    @Expose
    private String Description;

    public Integer getSensorid() {
        return Sensorid;
    }

    public Integer getInputPin() {
        return InputPin;
    }

    public Integer getOutputPin() {
        return OutputPin;
    }

    public Integer getStatusPin() {
        return StatusPin;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getDiameter() {
        return Diameter;
    }

    public String getDescription() {
        return Description;
    }

    public void setSensorid(Integer sensorid) {
        Sensorid = sensorid;
    }

    public void setInputPin(Integer inputPin) {
        InputPin = inputPin;
    }

    public void setOutputPin(Integer outputPin) {
        OutputPin = outputPin;
    }

    public void setStatusPin(Integer statusPin) {
        StatusPin = statusPin;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setDiameter(String diameter) {
        Diameter = diameter;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Sensors{" +
                "Sensorid=" + Sensorid +
                ", InputPin=" + InputPin +
                ", OutputPin=" + OutputPin +
                ", StatusPin=" + StatusPin +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Diameter='" + Diameter + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public Sensors(Integer inputPin, Integer outputPin, Integer statusPin, String name, String type, String diameter, String description) {
        InputPin = inputPin;
        OutputPin = outputPin;
        StatusPin = statusPin;
        Name = name;
        Type = type;
        Diameter = diameter;
        Description = description;
    }
}
