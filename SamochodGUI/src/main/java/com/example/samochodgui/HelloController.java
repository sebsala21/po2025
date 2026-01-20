package com.example.samochodgui;

import com.example.samochodgui.symulator.Pozycja;
import com.example.samochodgui.symulator.Samochod;
import javafx.application.Platform;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.shape.Rectangle;

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
    // Usunięto statyczny ImageView
    @FXML
    private Pane mapa;

    private Samochod mojeAuto;

    // Śledzenie wizualizacji i słuchaczy
    private final Map<Samochod, ImageView> widokiSamochodow = new HashMap<>();
    private Listener sluchaczPanelu; // Słuchacz dla *aktualnie wybranego* samochodu, aby aktualizować panele UI

    private static ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();

    // Ta metoda uruchamia się sama przy starcie okna
    @FXML
    public void initialize() {
        System.out.println("HelloController initialized");

        // Zdefiniuj słuchacza, który aktualizuje pola tekstowe panelu sterowania
        sluchaczPanelu = () -> Platform.runLater(this::odswiez);

        // Ustaw przycinanie (clipping) dla mapy, aby samochód nie wychodził poza obszar
        // i nie zasłaniał przycisków
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(mapa.widthProperty());
        clip.heightProperty().bind(mapa.heightProperty());
        mapa.setClip(clip);

        if (listaSamochodow.isEmpty()) {
            mojeAuto = new Samochod();
            listaSamochodow.add(mojeAuto);
        } else {
            mojeAuto = listaSamochodow.get(0);
        }

        // Wymuś pozycję poniżej napisu "Mapa" (teraz to środek auta)
        if (mojeAuto != null) {
            mojeAuto.getPozycja().ustaw(25.0, 40.0);
        }

        wyborSamochodu.setItems(listaSamochodow);
        wyborSamochodu.getSelectionModel().select(mojeAuto);

        // Zainicjuj wizualizacje dla istniejących samochodów
        for (Samochod auto : listaSamochodow) {
            stworzWizualizacjeSamochodu(auto);
        }

        // Dołącz słuchacza panelu do początkowego samochodu
        if (mojeAuto != null) {
            mojeAuto.dodajSluchacza(sluchaczPanelu);
        }

        // Nasłuchuj nowych samochodów
        listaSamochodow.addListener((javafx.collections.ListChangeListener<Samochod>) c -> {
            while (c.next()) {
                if (c.wasAdded() && !c.getAddedSubList().isEmpty()) {
                    for (Samochod noweAuto : c.getAddedSubList()) {
                        stworzWizualizacjeSamochodu(noweAuto);
                    }
                    // Automatyczne zaznaczanie ostatnio dodanego samochodu
                    Samochod ostatnioDodany = c.getAddedSubList().get(0);
                    wyborSamochodu.getSelectionModel().select(ostatnioDodany);
                    wybierzAutoZListy();
                }
            }
        });

        mapa.setOnMouseClicked(event -> {
            if (mojeAuto != null) {
                // Teraz celem jest dokładnie punkt kliknięcia (Logika "Centrum")
                double celX = event.getX();
                double celY = event.getY();

                System.out.println("Kliknięto w mapę: " + celX + ", " + celY + " -> Cel: " + celX + ", " + celY);
                Pozycja cel = new Pozycja(celX, celY);
                mojeAuto.jedzDo(cel);
            }
        });

        odswiez();
    }

    // Tworzy element wizualny dla samochodu i dołącza słuchacza pozycji
    private void stworzWizualizacjeSamochodu(Samochod samochod) {
        if (widokiSamochodow.containsKey(samochod))
            return;

        try {
            ImageView widok = new ImageView(new Image(getClass().getResource("/images/car.png").toExternalForm()));
            widok.setFitWidth(300);
            widok.setPreserveRatio(true);

            mapa.getChildren().add(widok);
            widokiSamochodow.put(samochod, widok);

            // Funkcja do aktualizacji wizualnej pozycji (CENTROWANIE)
            Runnable aktualizujWizualnie = () -> {
                double bruttoW = widok.getBoundsInLocal().getWidth();
                double bruttoH = widok.getBoundsInLocal().getHeight();
                // Fallback jeśli jeszcze nie przeliczone
                if (bruttoW == 0)
                    bruttoW = 300;
                if (bruttoH == 0)
                    bruttoH = 150;

                Pozycja pos = samochod.getPozycja();
                // Odejmij połowę wymiarów, aby środek obrazka był w 'pos'
                widok.setTranslateX(pos.getX() - bruttoW / 2.0);
                widok.setTranslateY(pos.getY() - bruttoH / 2.0);
            };

            // Ustaw pozycję początkową natychmiast
            aktualizujWizualnie.run();

            // Dodaj słuchacza SPECYFICZNEGO dla tego samochodu i jego widoku
            samochod.dodajSluchacza(() -> Platform.runLater(aktualizujWizualnie));

        } catch (Exception e) {
            System.err.println("Nie można załadować obrazka dla nowego samochodu: " + e.getMessage());
        }
    }

    public static void dodajSamochodDoListy(String model, String rejestracja, double waga, int predkosc) {
        Samochod nowySamochod = new Samochod(model, rejestracja, waga, predkosc);

        // te same liczby co w metodzie initialize)
        nowySamochod.getPozycja().ustaw(25.0, 40.0);

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
        if (wybrany != null && wybrany != mojeAuto) {

            // Odłącz słuchacza panelu od starego samochodu
            if (mojeAuto != null && sluchaczPanelu != null) {
                mojeAuto.usunSluchacza(sluchaczPanelu);
            }

            mojeAuto = wybrany;

            // Dołącz słuchacza panelu do nowego samochodu
            if (mojeAuto != null && sluchaczPanelu != null) {
                mojeAuto.dodajSluchacza(sluchaczPanelu);
            }

            System.out.println("Wybrano: " + mojeAuto.getModel());
            odswiez();
        }
    }

    @FXML
    public void usunSamochod() {
        if (mojeAuto == null)
            return;

        // 1. Zatrzymaj wątek samochodu
        mojeAuto.zakoncz();

        // 2. Usuń wizualizację z mapy
        ImageView widok = widokiSamochodow.get(mojeAuto);
        if (widok != null) {
            mapa.getChildren().remove(widok);
            widokiSamochodow.remove(mojeAuto);
        }

        // 3. Usuń słuchacza panelu
        if (sluchaczPanelu != null) {
            mojeAuto.usunSluchacza(sluchaczPanelu);
        }

        // 4. Usuń z listy
        listaSamochodow.remove(mojeAuto);

        // 5. Zaktualizuj wybór
        if (!listaSamochodow.isEmpty()) {
            // Wybierz pierwszy dostępny
            Samochod noweAuto = listaSamochodow.get(0);
            wyborSamochodu.getSelectionModel().select(noweAuto);
            // Listener listy wywoła wybierzAutoZListy, ale dla pewności:
            // wybierzAutoZListy() zostanie wywołane przez zmianę w ComboBox jeśli jest
            // zbindowany?
            // ComboBox jest zbindowany z listą, ale selekcja to co innego.
            // Ręcznie ustawiamy nowe auto jako wybrane w logice
            wybierzAutoZListy();
        } else {
            // Brak samochodów
            mojeAuto = null;
            wyborSamochodu.getSelectionModel().clearSelection();

            // Wyczyść pola
            jakimodel.setText("");
            nrrejstracji.setText("");
            biegPole.setText("");
            obrotyPole.setText("");
            sprzegloField.setText("");
            wagaField.setText("");
            predkoscField.setText("");
        }

        System.out.println("Usunięto samochód.");
    }

    // zaktualizujWidok
    private void odswiez() {
        if (mojeAuto == null)
            return;

        jakimodel.setText(mojeAuto.getModel());
        nrrejstracji.setText(mojeAuto.getNrRejestracyjny());

        int aktualnyBieg = mojeAuto.getSkrzynia().getAktualnyBieg();
        biegPole.setText(String.valueOf(aktualnyBieg));

        double aktualneObroty = mojeAuto.getSilnik().getObroty();
        obrotyPole.setText(String.format("%.0f", aktualneObroty));// zaokrągla

        if (mojeAuto.getSprzeglo().czyWcisniete()) {
            sprzegloField.setText("Wciśnięte");
        } else {
            sprzegloField.setText("Puszczone");
        }

        wagaField.setText(String.valueOf(mojeAuto.getWaga()));
        predkoscField.setText(String.format("%.1f", mojeAuto.getAktualnaPredkosc()));// zaokrągla
    }

    private void pokazBlad(String wiadomosc) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
}