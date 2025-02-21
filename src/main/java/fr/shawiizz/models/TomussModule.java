package fr.shawiizz.models;

import fr.shawiizz.util.TomussParser;

import java.util.List;

public class TomussModule {
    public String title;
    public String ue;
    public String apogeeCode;
    public List<Grade> notes;

    public TomussModule(String tableTitle, String ue, List<Grade> notes) {
        this.title = tableTitle;
        this.ue = ue;
        this.notes = notes;
        this.apogeeCode = TomussParser.getApogeeCode(ue);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getApogeeCode() {
        return apogeeCode;
    }

    public void setApogeeCode(String apogeeCode) {
        this.apogeeCode = apogeeCode;
    }

    public List<Grade> getNotes() {
        return notes;
    }

    public void setNotes(List<Grade> notes) {
        this.notes = notes;
    }
}