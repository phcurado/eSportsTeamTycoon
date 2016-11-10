package com.paulocurado.esportsmanager.model;

import java.util.ArrayList;

/**
 * Created by Paulo on 05/11/2016.
 */

public class Team {
    /*  Basic information   */
    private String id;
    private String name;
    private String abbreviatedName;
    private long budget;
    private ArrayList<Player> players;
    private String[] playersId;
    private int tier;

    public Team() {

        playersId = new String[6];
        players = new ArrayList<Player>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String[] getPlayersId() {
        return playersId;
    }

    public void setPlayersId(String[] playersId) {
        this.playersId = playersId;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }
}
