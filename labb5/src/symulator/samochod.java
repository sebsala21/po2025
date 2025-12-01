package symulator;

public class Samochod  {
    Silnik silnik;
    SkrzyniaBiegow skrzynia;
    Pozycja  aktualnaPozycja;

    public Samochod(Silnik silnik, SkrzyniaBiegow skrzynia, Pozycja aktualnaPozycja) {
        this.silnik = silnik;
        this.skrzynia = skrzynia;
        this.aktualnaPozycja = aktualnaPozycja;
    }

    public void wlacz() {
        silnik.uruchom();
        System.out.println("Samochod wlaczony");
    }

    public void wylacz() {
        silnik.zatrzymaj();
        skrzynia.aktualnyBieg = 0;
        System.out.println("Samochod wylaczony");
    }
}