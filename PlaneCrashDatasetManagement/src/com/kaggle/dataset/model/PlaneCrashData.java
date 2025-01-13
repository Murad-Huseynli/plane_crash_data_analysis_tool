package com.kaggle.dataset.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * The plane crash data class
 */
public class PlaneCrashData {

    private LocalDate date;
    private LocalTime time;
    private String location;
    private String operator;
    private String flight;
    private String route;
    private String type;
    private String registration;
    private String cnIn;
    private Integer aboard;
    private Integer fatalities;
    private Integer ground;
    private Integer survivors;
    private Double survivalRate;
    private String summary;
    private String clustId;

    public PlaneCrashData(LocalDate date, LocalTime time, String location, String operator, String flight, String route, String type,
                          String registration, String cnIn, Integer aboard, Integer fatalities, Integer ground,
                          Integer survivors, Double survivalRate, String summary, String clustId) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.operator = operator;
        this.flight = flight;
        this.route = route;
        this.type = type;
        this.registration = registration;
        this.cnIn = cnIn;
        this.aboard = aboard;
        this.fatalities = fatalities;
        this.ground = ground;
        this.survivors = survivors;
        this.survivalRate = survivalRate;
        this.summary = summary;
        this.clustId = clustId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getCnIn() {
        return cnIn;
    }

    public void setCnIn(String cnIn) {
        this.cnIn = cnIn;
    }

    public Integer getAboard() {
        return aboard;
    }

    public void setAboard(Integer aboard) {
        this.aboard = aboard;
    }

    public Integer getFatalities() {
        return fatalities;
    }

    public void setFatalities(Integer fatalities) {
        this.fatalities = fatalities;
    }

    public Integer getGround() {
        return ground;
    }

    public void setGround(Integer ground) {
        this.ground = ground;
    }

    public Integer getSurvivors() {
        return survivors;
    }

    public void setSurvivors(Integer survivors) {
        this.survivors = survivors;
    }

    public Double getSurvivalRate() {
        return survivalRate;
    }

    public void setSurvivalRate(Double survivalRate) {
        this.survivalRate = survivalRate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClustId() {
        return clustId;
    }

    public void setClustId(String clustId) {
        this.clustId = clustId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaneCrashData that = (PlaneCrashData) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(location, that.location) && Objects.equals(operator, that.operator) && Objects.equals(flight, that.flight) && Objects.equals(route, that.route) && Objects.equals(type, that.type) && Objects.equals(registration, that.registration) && Objects.equals(cnIn, that.cnIn) && Objects.equals(aboard, that.aboard) && Objects.equals(fatalities, that.fatalities) && Objects.equals(ground, that.ground) && Objects.equals(survivors, that.survivors) && Objects.equals(survivalRate, that.survivalRate) && Objects.equals(summary, that.summary) && Objects.equals(clustId, that.clustId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, location, operator, flight, route, type, registration, cnIn, aboard, fatalities, ground, survivors, survivalRate, summary, clustId);
    }

    @Override
    public String toString() {
        return "PlaneCrashData{" +
                "date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", operator='" + operator + '\'' +
                ", flight='" + flight + '\'' +
                ", route='" + route + '\'' +
                ", type='" + type + '\'' +
                ", registration='" + registration + '\'' +
                ", cnIn='" + cnIn + '\'' +
                ", aboard=" + aboard +
                ", fatalities=" + fatalities +
                ", ground=" + ground +
                ", survivors=" + survivors +
                ", survivalRate=" + survivalRate +
                ", summary='" + summary + '\'' +
                ", clustId='" + clustId + '\'' +
                '}';
    }
}
