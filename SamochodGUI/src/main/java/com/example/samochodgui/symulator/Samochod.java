package com.example.samochodgui.symulator;

public class Samochod {
    private Silnik silnik;
    private SkrzyniaBiegow skrzynia;
    private Sprzeglo sprzeglo;
    private Pozycja pozycja;
    private double waga;
    private double aktualnaPredkosc;

    public Samochod() {
        this.silnik= new Silnik("TDI","2.0 DIseal");
        this.skrzynia= new SkrzyniaBiegow("Manualna","6-biegowa");
        this.sprzeglo= new Sprzeglo("Sachs","Sportowe");
        this.pozycja= new Pozycja();
        this.waga = 1500.0;
        this.aktualnaPredkosc = 0.0;
    }
    public void wlacz(){
        System.out.println("Przekręcam kluczyk");
        silnik.uruchom();
    }
    public void wylacz(){
        System.out.println("wyciagam kluczyk");
        silnik.zatrzymaj();
        skrzynia.ustawBieg(0);
    }
    public Pozycja getPozycja() {
        return pozycja;
    }
    public SkrzyniaBiegow getSkrzynia(){
        return skrzynia;
    }
    public Sprzeglo getSprzeglo(){
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

        // 2. Jeśli sprzęgło jest wciśnięte, auto się toczy (uproszczenie: prędkość spada do 0)


        if (sprzeglo.czyWcisniete()) {
            return 0.0; // Jak wciśniesz sprzęgło, to napęd jest rozłączony -> prędkość spada (uproszczenie)
        }

        // 3. Wzór na prędkość (0.02 to współczynnik, żeby wyniki były realne, np. 2000 obrotów * 5 bieg * 0.02 = 200 km/h)
        aktualnaPredkosc = obecneObroty * obecnyBieg * 0.02;

        return aktualnaPredkosc;
    }


}
