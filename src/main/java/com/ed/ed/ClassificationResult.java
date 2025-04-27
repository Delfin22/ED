package com.ed.ed;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

//Klasa będąca wynikiem analizy klasyfikacyjnej dla jednego modelu
public class ClassificationResult {
    private double trafnosc;
    private double czulosc;
    private double swoistosc;
    private double precyzja;
    private double f1;
    private List<Point2D> punktyROC;
    private double auc;

    public ClassificationResult() {
        punktyROC = new ArrayList<>();
    }

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

    public List<Point2D> getPunktyROC() {
        return punktyROC;
    }

    public void setPunktyROC(List<Point2D> punktyROC) {
        this.punktyROC = punktyROC;
    }

    public double getAuc() {
        return auc;
    }

    public void setAuc(double auc) {
        this.auc = auc;
    }
}
