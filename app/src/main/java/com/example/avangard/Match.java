package com.example.avangard;

import java.io.Serializable;

public class Match implements Serializable {
    private Team TeamHome;
    private Team TeamGuest;
    private int TeamHomeGoals;
    private int TeamGuestGoals;
    private boolean isOver;
    public Match(){}



    public Team getTeamHome() {
        return TeamHome;
    }

    public void setTeamHome(Team teamHome) {
        TeamHome = teamHome;
    }

    public Team getTeamGuest() {
        return TeamGuest;
    }

    public void setTeamGuest(Team teamGuest) {
        TeamGuest = teamGuest;
    }

    public int getTeamHomeGoals() {
        return TeamHomeGoals;
    }

    public void setTeamHomeGoals(int teamHomeGoals) {
        TeamHomeGoals = teamHomeGoals;
    }

    public int getTeamGuestGoals() {
        return TeamGuestGoals;
    }

    public void setTeamGuestGoals(int teamGuestGoals) {
        TeamGuestGoals = teamGuestGoals;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public Match(Team teamHome, Team teamGuest, int teamHomeGoals, int teamGuestGoals, boolean isOver) {
        TeamHome = teamHome;
        TeamGuest = teamGuest;
        TeamHomeGoals = teamHomeGoals;
        TeamGuestGoals = teamGuestGoals;
        this.isOver = isOver;
    }
}
