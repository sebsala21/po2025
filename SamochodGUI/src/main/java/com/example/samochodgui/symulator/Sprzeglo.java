package com.example.samochodgui.symulator;

public class Sprzeglo extends Komponent{

    private boolean stanSprzegla;
    public Sprzeglo(String producent, String model){
        super(producent,model);
        this.stanSprzegla = false;
    }
    public void wcisnij(){
        stanSprzegla = true;
        System.out.println("Sprzeglo zostało wciśniete ");
    }
    public void zwolnij(){
        stanSprzegla=false;
        System.out.println("sprzegło zostało zwolnione");
    }
    public boolean czyWcisniete() {
        return stanSprzegla;
    }
}
