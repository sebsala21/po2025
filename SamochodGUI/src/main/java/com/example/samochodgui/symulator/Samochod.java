package com.example.samochodgui.symulator;

import com.example.samochodgui.Listener;
import java.util.ArrayList;
import java.util.List;

public class Samochod extends Thread {
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;
    private Pozycja pozycja;
    private double waga;
    private double aktualnaPredkosc;

    private String model;
    private String nrRejestracyjny;
    private int predkoscMaksymalna;

    private Pozycja cel;
    private boolean aktywny = true;
    private List<Listener> sluchacze = new ArrayList<>();//lista kontrolerów które chca wiedzieć o zmienie w aucie

    public Samochod() {
        this.silnik = new Silnik("TDI", "2.0 DIseal", 6000, "Diesel");
        this.skrzynia = new SkrzyniaBiegow("Manualna", "6-biegowa", false);
        this.sprzeglo = new Sprzeglo("Sachs", "Sportowe");
        this.pozycja = new Pozycja(160.0, 200.0);
        this.waga = 1500.0;
        this.aktualnaPredkosc = 0.0;

        this.model = "Audi A4";
        this.nrRejestracyjny = "KR 12345";
        this.predkoscMaksymalna = 240;

        // Uruchom wątek natychmiast samochód zaczyna życ odrazu po stworzeniu
        start();
    }

    public Samochod(String model, String nrRejestracyjny, double waga, int predkoscMaksymalna, int maxObrotySilnika,
            boolean automat, String typSilnika) {
        this.silnik = new Silnik("TDI", "2.0 DIseal", maxObrotySilnika, typSilnika);
        this.skrzynia = new SkrzyniaBiegow(automat ? "Automat" : "Manual", "6-biegowa", automat);
        this.sprzeglo = new Sprzeglo("Sachs", "Sportowe");
        this.pozycja = new Pozycja(160.0, 200.0);
        this.waga = waga;
        this.aktualnaPredkosc = 0.0;
        this.model = model;
        this.nrRejestracyjny = nrRejestracyjny;
        this.predkoscMaksymalna = predkoscMaksymalna;

        // Jeśli automat, ustaw bieg na 1
        if (automat) {
            skrzynia.ustawBieg(1);
        }

        start();
    }

    //dodaje słuchacza
    public void dodajSluchacza(Listener sluchacz) {
        sluchacze.add(sluchacz);
    }
//usuniecie
    public void usunSluchacza(Listener sluchacz) {
        sluchacze.remove(sluchacz);
    }
//bo moje auto 2 słuchaczy jedno odpowiada za mape a drugi za te liczniki
    private void powiadomSluchaczy() {
        for (Listener sluchacz : sluchacze) {
            sluchacz.aktualizuj();
        }
    }

    public void jedzDo(Pozycja cel) {
        this.cel = cel;
        System.out.println("Jadę do: " + cel.getPozycja());
    }

    @Override
    public void run() {
        while (aktywny) {
            if (cel != null) {
                // Sprawdź czy silnik jest włączony
                if (silnik.czyUruchomiony()) {
                    double deltaT = 0.1;

                    // Prosta logika ruchu Tutaj samochód oblicza, gdzie jest cel względem niego:
                    double dx = cel.getX() - pozycja.getX();
                    double dy = cel.getY() - pozycja.getY();
                    double dystans = Math.sqrt(dx * dx + dy * dy);

                    if (dystans > 1.0) { // Jeśli jeszcze nie dotarł (tolerancja 1.0)
                        double v = getAktualnaPredkosc();//oblicza ile pikseli ma przejechać w jednej klatce animacji

                        // Jeśli prędkość wynosi 0, nie ruszamy się
                        if (v <= 0) {
                            // Stój w miejscu
                        } else {
                            double krok = v * deltaT;

                            if (krok >= dystans) {// jesli krok jest wiekszy niz odległość to po prostu stań idalnie na celu
                                // Jesteśmy blisko celu, skocz do niego aby nie przeskoczyć
                                pozycja.aktualizujPozycje(dx, dy);
                                cel = null; // Dotarto
                                System.out.println("Dojechano do celu.");
                            } else {
                                double przesuniecieX = krok * (dx / dystans);
                                double przesuniecieY = krok * (dy / dystans);
                                pozycja.aktualizujPozycje(przesuniecieX, przesuniecieY);
                            }//Auto wylicza proporcje, ile musi się przesunąć w bok, a ile w dół, żeby jechać prosto do celu, a nie na ukos pod złym kątem.
                        }
                    } else {
                        cel = null; // Dotarto
                        System.out.println("Dojechano do celu.");
                    }
                    powiadomSluchaczy();//powiadamia słuchaczy
                } else {

                }

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                aktywny = false;
            }//zasypia na ułamek sekundy zeby nie obciazyć procesora
        }
    }//metoda run czyli sprawdz gdzie jechać,oblicz krok, przesuń sie ,powiadom ekran,odpocznij chwile,powtórz

    public String getModel() {
        return model;
    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny;
    }

    public int getPredkoscMaksymalna() {
        return predkoscMaksymalna;
    }

    public void wlacz() {
        System.out.println("Przekręcam kluczyk");
        silnik.uruchom();
    }

    public void wylacz() {
        System.out.println("wyciagam kluczyk");
        silnik.zatrzymaj();
        skrzynia.ustawBieg(0);
    }

    public void zakoncz() {
        this.aktywny = false;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public SkrzyniaBiegow getSkrzynia() {
        return skrzynia;
    }

    public Sprzeglo getSprzeglo() {
        return sprzeglo;
    }

    public Silnik getSilnik() {
        return silnik;
    }

    public double getWaga() {
        return waga;
    }

    public double getAktualnaPredkosc() {
        // 1. Pobieramy dane z podzespołów
        double obecneObroty = silnik.getObroty();
        int obecnyBieg = skrzynia.getAktualnyBieg();

        // 2. Jeśli sprzęgło jest wciśnięte, auto się toczy (uproszczenie: prędkość
        // spada do 0)

        if (sprzeglo.czyWcisniete()) {
            return 0.0; // Jak wciśniesz sprzęgło, to napęd jest rozłączony -> prędkość spada
                        // (uproszczenie)
        }

        // 3. Wzór na prędkość (0.02 to współczynnik, żeby wyniki były realne, np. 2000
        // obrotów * 5 bieg * 0.02 = 200 km/h)
        aktualnaPredkosc = obecneObroty * obecnyBieg * 0.02;


        if (aktualnaPredkosc > predkoscMaksymalna) {
            aktualnaPredkosc = predkoscMaksymalna;
        }

        return aktualnaPredkosc;
    }

    @Override // nadpisuje metode która juz istnieje
    public String toString() {
        return model;
    }// dzieki temu zobacze np audi A4 a nie jakiś dziwny model
}
