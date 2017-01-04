package com.paulocurado.esportsmanager.model.simulation;

/**
 * Created by phcur on 02/01/2017.
 */

public class TowerInBattle {
    private int damage = 10;
    private int life;

    //basic variables
    private static int TOWER_LIFE = 400;

    public TowerInBattle() {
        life = TOWER_LIFE;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public static int getTowerLife() {
        return TOWER_LIFE;
    }

    public static void setTowerLife(int towerLife) {
        TOWER_LIFE = towerLife;
    }
}
