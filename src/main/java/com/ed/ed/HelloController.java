package com.ed.ed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Kontroler dla pierwszego okna
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> modeComboBox;
    private List<String[]> csvData = new ArrayList<>();
    private Analysis analysis = new Analysis();

    @FXML
    protected void onLoadCSVClick() {
        //Stworzenie okna wyboru pliku i pobranie wartości
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(welcomeText.getScene().getWindow());
        //Wczytanie pliku
        if (file != null) {
            welcomeText.setText("Wczytano plik: " + file.getName());
            csvData.clear();
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

    //Po kliknięciu "Oblicz" - podstawowa weryfikacja danych i wywołanie nowego okna z wynikami
    @FXML
    protected void onCalculateClick() {
        if (csvData.isEmpty()) {
            welcomeText.setText("Nie wybrano pliku!");
            return;
        }
        if ("Regresja".equalsIgnoreCase(modeComboBox.getValue())) {
            if(csvData.get(0).length != 3) {
                welcomeText.setText("Załadowano niepoprawne dane. Załaduj dane w formacie TV,PV1,prob1,PV2,prob2");
                return;
            }
            welcomeText.setText("Ładowanie... Nie wyłączaj aplikacji.");
            try{
                RegressionResult[] results = analysis.regressionAnalysis(csvData);
                showRegressionWindow(results);
                welcomeText.setText("Załadowano!");
            }catch(ArithmeticException e){
                e.printStackTrace();
            }
        } else if ("Klasyfikacja".equalsIgnoreCase(modeComboBox.getValue())) {
            if(csvData.get(0).length != 5) {
                welcomeText.setText("Załadowano niepoprawne dane. Załaduj dane w formacie TV,PV1,prob1,PV2,prob2");
                return;
            }
            welcomeText.setText("Ładowanie... Nie wyłączaj aplikacji.");
            try {
                ClassificationResult[] results = analysis.classificationAnalysis(csvData);
                showResultWindow(results);
                welcomeText.setText("Załadowano!");
            }catch (ArithmeticException e){
                e.printStackTrace();
            }
        } else {
            welcomeText.setText("Nie wybrano trybu analizy!");
        }
    }

    //Wywołanie okna dla analizy klasyfikacyjnej
    private void showResultWindow(ClassificationResult[] results) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ed/ed/result-window-view.fxml"));
            Parent root = loader.load();

            ResultWindowController controller = loader.getController();
            controller.setResults(results);

            Stage stage = new Stage();
            stage.setTitle("Wyniki Klasyfikacji");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Wywołanie okna dla analizy regresyjnej
    private void showRegressionWindow(RegressionResult[] results) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ed/ed/regression-window-view.fxml"));
            Parent root = loader.load();
            RegressionWindowController controller = loader.getController();

            controller.setResults(results);

            Stage stage = new Stage();
            stage.setTitle("Wyniki Regresji");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}