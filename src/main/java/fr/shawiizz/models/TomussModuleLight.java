package fr.shawiizz.models;

import fr.shawiizz.util.TomussParser;

public record TomussModuleLight(String title, String ue, String apogeeCode) {
    public TomussModuleLight(String tableTitle, String ue) {
        this(tableTitle, ue, TomussParser.getApogeeCode(ue));
    }
}
