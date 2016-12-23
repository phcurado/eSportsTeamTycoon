package com.paulocurado.esportsmanager.model;

import java.util.ArrayList;

/**
 * Created by phcur on 20/12/2016.
 */

public class Championship {
    private transient ArrayList<Team> teams;
    private static int DATE_OCCURRENCES = 3;
    private boolean championshipIsUp = false;
    private ArrayList<BattleSimulation> battles;
    private GameSchedule schedule;
    private GameSchedule championshipSchedule;
    private boolean matchReady = false;
    private static int STARTING_WEEK = 2;
    private int week = STARTING_WEEK;
    private int gamesPlayed = 0;
    private boolean organizeTeams = false;

    public Championship(ArrayList<Team> teams, GameSchedule schedule) {
        this.teams = teams;
        this.schedule = schedule;
        championshipSchedule = new GameSchedule();
        battles = new ArrayList<BattleSimulation>();
    }

    public void startChampionship() {
        if(!championshipIsUp) {
            for (int i = 1; i <= schedule.MIN_MONTH / DATE_OCCURRENCES; i++) {
                if (schedule.getDay() == 1 && schedule.getWeek() == 1 && schedule.getMonth() == i * DATE_OCCURRENCES) {
                    championshipIsUp = true;
                    championshipSchedule.setDay(schedule.getDay());
                    championshipSchedule.setWeek(schedule.getWeek() + 1);
                    championshipSchedule.setMonth(schedule.getMonth());
                    championshipSchedule.setYear(schedule.getYear());

                }
            }
        }
    }

    public void updateTeamsMatches(User user) {
        //battles.clear();
        if(championshipIsUp && !organizeTeams) {
            for (int i = 0; i < teams.size(); i++) {
                if (!teams.get(i).equals(user.getTeam())) {
                    if(user.getTeam().getTier() == teams.get(i).getTier()) {
                        battles.add(new BattleSimulation(user.getTeam(), teams.get(i)));
                        System.out.println(teams.get(i).getName());
                    }
                }
            }
            organizeTeams = true;
        }
    }

    public void matches() {
        if(championshipIsUp && schedule.getWeek() == championshipSchedule.getWeek()) {
            championshipSchedule.addWeek(1);
            matchReady = true;
        }
        if(championshipIsUp && matchReady) {
            if(gamesPlayed >= battles.size()) {
                championshipIsUp = false;
                organizeTeams = false;
                matchReady = false;
                battles.clear();
                gamesPlayed = 0;
            }
            else {
                battles.get(gamesPlayed).bestofBattle(3);
                gamesPlayed++;
            }

        }
    }

    public ArrayList<BattleSimulation> getBattles() {
        return battles;
    }

    public boolean isChampionshipIsUp() {
        return championshipIsUp;
    }

    public boolean isMatchReady() {
        return matchReady;
    }

    public void setMatchReady(boolean matchReady) {
        this.matchReady = matchReady;
    }

    public int getWeek() {
        return week;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
