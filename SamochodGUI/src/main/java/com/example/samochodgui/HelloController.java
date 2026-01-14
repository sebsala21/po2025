package com.example.samochodgui;

import com.example.samochodgui.symulator.Samochod;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

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
    private ComboBox wyborSamochodu;


    private Samochod mojeAuto;

    // Ta metoda uruchamia się sama przy starcie okna
    @FXML
    public void initialize() {
        // Tworzymy prawdziwy samochód w pamięci
        mojeAuto = new Samochod();

        // Ustawiamy napisy początkowe
        jakimodel.setText("Audi A4");
        nrrejstracji.setText("KR 12345");
        zaktualizujWidok(); // Funkcja do odświeżania liczników
    }




    @FXML
    public void Wlacz_auto() {
        mojeAuto.wlacz();
        zaktualizujWidok();
    }

    @FXML
    public void wylacz_auto() {
        mojeAuto.wylacz();
        zaktualizujWidok();
    }


    @FXML
    public void zwieksz_bieg() {
        mojeAuto.getSkrzynia().zwiekszBieg();
        zaktualizujWidok();
    }

    @FXML
    public void zmniejsz_bieg() {
        mojeAuto.getSkrzynia().zmniejszBieg();
        zaktualizujWidok();
    }


    @FXML
    public void dodaj_gazu() {
        mojeAuto.getSilnik().zwiekszObroty();
        zaktualizujWidok();
    }

    @FXML
    public void pusc_gaz() {
        mojeAuto.getSilnik().zmniejszObroty();
        zaktualizujWidok();
    }


    @FXML
    public void wcisnij_sprzeglo() {
        mojeAuto.getSprzeglo().wcisnij();
        sprzegloField.setText("Wciśnięte");
        zaktualizujWidok();
    }

    @FXML
    public void zwolnij_sprzeglo() {
        mojeAuto.getSprzeglo().zwolnij();
        sprzegloField.setText("Puszczone");
        zaktualizujWidok();
    }


    @FXML
    public void zamknijAplikacje() {
        System.out.println("Zamykam program...");
        System.exit(0); // To polecenie całkowicie wyłącza program
    }

    @FXML
    public void utworzNowySamochod() {
        // Resetujemy auto do fabrycznego stanu
        mojeAuto = new Samochod();

        jakimodel.setText("");
        nrrejstracji.setText("");
        // Dodajemy przykładowe modele do listy (ComboBox)
        wyborSamochodu.getItems().clear();
        wyborSamochodu.getItems().addAll("Audi A4", "BMW E36", "Fiat 126p");

        System.out.println("Utworzono nowy samochód.");
        zaktualizujWidok();
    }
    @FXML
    public void wybierzAutoZListy() {
        // Sprawdzamy, co użytkownik wybrał
        Object wybranyModel = wyborSamochodu.getValue();

        // Jeśli coś wybrał (nie jest puste), wpisujemy to w pole Model
        if (wybranyModel != null) {
            jakimodel.setText(wybranyModel.toString());
            System.out.println("Wybrano z listy: " + wybranyModel);
        }
    }

    @FXML
    public void wyczyscFormularz() {
        // Czyścimy pola tekstowe (wizualnie)
        jakimodel.setText("");
        nrrejstracji.setText("");

        // Gasimy auto i resetujemy
        mojeAuto.wylacz();
        zaktualizujWidok();
        System.out.println("Wyczyszczono formularz.");
    }

    // Ta metoda odświeża wszystkie liczniki na ekranie
    private void zaktualizujWidok() {

        int aktualnyBieg = mojeAuto.getSkrzynia().getAktualnyBieg();
        biegPole.setText(String.valueOf(aktualnyBieg));

        double aktualneObroty = mojeAuto.getSilnik().getObroty();
        obrotyPole.setText(String.valueOf(aktualneObroty));


        if (mojeAuto.getSprzeglo().czyWcisniete()) {
            sprzegloField.setText("Wciśnięte");
        } else {
            sprzegloField.setText("Puszczone");
        }


        wagaField.setText(String.valueOf(mojeAuto.getWaga()));


        predkoscField.setText(String.valueOf(mojeAuto.getAktualnaPredkosc()));

    }
}