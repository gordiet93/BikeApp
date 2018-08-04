package com.example.projects.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by GTaggart on 02/03/2018.
 */
@XmlRootElement(name = "city")
public class
City {

    private List<Station> stations;

    private String name;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    @XmlElement(name = "place")
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
