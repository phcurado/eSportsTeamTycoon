package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Paulo on 03/11/2016.
 */

public class Player {
    /*  Personal information    */
    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private Calendar birthDay;
    private int rank; //similar to ELO/MMR
    /*  Team information  */
    private int popularity;
    private String teamId;
    /*  Technique   */
    private int farm;
    private int independency;
    private int fighting;
    private int support;
    private int rotation;
    private int positioning;
    private int mapAwareness;
    private int aggression;
    private int reflex;
    private int decisionMaking;
    private int potential;
    /*  Personal Behavior   ps: some are hidden from the user*/
    private int happiness;
    private int intelligence;
    private int concentration;
    private int leadership;
    private int rage;
    /*  Appearance   variables*/
    private int hairType;
    private int hairColor;
    private int faceType;
    private int faceColor;
    private int expressionType;
    private int accessory;

    /*  in game informations*/
    private Vector2 position;
    private float speed = 20f;
    private boolean moving = true;


    public Player() {
        this.firstName = "";
        this.lastName = "";
        this.nickName = "";
        this.birthDay = Calendar.getInstance();
        position = new Vector2(0, 0);

    }

    public void setPosition(int xd, int yd) {
        position.x = xd;
        position.y = yd;

    }

    public void translate(float xd, float yd) {
        Vector2 start = new Vector2(position.x, position.y);
        Vector2 end = new Vector2(xd, yd);
        float distance = (float)Math.sqrt(Math.pow(end.x - start.x,2)+Math.pow(end.y - start.y,2));
        float directionX = (end.x - start.x) / distance;
        float directionY = (end.y - start.y) / distance;
        if (moving == true) {
            position.x += directionX * speed * Gdx.graphics.getDeltaTime();
            position.y += directionY * speed * Gdx.graphics.getDeltaTime();
            if(Math.sqrt(Math.pow(position.x - start.x, 2)+Math.pow(position.y - start.y, 2)) >= distance) {
                position.x = end.x;
                position.y = end.y;
                moving = false;
            }
        }

    }

    public void randomMovement() {

        if(moving == false) {
            Random random = new Random();
            boolean stopped = false;
            Vector2 start = new Vector2(position.x, position.y);
            Vector2 end = new Vector2(start.x + 5, start.y + 5);
            float distance = (float)Math.sqrt(Math.pow(end.x - start.x,2)+Math.pow(end.y - start.y,2));
            float directionX = (end.x - start.x) / distance;
            float directionY = (end.y - start.y) / distance;
            position.x += directionX * speed * Gdx.graphics.getDeltaTime();
            position.y += directionY * speed * Gdx.graphics.getDeltaTime();
            if(Math.sqrt(Math.pow(position.x - start.x, 2)+Math.pow(position.y - start.y, 2)) >= distance) {
                System.out.println("dasdas");
                position.x = end.x;
                position.y = end.y;
                moving = true;
            }
        }
        //translate(position.x + random.nextInt() % 10, position.y + random.nextInt() % 10);
    }


    public void render(TextureRegion[][] texture, SpriteBatch batch) {
        switch (faceColor) {
            case 0:
                batch.setColor(232f/255, 193f/255, 158f/255, 1);
                break;
            case 1:
                batch.setColor(196f/255, 146f/255, 112f/255, 1);
                break;
            case 2:
                batch.setColor(223f/255, 190f/255, 143f/255, 1);
                break;
            case 3:
                batch.setColor(141f/255, 105f/255, 177f/255, 1);
                break;
        }
        batch.draw(texture[1][faceType], position.x, position.y);
        batch.setColor(Color.WHITE);
        switch (hairColor) {
            case 0:
                batch.setColor(255f/255, 24f/255, 225f/255, 1);
                break;
            case 1:
                batch.setColor(83f/255, 61f/255, 53f/255, 1);
                break;
            case 2:
                batch.setColor(9f/255, 8f/255, 6f/255, 1);
                break;
            case 3:
                batch.setColor(207f/255, 204f/255, 112f/255, 1);
                break;
            case 4:
                batch.setColor(137f/255, 56f/255, 66f/255, 1);
                break;
            case 5:
                batch.setColor(95f/255, 159f/255, 100f/255, 1);
                break;
        }
        if(hairType != 0)
            batch.draw(texture[0][hairType], position.x, position.y);
        batch.setColor(Color.WHITE);
        batch.draw(texture[1][expressionType+4], position.x, position.y);
        switch (accessory) {
            case 1:
                batch.draw(texture[1][accessory+9], position.x, position.y);
                break;
            case 2:
                batch.draw(texture[1][accessory+9], position.x, position.y);
                break;
        }


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Calendar getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Calendar birthDay) {
        this.birthDay = birthDay;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public int getIndependency() {
        return independency;
    }

    public void setIndependency(int independency) {
        this.independency = independency;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getPositioning() {
        return positioning;
    }

    public void setPositioning(int positioning) {
        this.positioning = positioning;
    }

    public int getMapAwareness() {
        return mapAwareness;
    }

    public void setMapAwareness(int mapAwareness) {
        this.mapAwareness = mapAwareness;
    }

    public int getAggression() {
        return aggression;
    }

    public void setAggression(int aggression) {
        this.aggression = aggression;
    }

    public int getReflex() {
        return reflex;
    }

    public void setReflex(int reflex) {
        this.reflex = reflex;
    }

    public int getDecisionMaking() {
        return decisionMaking;
    }

    public void setDecisionMaking(int decisionMaking) {
        this.decisionMaking = decisionMaking;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }

    public int getHairType() {
        return hairType;
    }

    public void setHairType(int hairType) {
        this.hairType = hairType;
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getFaceType() {
        return faceType;
    }

    public void setFaceType(int faceType) {
        this.faceType = faceType;
    }

    public int getFaceColor() {
        return faceColor;
    }

    public void setFaceColor(int faceColor) {
        this.faceColor = faceColor;
    }

    public int getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(int expressionType) {
        this.expressionType = expressionType;
    }

    public int getAccessory() {
        return accessory;
    }

    public void setAccessory(int accessory) {
        this.accessory = accessory;
    }

}