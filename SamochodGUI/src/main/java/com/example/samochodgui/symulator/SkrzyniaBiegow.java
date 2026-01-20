package com.example.samochodgui.symulator;

public class SkrzyniaBiegow extends Komponent {
    private int aktualnyBieg;
    private int iloscBiegow;
    private boolean automatyczna;

    public SkrzyniaBiegow(String producent, String model, boolean automatyczna) {
        super(producent, model);
        this.aktualnyBieg = 0;
        this.iloscBiegow = 6;
        this.automatyczna = automatyczna;
    }

    public void zwiekszBieg() {
        if (automatyczna) {
            System.out.println("Skrzynia automatyczna - nie można zmieniać biegów ręcznie!");
            return;
        }
        if (aktualnyBieg < iloscBiegow) {
            aktualnyBieg++;
            System.out.println("wrzucono wyższy bieg" + aktualnyBieg);
        } else {
            System.out.println("Nie mozna wrzucić wyższego biegu");
        }
    }

    public void zmniejszBieg() {
        if (automatyczna) {
            System.out.println("Skrzynia automatyczna - nie można zmieniać biegów ręcznie!");
            return;
        }
        if (aktualnyBieg > 0) {
            aktualnyBieg--;
            System.out.println("zmniejszono bieg aktualny bieg to " + aktualnyBieg);
        } else {
            System.out.println("nie da sie zmniejszyć tego biegu bo to jest minimalny bieg");
        }
    }

    public void ustawBieg(int bieg) {
        this.aktualnyBieg = bieg;
        System.out.println("Zmieniono bieg na" + aktualnyBieg);
    }

    // Metoda do podglądania biegu przez sterownik
    public int getAktualnyBieg() {
        return aktualnyBieg;
    }

    public boolean czyAutomatyczna() {
        return automatyczna;
    }
}
