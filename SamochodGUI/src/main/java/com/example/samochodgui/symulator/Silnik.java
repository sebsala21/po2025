package com.example.samochodgui.symulator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;

    private String typ; // "Diesel" lub "Benzyna"

    public Silnik(String producent, String model, int maxObroty, String typ) {
        super(producent, model);
        this.maxObroty = maxObroty;
        this.typ = typ;
        this.obroty = 0;
    }

    public void uruchom() {
        this.obroty = 800;
        System.out.println("Silnik został uruchomionyObroty to:" + obroty);
    }

    public void zatrzymaj() {
        this.obroty = 0;
        System.out.println("Silnik został zgaszony");
    }

    public int getObroty() {
        return obroty;
    }

    public int getMaxObroty() {
        return maxObroty;
    }

    public String getTyp() {
        return typ;
    }

    public void zwiekszObroty() {
        // Dodajemy gazu tylko jeśli silnik jest włączony!
        if (obroty > 0) {
            obroty += 500;
            // Zabezpieczenie: nie przekręć silnika (maxObroty)
            if (obroty > maxObroty) {
                obroty = maxObroty;
            }
            System.out.println("Dodano gazu. Obroty: " + obroty);
        } else {
            System.out.println("Silnik zgaszony! Nie mogę dodać gazu.");
        }
    }

    public void zmniejszObroty() {
        // Nie schodzimy poniżej biegu jałowego (800), chyba że gasimy auto
        if (obroty > 800) {
            obroty -= 500;
            // Zabezpieczenie: żeby nie spadło poniżej 800
            if (obroty < 800) {
                obroty = 800;
            }
            System.out.println("Obroty: " + obroty);
        }
    }

    public boolean czyUruchomiony() {
        return obroty > 0;
    }
}
