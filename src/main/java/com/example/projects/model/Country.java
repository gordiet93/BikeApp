package com.example.projects.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Country {

    private City city;
    private String name;

    public City getCity() {
        return city;
    }

    @XmlElement(name = "city")
    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }
}
