package com.project.myapplication;

public class Model {

    String cityname;
    String name;
    String discription;
    String image;

    public Model() {
    }

    public Model(String cityname, String name, String discription, String image) {
        this.cityname = cityname;
        this.name = name;
        this.discription = discription;
        this.image = image;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
