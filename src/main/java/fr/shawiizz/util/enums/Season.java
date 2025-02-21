package fr.shawiizz.util.enums;

public enum Season {
    AUTOMNE("Automne"),
    PRINTEMPS("Printemps");

    private String name;

    Season(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}