package com.paulocurado.esportsmanager.model;

import java.util.ArrayList;

/**
 * Created by phcur on 06/01/2017.
 */

public class Scout {
    private int level;
    public static int MAX_LEVEL = 5;
    private transient ArrayList<Player> playersScouts;
    private static int MIN_ABILITY = 73;
    private static int RANGE_INCREASE = 4;
    public static int BASE_PRICE = 100000;


    public Scout(int level) {
        this.level = level;
    }

    public void scoutAllPlayers(ArrayList<Player> playersToScout) {
        playersScouts = new ArrayList<Player>();

        for (Player player : playersToScout) {
            if (level != MAX_LEVEL) {
                if (player.getOverall() <= MIN_ABILITY + RANGE_INCREASE + (level - 1) * RANGE_INCREASE) {
                    playersScouts.add(player);

                }
            }
            else {
                playersScouts.add(player);
            }
        }
    }

    public ArrayList<Player> getPlayersPerPosition(int position) {
        ArrayList<Player> playersPerPosition = new ArrayList<Player>();
        for (Player player : playersScouts) {
            if (player.bestPositionInteger() == position) {
                playersPerPosition.add(player);
            }
        }
        return playersPerPosition;
    }

    public void addLevel() {
        if (level < MAX_LEVEL) {
            level++;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static int getMaxLevel() {
        return MAX_LEVEL;
    }

    public static void setMaxLevel(int maxLevel) {
        MAX_LEVEL = maxLevel;
    }

    public ArrayList<Player> getPlayersScouts() {
        return playersScouts;
    }

    public void setPlayersScouts(ArrayList<Player> playersScouts) {
        this.playersScouts = playersScouts;
    }

    public static int getMinAbility() {
        return MIN_ABILITY;
    }

    public static void setMinAbility(int minAbility) {
        MIN_ABILITY = minAbility;
    }

    public static int getRangeIncrease() {
        return RANGE_INCREASE;
    }

    public static void setRangeIncrease(int rangeIncrease) {
        RANGE_INCREASE = rangeIncrease;
    }
}
