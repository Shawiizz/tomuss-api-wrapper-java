package fr.shawiizz.models;

public class Stats {
    public StatsGroup statsGroup;
    public StatsPromo statsPromo;
    public Double average;
    public Double mediane;

    public Stats(Double average, Double mediane, StatsGroup statsGroup, StatsPromo statsPromo) {
        this.average = average;
        this.mediane = mediane;
        this.statsGroup = statsGroup;
        this.statsPromo = statsPromo;
    }

    public Stats() {
    }

    public StatsGroup getStatsGroup() {
        return statsGroup;
    }

    public void setStatsGroup(StatsGroup statsGroup) {
        this.statsGroup = statsGroup;
    }

    public StatsPromo getStatsPromo() {
        return statsPromo;
    }

    public void setStatsPromo(StatsPromo statsPromo) {
        this.statsPromo = statsPromo;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getMediane() {
        return mediane;
    }

    public void setMediane(Double mediane) {
        this.mediane = mediane;
    }
}