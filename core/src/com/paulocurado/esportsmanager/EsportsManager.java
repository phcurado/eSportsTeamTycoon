package com.paulocurado.esportsmanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.paulocurado.esportsmanager.model.Championship;
import com.paulocurado.esportsmanager.model.Contract;
import com.paulocurado.esportsmanager.model.GameSchedule;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.model.User;
import com.paulocurado.esportsmanager.screens.LoadingScreen;


import java.util.ArrayList;

public class EsportsManager extends Game {

    public static final String TITLE = "eSports Manager";
    public static final float VERSION = 0.1f;
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 960;

    public ArrayList<Player> playerList;
    public ArrayList<Team> teamList;
    public ArrayList<Contract> contractList;

    public User user;
    public GameSchedule schedule;
    public Championship championship;


    public OrthographicCamera camera;
    public SpriteBatch batch;
    public AssetManager assets;
    public I18NBundle bundle;

    public TextureRegion[][] facesOptions;

    public BitmapFont buttonFont;
    public BitmapFont labelFont;
    public BitmapFont labelFontSmall;
    public BitmapFont positionFont;
    public BitmapFont positionSmallFont;
    public BitmapFont labelFontMedium;
    public BitmapFont cleanFont;


    @Override
    public void create() {
        assets = new AssetManager();

        initFonts();
        playerList = new ArrayList<Player>();
        teamList = new ArrayList<Team>();
        contractList = new ArrayList<Contract>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        batch = new SpriteBatch();
        this.setScreen(new LoadingScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
        this.getScreen().dispose();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 3;
        buttonFont = generator.generateFont(parameter);

        FreeTypeFontGenerator generatorLabel = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 30;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        labelFont = generatorLabel.generateFont(parameter);

        FreeTypeFontGenerator generatorLabelMedium = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 25;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        labelFontMedium = generatorLabel.generateFont(parameter);

        FreeTypeFontGenerator generatorLabelSmall = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 22;
        parameter.borderWidth = 0;
        parameter.spaceX = 2;
        parameter.spaceY = 2;
        parameter.color = Color.WHITE;
        labelFontSmall = generatorLabelSmall.generateFont(parameter);

        FreeTypeFontGenerator generatorPosition = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        parameter.size = 24;
        parameter.borderWidth = 2;
        parameter.color = Color.WHITE;
        positionFont = generatorPosition.generateFont(parameter);

        FreeTypeFontGenerator generatorSmallPosition = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 11;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        positionSmallFont = generatorSmallPosition.generateFont(parameter);

        FreeTypeFontGenerator generatorCleanFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Neuton-Extrabold.ttf"));
        parameter.size = 28;
        parameter.borderWidth = 0;
        parameter.color = Color.WHITE;
        cleanFont = generatorCleanFont.generateFont(parameter);

    }

    public Player findPlayerbyId(String playerId) {

        for(int i = 0; i < playerList.size(); i++) {
            if(playerId.equals(playerList.get(i).getId()))
                return playerList.get(i);
        }
        return null;
    }

    public Player findPlayerbyNickName(String NickName) {

        for(int i = 0; i < playerList.size(); i++) {
            if(NickName.equals(playerList.get(i).getNickName()))
                return playerList.get(i);
        }
        return null;
    }
}