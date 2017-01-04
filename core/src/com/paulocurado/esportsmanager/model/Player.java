package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
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
    private transient Vector2 position;
    private transient float speed = 20f;
    private transient boolean moving = true;



    public Player() {
        this.firstName = "";
        this.lastName = "";
        this.nickName = "";
        this.birthDay = Calendar.getInstance();
        position = new Vector2(0, 0);

    }
    public Player(Player player) {
        id = player.getId();
        firstName = player.getFirstName();
        lastName = player.lastName;
        nickName = player.getNickName();
        birthDay = player.getBirthDay();
        rank = player.getRank();
        popularity = player.popularity;
        teamId = player.getTeamId();
        farm = player.getFarm();
        independency = player.getIndependency();
        fighting = player.getFighting();
        support = player.getSupport();
        rotation = player.getRotation();
        intelligence = player.getIntelligence();
        leadership = player.getLeadership();
        rage = player.getRage();
        hairType = player.getHairType();
        hairColor = player.getHairColor();
        faceType = player.getFaceType();
        faceColor = player.getFaceColor();
        expressionType = player.getExpressionType();
        accessory = player.getAccessory();

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

    public Image createPlayerFace(TextureRegion[][] texture, Viewport viewport) {

        Batch batch = new SpriteBatch();

        FrameBuffer faceDraw = new FrameBuffer(Pixmap.Format.RGBA8888, (int)viewport.getWorldWidth(), (int)viewport.getWorldHeight(), false);

        Matrix4 m = new Matrix4();
        m.setToOrtho2D(0, 0, 32, 32);
        batch.setProjectionMatrix(m);

        faceDraw.begin();

        batch.enableBlending();
        Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

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


        batch.end();
        faceDraw.end();

        viewport.apply();


        TextureRegion combinedTexture = new TextureRegion(faceDraw.getColorBufferTexture());
        combinedTexture.flip(false, true);

        Image playerFace = new Image(combinedTexture);
        playerFace.setSize(128, 128);

        return playerFace;
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

    public String hability(int role) {

        double positionAbility;

        if(role == 1) {
            positionAbility = 0.65 * farm + 0.05 * independency + 0 * support + 0.05 * rotation + 0.25 * fighting;
        }
        else if(role == 2) {
            positionAbility = 0.15 * farm + 0.10 * independency + 0 * support + 0.10 * rotation + 0.65 * fighting;
        }
        else if(role == 3) {
            positionAbility = 0.1 * farm + 0.60 * independency + 0 * support + 0.15 * rotation + 0.15 * fighting;
        }
        else if(role == 4) {
            positionAbility = 0 * farm + 0.05 * independency + 0.4 * support + 0.50 * rotation + 0.05 * fighting;
        }
       else {
            positionAbility = 0 * farm + 0.05 * independency + 0.75 * support + 0.1 * rotation + 0.10 * fighting;
        }

        return abilityResult((int)positionAbility);
    }


    protected String abilityResult(int positionAbility) {
        String ability = "G";
        int range = 5;
        int maxAbility = 91;

        if(positionAbility >= maxAbility)
            ability = "S";
        if(positionAbility >= maxAbility - range && positionAbility < maxAbility)
            ability = "A";
        if(positionAbility >= maxAbility - 2*range && positionAbility < maxAbility - range)
            ability = "B";
        if(positionAbility >= maxAbility - 3*range && positionAbility < maxAbility - 2*range)
            ability = "C";
        if(positionAbility >= maxAbility - 4*range && positionAbility < maxAbility - 3*range)
            ability = "D";
        if(positionAbility >= maxAbility - 5*range && positionAbility < maxAbility - 4*range)
            ability = "F";
        if(positionAbility >= maxAbility - 5*range && positionAbility < maxAbility - 4*range)
            ability = "G";

        return ability;
    }

    public int abilityInteger(int role) {

        double positionHability;

        if(role == 1) {
            positionHability = 0.85 * farm + 0 * independency + 0 * support + 0 * rotation + 0.15 * fighting;
        }
        else if(role == 2) {
            positionHability = 0.25 * farm + 0.05 * independency + 0 * support + 0.05 * rotation + 0.65 * fighting;
        }
        else if(role == 3) {
            positionHability = 0.15 * farm + 0.65 * independency + 0 * support + 0.05 * rotation + 0.15 * fighting;
        }
        else if(role == 4) {
            positionHability = 0 * farm + 0.05 * independency + 0.4 * support + 0.50 * rotation + 0.05 * fighting;
        }
        else {
            positionHability = 0 * farm + 0.05 * independency + 0.75 * support + 0.1 * rotation + 0.10 * fighting;
        }

        return (int)positionHability;
    }

    public boolean hasTeam(ArrayList<Team> teams) {
        if(id.equals("TEAM_USER"))
            return true;

        for(int i = 0; i < teams.size(); i++) {
            if(teams.get(i).getPlayers().size() != 0) {
                for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {
                    if(id.equals(teams.get(i).getPlayers().get(j).getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getSalary(ArrayList<Contract> contracts) {
        for(int i = 0; i < contracts.size(); i++) {
            if(id.equals(contracts.get(i).getPlayerId())) {
                return contracts.get(i).getSalary();
            }
        }

        int positions[] = {abilityInteger(1),abilityInteger(2),abilityInteger(3),abilityInteger(4),abilityInteger(5)};

        for(int i = 4; i >= 1; i--) {
            for( int j = 0; j < i; j++) {
                if(positions[j] > positions[j+1]) {
                    int buffer = positions[j];
                    positions[j] = positions[j+1];
                    positions[j+1] = buffer;
                }
            }

        }

        return (int) (Math.pow(Math.pow(1.09, positions[4]) + Math.pow(1.075, popularity), 1.22)*3/(Math.pow(1.035,200-1.1*positions[4]-0.9*popularity)) );
    }

    public int getRecomendedSalary(){
        int positions[] = {abilityInteger(1),abilityInteger(2),abilityInteger(3),abilityInteger(4),abilityInteger(5)};

        for(int i = 4; i >= 1; i--) {
            for( int j = 0; j < i; j++) {
                if(positions[j] > positions[j+1]) {
                    int buffer = positions[j];
                    positions[j] = positions[j+1];
                    positions[j+1] = buffer;
                }
            }

        }
        return (int) (Math.pow(Math.pow(1.09, positions[4]) + Math.pow(1.075, popularity), 1.22)*3/(Math.pow(1.035,200-1.1*positions[4]-0.9*popularity)) );

    }

    public int getCost(ArrayList<Contract> contracts) {
        for (int i = 0; i < contracts.size(); i++) {
            if (id.equals(contracts.get(i).getPlayerId())) {
                return contracts.get(i).getTransferFee();
            }
        }


        return 0;
    }

    public int getRecomendedCost() {
        int positions[] = {abilityInteger(1),abilityInteger(2),abilityInteger(3),abilityInteger(4),abilityInteger(5)};

        for(int i = 4; i >= 1; i--) {
            for( int j = 0; j < i; j++) {
                if(positions[j] > positions[j+1]) {
                    int buffer = positions[j];
                    positions[j] = positions[j+1];
                    positions[j+1] = buffer;
                }
            }

        }
        return (int) (Math.pow(Math.pow(1.12, positions[4]) + Math.pow(1.09, popularity), 1.25)*14/(Math.pow(1.035,200-positions[4]-popularity)) );
    }

    public String bestPosition() {

        for(int i = 1; i < 5; i++) {
            int loops = 0;
            for(int j = 1; j < 5; j++) {
                if (abilityInteger(i) >= abilityInteger(j + 1)) {
                    loops++;
                    if(loops == 4) {
                        if(i == 1)
                            return "Carry";
                        if(i == 2)
                            return "Mid";
                        if(i == 3)
                            return "Offlane";
                        if(i == 4)
                            return "Supp4";


                    }

                }
            }
        }

        return "Supp5";
    }
    public int bestPositionInteger() {

        for(int i = 1; i < 5; i++) {
            int loops = 0;
            for(int j = 1; j < 5; j++) {
                if (abilityInteger(i) >= abilityInteger(j + 1)) {
                    loops++;
                    if(loops == 4) {
                        if(i == 1)
                            return Position.CARRY;
                        if(i == 2)
                            return Position.MID;
                        if(i == 3)
                            return Position.OFFLANE;
                        if(i == 4)
                            return Position.SUPP4;

                    }

                }
            }
        }

        return Position.SUPP5;
    }

    public Team playerTeam(ArrayList<Team> teams) {
        for(int i = 0; i < teams.size(); i++ ) {
            for(int j = 0; j < teams.get(i).getPlayers().size(); j++) {
                if(teams.get(i).getPlayers().get(j).getId().equals(id) ) {
                    return teams.get(i);
                }
            }
        }
        return null;
    }

    public boolean isEqual(Player player) {
        if(player.getId().equals(id))
            return true;
        else
            return false;
    }

    public int getOverall() {
        for(int i = 1; i < 5; i++) {
            int loops = 0;
            for(int j = 1; j < 5; j++) {
                if (abilityInteger(i) >= abilityInteger(j + 1)) {
                    loops++;
                    if(loops == 4) {
                        if(i == 1)
                            return abilityInteger(1);
                        if(i == 2)
                            return abilityInteger(2);
                        if(i == 3)
                            return abilityInteger(3);
                        if(i == 4)
                            return abilityInteger(4);
                    }

                }
            }
        }

        return abilityInteger(5);
    }

}