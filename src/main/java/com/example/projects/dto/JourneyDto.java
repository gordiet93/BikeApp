package com.example.projects.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class JourneyDto implements DtoInterface {

    private long journeyId;
    private Long bikeId;
    private String startStationName;
    private String endStationName;
    private Long startStationId;
    private Long endStationId;
    private int duration;
    private Date dateTimeFinish;
    private boolean outsideTopFive;

    public JourneyDto(long journeyId, Long bikeId, Long startStationId, Long endStationId, String startStation,
                      String endStation, int duration, Date dateTimeFinish, boolean outsideTopFive) {
        this.journeyId = journeyId;
        this.bikeId = bikeId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.startStationName = startStation;
        this.endStationName = endStation;
        this.duration = duration;
        this.dateTimeFinish = dateTimeFinish;
        this.outsideTopFive = outsideTopFive;
    }

    public JourneyDto() {
    }

    public long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(long journeyId) {
        this.journeyId = journeyId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    @NotNull
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDateTimeFinish() {
        return dateTimeFinish;
    }

    public void setDateTimeFinish(Date dateTimeFinish) {
        this.dateTimeFinish = dateTimeFinish;
    }

    public boolean isOutsideTopFive() {
        return outsideTopFive;
    }

    public void setOutsideTopFive(boolean outsideTopFive) {
        this.outsideTopFive = outsideTopFive;
    }

    public Long getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(Long startStationId) {
        this.startStationId = startStationId;
    }

    public Long getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(Long endStationId) {
        this.endStationId = endStationId;
    }
}
