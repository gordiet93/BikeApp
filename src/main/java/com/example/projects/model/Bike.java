package com.example.projects.model;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GTaggart on 28/02/2018.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "bike")
public class Bike implements Serializable {

    @Id
    @NotNull
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
    private List<Journey> journeys = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "current_station")
    private Station currentStation;

    @ManyToOne
    @JoinColumn(name = "previous_station")
    private Station previousStation;

    @Transient
    private boolean departedOutsideTopFive;

    @Enumerated(EnumType.STRING)
    private BikeStatus status;

    public Bike() {
        departedOutsideTopFive = false;
        this.status = BikeStatus.DOCKED;
    }

    public Bike(Long id, Station currentStation) {
        this.id = id;
        this.currentStation = currentStation;
        departedOutsideTopFive = false;
        status = BikeStatus.DOCKED;
    }

    //Check for id match
    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Bike) {
            isEqual = (this.id.equals(((Bike) object).id));
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Long.hashCode(id);
        return result;
        //return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bike " + id;
    }

    public Long getId() {
        return id;
    }

    @XmlAttribute(name = "number")
    private void setId(Long id) {
        this.id = id;
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

    public boolean isDepartedOutsideTopFive() {
        return departedOutsideTopFive;
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

    public BikeStatus getStatus() {
        return status;
    }

    public void setStatus(BikeStatus status) {
        this.status = status;
    }
}
