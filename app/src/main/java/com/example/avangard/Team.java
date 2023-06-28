package com.example.avangard;

import java.io.Serializable;

public class Team implements Serializable {
    private String name;
    private String city;
    private String emblemPath;
    private Team(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmblemPath() {
        return emblemPath;
    }

    public void setEmblemPath(String emblemPath) {
        this.emblemPath = emblemPath;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Team(String name, String city, String emblemPath) {
        this.name = name;
        this.city = city;
        this.emblemPath = emblemPath;
    }
}
