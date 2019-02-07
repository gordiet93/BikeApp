package com.example.projects.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by GTaggart on 28/02/2018.
 */

@Entity
@Table(name = "journey")
public class Journey {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_station", nullable = false)
    private Station startStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "end_station")
    private Station endStation;

    @NotNull
    private int duration;

    @NotNull
    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;

    @NotNull
    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    @NotNull
    @Column(name = "outside_top_five")
    private boolean outsideTopFive;

    public Journey() {}

    public Journey(Bike bike, Station startStation, Station endStation, int duration, Date departureTime,
                   Date arrivalTime, boolean outsideTopFive) {
        this.bike = bike;
        this.startStation = startStation;
        this.endStation = endStation;
        this.duration = duration;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.outsideTopFive = outsideTopFive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isOutsideTopFive() {
        return outsideTopFive;
    }

    public void setOutsideTopFive(boolean outsideTopFive) {
        this.outsideTopFive = outsideTopFive;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date dateTimeFinish) {
        this.arrivalTime = dateTimeFinish;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Override
    public String toString() {
        return "Journey " + id;
    }
}
