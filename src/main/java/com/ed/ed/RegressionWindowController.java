package com.ed.ed;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;

//Kontroler okna z wynikami analizy regresyjnej
public class RegressionWindowController {
    //Ilość słupków do ustawienia
    private int binCount = 20;
    @FXML
    private Label mae1, mape1, mse1, rmse1;
    @FXML
    private Label mae2, mape2, mse2, rmse2;
    @FXML
    private BarChart<String, Number> histogramChart;

    public void setResults(RegressionResult[] results) {
        RegressionResult model1 = results[0];
        RegressionResult model2 = results[1];

        mae1.setText(String.valueOf(model1.getMAE()));
        mape1.setText(String.valueOf(model1.getMAPE()) + "%");
        mse1.setText(String.valueOf(model1.getMSE()));
        rmse1.setText(String.valueOf(model1.getRMSE()));

        mae2.setText(String.valueOf(model2.getMAE()));
        mape2.setText(String.valueOf(model2.getMAPE()) + "%");
        mse2.setText(String.valueOf(model2.getMSE()));
        rmse2.setText(String.valueOf(model2.getRMSE()));

        setColorRegression(mae1, mae2, model1.getMAE(), model2.getMAE());
        setColorRegression(mape1, mape2, model1.getMAPE(), model2.getMAPE());
        setColorRegression(mse1, mse2, model1.getMSE(), model2.getMSE());
        setColorRegression(rmse1, rmse2, model1.getRMSE(), model2.getRMSE());

        drawHistogram(model1.getDiff(), model2.getDiff());
    }

    //Wyróżnienie lepszego modelu
    private void setColorRegression(Label label1, Label label2, double value1, double value2) {
        if (value1 < value2) {
            label1.setStyle("-fx-text-fill: green;");
            label2.setStyle("-fx-text-fill: red;");
        } else if (value1 > value2) {
            label1.setStyle("-fx-text-fill: red;");
            label2.setStyle("-fx-text-fill: green;");
        } else {
            label1.setStyle("-fx-text-fill: black;");
            label2.setStyle("-fx-text-fill: black;");
        }
    }

    //Rysowanie histogramu bez krzywej normalnej
    private void drawHistogram(List<Double> diff1, List<Double> diff2) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Model 1");
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Model 2");

        //Znalezienie granic zakresu
        double minDiff = Math.min(diff1.stream().min(Double::compareTo).orElse(0.0), diff2.stream().min(Double::compareTo).orElse(0.0));
        double maxDiff = Math.max(diff1.stream().max(Double::compareTo).orElse(0.0), diff2.stream().max(Double::compareTo).orElse(0.0));

        //Obliczenie zakresów
        double binSize = (maxDiff - minDiff) / binCount;

        int[] bins1 = new int[binCount];
        int[] bins2 = new int[binCount];

        //Zliczenie wartości
        for (double d : diff1) {
            int index = (int) Math.min((d - minDiff) / binSize, binCount - 1);
            bins1[index]++;
        }

        for (double d : diff2) {
            int index = (int) Math.min((d - minDiff) / binSize, binCount - 1);
            bins2[index]++;
        }

        for (int i = 0; i < binCount; i++) {
            double binStart = minDiff + i * binSize;
            String range = String.format("%.2f", binStart);
            series1.getData().add(new XYChart.Data<>(range, bins1[i]));
            series2.getData().add(new XYChart.Data<>(range, bins2[i]));
        }

        histogramChart.getData().clear();
        histogramChart.getData().addAll(series1, series2);

    }
}

