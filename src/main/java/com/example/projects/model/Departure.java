package com.example.projects.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "departure")
public class Departure {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "departure_id")
    private Long departureId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_station", referencedColumnName = "station_id")
    private Station departureStation;

    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    public Departure() {}

    public Departure(Bike bike, Station departureStation, Date departureTime) {
        this.bike = bike;
        this.departureStation = departureStation;
        this.departureTime = departureTime;
    }

    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Departure " + departureId;
    }
}
