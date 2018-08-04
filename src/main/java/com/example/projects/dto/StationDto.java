package com.example.projects.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class StationDto implements DtoInterface {

    private Long stationId;
    private String stationName;
    private int totalDepartures;
    private int totalArrivals;
    private List<Long> bikes;

    public StationDto(Long stationId, String stationName, int totalDepartures, int totalArrivals, List<Long> bikes) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.totalDepartures = totalDepartures;
        this.totalArrivals = totalArrivals;
        this.bikes = bikes;
    }

    public StationDto() {
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getTotalDepartures() {
        return totalDepartures;
    }

    public void setTotalDepartures(int totalDepartures) {
        this.totalDepartures = totalDepartures;
    }

    public int getTotalArrivals() {
        return totalArrivals;
    }

    public void setTotalArrivals(int totalArrivals) {
        this.totalArrivals = totalArrivals;
    }

    public List<Long> getBikes() {
        return bikes;
    }

    public void setBikes(List<Long> bikes) {
        this.bikes = bikes;
    }
}
