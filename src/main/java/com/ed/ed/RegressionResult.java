package com.ed.ed;

import java.util.ArrayList;
import java.util.List;

//Klasa będąca wynikiem Modelu regresyjnego
public class RegressionResult {
    private double MAE;
    private double MAPE;
    private double MSE;
    private double RMSE;
    private List<Double> diff;

    public RegressionResult(int dataSize) {
        diff = new ArrayList<>(dataSize);
    }

    public List<Double> getDiff() {
        return diff;
    }

    public void setDiff(List<Double> diff) {
        this.diff = diff;
    }

    public double getMAE() {
        return MAE;
    }

    public void setMAE(double MAE) {
        this.MAE = MAE;
    }

    public double getMAPE() {
        return MAPE;
    }

    public void setMAPE(double MAPE) {
        this.MAPE = MAPE;
    }

    public double getMSE() {
        return MSE;
    }

    public void setMSE(double MSE) {
        this.MSE = MSE;
    }

    public double getRMSE() {
        return RMSE;
    }

    public void setRMSE(double RMSE) {
        this.RMSE = RMSE;
    }
}
