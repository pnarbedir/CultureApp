package com.example.cultureapp.model;

public class Notification {
    private String kullaniciId;
    private String text;
    private String gonderiId;
    private boolean ispost;

    public Notification(String kullaniciId, String text, String gonderiId, boolean ispost) {
        this.kullaniciId = kullaniciId;
        this.text = text;
        this.gonderiId = gonderiId;
        this.ispost = ispost;
    }

    public Notification() {
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(String gonderiId) {
        this.gonderiId = gonderiId;
    }

    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }
}
