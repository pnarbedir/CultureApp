package com.example.cultureapp.model;

public class Yorum {

    private String yorum;
    private String gonderen;
    private String yorumId;

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

    public String getYorumId() {
        return yorumId;
    }

    public void setYorumId(String yorumId) {
        this.yorumId = yorumId;
    }

    public Yorum(String yorum, String gonderen, String yorumId) {
        this.yorum = yorum;
        this.gonderen = gonderen;
        this.yorumId = yorumId;
    }


}