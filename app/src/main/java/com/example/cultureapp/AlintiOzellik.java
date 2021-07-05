package com.example.cultureapp;

public class AlintiOzellik {

    String alintiNo;
    String alintiMetni;
    String alintiKitap;
    String alintiYazarad;
    String alintiSayfano;
    String kullaniciID;

    public AlintiOzellik() {

    }


    public AlintiOzellik(String alintiNo, String alintiMetni, String alintiKitap, String alintiYazarad, String alintiSayfano, String kullaniciID) {
        this.alintiNo = alintiNo;
        this.alintiMetni = alintiMetni;
        this.alintiKitap = alintiKitap;
        this.alintiYazarad = alintiYazarad;
        this.alintiSayfano = alintiSayfano;
        this.kullaniciID = kullaniciID;
    }

    public String getAlintiNo() {
        return alintiNo;
    }

    public void setAlintiNo(String alintiNo) {
        this.alintiNo = alintiNo;
    }

    public String getAlintiMetni() {
        return alintiMetni;
    }

    public void setAlintiMetni(String alintiMetni) {
        this.alintiMetni = alintiMetni;
    }

    public String getAlintiKitap() {
        return alintiKitap;
    }

    public void setAlintiKitap(String alintiKitap) {
        this.alintiKitap = alintiKitap;
    }

    public String getAlintiYazarad() {
        return alintiYazarad;
    }

    public void setAlintiYazarad(String alintiYazarad) {
        this.alintiYazarad = alintiYazarad;
    }

    public String getAlintiSayfano() {
        return alintiSayfano;
    }

    public void setAlintiSayfano(String alintiSayfano) {
        this.alintiSayfano = alintiSayfano;
    }

    public String getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(String kullaniciID) {
        this.kullaniciID = kullaniciID;
    }
}
