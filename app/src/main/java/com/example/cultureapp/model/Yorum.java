package com.example.cultureapp.model;

public class Yorum {

    private String yorum;
    private String gonderen;

    public Yorum() {
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public Yorum(String yorum, String gonderen) {
        this.yorum = yorum;
        this.gonderen = gonderen;
    }


}
