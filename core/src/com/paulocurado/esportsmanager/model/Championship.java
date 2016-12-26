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
    private int roundsPlayed = 0;
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
        if(championshipIsUp && !organizeTeams) {
            Team teamDummy = teams.get(0);
            ArrayList<Team> teamsDummy = new ArrayList<Team>();
            teamsDummy.addAll(teams);
            int numDays = (teams.size() - 1);
            int halfSize = teams.size() / 2;
            teamsDummy.remove(0);

            for(int j = 0; j < numDays; j++) {
                battles.add(new BattleSimulation(teamsDummy.get(j % teamsDummy.size()), teamDummy));
                for (int i = 1; i < halfSize; i++) {

                    battles.add(new BattleSimulation(teamsDummy.get((j + i) % teamsDummy.size() ),
                            teamsDummy.get((j + teamsDummy.size() - i) % teamsDummy.size())));
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
                roundsPlayed = 0;
            }
            else {
                for(int i = 0; i < teams.size() / 2; i++) {
                    battles.get(gamesPlayed).bestofBattle(3);
                    gamesPlayed++;
                }
                //roundsPlayed = roundsPlayed + teams.size() / 2;
                roundsPlayed++;
            }

        }
    }

    public ArrayList<BattleSimulation> getMatchesPerRound(int round) {
        ArrayList<BattleSimulation> battleSimulations = new ArrayList<BattleSimulation>();
        for(int i = 0; i < teams.size() / 2; i++) {
            battleSimulations.add(battles.get(i + (round - 1) * teams.size() / 2));
        }
        return battleSimulations;
    }

    public BattleSimulation findBattleByTeam(Team team, int round) {
        for(int i = 0; i < teams.size() / 2; i++) {
            if(battles.get(i + (round - 1) * teams.size() / 2).getRadiantTeam().equals(team) ||
                    battles.get(i + (round - 1) * teams.size() / 2).getDireTeam().equals(team)) {
                return battles.get(i + (round - 1) * teams.size() / 2);
            }
        }
        return null;
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

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
}
