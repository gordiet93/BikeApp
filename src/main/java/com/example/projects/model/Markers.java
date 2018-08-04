package com.example.projects.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "markers")
public class Markers {

    /*private Country country;

    public Country getCountry() {
        return country;
    }

    @XmlElement(name = "country")
    public void setCountry(Country country) {
        this.country = country;
    }*/


    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    @XmlElement(name = "country")
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
