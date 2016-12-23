package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paulocurado.esportsmanager.EsportsManager;

import java.util.Random;

/**
 * Created by Paulo on 06/11/2016.
 */

public class BattleSimulation {

    private transient Team radiantTeam;
    private transient Team direTeam;
    private int radiantVictories;
    private int direVictories;


    public BattleSimulation(Team radiantTeam, Team direTeam) {
        this.radiantTeam = radiantTeam;
        this.direTeam = direTeam;
    }

    public void bestofBattle(int number) {
        for(int i = 0; i < number; i++) {
            if (radiantVictories < number / 2 + 1 && direVictories < number / 2 + 1) {
                Random randomStr = new Random();
                if (radiantTeam.getStrength() - randomStr.nextInt(10) >= direTeam.getStrength() - randomStr.nextInt(10)) {
                    radiantVictories++;
                    System.out.println(radiantVictories + " X " + direVictories);
                } else {
                    direVictories++;
                    System.out.println(radiantVictories + " X " + direVictories);

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
}
