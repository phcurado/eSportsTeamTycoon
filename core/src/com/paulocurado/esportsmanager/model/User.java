package com.paulocurado.esportsmanager.model;

/**
 * Created by Paulo on 28/11/2016.
 */

public class User {
    private String name;
    private Team team;

    public User(String name, String teamName, String shortTeamName) {
        this.name = name;
        team = new Team();
        team.setName(teamName);
        team.setAbbreviatedName(shortTeamName);
    }

    public Team getTeam() {
        return team;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
