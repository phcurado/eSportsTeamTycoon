package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by Paulo on 06/11/2016.
 */

public class BattleSimulation {

    public enum Roles {
        CARRY, MIDLANE, OFFLANE, SUPP4, SUPP5;
    }
    float matchTime;

    private EsportsManager mainApp;

    private Team radiantTeam;
    private Team direTeam;

    public static final int MAP_WIDTH = 480;
    public static final int MAP_HEIGHT = 420;

    public BattleSimulation(Team radiantTeam, Team direTeam) {
        this.radiantTeam = radiantTeam;
        this.direTeam = direTeam;
    }

    public void setPositions() {
        matchTime += Gdx.graphics.getDeltaTime();
        if(matchTime > 3.0f) {
            radiantTeam.getPlayers().get(Roles.CARRY.ordinal()).translate(MAP_WIDTH / 2, 0);
            radiantTeam.getPlayers().get(Roles.MIDLANE.ordinal()).translate(MAP_WIDTH / 2, MAP_HEIGHT  / 2);
            radiantTeam.getPlayers().get(Roles.OFFLANE.ordinal()).translate(0, MAP_HEIGHT  / 2);
            radiantTeam.getPlayers().get(Roles.SUPP4.ordinal()).translate(MAP_WIDTH / 2, 25);
            radiantTeam.getPlayers().get(Roles.SUPP5.ordinal()).translate(MAP_WIDTH / 2 + 25, 25);
        }
    }

    public void renderPlayers(SpriteBatch batch) {

    }
}
