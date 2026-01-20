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
    private final Map<Samochod, ImageView> widokiSamochodow = new HashMap<>();//daje mus samochod(wart logicsna) i obrazek a otrzymuje ten obiekt
    private Listener sluchaczPanelu; // Słuchacz dla *aktualnie wybranego* samochodu, aby aktualizować panele UI

    private static ObservableList<Samochod> listaSamochodow = FXCollections.observableArrayList();// przechowuje stworzone auta

    // Ta metoda uruchamia się sama przy starcie okna
    @FXML
    public void initialize() {//automatyczny start
        System.out.println("HelloController initialized");

        // Zdefiniuj słuchacza, który aktualizuje pola tekstowe panelu sterowania zmienia to cos sie dzieje na obrazku,on wydaje rozkazy mówi np przesuń sie o 5 pikseli a wizualizacja(imageVieww) sie wtedy przesówa
        sluchaczPanelu = () -> Platform.runLater(this::odswiez);//mówi gdy cos sie u ciebie zmieni uruchom metodę odśwież

        // Ustaw przycinanie dla mapy, aby samochód nie wychodził poza obszar
        // i nie zasłaniał przycisków
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(mapa.widthProperty());
        clip.heightProperty().bind(mapa.heightProperty());
        mapa.setClip(clip);//ustawia niewidzialną sciane gdzie nie moge poza nia wyjechać

        if (listaSamochodow.isEmpty()) {//czy lista aut jest pusta
            mojeAuto = new Samochod();
            listaSamochodow.add(mojeAuto);//jesli tak domyslnie dodaje to moje nowe audi do listy
        } else {
            mojeAuto = listaSamochodow.get(0);//jesli nie ti bierze auto z brzegu bo musi cos byc zeby nie wyskoczył bład
        }

        // Wymuś pozycję poniżej napisu "Mapa"
        if (mojeAuto != null) {
            mojeAuto.getPozycja().ustaw(25.0, 40.0);
        }

        wyborSamochodu.setItems(listaSamochodow);//wkłada liste aut do rozwijanego menu
        wyborSamochodu.getSelectionModel().select(mojeAuto);//zaznacza auto ktore stworzylismy przed chwila

        // Zainicjuj wizualizacje dla istniejących samochodów
        for (Samochod auto : listaSamochodow) {
            stworzWizualizacjeSamochodu(auto);
        }//dla kazdego auta które jest na liscie, wywołuje metode ktora tworzy nowy obrazek car.png i wrzuca go na mape

        // Dołącz słuchacza panelu do początkowego samochodu
        if (mojeAuto != null) {
            mojeAuto.dodajSluchacza(sluchaczPanelu);
        }//fizycznie podpina stworzonego sluchacza do aktualnego auta

        // Nasłuchuj nowych samochodów
        listaSamochodow.addListener((javafx.collections.ListChangeListener<Samochod>) zmiana -> {//gdy w drugim kodzie klikne zatwiedz i nowe auto wpadnie do listy to wtedy to sie uruchamia
            while (zmiana.next()) {
                if (zmiana.wasAdded() && !zmiana.getAddedSubList().isEmpty()) {//czy ono "wjechałp" na liste
                    for (Samochod noweAuto : zmiana.getAddedSubList()) {
                        stworzWizualizacjeSamochodu(noweAuto);//tworzy obrazek na mapie
                    }
                    // Automatyczne zaznaczanie ostatnio dodanego samochodu
                    Samochod ostatnioDodany = zmiana.getAddedSubList().get(0);
                    wyborSamochodu.getSelectionModel().select(ostatnioDodany);//automatycznie ustawia nowe auto w rozwijanej liscie(combobox)
                    wybierzAutoZListy();//sprawia ze przyciski np gaz bieg od razu steruja tym nowym autem
                }
            }
        });//w skrócie jak dodam nowe auto to od razu je widze i moge nim sterować

        mapa.setOnMouseClicked(event -> {//jak ktos kliknie myszka to mam wykonać tek kod w srodku klamerek
            if (mojeAuto != null) {//zabezpieczenie
                // pobiera z baczki event te dane
                double celX = event.getX();
                double celY = event.getY();

                System.out.println("Kliknięto w mapę: " + celX + ", " + celY + " -> Cel: " + celX + ", " + celY);
                Pozycja cel = new Pozycja(celX, celY);//pakuje te nowe dane w jeden obiekt
                mojeAuto.jedzDo(cel);//wydaje rozkaz by tam jechał
            }
        });

        odswiez();//recznie wymmuszam program do pobrania dazych z auta i wpisania ich do okienka
    }

    // Tworzy element wizualny dla samochodu i dołącza słuchacza pozycji
    private void stworzWizualizacjeSamochodu(Samochod samochod) {
        if (widokiSamochodow.containsKey(samochod))
            return;//zabezpieczenie zeby tylko był jeden obrazek dla kazdegog samochodu

        try {
            ImageView widok = new ImageView(new Image(getClass().getResource("/images/car.png").toExternalForm()));
            widok.setFitWidth(300);
            widok.setPreserveRatio(true);//narodziny obrazka

            mapa.getChildren().add(widok);//wrzuca obrazek na to tło
            widokiSamochodow.put(samochod, widok);//zapisuje ze dla tego konkretnego obrazka jest ten samochód z tymii parametrami

            // Funkcja do aktualizacji wizualnej pozycji (CENTROWANIE)
            Runnable aktualizujWizualnie = () -> {
                double bruttoW = widok.getBoundsInLocal().getWidth();
                double bruttoH = widok.getBoundsInLocal().getHeight();//sprawdza jak szeroki i wysoki jest obrazek w dane milisekundzie

                if (bruttoW == 0)
                    bruttoW = 300;
                if (bruttoH == 0)//gdy nie zdazył przeliczyć i dał 0 to zmień na 300
                    bruttoH = 150;//pobiera wymiary obrazka i zabezpiecza sie przed błedem

                Pozycja pos = samochod.getPozycja();
                widok.setTranslateX(pos.getX() - bruttoW / 2.0);
                widok.setTranslateY(pos.getY() - bruttoH / 2.0);
            };//centruje obrazek tak zeby srodek obrazeka był dokładnie w punkcie w którym powinien byc

            // Ustaw pozycję początkową natychmiast
            aktualizujWizualnie.run();

            // Dodaj słuchacza SPECYFICZNEGO dla tego samochodu i jego widoku
            samochod.dodajSluchacza(() -> Platform.runLater(aktualizujWizualnie));

        } catch (Exception e) {
            System.err.println("Nie można załadować obrazka dla nowego samochodu: " + e.getMessage());
        }//gdy plik nie istnieje to nie wywalaj erroru tylko dzialaj dalej
    }

    public static void dodajSamochodDoListy(String model, String rejestracja, double waga, int predkosc, int maxObroty,
            boolean automat, String typSilnika) {//przyjmuje surowe dane
        Samochod nowySamochod = new Samochod(model, rejestracja, waga, predkosc, maxObroty, automat, typSilnika);//tworzy ten obiekt który ma sklejone wsyzstkie te dane

        // te same liczby co w metodzie initialize)
        nowySamochod.getPozycja().ustaw(25.0, 40.0);

        listaSamochodow.add(nowySamochod);//wrzuca go do listy
    }

    @FXML
    public void otworzOknoDodawania() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DodajSamochod.fxml"));
            Parent root = loader.load();//zamienia plik tekstowy XML na gotowe obiekty JAVA które komputer moze wyswietlić
            Stage stage = new Stage();//tworze te nowe okno
            stage.setTitle("Dodaj nowy samochód");
            stage.setScene(new Scene(root));//zmienna root to kontener czyli zawartość tego okna, Stage to ramka obrazu a Scene(root) to płótno
            stage.show();//fizycznie pojawia sie to na moim komputerze
        } catch (IOException e) {
            e.printStackTrace();
        }//zabezpieczenie
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
    public void wybierzAutoZListy() {//metoda przeciadki w combobox wybiram inny samochód
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

        // Zatrzymaj wątek samochodu
        mojeAuto.zakoncz();

        //Usuń wizualizację z mapy
        ImageView widok = widokiSamochodow.get(mojeAuto);
        if (widok != null) {
            mapa.getChildren().remove(widok);
            widokiSamochodow.remove(mojeAuto);
        }

        // Usuń słuchacza panelu
        if (sluchaczPanelu != null) {
            mojeAuto.usunSluchacza(sluchaczPanelu);
        }

        // Usuń z listy
        listaSamochodow.remove(mojeAuto);

        //  Zaktualizuj wybór
        if (!listaSamochodow.isEmpty()) {
            // Wybierz pierwszy dostępny
            Samochod noweAuto = listaSamochodow.get(0);
            wyborSamochodu.getSelectionModel().select(noweAuto);

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

    @FXML
    private TextField typSkrzyniPole;
    @FXML
    private TextField typSilnikaPole;
    @FXML
    private TextField maxObrotyPole;

    // zaktualizujWidok
    private void odswiez() {
        if (mojeAuto == null)
            return;
//pobiera gootowe napisy z obiekty i wkleja je w pola na górze okna
        jakimodel.setText(mojeAuto.getModel());
        nrrejstracji.setText(mojeAuto.getNrRejestracyjny());
//bierze numer biegu z wnetrza samochodu i wyswietla go na ekranie
        int aktualnyBieg = mojeAuto.getSkrzynia().getAktualnyBieg();
        biegPole.setText(String.valueOf(aktualnyBieg));
//sprawdza jaki typ i wstawia go w pole tpyskrzyni pole
        if (mojeAuto.getSkrzynia().czyAutomatyczna()) {
            typSkrzyniPole.setText("Automatyczna");
        } else {
            typSkrzyniPole.setText("Manualna");
        }
//podaje obroty i wyswietlam max obroty i typ silnika
        double aktualneObroty = mojeAuto.getSilnik().getObroty();
        obrotyPole.setText(String.format("%.0f", aktualneObroty));// zaokrągla

        maxObrotyPole.setText(String.valueOf(mojeAuto.getSilnik().getMaxObroty()));
        typSilnikaPole.setText(mojeAuto.getSilnik().getTyp());
// na sprzegle czy wcisniete czy nie
        if (mojeAuto.getSprzeglo().czyWcisniete()) {
            sprzegloField.setText("Wciśnięte");
        } else {
            sprzegloField.setText("Puszczone");
        }
//waga i predkość
        wagaField.setText(String.valueOf(mojeAuto.getWaga()));
        predkoscField.setText(String.format("%.1f", mojeAuto.getAktualnaPredkosc()));// zaokrągla
    }
//wyswietla na ekranie wyskakujące okienko z komunikatem o błędzie
    private void pokazBlad(String wiadomosc) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }
}