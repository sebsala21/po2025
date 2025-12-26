import symulator.*;

public class Main {
    public static void main(String[] args) {
        Sprzeglo sprzeglo = new Sprzeglo(false, "BMW", "Clutch");
        Silnik silnik = new Silnik(7000, 0, "BMW", "V8");
        SkrzyniaBiegow skrzynia = new SkrzyniaBiegow(1, 6, sprzeglo, "BMW", "Skrzynia Manualna");
        Pozycja pozycja = new Pozycja(0, 0);
        Samochod bmw = new Samochod(silnik, skrzynia, pozycja);
        bmw.wlacz();
    }
}