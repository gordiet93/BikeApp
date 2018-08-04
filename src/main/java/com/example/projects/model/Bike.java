package com.example.projects.model;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by GTaggart on 28/02/2018.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "bike")
public class Bike implements Serializable {

    @Id
    @NotNull
    @Column(name = "bike_id")
    private Long bikeId;

    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
    private List<Journey> journeys = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "current_station", nullable = false)
    private Station currentStation;

    @ManyToOne
    @JoinColumn(name = "previous_station", referencedColumnName = "station_id")
    private Station previousStation;

    @Transient
    private long departureTime;

    @Transient
    private boolean departedOutsideTopFive;

    @Column(name = "tracked")
    private boolean tracked;

    public Bike() {
        departedOutsideTopFive = false;
    }

    public Bike(Long id, Station currentStation) {
        this.bikeId = id;
        this.currentStation = currentStation;
        departedOutsideTopFive = false;
        tracked = false;
    }

    //Check for bikeId match
    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Bike) {
            isEqual = (this.bikeId.equals(((Bike) object).bikeId));
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Long.hashCode(bikeId);
        return result;
        //return Objects.hash(bikeId);
    }

    @Override
    public String toString() {
        return "Bike " + bikeId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    @XmlAttribute(name = "number")
    private void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(long departureTime) {
        this.departureTime = departureTime;
    }

    public boolean isDepartedOutsideTopFive() {
        return departedOutsideTopFive;
    }

    public boolean isTracked() {
        return tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public void setDepartedOutsideTopFive(boolean departedOutsideTopFive) {
        this.departedOutsideTopFive = departedOutsideTopFive;
    }

    public List<Journey> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<Journey> journeys) {
        this.journeys = journeys;
    }
}