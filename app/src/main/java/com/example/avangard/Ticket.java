package com.example.avangard;

public class Ticket {
    private Match match;
    private int sector;
    private int row;
    private int place;
    private String tribuna;

    public String getTribuna() {
        return tribuna;
    }

    public void setTribuna(String tribuna) {
        this.tribuna = tribuna;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Ticket(Match match, int sector, int row, int place, String tribuna) {
        this.match = match;
        this.sector = sector;
        this.row = row;
        this.place = place;
        this.tribuna = tribuna;
    }
}
