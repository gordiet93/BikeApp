package com.example.projects.model;

import org.hibernate.annotations.Formula;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GTaggart on 28/02/2018.
 */
@Entity
@XmlRootElement(name = "place")
@Table(name = "station")
public class Station implements Serializable {

    @Id
    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "name")
    private String stationName;

    private static Map<Long, Bike> bikesInTransit = new HashMap<>();

    //not sure about CascadeType
    @OneToMany(mappedBy = "currentStation", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Bike> bikes;

    @Formula("(select count(*) from journey j where j.start_station = station_id)")
    private Integer totalDepartures;

    @Formula("(select count(*) from journey j where j.end_station = station_id)")
    private Integer totalArrivals;

    public Station() {
        this.bikes = new ArrayList<Bike>() {};
    }

    public Station(Long stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.bikes = new ArrayList<Bike>() {};
    }

    @Override
    public String toString() {
        return stationName;
    }

    public Long getStationId() {
        return stationId;
    }

    @XmlAttribute(name = "uid")
    private void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    @XmlAttribute(name = "name")
    private void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    @XmlElement(name = "bike")
    private void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public static Map<Long, Bike> getBikesInTransit() {
        return bikesInTransit;
    }

    public static void setBikesInTransit(Map<Long, Bike> bikesInTransit) {
        Station.bikesInTransit = bikesInTransit;
    }

    public int getTotalDepartures() {
        return totalDepartures;
    }

    public int getTotalArrivals() {
        return totalArrivals;
    }
}
