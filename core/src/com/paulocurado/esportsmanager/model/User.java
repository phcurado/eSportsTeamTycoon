package com.paulocurado.esportsmanager.model;

/**
 * Created by Paulo on 28/11/2016.
 */

public class User {
    private String userName;
    private Team team;
    private int fansNumber = 0;
    private Scout scout;

    private boolean hireScreenFirstTime = true;

    public User(String userName, String teamName, String shortTeamName) {
        this.userName = userName;
        team = new Team();
        team.setName(teamName);
        team.setAbbreviatedName(shortTeamName);
        scout = new Scout(1);
    }

    public Team getTeam() {
        return team;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public boolean isHireScreenFirstTime() {
        return hireScreenFirstTime;
    }

    public void setHireScreenFirstTime(boolean hireScreenFirstTime) {
        this.hireScreenFirstTime = hireScreenFirstTime;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(int fansNumber) {
        this.fansNumber = fansNumber;
    }

    public Scout getScout() {
        return scout;
    }

    public void setScout(Scout scout) {
        this.scout = scout;
    }
}
