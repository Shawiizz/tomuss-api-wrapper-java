package fr.shawiizz.models;

import java.time.Instant;

public class Grade {
    public String title;
    public String comment;
    public Mark mark;
    public String teacherName;
    public Instant date;
    public Stats stats;
    public boolean isAverage;
    public double weight;


    public Grade(String title, String comment, Mark mark, String teacherName, Instant date, Stats stats, boolean isAverage, double weight) {
        this.title = title;
        this.comment = comment;
        this.mark = mark;
        this.teacherName = teacherName;
        this.date = date;
        this.stats = stats;
        this.isAverage = isAverage;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public boolean isAverage() {
        return isAverage;
    }

    public void setAverage(boolean average) {
        isAverage = average;
    }
}