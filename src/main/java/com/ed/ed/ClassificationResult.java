package com.ed.ed;

public class ClassificationResult {
    //For better understanding - polish names
    private double trafnosc;
    private double czulosc;
    private double swoistosc;
    private double precyzja;
    private double f1;

    public double getTrafnosc() {
        return trafnosc;
    }

    public void setTrafnosc(double trafnosc) {
        this.trafnosc = trafnosc;
    }

    public double getCzulosc() {
        return czulosc;
    }

    public void setCzulosc(double czulosc) {
        this.czulosc = czulosc;
    }

    public double getSwoistosc() {
        return swoistosc;
    }

    public void setSwoistosc(double swoistosc) {
        this.swoistosc = swoistosc;
    }

    public double getPrecyzja() {
        return precyzja;
    }

    public void setPrecyzja(double precyzja) {
        this.precyzja = precyzja;
    }

    public double getF1() {
        return f1;
    }

    public void setF1(double f1) {
        this.f1 = f1;
    }
}
