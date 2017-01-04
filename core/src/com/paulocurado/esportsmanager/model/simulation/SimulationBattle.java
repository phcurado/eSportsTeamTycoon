package com.paulocurado.esportsmanager.model.simulation;

import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Position;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by phcur on 02/01/2017.
 */

public class SimulationBattle {
    private AmbientInBattle ambientInBattle;
    private ArrayList<PlayerInBattle> radiantPlayersInBattle;
    private ArrayList<PlayerInBattle> direPlayersInBattle;
    private int radiantKills;
    private int direKills;

    //simulation stage
    private boolean isLanePhaseTime;
    private boolean isBattlePhaseTime;
    private boolean isTowerDown;
    private boolean isMatchUp;

    private static float FARM_MULTIPLIER = 13f;


    public SimulationBattle(BattleSimulation battle) {
        ambientInBattle = new AmbientInBattle();
        radiantPlayersInBattle = new ArrayList<PlayerInBattle>();
        direPlayersInBattle = new ArrayList<PlayerInBattle>();
        for (Player player : battle.getRadiantTeam().getPlayers()) {
            PlayerInBattle playerInBattle = new PlayerInBattle(player);
            radiantPlayersInBattle.add(playerInBattle);
        }
        for (Player player : battle.getDireTeam().getPlayers()) {
            PlayerInBattle playerInBattle = new PlayerInBattle(player);
            direPlayersInBattle.add(playerInBattle);
        }
    }


    public void gameSimulation() {
        if (ambientInBattle.getRadiantTowersInBattle().size() !=0 && ambientInBattle.getDireTowersInBattle().size() != 0) {
            ambientInBattle.setTimePass(true);
            if(ambientInBattle.isTimePass()) {
                defineTimePhases();
                deathRespawnTime();

                if (isLanePhaseTime) {
                    lanePhase();
                }
                else if (isBattlePhaseTime) {
                    battlePhase();
                }
            }
        }
        else {
            ambientInBattle.setTimePass(false);
            isMatchUp = false;
            System.out.println("hi");
        }
    }

    private void deathRespawnTime() {
        for (PlayerInBattle player : radiantPlayersInBattle) {
            player.countRespawnTime();
        }
        for (PlayerInBattle player : direPlayersInBattle) {
            player.countRespawnTime();
        }

    }
    private void lanePhase() {
        buyItemsLogic();
        //Libera todos os players para ficar na farming phase
        for (PlayerInBattle playerInBattle : radiantPlayersInBattle) {
            playerInBattle.setFarming(true);
        }
        for (PlayerInBattle playerInBattle : direPlayersInBattle) {
            playerInBattle.setFarming(true);
        }

        safeOffBattle();
        midBattle();
    }

    private void battlePhase() {
        int radiantFarm = 0;
        int direFarm = 0;
        for (PlayerInBattle player : radiantPlayersInBattle) {
            radiantFarm += player.getTotalNetWorth();
        }
        for (PlayerInBattle player : direPlayersInBattle) {
            direFarm += player.getTotalNetWorth();
        }

        if(ambientInBattle.getRadiantTowersInBattle().size() != 0 && ambientInBattle.getDireTowersInBattle().size() != 0) {
            if (radiantFarm >= direFarm) {
                radiantAttackTowers();
                if (ambientInBattle.getRadiantTowersInBattle().get(0).getLife() <= 0) {
                    ambientInBattle.getRadiantTowersInBattle().remove(0);
                }
                if (ambientInBattle.getDireTowersInBattle().get(0).getLife() <= 0) {
                    ambientInBattle.getDireTowersInBattle().remove(0);
                    direAttackTowers();
                }
            } else {
                direAttackTowers();
                if (ambientInBattle.getDireTowersInBattle().get(0).getLife() <= 0) {
                    ambientInBattle.getDireTowersInBattle().remove(0);
                }
                if (ambientInBattle.getRadiantTowersInBattle().get(0).getLife() <= 0) {
                    ambientInBattle.getRadiantTowersInBattle().remove(0);
                    radiantAttackTowers();
                }
            }
        }

    }
    private void radiantAttackTowers() {
        Random random = new Random();
        int newRadiantCarryAbility = radiantPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newRadiantMidAbility = radiantPlayersInBattle.get(1).abilityInteger(Position.MID) + random.nextInt(10)-10;
        int newRadiantOfflaneAbility = radiantPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newRadiantRoamerAbility = radiantPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newRadiantSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;

        int newDireCarryAbility = direPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newDireMidAbility = direPlayersInBattle.get(1).abilityInteger(Position.MID) + random.nextInt(10)-10;
        int newDireOfflaneAbility = direPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newDireRoamerAbility = direPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newDireSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;

        if (ambientInBattle.getRadiantTowersInBattle().size() != 0 && ambientInBattle.getDireTowersInBattle().size() != 0) {


            radiantPlayersInBattle.get(0).addLastHit(radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
            radiantPlayersInBattle.get(0).hitTower(ambientInBattle.getDireTowersInBattle().get(0));
            radiantPlayersInBattle.get(1).hitTower(ambientInBattle.getDireTowersInBattle().get(0));
            radiantPlayersInBattle.get(2).hitTower(ambientInBattle.getDireTowersInBattle().get(0));

            //Carry Batendo
            if (newRadiantCarryAbility >= newDireMidAbility) {
                radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(1));
            }
            else {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(0));
            }
            if (newRadiantCarryAbility >= newDireOfflaneAbility) {
                radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(2));
            }
            else {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(0));
            }
            if (newRadiantCarryAbility >= newDireRoamerAbility) {
                radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(3));
            }
            else {
                direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(0));
            }
            if (newRadiantCarryAbility >= newDireSuppAbility) {
                radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(4));
            }
            else {
                direPlayersInBattle.get(4).hit(radiantPlayersInBattle.get(0));
            }

            //Mid Batendo
            if (newRadiantMidAbility >= newDireMidAbility) {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(1));
            }
            else {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(1));
            }
            if (newRadiantMidAbility >= newDireOfflaneAbility) {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(2));
            }
            else {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(1));
            }
            if (newRadiantMidAbility >= newDireRoamerAbility) {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(3));
            }
            else {
                direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(1));
            }
            if (newRadiantMidAbility >= newDireSuppAbility) {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(4));
            }
            else {
                direPlayersInBattle.get(4).hit(radiantPlayersInBattle.get(1));
            }
            //Offlane Batendo
            if (newRadiantOfflaneAbility >= newDireMidAbility) {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(1));
            }
            else {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(2));
            }
            if (newRadiantOfflaneAbility >= newDireOfflaneAbility) {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(2));
            }
            else {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(2));
            }
            if (newRadiantOfflaneAbility >= newDireRoamerAbility) {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(3));
            }
            else {
                direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(2));
            }
            if (newRadiantOfflaneAbility >= newDireSuppAbility) {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(4));
            }
            else {
                direPlayersInBattle.get(4).hit(radiantPlayersInBattle.get(2));
            }


            //carry split pushing
            if (newDireCarryAbility >= newRadiantRoamerAbility) {
                direPlayersInBattle.get(0).addLastHit(direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
                if(ambientInBattle.getMinute() % 2 == 0) {
                    direPlayersInBattle.get(0).hitTower(ambientInBattle.getRadiantTowersInBattle().get(0));
                }
                for (int i = 0; i < (newDireCarryAbility - newRadiantRoamerAbility) / 5; i++) {
                    direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(3));
                }
            }
            else {
                direPlayersInBattle.get(0).addLastHit(direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER -
                        (newRadiantRoamerAbility - newDireOfflaneAbility) / 10f);
                for (int i = 0; i < (newRadiantRoamerAbility - newDireCarryAbility) / 5; i++) {
                    radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(3));
                }
            }

        }
    }
    private void direAttackTowers() {
        Random random = new Random();
        int newRadiantCarryAbility = radiantPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newRadiantMidAbility = radiantPlayersInBattle.get(1).abilityInteger(Position.MID) + random.nextInt(10)-10;
        int newRadiantOfflaneAbility = radiantPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newRadiantRoamerAbility = radiantPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newRadiantSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;

        int newDireCarryAbility = direPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newDireMidAbility = direPlayersInBattle.get(1).abilityInteger(Position.MID) + random.nextInt(10)-10;
        int newDireOfflaneAbility = direPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newDireRoamerAbility = direPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newDireSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;

        if (ambientInBattle.getRadiantTowersInBattle().size() != 0 && ambientInBattle.getDireTowersInBattle().size() != 0) {

            direPlayersInBattle.get(0).addLastHit(direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
            direPlayersInBattle.get(0).hitTower(ambientInBattle.getRadiantTowersInBattle().get(0));
            direPlayersInBattle.get(1).hitTower(ambientInBattle.getRadiantTowersInBattle().get(0));
            direPlayersInBattle.get(2).hitTower(ambientInBattle.getRadiantTowersInBattle().get(0));


            //Carry batendo
            if (newDireCarryAbility >= newRadiantMidAbility) {
                direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(1));
            }
            else {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(0));
            }
            if (newDireCarryAbility >= newRadiantOfflaneAbility) {
                direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(2));
            }
            else {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(0));
            }
            if (newDireCarryAbility >= newRadiantRoamerAbility) {
                direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(3));
            }
            else {
                radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(0));
            }
            if (newDireCarryAbility >= newRadiantSuppAbility) {
                direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(4));
            }
            else {
                radiantPlayersInBattle.get(4).hit(direPlayersInBattle.get(0));
            }
            //Mid Batendo
            if (newDireMidAbility >= newRadiantMidAbility) {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(1));
            }
            else {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(1));
            }
            if (newDireMidAbility >= newRadiantOfflaneAbility) {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(2));
            }
            else {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(1));
            }
            if (newDireMidAbility >= newRadiantRoamerAbility) {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(3));
            }
            else {
                radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(1));
            }
            if (newDireMidAbility >= newRadiantSuppAbility) {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(4));
            }
            else {
                radiantPlayersInBattle.get(4).hit(direPlayersInBattle.get(1));
            }


            //Offlane Batendo // TODO acho que seria melhor fazer o carry batendo e nao o offlaner
            if (newDireOfflaneAbility >= newRadiantMidAbility) {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(1));
            }
            else {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(2));
            }
            if (newDireOfflaneAbility >= newRadiantOfflaneAbility) {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(2));
            }
            else {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(2));
            }
            if (newDireOfflaneAbility >= newRadiantRoamerAbility) {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(3));
            }
            else {
                radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(2));
            }
            if (newDireOfflaneAbility >= newRadiantSuppAbility) {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(4));
            }
            else {
                radiantPlayersInBattle.get(4).hit(direPlayersInBattle.get(2));
            }


            //carry split pushing
            if (newRadiantCarryAbility >= newDireRoamerAbility) {
                radiantPlayersInBattle.get(0).addLastHit(radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
                if(ambientInBattle.getMinute() % 2 == 0) {
                    radiantPlayersInBattle.get(0).hitTower(ambientInBattle.getDireTowersInBattle().get(0));
                }
                for (int i = 0; i < (newRadiantCarryAbility - newDireRoamerAbility) / 5; i++) {
                    radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(3));
                }
            }
            else {
                radiantPlayersInBattle.get(0).addLastHit(radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER -
                        (newDireRoamerAbility - newRadiantOfflaneAbility) / 10f);
                for (int i = 0; i < (newDireRoamerAbility - newRadiantCarryAbility) / 5; i++) {
                    direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(3));
                }
            }

        }
    }


    private void defineTimePhases() {
        if (ambientInBattle.getMinute() <= 10) {
            isLanePhaseTime = true;
            isBattlePhaseTime = false;
        }
        else if (ambientInBattle.getMinute() > 10 && ambientInBattle.getMinute() <= 15) {
            isLanePhaseTime = false;
            isBattlePhaseTime = true;
        }
        else if (ambientInBattle.getMinute() > 15 && ambientInBattle.getMinute() <= 20) {
            isLanePhaseTime = true;
            isBattlePhaseTime = false;
        }
        else if (ambientInBattle.getMinute() > 20 && ambientInBattle.getMinute() <= 25) {
            isLanePhaseTime = false;
            isBattlePhaseTime = true;
        }
        else if (ambientInBattle.getMinute() > 25 && ambientInBattle.getMinute() <= 40) {
            isLanePhaseTime = true;
            isBattlePhaseTime = false;
        }

        else {
            isLanePhaseTime = false;
            isBattlePhaseTime = true;
        }

    }


    private void safeOffBattle() {
        Random random = new Random();
        int newRadiantCarryAbility = radiantPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newRadiantOfflaneAbility = radiantPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newRadiantRoamerAbility = radiantPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newRadiantSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;

        int newDireCarryAbility = direPlayersInBattle.get(0).abilityInteger(Position.CARRY) + random.nextInt(10)-10;
        int newDireOfflaneAbility = direPlayersInBattle.get(2).abilityInteger(Position.OFFLANE) + random.nextInt(10)-10;
        int newDireRoamerAbility = direPlayersInBattle.get(3).abilityInteger(Position.SUPP4) + random.nextInt(10)-10;
        int newDireSuppAbility = radiantPlayersInBattle.get(4).abilityInteger(Position.SUPP5) + random.nextInt(10)-10;


        if (newRadiantCarryAbility >= newDireOfflaneAbility) {
            radiantPlayersInBattle.get(0).addLastHit(radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
            radiantPlayersInBattle.get(3).addLastHit(radiantPlayersInBattle.get(3).getSupport() / FARM_MULTIPLIER / 5);
            radiantPlayersInBattle.get(4).addLastHit(radiantPlayersInBattle.get(4).getSupport() / FARM_MULTIPLIER / 5);
            if ((newRadiantCarryAbility - newDireOfflaneAbility) / 10f - newRadiantSuppAbility <
                    direPlayersInBattle.get(2).getIndependency() / FARM_MULTIPLIER) {
                direPlayersInBattle.get(2).addLastHit(direPlayersInBattle.get(2).getIndependency() / FARM_MULTIPLIER -
                        (newRadiantCarryAbility - newDireOfflaneAbility) / 10f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newRadiantCarryAbility - newDireOfflaneAbility) / 5; i++) {
                radiantPlayersInBattle.get(0).hit(direPlayersInBattle.get(2)); //calcula o quanto o off vai apanhar se perder
                if(i%2 == 0) {
                    if (newDireRoamerAbility >= newRadiantSuppAbility) {
                        direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(4));
                        direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(4));
                    }
                    if (newDireOfflaneAbility >= newRadiantSuppAbility) {
                        direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(4));
                        //direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(4));
                    }
                    if(newDireRoamerAbility >= newRadiantCarryAbility) {
                        direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(0));
                        direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(0));
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(3));
                        radiantPlayersInBattle.get(4).hit(direPlayersInBattle.get(3));
                    }
                }
            }

        }
        else {
            direPlayersInBattle.get(2).addLastHit(direPlayersInBattle.get(2).getFarm() / FARM_MULTIPLIER); //ganho a batalha, full farm
            if ((newDireOfflaneAbility - newRadiantCarryAbility) / 5f <  radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER) {
                radiantPlayersInBattle.get(0).addLastHit(radiantPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER -
                        (newDireOfflaneAbility - newRadiantCarryAbility) / 5f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newDireOfflaneAbility - newRadiantCarryAbility) / 3; i++) {
                direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(0));
                if(i%2 == 0) {
                    if (newDireRoamerAbility >= newRadiantSuppAbility) {
                        direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(4));
                        direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(4));
                    }
                    if (newDireOfflaneAbility >= newRadiantSuppAbility) {
                        direPlayersInBattle.get(2).hit(radiantPlayersInBattle.get(4));
                        //direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(4));
                    }
                    if(newRadiantRoamerAbility >= newDireOfflaneAbility) {
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(2));
                    }
                    radiantPlayersInBattle.get(4).hit(direPlayersInBattle.get(2));
                }

            }
        }



        if (newDireCarryAbility >= newRadiantOfflaneAbility) {
            direPlayersInBattle.get(0).addLastHit(direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER);
            direPlayersInBattle.get(3).addLastHit(direPlayersInBattle.get(3).getSupport() / FARM_MULTIPLIER / 5);
            direPlayersInBattle.get(4).addLastHit(direPlayersInBattle.get(4).getSupport() / FARM_MULTIPLIER / 5);
            if ((newDireCarryAbility  - newRadiantOfflaneAbility) / 10f - newDireSuppAbility <
                    radiantPlayersInBattle.get(2).getIndependency() / FARM_MULTIPLIER) {
                radiantPlayersInBattle.get(2).addLastHit(radiantPlayersInBattle.get(2).getIndependency() / FARM_MULTIPLIER -
                        (newDireCarryAbility  - newRadiantOfflaneAbility) / 10f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newDireCarryAbility  - newRadiantOfflaneAbility) / 5; i++) {
                direPlayersInBattle.get(0).hit(radiantPlayersInBattle.get(2)); //calcula o quanto o off vai apanhar se perder
                if(i%2 == 0) {
                    if (newRadiantRoamerAbility >= newDireSuppAbility) {
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(4));
                        radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(4));
                    }
                    if (newRadiantOfflaneAbility >= newDireSuppAbility) {
                        radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(4));
                        //radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(4));
                    }
                    if(newRadiantRoamerAbility >= newDireCarryAbility) {
                        radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(0));
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(0));
                        direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(3));
                        direPlayersInBattle.get(4).hit(radiantPlayersInBattle.get(3));
                    }
                }
            }

        }
        else {
            radiantPlayersInBattle.get(2).addLastHit(radiantPlayersInBattle.get(2).getFarm() / FARM_MULTIPLIER); //ganho a batalha, full farm
            if ((newRadiantOfflaneAbility - newDireCarryAbility) / 5f <  direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER) {
                direPlayersInBattle.get(0).addLastHit(direPlayersInBattle.get(0).getFarm() / FARM_MULTIPLIER -
                        (newRadiantOfflaneAbility - newDireCarryAbility) / 5f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newRadiantOfflaneAbility - newDireCarryAbility) / 3; i++) {
                radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(0));
                if(i%2 == 0) {
                    if (newRadiantRoamerAbility >= newDireSuppAbility) {
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(4));
                        radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(4));
                    }
                    if (newRadiantOfflaneAbility >= newDireSuppAbility) {
                        radiantPlayersInBattle.get(3).hit(direPlayersInBattle.get(4));
                        //radiantPlayersInBattle.get(2).hit(direPlayersInBattle.get(4));
                    }
                    if(newDireRoamerAbility >= newRadiantOfflaneAbility) {
                        direPlayersInBattle.get(3).hit(radiantPlayersInBattle.get(2));
                    }
                    direPlayersInBattle.get(4).hit(radiantPlayersInBattle.get(2));
                }
            }
        }

    }

    private void midBattle() {
        Random random = new Random();
        int newRadiantMidHability = radiantPlayersInBattle.get(1).getFighting() + random.nextInt(40)-20;
        System.out.println(newRadiantMidHability);
        int newDireMidHability = direPlayersInBattle.get(1).getFighting() + random.nextInt(40)-20;
        if (newRadiantMidHability > newDireMidHability) {
            //calcula quanto cada um consegue de farm
            radiantPlayersInBattle.get(1).addLastHit(radiantPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER); //ganho a batalha, full farm
            if ((newRadiantMidHability - newDireMidHability) / 8f <  direPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER) {
                direPlayersInBattle.get(1).addLastHit(direPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER -
                        (newRadiantMidHability - newDireMidHability) / 8f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newRadiantMidHability - newDireMidHability) / 6; i++) {
                radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(1)); //calcula o quanto vai apanhar no mid se perder
                if (i == 0) {
                    direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(1));
                }
            }

        }
        else if (newRadiantMidHability == newDireMidHability) {
            radiantPlayersInBattle.get(1).addLastHit(radiantPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER);
            direPlayersInBattle.get(1).addLastHit(direPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER);
            radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(1));
            direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(1));
        }
        else {
            //calcula quanto cada um consegue de farm
            direPlayersInBattle.get(1).addLastHit(direPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER); //ganho a batalha, full farm
            if ((newDireMidHability - newRadiantMidHability) / 8f <  radiantPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER) {
                radiantPlayersInBattle.get(1).addLastHit(radiantPlayersInBattle.get(1).getFarm() / FARM_MULTIPLIER -
                        (newDireMidHability - newRadiantMidHability) / 8f); //perdeu, pega parte do farm
            }
            for (int i = 0; i < (newDireMidHability - newRadiantMidHability) / 6; i++) {
                direPlayersInBattle.get(1).hit(radiantPlayersInBattle.get(1)); //calcula o quanto vai apanhar no mid se perder
                if (i == 0) {
                    radiantPlayersInBattle.get(1).hit(direPlayersInBattle.get(1));
                }
            }

        }
    }

    private void buyItemsLogic() {
        if (ambientInBattle.getMinute() % 3 == 0) {

            for (PlayerInBattle player : radiantPlayersInBattle) {
                player.setBuyingItems(true);
                player.buyItems();
            }
            for (PlayerInBattle player : direPlayersInBattle) {
                player.setBuyingItems(true);
                player.buyItems();
            }
        }
    }



    public AmbientInBattle getAmbientInBattle() {
        return ambientInBattle;
    }

    public void setAmbientInBattle(AmbientInBattle ambientInBattle) {
        this.ambientInBattle = ambientInBattle;
    }

    public ArrayList<PlayerInBattle> getRadiantPlayersInBattle() {
        return radiantPlayersInBattle;
    }

    public void setRadiantPlayersInBattle(ArrayList<PlayerInBattle> radiantPlayersInBattle) {
        this.radiantPlayersInBattle = radiantPlayersInBattle;
    }

    public ArrayList<PlayerInBattle> getDirePlayersInBattle() {
        return direPlayersInBattle;
    }

    public void setDirePlayersInBattle(ArrayList<PlayerInBattle> direPlayersInBattle) {
        this.direPlayersInBattle = direPlayersInBattle;
    }

    public int getRadiantKills() {
        return radiantKills;
    }

    public void setRadiantKills(int radiantKills) {
        this.radiantKills = radiantKills;
    }

    public int getDireKills() {
        return direKills;
    }

    public void setDireKills(int direKills) {
        this.direKills = direKills;
    }

    public boolean isLanePhaseTime() {
        return isLanePhaseTime;
    }

    public void setLanePhaseTime(boolean lanePhaseTime) {
        isLanePhaseTime = lanePhaseTime;
    }

    public boolean isBattlePhaseTime() {
        return isBattlePhaseTime;
    }

    public void setBattlePhaseTime(boolean battlePhaseTime) {
        isBattlePhaseTime = battlePhaseTime;
    }

    public void reset() {
        radiantKills = 0;
        direKills = 0;
        ambientInBattle.setMinute(0);
        ambientInBattle.getRadiantTowersInBattle().clear();
        ambientInBattle.getDireTowersInBattle().clear();
        for (int i = 0; i < ambientInBattle.getStartingTowersNum(); i++) {
            ambientInBattle.getRadiantTowersInBattle().add(new TowerInBattle());
            ambientInBattle.getDireTowersInBattle().add(new TowerInBattle());
        }
        for (PlayerInBattle playerInBattle : radiantPlayersInBattle) {
            playerInBattle.reset();
        }
        for(PlayerInBattle playerInBattle : direPlayersInBattle) {
            playerInBattle.reset();
        }
    }
}
