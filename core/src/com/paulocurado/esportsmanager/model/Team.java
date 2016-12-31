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
    private transient ArrayList<Player> players;
    private String[] playersId;
    private int tier;
    private int victoriesChampionship;

    public Team() {

        playersId = new String[5];
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

    public int getStrength() {
        int strength = 0;
        for(int i = 0; i < players.size(); i++) {
            strength += players.get(i).abilityInteger(i + 1);
        }

        return strength / players.size();
    }

    public int positionNeeded() {
        int[] positions = {0, 0, 0, 0, 0};
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).bestPositionInteger() == Position.CARRY)
                positions[0] = 1;
            if(players.get(i).bestPositionInteger() == Position.MID)
                positions[1] = 1;
            if(players.get(i).bestPositionInteger() == Position.OFFLANE)
                positions[2] = 1;
            if(players.get(i).bestPositionInteger() == Position.SUPP4)
                positions[3] = 1;
            if(players.get(i).bestPositionInteger() == Position.SUPP5)
                positions[4] = 1;
        }

        for(int i = 0; i < positions.length; i++) {
            if(positions[i] == 0) {
                return (i + 1);
            }
        }

        return 0;
    }

    public Player recomendedPlayer(ArrayList<Player> playersArray) {
        ArrayList<Player> playersChoice = new ArrayList<Player>();
        for(int i = 0; i < playersArray.size(); i++) {
            if(playersArray.get(i).getTeamId().equals("TEAM_0000") ) {
                playersChoice.add(playersArray.get(i));
                if(playersArray.get(i).abilityInteger(positionNeeded()) >= getStrength() - 5) {
                    if(budget >= 4*playersArray.get(i).getRecomendedSalary()) {
                        return playersArray.get(i);
                    }
                }

            }
        }


        return playersChoice.get(0);
    }

    public void setUpPlayers(){
        ArrayList<Player> positionedPlayers = players;
        for(int i = 0; i < players.size(); i++) {

            for(int j = 1; j < players.size() - i; j++) {
                if (players.get(i).abilityInteger(i+1) >= players.get(j+i).abilityInteger(i+1)) {
                    positionedPlayers.set(i, players.get(i));
                } else {
                    Player bufferPlayer = players.get(i);
                    positionedPlayers.set(i, players.get(j+i));
                    positionedPlayers.set(j+i, bufferPlayer);
                }

            }

        }


    }

    public void organizeIdPlayers() {
        for(int i = 0; i < players.size(); i++) {
            playersId[i] = players.get(i).getId();
        }
    }

    public int getVictoriesChampionship() {
        return victoriesChampionship;
    }

    public void setVictoriesChampionship(int victoriesChampionship) {
        this.victoriesChampionship = victoriesChampionship;
    }
}
