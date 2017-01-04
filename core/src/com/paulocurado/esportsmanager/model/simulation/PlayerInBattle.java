package com.paulocurado.esportsmanager.model.simulation;

import com.badlogic.gdx.math.Vector2;
import com.paulocurado.esportsmanager.model.Player;

import java.util.Calendar;

/**
 * Created by phcur on 02/01/2017.
 */

public class PlayerInBattle extends Player {
    private double life = 400;
    private double actualLife = 400;
    private double damage = 60;
    private int kills;
    private int deaths;
    private int assists;
    private double lastHit;
    private int towersDestroyed;
    private int respawnTime = RESPAWN_TIME;
    private double netWorth;


    //player states
    private boolean isDeath;
    private boolean isStunned;
    private boolean isFarming;
    private boolean isRotationing;
    private boolean isSplitting;
    private boolean isBuyingItems;

    //in game variables
    private static int LASTHIT_GOLD = 45;
    private static int DEATH_GOLD = 400;
    private static int TOWER_GOLD = 200;
    private static int KILL_GOLD = 500;
    private static int RESPAWN_TIME = 1;

    public PlayerInBattle(Player player) {
        super(player);
    }


    public int getLife() {
        return (int)life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public double getLastHit() {
        return lastHit;
    }

    public void setLastHit(double lastHit) {
        this.lastHit = lastHit;
    }

    public int getTowersDestroyed() {
        return towersDestroyed;
    }

    public void setTowersDestroyed(int towersDestroyed) {
        this.towersDestroyed = towersDestroyed;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(int respawnTime) {
        this.respawnTime = respawnTime;
    }

    public boolean isDeath() {
        return isDeath;
    }



    public static int getLasthitGold() {
        return LASTHIT_GOLD;
    }

    public static void setLasthitGold(int lasthitGold) {
        LASTHIT_GOLD = lasthitGold;
    }

    public static int getDeathGold() {
        return DEATH_GOLD;
    }

    public static void setDeathGold(int deathGold) {
        DEATH_GOLD = deathGold;
    }

    public static int getTowerGold() {
        return TOWER_GOLD;
    }

    public static void setTowerGold(int towerGold) {
        TOWER_GOLD = towerGold;
    }

    public static int getKillGold() {
        return KILL_GOLD;
    }

    public static void setKillGold(int killGold) {
        KILL_GOLD = killGold;
    }

    public int getDamage() {
        return (int)damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public boolean isStunned() {
        return isStunned;
    }

    public void setStunned(boolean stunned) {
        isStunned = stunned;
    }

    public boolean isFarming() {
        return isFarming;
    }

    public void setFarming(boolean farming) {
        isFarming = farming;
    }

    public boolean isRotationing() {
        return isRotationing;
    }

    public void setRotationing(boolean rotationing) {
        isRotationing = rotationing;
    }

    public boolean isSplitting() {
        return isSplitting;
    }

    public void setSplitting(boolean splitting) {
        isSplitting = splitting;
    }

    public boolean isBuyingItems() {
        return isBuyingItems;
    }

    public void setBuyingItems(boolean buyingItems) {
        isBuyingItems = buyingItems;
    }


    public void addNetWorth(double number) {
        netWorth += number;
    }

    public void addLastHit(double number) {
        if (!isDeath) {
            if (isFarming) {
                lastHit += number;
                addNetWorth(number * LASTHIT_GOLD);
            }
        }
    }

    public void killEnemy(PlayerInBattle enemy) {
        if (!enemy.isDeath()) {
            enemy.deaths += 1;

            if (enemy.netWorth - DEATH_GOLD > 0) {
                enemy.netWorth += -DEATH_GOLD;
            }
            else {
                enemy.netWorth = 0;
            }

            enemy.setDeath(true);

            this.netWorth += KILL_GOLD;

            this.kills += 1;
        }
    }

    public void countRespawnTime() {
        if (isDeath) {
            respawnTime += -1;
            if (respawnTime == 0) {
                isDeath = false;
                respawnTime = RESPAWN_TIME;
            }
        }
    }


    public void destroyTower() {
        this.netWorth += TOWER_GOLD;
    }

    public void buyItems() {
        if (isBuyingItems) {
            if(netWorth > 0) {
                life += netWorth / 6;
                damage += netWorth / 60;
            }
            netWorth = 0;
            actualLife = life;

            setBuyingItems(false);
        }
    }
    public void hit(PlayerInBattle enemy) {
        if (!isDeath) {
            if (!enemy.isDeath) {
                enemy.actualLife += -damage;
                if (enemy.actualLife <= 0) {
                    enemy.actualLife = enemy.life;
                    killEnemy(enemy);
                    System.out.println(getNickName() + " matou " + enemy.getNickName());
                }
            }
        }
    }

    public void hitTower(TowerInBattle towerInBattle) {
        if (!isDeath) {
            towerInBattle.setLife(towerInBattle.getLife() - (int)damage);
            if (towerInBattle.getLife() <= 0) {
                System.out.println(getNickName() + " destruiu a torre");
            }

        }

    }

    public int getTotalNetWorth() {
        return (int)lastHit * LASTHIT_GOLD + kills * KILL_GOLD + towersDestroyed * TOWER_GOLD - deaths * DEATH_GOLD;
    }

    public int getActualLife() {
        return (int)actualLife;
    }

    public void setActualLife(double actualLife) {
        this.actualLife = actualLife;
    }

    public void reset() {
        life = 400;
        actualLife = 400;
        damage = 60;
        kills = 0;
        deaths = 0;
        assists = 0;
        lastHit = 0;
        towersDestroyed = 0;
        respawnTime = RESPAWN_TIME;
        netWorth = 0;
        isDeath = false;
        isStunned = false;
        isFarming = false;
        isRotationing = false;
        isSplitting = false;
        isBuyingItems = false;
    }
}
