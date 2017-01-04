package com.paulocurado.esportsmanager.model;


import java.util.Random;

/**
 * Created by Paulo on 06/11/2016.
 */

public class BattleSimulation {

    private transient Team radiantTeam;
    private transient Team direTeam;
    private String radiantTeamId;
    private String direTeamId;
    private int radiantVictories;
    private int direVictories;


    public BattleSimulation(Team radiantTeam, Team direTeam) {
        this.radiantTeam = radiantTeam;
        this.direTeam = direTeam;
        radiantTeamId = radiantTeam.getId();
        direTeamId = direTeam.getId();
    }

    public void bestofBattle(int number) {
        for(int i = 0; i < number; i++) {
            if (radiantVictories < number / 2 + 1 && direVictories < number / 2 + 1) {
                Random randomStr = new Random();
                if (radiantTeam.getStrength() - randomStr.nextInt(8) >= direTeam.getStrength() - randomStr.nextInt(8)) {
                    radiantVictories++;
                } else {
                    direVictories++;
                }
            }
        }
    }

    public Team winner() {
        if(radiantVictories >= direVictories) {
            return radiantTeam;
        }
        else {
            return direTeam;
        }
    }

    public int getRadiantVictories() {
        return radiantVictories;
    }

    public void setRadiantVictories(int radiantVictories) {
        this.radiantVictories = radiantVictories;
    }

    public int getDireVictories() {
        return direVictories;
    }

    public void setDireVictories(int direVictories) {
        this.direVictories = direVictories;
    }

    public Team getRadiantTeam() {
        return radiantTeam;
    }

    public Team getDireTeam() {
        return direTeam;
    }

    public void setRadiantTeam(Team radiantTeam) {
        this.radiantTeam = radiantTeam;
    }

    public void setDireTeam(Team direTeam) {
        this.direTeam = direTeam;
    }

    public String getRadiantTeamId() {
        return radiantTeamId;
    }

    public void setRadiantTeamId(String radiantTeamId) {
        this.radiantTeamId = radiantTeamId;
    }

    public String getDireTeamId() {
        return direTeamId;
    }

    public void setDireTeamId(String direTeamId) {
        this.direTeamId = direTeamId;
    }
}
