package com.ed.ed;

import javafx.geometry.Point2D;

import java.awt.*;
import java.util.*;
import java.util.List;

//Główna klasa odpowiadająca za obliczenia
public class Analysis {
    //Do ustawienia dla innych zestawów danych
    private String trueForClassification = ">50K";

    //Model regresyjny
    public RegressionResult[] regressionAnalysis(List csvFile) throws ArithmeticException {
        int n = csvFile.size() - 1;
        RegressionResult resultModel1 = new RegressionResult(n);
        RegressionResult resultModel2 = new RegressionResult(n);
        double real, expected1, expected2;
        double mae1 = 0, mse1 = 0, mape1 = 0, mae2 = 0, mse2 = 0, mape2 = 0;

        // Pomijamy nagłówek
        for (int i = 1; i < csvFile.size(); i++) {
            String[] record = (String[]) csvFile.get(i);
            real = Double.parseDouble(record[0]);
            expected1 = Double.parseDouble(record[1]);
            expected2 = Double.parseDouble(record[2]);

            // Model 1
            mae1 += Math.abs(real - expected1);
            mse1 += Math.pow(real - expected1, 2);
            mape1 += Math.abs((real - expected1) / real);
            resultModel1.getDiff().add(real - expected1);

            // Model 2
            mae2 += Math.abs(real - expected2);
            mse2 += Math.pow(real - expected2, 2);
            mape2 += Math.abs((real - expected2) / real);
            resultModel2.getDiff().add(real - expected2);
        }
        //*100% do wyświetlania
        mape1*=100;
        mape2*=100;

        // Model1
        resultModel1.setMAE(mae1 / n);
        resultModel1.setMSE(mse1 / n);
        resultModel1.setRMSE(Math.sqrt(mse1 / n));
        resultModel1.setMAPE(mape1 / n);

        // Model2
        resultModel2.setMAE(mae2 / n);
        resultModel2.setMSE(mse2 / n);
        resultModel2.setRMSE(Math.sqrt(mse2 / n));
        resultModel2.setMAPE(mape2 / n);

        return new RegressionResult[] {resultModel1, resultModel2};
    }

    //Model klasyfikacyjny
    public ClassificationResult[] classificationAnalysis(List csvFile) throws ArithmeticException{
        //Sprawdzenie unikalnych wartości
        Set<String> unique = new HashSet<>();
        for(int i = 1; i < csvFile.size(); i++){
            String[] record = (String[]) csvFile.get(i);
            if(record.length>0){
                unique.add(record[0]);
                unique.add(record[1]);
                unique.add(record[3]);
            }
        }
        //Jeśli nie zgadza się z typem danych
        if(unique.size()!=2) return null;

        ClassificationResult resultModel1 = new ClassificationResult();
        ClassificationResult resultModel2 = new ClassificationResult();

        int truePositiveModel1 = 0, trueNegativeModel1 = 0, falsePositiveModel1 = 0, falseNegativeModel1 = 0;
        int truePositiveModel2 = 0, trueNegativeModel2 = 0, falsePositiveModel2 = 0, falseNegativeModel2 = 0;
        int totalPositives = 0, totalNegatives = 0;
        boolean isReal, isPredictedModel1, isPredictedModel2;
        List<ROCPair> probabilitiesModel1 = new ArrayList<>(csvFile.size()-1);
        List<ROCPair> probabilitiesModel2 = new ArrayList<>(csvFile.size()-1);


        //Pomijamy nagłówek
        for (int i = 1; i < csvFile.size(); i++) {
            String[] record = (String[]) csvFile.get(i);
            //Zamieniamy wartości na true/false
            isReal = record[0].equals(trueForClassification);
            isPredictedModel1 = record[1].equals(trueForClassification);
            isPredictedModel2 = record[3].equals(trueForClassification);
            //Tworzenie macierzy pomyłek
            if (isReal) {
                if (isPredictedModel1) truePositiveModel1++;
                else falseNegativeModel1++;
                if (isPredictedModel2) truePositiveModel2++;
                else falseNegativeModel2++;
                totalPositives++;
            } else {
                if (isPredictedModel1) falsePositiveModel1++;
                else trueNegativeModel1++;
                if (isPredictedModel2) falsePositiveModel2++;
                else trueNegativeModel2++;
                totalNegatives++;
            }

            //Dodanie par prawdziwa wartosc, przewidywanie w celu obliczenia ROC
            probabilitiesModel1.add(new ROCPair(isReal, Double.parseDouble(record[2])));
            probabilitiesModel2.add(new ROCPair(isReal, Double.parseDouble(record[4])));
        }

        //Obliczenie podstawowych współczynników dla obydwu modeli
        resultModel1.setTrafnosc((double) (truePositiveModel1 + trueNegativeModel1) / (truePositiveModel1 + trueNegativeModel1 + falsePositiveModel1 + falseNegativeModel1));
        resultModel1.setCzulosc((double) truePositiveModel1 / (truePositiveModel1 + falseNegativeModel1));
        resultModel1.setSwoistosc((double) trueNegativeModel1 / (trueNegativeModel1 + falsePositiveModel1));
        resultModel1.setPrecyzja((double) truePositiveModel1 / (truePositiveModel1 + falsePositiveModel1));
        resultModel1.setF1((resultModel1.getPrecyzja() + resultModel1.getCzulosc()) == 0 ? 0 : 2 * (resultModel1.getPrecyzja() * resultModel1.getCzulosc()) / (resultModel1.getPrecyzja() + resultModel1.getCzulosc()));

        resultModel2.setTrafnosc((double) (truePositiveModel2 + trueNegativeModel2) / (truePositiveModel2 + trueNegativeModel2 + falsePositiveModel2 + falseNegativeModel2));
        resultModel2.setCzulosc((double) truePositiveModel2 / (truePositiveModel2 + falseNegativeModel2));
        resultModel2.setSwoistosc((double) trueNegativeModel2 / (trueNegativeModel2 + falsePositiveModel2));
        resultModel2.setPrecyzja((double) truePositiveModel2 / (truePositiveModel2 + falsePositiveModel2));
        resultModel2.setF1((resultModel2.getPrecyzja() + resultModel2.getCzulosc()) == 0 ? 0 : 2 * (resultModel2.getPrecyzja() * resultModel2.getCzulosc()) / (resultModel2.getPrecyzja() + resultModel2.getCzulosc()));

        //Sortowanie malejące
        probabilitiesModel1.sort(Collections.reverseOrder());
        probabilitiesModel2.sort(Collections.reverseOrder());

        //Obliczenie ROC i AUC z zastosowaną optymalizacją
        int tpModel1 = 0, fpModel1 = 0;
        double previousFPR1 = 0.0, previousTPR1 = 0.0;
        double aucModel1 = 0.0;

        int tpModel2 = 0, fpModel2 = 0;
        double previousFPR2 = 0.0, previousTPR2 = 0.0;
        double aucModel2 = 0.0;

        //Obliczenie punktów i zapisanie do zmiennych
        for (int i = 0; i < probabilitiesModel1.size(); i++) {
            //Model1
            if (probabilitiesModel1.get(i).getPrawdziwaWartosc()) tpModel1++;
            else fpModel1++;
            double tprModel1 = (double) tpModel1 / totalPositives;
            double fprModel1 = (double) fpModel1 / totalNegatives;
            resultModel1.getPunktyROC().add(new Point2D(fprModel1, tprModel1));

            //Obliczanie AUC model1
            if (i > 0) aucModel1 += (fprModel1 - previousFPR1) * (tprModel1 + previousTPR1) / 2;
            previousFPR1 = fprModel1;
            previousTPR1 = tprModel1;

            //Model2
            if (probabilitiesModel2.get(i).getPrawdziwaWartosc()) tpModel2++;
            else fpModel2++;
            double tprModel2 = (double) tpModel2 / totalPositives;
            double fprModel2 = (double) fpModel2 / totalNegatives;
            resultModel2.getPunktyROC().add(new Point2D(fprModel2, tprModel2));

            //Obliczanie AUC model2
            if (i > 0) aucModel2 += (fprModel2 - previousFPR2) * (tprModel2 + previousTPR2) / 2;
            previousFPR2 = fprModel2;
            previousTPR2 = tprModel2;
        }

        resultModel1.setAuc(aucModel1);
        resultModel2.setAuc(aucModel2);

        return new ClassificationResult[]{resultModel1, resultModel2};
    }
}
