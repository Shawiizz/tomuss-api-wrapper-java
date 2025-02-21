package fr.shawiizz.models.tomuss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Map;

public class TomussGradesModel {
    public enum TomussType {
        Bool, Enumeration, Max, Moy, Note, Text, UeGrade, Replace, Login, Upload, Ue_Grade, URL, Notation, COW, Prst, Date;

        public static boolean isType(String type, TomussType tomussType) {
            return type.equals(tomussType.name());
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TomussStat {
        public int nr;
        public int nr_in_grp;
        public int rank;
        public int rank_grp;
        public double average;
        public double mediane;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TomussColumn {
        public String type;
        public String author;
        public String comment;
        public String freezed;
        public int position;
        public int repetition;
        public String title;
        public String the_id;
        public int width;
        public int hidden;
        public String minmax;
        public String weight;
        public String columns;
        public String rounding;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TomussGradeElement {
        public String table_title;
        public int popup_on_red_line;
        public ArrayList<String> managers;
        public int bookmark;
        public ArrayList<Double> dates;
        public int official_ue;
        public ArrayList<Object> masters;
        public String ue;
        public int year;
        public String semester;
        public ArrayList<TomussColumn> columns;
        public String line_id;
        public ArrayList<ArrayList<Object>> line;
        public Map<String, TomussStat> stats;
    }

}
