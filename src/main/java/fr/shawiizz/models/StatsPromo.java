package fr.shawiizz.models;

public class StatsPromo {
    public Integer students;
    public Integer rank;

    public StatsPromo(Integer students, Integer rank) {
        this.students = students;
        this.rank = rank;
    }

    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}