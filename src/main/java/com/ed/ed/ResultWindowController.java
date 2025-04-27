// File: ResultWindowController.java

package com.ed.ed;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.geometry.Point2D;

import java.util.List;

//Kontroler okna z wynikami analizy klasyfikacyjnej
public class ResultWindowController {
    @FXML
    private Label trafnosc1, czulosc1, swoistosc1, precyzja1, f11, auc;
    @FXML
    private Label trafnosc2, czulosc2, swoistosc2, precyzja2, f12, auc2;
    @FXML
    private LineChart<Number, Number> rocChart;

    //Rysuj wykres pomijając co {step} punktów ROC. Kosztem dokładności uzyskaliśmy optymalizację
    private int step = 15;

    public void setResults(ClassificationResult[] results) {
        ClassificationResult model1 = results[0];
        ClassificationResult model2 = results[1];

        trafnosc1.setText(String.valueOf(model1.getTrafnosc()));
        czulosc1.setText(String.valueOf(model1.getCzulosc()));
        swoistosc1.setText(String.valueOf(model1.getSwoistosc()));
        precyzja1.setText(String.valueOf(model1.getPrecyzja()));
        f11.setText(String.valueOf(model1.getF1()));
        auc.setText(String.valueOf(model1.getAuc()));

        trafnosc2.setText(String.valueOf(model2.getTrafnosc()));
        czulosc2.setText(String.valueOf(model2.getCzulosc()));
        swoistosc2.setText(String.valueOf(model2.getSwoistosc()));
        precyzja2.setText(String.valueOf(model2.getPrecyzja()));
        f12.setText(String.valueOf(model2.getF1()));
        auc2.setText(String.valueOf(model2.getAuc()));

        setColor(trafnosc1, trafnosc2, model1.getTrafnosc(), model2.getTrafnosc());
        setColor(czulosc1, czulosc2, model1.getCzulosc(), model2.getCzulosc());
        setColor(swoistosc1, swoistosc2, model1.getSwoistosc(), model2.getSwoistosc());
        setColor(precyzja1, precyzja2, model1.getPrecyzja(), model2.getPrecyzja());
        setColor(f11, f12, model1.getF1(), model2.getF1());
        setColor(auc, auc2, model1.getAuc(), model2.getAuc());

        drawROC(model1, model2);
    }
    private void setColor(Label label1, Label label2, double value1, double value2) {
        if (value1 > value2) {
            label1.setStyle("-fx-text-fill: green;");
            label2.setStyle("-fx-text-fill: red;");
        } else if (value1 < value2) {
            label1.setStyle("-fx-text-fill: red;");
            label2.setStyle("-fx-text-fill: green;");
        } else {
            label1.setStyle("-fx-text-fill: black;");
            label2.setStyle("-fx-text-fill: black;");
        }
    }
    private void drawROC(ClassificationResult model1, ClassificationResult model2) {
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series1.setName("Model 1");
        series2.setName("Model 2");

        List<Point2D> points1 = model1.getPunktyROC();
        List<Point2D> points2 = model2.getPunktyROC();

        for (int i = 0; i < points1.size(); i += step) {
            Point2D p1 = points1.get(i);
            series1.getData().add(new XYChart.Data<>(p1.getX(), p1.getY()));
            Point2D p2 = points2.get(i);
            series2.getData().add(new XYChart.Data<>(p2.getX(), p2.getY()));
        }
        rocChart.getData().clear();
        rocChart.getData().addAll(series1, series2);
    }
}