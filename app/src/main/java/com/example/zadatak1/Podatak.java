package com.example.zadatak1;

public class Podatak {
    int id;
    int userId;
    String naslov;
    String body;

    public Podatak(int id, int userId, String naslov, String body) {
        this.id = id;
        this.userId = userId;
        this.naslov = naslov;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
