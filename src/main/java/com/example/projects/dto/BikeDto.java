package com.example.projects.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
@JsonIgnoreProperties
public class BikeDto implements DtoInterface {

    private Long bikeId;
    private List<Long> journeyIds;
    private String currentStation;
    private String previousStation;


    public BikeDto(Long bikeId, List<Long> journeyIds, String currentStation, String previousStation) {
        this.bikeId = bikeId;
        this.journeyIds = journeyIds;
        this.currentStation = currentStation;
        this.previousStation = previousStation;
    }

    public BikeDto() {
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public List<Long> getJourneyIds() {
        return journeyIds;
    }

    public void setJourneyIds(List<Long> journeyIds) {
        this.journeyIds = journeyIds;
    }

    @NotNull
    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public String getPreviousStation() {
        return previousStation;
    }

    public void setPreviousStation(String previousStation) {
        this.previousStation = previousStation;
    }
}
