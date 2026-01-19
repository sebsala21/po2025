package com.example.samochodgui;

import com.example.samochodgui.symulator.Samochod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField jakimodel;
    @FXML
    private TextField nrrejstracji;
    @FXML
    private TextField wagaField;
    @FXML
    private TextField predkoscField;
    @FXML
    private TextField biegPole;
    @FXML
    private TextField obrotyPole;
    @FXML
    private TextField sprzegloField;
    @FXML
    private ComboBox<Samochod> wyborSamochodu;
    @FXML
    private ImageView obrazekSamochodu;

    private Samochod mojeAuto;

    // Static list to be accessed by addCarToList
    private static ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();

    // Ta metoda uruchamia się sama przy starcie okna
    @FXML
    public void initialize() {
        System.out.println("HelloController initialized");

        // Initial car
        if (listaSamochodow.isEmpty()) {
            mojeAuto = new Samochod(); // Default Audi
            listaSamochodow.add(mojeAuto);
        } else {
            mojeAuto = listaSamochodow.get(0);
        }

        // Setup ComboBox
        wyborSamochodu.setItems(listaSamochodow);

        // Set a custom cell factory if needed to show names nicely, but verify
        // Samochod.toString() first.
        // Assuming we rely on toString() for now, or we can just set it.
        // But for now let's leave as is, verify how it displays.

        wyborSamochodu.getSelectionModel().select(mojeAuto);

        // Load image
        try {
            Image carImage = new Image(getClass().getResource("/images/car.png").toExternalForm());
            obrazekSamochodu.setImage(carImage);

            // Optional resizing logic from lab instructions
            obrazekSamochodu.setFitWidth(300);
            obrazekSamochodu.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Could not load image: " + e.getMessage());
        }

        odswiez();
    }

    public static void dodajSamochodDoListy(String model, String rejestracja, double waga, int predkosc) {
        Samochod nowySamochod = new Samochod(model, rejestracja, waga, predkosc);
        listaSamochodow.add(nowySamochod);
    }

    @FXML
    public void otworzOknoDodawania() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajSamochod.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Dodaj nowy samochód");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Wlacz_auto() {
        mojeAuto.wlacz();
        odswiez();
    }

    @FXML
    public void wylacz_auto() {
        mojeAuto.wylacz();
        odswiez();
    }

    @FXML
    public void zwieksz_bieg() {
        mojeAuto.getSkrzynia().zwiekszBieg();
        odswiez();
    }

    @FXML
    public void zmniejsz_bieg() {
        mojeAuto.getSkrzynia().zmniejszBieg();
        odswiez();
    }

    @FXML
    public void dodaj_gazu() {
        mojeAuto.getSilnik().zwiekszObroty();
        odswiez();
    }

    @FXML
    public void pusc_gaz() {
        mojeAuto.getSilnik().zmniejszObroty();
        odswiez();
    }

    @FXML
    public void wcisnij_sprzeglo() {
        mojeAuto.getSprzeglo().wcisnij();
        odswiez();
    }

    @FXML
    public void zwolnij_sprzeglo() {
        mojeAuto.getSprzeglo().zwolnij();
        odswiez();
    }

    @FXML
    public void zamknijAplikacje() {
        System.out.println("Zamykam program...");
        System.exit(0);
    }

    @FXML
    public void utworzNowySamochod() {
        otworzOknoDodawania();
    }

    @FXML
    public void wybierzAutoZListy() {
        Samochod wybrany = wyborSamochodu.getSelectionModel().getSelectedItem();
        if (wybrany != null) {
            mojeAuto = wybrany;
            System.out.println("Wybrano: " + mojeAuto.getModel());
            odswiez();
        }
    }

    @FXML
    public void wyczyscFormularz() {
        // Implementation for Delete if needed, or just clear fields (but they are read
        // only now)
        // Leaving logic similar to before but calling refresh
        mojeAuto.wylacz();
        odswiez();
        System.out.println("Wyczyszczono formularz (reset state).");
    }

    // Renamed from zaktualizujWidok
    private void odswiez() {
        if (mojeAuto == null)
            return;

        jakimodel.setText(mojeAuto.getModel());
        nrrejstracji.setText(mojeAuto.getNrRejestracyjny());

        int aktualnyBieg = mojeAuto.getSkrzynia().getAktualnyBieg();
        biegPole.setText(String.valueOf(aktualnyBieg));

        double aktualneObroty = mojeAuto.getSilnik().getObroty();
        obrotyPole.setText(String.format("%.0f", aktualneObroty));

        if (mojeAuto.getSprzeglo().czyWcisniete()) {
            sprzegloField.setText("Wciśnięte");
        } else {
            sprzegloField.setText("Puszczone");
        }

        wagaField.setText(String.valueOf(mojeAuto.getWaga()));
        predkoscField.setText(String.format("%.1f", mojeAuto.getAktualnaPredkosc()));
    }
}