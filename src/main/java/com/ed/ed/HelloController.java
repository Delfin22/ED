package com.ed.ed;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> modeComboBox;

    private List<String[]> csvData = new ArrayList<>();
    private Analysis analysis;
    @FXML
    protected void onLoadCSVClick() {
        //Stworzenie okna wyboru pliku i pobranie wartości
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(welcomeText.getScene().getWindow());
        if (file != null) {
            welcomeText.setText("Wczytano plik: " + file.getName());
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    csvData.add(values);
                }
            } catch (IOException e) {
                welcomeText.setText("Błąd podczas wczytywania pliku.");
                e.printStackTrace();
            }
        } else {
            welcomeText.setText("Nie wybrano pliku.");
        }
    }

    @FXML
    protected void onCalculateClick() {
        if (csvData.isEmpty()) {
            welcomeText.setText("Nie wybrano pliku!");
            return;
        }
        if ("Regresja".equalsIgnoreCase(modeComboBox.getValue())) {

            analysis.regressionAnalysis(csvData);
        } else if ("Klasyfikacja".equalsIgnoreCase(modeComboBox.getValue())) {
            ClassificationResult result = analysis.classificationAnalysis(csvData);
        } else {
            welcomeText.setText("Nie wybrano trybu analizy!");
        }
    }
}