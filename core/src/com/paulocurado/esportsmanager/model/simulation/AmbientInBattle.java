package com.paulocurado.esportsmanager.model.simulation;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by phcur on 02/01/2017.
 */

public class AmbientInBattle {
    private ArrayList<TowerInBattle> radiantTowersInBattle;
    private ArrayList<TowerInBattle> direTowersInBattle;
    private static int STARTING_TOWERS_NUM = 6;

    private int minute;
    private boolean timePass = false;
    private static float TIME_PASSING_SECONDS = 0.6f;
    private float realTime = 0;
    private boolean isMinutePassing = false;


    public AmbientInBattle() {
        minute = 0;

        radiantTowersInBattle = new ArrayList<TowerInBattle>();
        direTowersInBattle = new ArrayList<TowerInBattle>();
        for (int i = 0; i < STARTING_TOWERS_NUM; i++) {
            radiantTowersInBattle.add(new TowerInBattle());
            direTowersInBattle.add(new TowerInBattle());
        }
    }

    public void passTime() {
        if(timePass) {
            realTime = realTime + Gdx.graphics.getDeltaTime();
            if(realTime >= TIME_PASSING_SECONDS) {
                realTime = 0;
                minute += 1;
                isMinutePassing = true;
            }
        }
    }


    public ArrayList<TowerInBattle> getRadiantTowersInBattle() {
        return radiantTowersInBattle;
    }

    public void setRadiantTowersInBattle(ArrayList<TowerInBattle> radiantTowersInBattle) {
        this.radiantTowersInBattle = radiantTowersInBattle;
    }

    public ArrayList<TowerInBattle> getDireTowersInBattle() {
        return direTowersInBattle;
    }

    public void setDireTowersInBattle(ArrayList<TowerInBattle> direTowersInBattle) {
        this.direTowersInBattle = direTowersInBattle;
    }

    public static int getStartingTowersNum() {
        return STARTING_TOWERS_NUM;
    }

    public static void setStartingTowersNum(int startingTowersNum) {
        STARTING_TOWERS_NUM = startingTowersNum;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isTimePass() {
        return timePass;
    }

    public void setTimePass(boolean timePass) {
        this.timePass = timePass;
    }

    public static float getTimePassingSeconds() {
        return TIME_PASSING_SECONDS;
    }

    public static void setTimePassingSeconds(float timePassingSeconds) {
        TIME_PASSING_SECONDS = timePassingSeconds;
    }

    public float getRealTime() {
        return realTime;
    }

    public void setRealTime(float realTime) {
        this.realTime = realTime;
    }

    public boolean minutePassed() {
        if(isMinutePassing == true) {
            isMinutePassing = false;
            return true;
        }
        else {
            return false;
        }

    }
}
