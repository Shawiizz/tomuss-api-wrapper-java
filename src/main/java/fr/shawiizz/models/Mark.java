package fr.shawiizz.models;

public class Mark {
    public double value;
    public double on;

    public Mark(double value, double on) {
        this.value = value;
        this.on = on;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getOn() {
        return on;
    }

    public void setOn(double on) {
        this.on = on;
    }
}