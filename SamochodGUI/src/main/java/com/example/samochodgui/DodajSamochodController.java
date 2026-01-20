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
    private Button zatwierdzPrzycisk;
    @FXML
    private Button anulujPrzycisk;

    @FXML
    private void naZatwierdzPrzycisk() {
        String model = modelPole.getText();
        String rejestracja = rejestracjaPole.getText();//Program bierze to, co wpisałem jako napisy (String).
        double waga;
        int predkosc;

        try {
            waga = Double.parseDouble(wagaPole.getText());
            predkosc = Integer.parseInt(predkoscPole.getText());//zamienia tekst na liczbe
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawne dane. Spróbuj ponownie.");
            return;//gdy bład to zatrzymuje proogram
        }


        HelloController.dodajSamochodDoListy(model, rejestracja, waga, predkosc);//daje dane do głownego controlera


        Stage stage = (Stage) zatwierdzPrzycisk.getScene().getWindow();
        stage.close();//zamyka oknno
    }

    @FXML
    private void naAnulujPrzycisk() {
        Stage stage = (Stage) anulujPrzycisk.getScene().getWindow();
        stage.close();//anulowanie zamykanie okna ale nieprzerywa mojego programu
    }
}
