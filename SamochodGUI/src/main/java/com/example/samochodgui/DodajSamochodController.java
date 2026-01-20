package com.example.samochodgui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DodajSamochodController {

    @FXML
    private TextField modelPole;
    @FXML
    private TextField rejestracjaPole;
    @FXML
    private TextField wagaPole;
    @FXML
    private TextField predkoscPole;
    @FXML
    private javafx.scene.control.ComboBox<String> silnikBox;
    @FXML
    private javafx.scene.control.ComboBox<String> skrzyniaBox;
    @FXML
    private Button zatwierdzPrzycisk;
    @FXML
    private Button anulujPrzycisk;

    @FXML
    public void initialize() {
        silnikBox.getItems().addAll("Diesel (5000 RPM)", "Benzyna (7000 RPM)");
        silnikBox.getSelectionModel().selectFirst();

        skrzyniaBox.getItems().addAll("Manualna", "Automatyczna");
        skrzyniaBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void naZatwierdzPrzycisk() {
        String model = modelPole.getText();
        String rejestracja = rejestracjaPole.getText();
        double waga;
        int predkosc;

        try {
            waga = Double.parseDouble(wagaPole.getText());
            predkosc = Integer.parseInt(predkoscPole.getText());
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane. Spróbuj ponownie.");
            return;
        }

        // Pobranie danych o silniku
        String silnikWybor = silnikBox.getValue();
        int maxObroty = 6000;
        String typSilnika = "Nieznany";

        if (silnikWybor.contains("Diesel")) {
            maxObroty = 5000;
            typSilnika = "Diesel";
        } else if (silnikWybor.contains("Benzyna")) {
            maxObroty = 7000;
            typSilnika = "Benzyna";
        }

        // Pobranie danych o skrzyni
        String skrzyniaWybor = skrzyniaBox.getValue();
        boolean automat = skrzyniaWybor.equals("Automatyczna");

        HelloController.dodajSamochodDoListy(model, rejestracja, waga, predkosc, maxObroty, automat, typSilnika);

        Stage stage = (Stage) zatwierdzPrzycisk.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void naAnulujPrzycisk() {
        Stage stage = (Stage) anulujPrzycisk.getScene().getWindow();
        stage.close();
    }
}
