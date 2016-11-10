package com.paulocurado.esportsmanager.model;

import java.util.ArrayList;

/**
 * Created by Paulo on 09/11/2016.
 */

public class Tier {
    private int tiernumber;
    private ArrayList<Team> teams;

    public Tier() {
        teams = new ArrayList<Team>();
    }


    public int getTiernumber() {
        return tiernumber;
    }

    public void setTiernumber(int tiernumber) {
        this.tiernumber = tiernumber;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
