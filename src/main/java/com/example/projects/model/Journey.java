package com.example.projects.model;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by GTaggart on 28/02/2018.
 */

@Entity
@Table(name = "journey")
public class Journey {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "journey_id")
    private Long journeyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_station", referencedColumnName = "station_id", nullable = false)
    private Station startStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "end_station", referencedColumnName = "station_id", nullable = false)
    private Station endStation;

    @NotNull
    private int duration;

    @NotNull
    @Column(name = "datetime_finish")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeFinish;

    @NotNull
    @Column(name = "outside_top_five")
    private boolean outsideTopFive;

    public Journey() {}

    public Journey(Bike bike, Station startStation, Station endStation, int duration, Date dateTimeFinish, boolean outsideTopFive) {
        this.bike = bike;
        this.startStation = startStation;
        this.endStation = endStation;
        this.duration = duration;
        this.dateTimeFinish = dateTimeFinish;
        this.outsideTopFive = outsideTopFive;
    }

    @Override
    public String toString() {
        return "Journey " + journeyId;
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
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

    public Date getDateTimeFinish() {
        return dateTimeFinish;
    }

    public void setDateTimeFinish(Date dateTimeFinish) {
        this.dateTimeFinish = dateTimeFinish;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }
}
