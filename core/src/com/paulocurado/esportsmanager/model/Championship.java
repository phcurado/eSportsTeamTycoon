package com.paulocurado.esportsmanager.model;

import com.paulocurado.esportsmanager.EsportsManager;

import java.util.ArrayList;

/**
 * Created by phcur on 20/12/2016.
 */

public class Championship {
    public ArrayList<String> getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(ArrayList<String> teamsId) {
        this.teamsId = teamsId;
    }

    private transient ArrayList<com.paulocurado.esportsmanager.model.Team> teams;
    private ArrayList<String> teamsId;
    private static int DATE_OCCURRENCES = 3;
    private boolean championshipIsUp = false;
    private ArrayList<BattleSimulation> battles;
    private transient GameSchedule schedule;
    private GameSchedule championshipSchedule;
    private boolean matchReady = false;
    private static int STARTING_WEEK = 2;
    private int week = STARTING_WEEK;
    private int gamesPlayed = 0;
    private int roundsPlayed = 0;
    private int tierPlaying;
    private boolean organizeTeams = false;
    private boolean finalsUp = false;
    private boolean groupStage = false;
    private boolean organizeTeamsFinal = false;
    private boolean championshipEnded = false;
    private transient EsportsManager mainApp;
    private boolean isUserMatchShowed = false;
    private boolean isDuelShowed = false;
    private boolean playerAcceptedChampionship = false;
    public boolean isChampionshipIsCloseStart = false;

    public Championship(ArrayList<com.paulocurado.esportsmanager.model.Team> teams, EsportsManager mainApp) {
        this.mainApp = mainApp;
        this.teams = teams;
        this.schedule = mainApp.schedule;
        championshipSchedule = new GameSchedule();
        battles = new ArrayList<BattleSimulation>();
        teamsId = new ArrayList<String>();
        for(int j = 0; j < teams.size(); j++) {
            teamsId.add(teams.get(j).getId());
        }
        tierPlaying = mainApp.user.getTeam().getTier();


    }

    public void setBasicVariables(EsportsManager mainApp) {
        this.mainApp = mainApp;
        this.schedule = mainApp.schedule;
        teamsId = new ArrayList<String>();
        for(int j = 0; j < teams.size(); j++) {
            teamsId.add(teams.get(j).getId());
        }
    }

    public void closeChampionship() {
        for (int i = 1; i <= schedule.MIN_MONTH / DATE_OCCURRENCES; i++) {
            if (schedule.getDay() == 1 && schedule.getWeek() == 1 && schedule.getMonth() == i * DATE_OCCURRENCES - 1) {
                UsefulFunctions usefulFunctions = new UsefulFunctions(mainApp);
                teams = usefulFunctions.getTeamsByTier(mainApp.user.getTeam().getTier());
                //groupStage = true;
                tierPlaying = mainApp.user.getTeam().getTier();
                championshipSchedule.setDay(schedule.getDay());
                championshipSchedule.setWeek(schedule.getWeek());
                championshipSchedule.setMonth(schedule.getMonth() + 1);
                championshipSchedule.setYear(schedule.getYear());
                for (int j = 0; j < teams.size(); j++) {
                    teams.get(j).setVictoriesChampionship(0);
                    teams.get(j).setLosesChampionship(0);
                }
                isChampionshipIsCloseStart = true;
                organizeTeamsFinal = false;
            }
        }
    }
    public void startChampionship() {
        for (int i = 1; i <= schedule.MIN_MONTH / DATE_OCCURRENCES; i++) {
            if (schedule.getDay() == 1 && schedule.getWeek() == 1 && schedule.getMonth() == i * DATE_OCCURRENCES) {
                finishChampionship(); // testes
                  UsefulFunctions usefulFunctions = new UsefulFunctions(mainApp);
                 teams = usefulFunctions.getTeamsByTier(mainApp.user.getTeam().getTier());
                championshipIsUp = true;
                   groupStage = true;
                tierPlaying = mainApp.user.getTeam().getTier();
               // championshipSchedule.setDay(schedule.getDay());
               // championshipSchedule.setWeek(schedule.getWeek() + 1);
               // championshipSchedule.setMonth(schedule.getMonth());
               // championshipSchedule.setYear(schedule.getYear());
                  battles.clear();
                 for (int j = 0; j < teams.size(); j++) {
                       teams.get(j).setVictoriesChampionship(0);
                       teams.get(j).setLosesChampionship(0);
                   }
                organizeTeamsFinal = false;
                championshipEnded = false;

            }
        }

    }

    public void updateTeamsMatches() {
        com.paulocurado.esportsmanager.model.Team teamDummy = teams.get(0);
        ArrayList<com.paulocurado.esportsmanager.model.Team> teamsDummy = new ArrayList<com.paulocurado.esportsmanager.model.Team>();
        teamsDummy.addAll(teams);
        int numDays = (teams.size() - 1);
        int halfSize = teams.size() / 2;
        teamsDummy.remove(0);

        for (int j = 0; j < numDays; j++) {
            battles.add(new BattleSimulation(teamsDummy.get(j % teamsDummy.size()), teamDummy));
            for (int i = 1; i < halfSize; i++) {

                battles.add(new BattleSimulation(teamsDummy.get((j + i) % teamsDummy.size()),
                        teamsDummy.get((j + teamsDummy.size() - i) % teamsDummy.size())));
            }
        }
    }

    public void defineMatchesDate() {
        if (schedule.getWeek() == championshipSchedule.getWeek()) {
            matchReady = true;
            championshipSchedule.addWeek(1);
        }

    }

    public void groupMatches() {
        if (gamesPlayed < battles.size()) {
            for (int i = 0; i < teams.size() / 2; i++) {
                if(battles.get(gamesPlayed).getRadiantTeam() != (mainApp.user.getTeam()) &&
                        battles.get(gamesPlayed).getDireTeam() != (mainApp.user.getTeam())) {
                    battles.get(gamesPlayed).bestofBattle(3);

                    battles.get(gamesPlayed).getRadiantTeam().setVictoriesChampionship(
                            battles.get(gamesPlayed).getRadiantTeam().getVictoriesChampionship() +
                                    battles.get(gamesPlayed).getRadiantVictories()
                    );
                    battles.get(gamesPlayed).getRadiantTeam().setLosesChampionship(
                            battles.get(gamesPlayed).getRadiantTeam().getLosesChampionship() +
                                    battles.get(gamesPlayed).getDireVictories()
                    );

                    battles.get(gamesPlayed).getDireTeam().setVictoriesChampionship(
                            battles.get(gamesPlayed).getDireTeam().getVictoriesChampionship() +
                                    battles.get(gamesPlayed).getDireVictories()
                    );
                    battles.get(gamesPlayed).getDireTeam().setLosesChampionship(
                            battles.get(gamesPlayed).getDireTeam().getLosesChampionship() +
                                    battles.get(gamesPlayed).getRadiantVictories()
                    );
                    //battles.get(gamesPlayed).winner().setVictoriesChampionship(battles.get(gamesPlayed).winner().getVictoriesChampionship() + 1);

                }
                 gamesPlayed++;
            }
            roundsPlayed++;
        }
    }

    public void organizeFinalMatch() {
        battles.add(new BattleSimulation(getTeamsInOrderOfVictory().get(0), getTeamsInOrderOfVictory().get(1)) );
    }

    public void finalMatch() {
        if(battles.get(gamesPlayed).getRadiantTeam() != (mainApp.user.getTeam()) &&
                battles.get(gamesPlayed).getDireTeam() != (mainApp.user.getTeam())) {
            battles.get(battles.size() - 1).bestofBattle(5);
        }
        gamesPlayed++;

    }

    public ArrayList<com.paulocurado.esportsmanager.model.Team> getTeamsInOrderOfVictory() {
        ArrayList<com.paulocurado.esportsmanager.model.Team> teamsBuffer = new ArrayList<com.paulocurado.esportsmanager.model.Team>();
        for (int i = 0; i < teams.size(); i++) {
            teamsBuffer.add(teams.get(i));
        }
        for (int i = 0; i < teamsBuffer.size(); i++) {
            for (int j = 0; j < teamsBuffer.size() - 1 - i; j++) {
                if (teamsBuffer.get(i).getVictoriesChampionship() < teamsBuffer.get(i + j + 1).getVictoriesChampionship()) {
                    com.paulocurado.esportsmanager.model.Team teamBuff = teamsBuffer.get(i);
                    teamsBuffer.set(i, teamsBuffer.get(i + j + 1));
                    teamsBuffer.set(i + j + 1, teamBuff);

                }
            }
        }
        return teamsBuffer;
    }

    public void finishChampionship() {
        championshipIsUp = false;
        organizeTeams = false;
        matchReady = false;
        gamesPlayed = 0;
        roundsPlayed = 0;
        finalsUp = false;
        groupStage = false;
        championshipEnded = true;
        isUserMatchShowed = false;
        isDuelShowed = false;
        playerAcceptedChampionship = false;
        isChampionshipIsCloseStart = false;

    }
    public com.paulocurado.esportsmanager.model.Team getWinnerOfChampionship() {
        if(championshipEnded) {
            return battles.get(battles.size() - 1).winner();
        }
        else return null;
    }

    public ArrayList<BattleSimulation> getMatchesPerRound(int round) {
        ArrayList<BattleSimulation> battleSimulations = new ArrayList<BattleSimulation>();
        for(int i = 0; i < teams.size() / 2; i++) {
            battleSimulations.add(battles.get(i + (round - 1) * teams.size() / 2));
        }
        return battleSimulations;
    }

    public BattleSimulation findBattleByTeam(com.paulocurado.esportsmanager.model.Team team, int round) {
        for(int i = 0; i < teams.size() / 2; i++) {
            if(battles.get(i + (round - 1) * teams.size() / 2).getRadiantTeam().equals(team) ||
                    battles.get(i + (round - 1) * teams.size() / 2).getDireTeam().equals(team)) {
                return battles.get(i + (round - 1) * teams.size() / 2);
            }
        }
        return null;
    }

    public ArrayList<com.paulocurado.esportsmanager.model.Team> getFinalChampionshipPositions() {
        ArrayList<com.paulocurado.esportsmanager.model.Team> teamsArrange = getTeamsInOrderOfVictory();
        if(teamsArrange.get(0) == getWinnerOfChampionship()) {
            return teamsArrange;
        }
        else {
            com.paulocurado.esportsmanager.model.Team teamDummy = teamsArrange.get(0);
            teamsArrange.set(0, teamsArrange.get(1));
            teamsArrange.set(1, teamDummy);
            return teamsArrange;
        }
    }

    public double payPrizeToUser() {
        double prize;

        if (mainApp.user.getTeam().getTier() == 1) {
            prize =  5100000;
        }
        else if (mainApp.user.getTeam().getTier() == 2) {
            prize =  3100000;
        }
        else if (mainApp.user.getTeam().getTier() == 3) {
            prize =  1600000;
        }
        else {
            prize =  1100000;
        }

        for(int i = 0; i < getFinalChampionshipPositions().size(); i++) {
            if(mainApp.user.getTeam().equals(getFinalChampionshipPositions().get(i))) {

                if(getWinnerOfChampionship().equals(mainApp.user.getTeam())) {
                    prize = 0.30 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou em primeiro " + prize);
                }
                else if (i == 1) {
                    prize = 0.23 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou em segundo " + prize);
                }
                else if (i == 2) {
                    prize = 0.17 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou em terceiro " + prize);
                }
                else if (i == 3){
                    prize = 0.13 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou em quarto " + prize);
                }
                else if (i == 4){
                    prize = 0.10 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou em quinto " + prize);
                }
                else {
                    prize = 0.07 * prize;
                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (long)prize);
                    System.out.println("Ficou depois do quinto " + prize);
                }
            }
        }

        return prize;
    }

    public double payPrizePerMatchToUser() {

        if (mainApp.user.getTeam().getTier() == 1) {
            return 18000;
        }
        if (mainApp.user.getTeam().getTier() == 2) {
            return 14000;
        }
        if (mainApp.user.getTeam().getTier() == 3) {
            return 10000;
        }
        else {
            return 6500;
        }

    }


    public ArrayList<com.paulocurado.esportsmanager.model.Team> getTeams() {
        return teams;
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public boolean isFinalsUp() {
        return finalsUp;
    }

    public void setFinalsUp(boolean finalsUp) {
        this.finalsUp = finalsUp;
    }

    public BattleSimulation getFinalBattle() {
        return battles.get(battles.size() - 1);
    }

    public boolean isGroupStage() {
        return groupStage;
    }

    public void setGroupStage(boolean groupStage) {
        this.groupStage = groupStage;
    }

    public void setChampionshipIsUp(boolean championshipIsUp) {
        this.championshipIsUp = championshipIsUp;
    }

    public boolean isOrganizeTeams() {
        return organizeTeams;
    }

    public void setOrganizeTeams(boolean organizeTeams) {
        this.organizeTeams = organizeTeams;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public GameSchedule getChampionshipSchedule() {
        return championshipSchedule;
    }

    public void setChampionshipSchedule(GameSchedule championshipSchedule) {
        this.championshipSchedule = championshipSchedule;
    }

    public boolean isOrganizeTeamsFinal() {
        return organizeTeamsFinal;
    }

    public void setOrganizeTeamsFinal(boolean organizeTeamsFinal) {
        this.organizeTeamsFinal = organizeTeamsFinal;
    }

    public GameSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(GameSchedule schedule) {
        this.schedule = schedule;
    }

    public void setTeams(ArrayList<com.paulocurado.esportsmanager.model.Team> teams) {
        this.teams = teams;
    }

    public EsportsManager getMainApp() {
        return mainApp;
    }

    public void setMainApp(EsportsManager mainApp) {
        this.mainApp = mainApp;
    }

    public boolean isUserMatchShowed() {
        return isUserMatchShowed;
    }

    public void setUserMatchShowed(boolean userMatchShowed) {
        isUserMatchShowed = userMatchShowed;
    }

    public boolean isDuelShowed() {
        return isDuelShowed;
    }

    public void setDuelShowed(boolean duelShowed) {
        isDuelShowed = duelShowed;
    }

    public boolean isPlayerAcceptedChampionship() {
        return playerAcceptedChampionship;
    }

    public void setPlayerAcceptedChampionship(boolean playerAcceptedChampionship) {
        this.playerAcceptedChampionship = playerAcceptedChampionship;
    }

    public int getTierPlaying() {
        return tierPlaying;
    }

    public void setTierPlaying(int tierPlaying) {
        this.tierPlaying = tierPlaying;
    }
}
