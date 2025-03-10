package fr.shawiizz.util;

import fr.shawiizz.util.enums.Season;

import java.time.LocalDate;
import java.util.Objects;

public class Semester {
    private Season season;
    private int year;

    public Semester(Season season, int year) {
        this.season = season;
        this.year = year;
    }

    /**
     * Returns the semester from the given year and season
     */
    public static Semester fromYearAndSeason(int year, Season season) {
        return new Semester(season, year);
    }

    /**
     * Returns the current semester
     */
    public static Semester current() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        return new Semester(month >= 9 ? Season.AUTOMNE : Season.PRINTEMPS, year);
    }

    /**
     * Returns the semester number from the first semester to the wanted semester
     * <p>
     * Example:
     * Semester firstSemester = new Semester(Season.AUTOMNE, 2022);
     * Semester wantedSemester = new Semester(Season.PRINTEMPS, 2024);
     * => 4
     *
     * @param firstSemester  The first semester of the BUT (start index)
     * @param wantedSemester The wanted semester number
     * @return The semester number
     */
    public static int getSemesterNumber(Semester firstSemester, Semester wantedSemester) {
        int semesterNumber = 1;
        Semester semester = firstSemester;
        while (!semester.equals(wantedSemester)) {
            semester = semester.next();
            semesterNumber++;
        }
        return semesterNumber;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the Tomuss url for the given semester
     */
    public String getTomussHomeUrl() {
        return "https://tomuss.univ-lyon1.fr/" + this.year + "/" + this.season.getName();
    }

    /**
     * Returns the next semester
     */
    public Semester next() {
        return this.season == Season.AUTOMNE ? new Semester(Season.PRINTEMPS, this.year + 1) : new Semester(Season.AUTOMNE, this.year);
    }

    /**
     * Returns the previous semester
     */
    public Semester previous() {
        return this.season == Season.PRINTEMPS ? new Semester(Season.AUTOMNE, this.year - 1) : new Semester(Season.PRINTEMPS, this.year);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return getYear() == semester.getYear() && getSeason() == semester.getSeason();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeason(), getYear());
    }

    @Override
    public String toString() {
        return "Semester{" +
                "season=" + season +
                ", year=" + year +
                '}';
    }
}