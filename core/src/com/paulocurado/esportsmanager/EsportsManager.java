package com.paulocurado.esportsmanager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.I18NBundle;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.screens.LoadingScreen;

import java.util.ArrayList;
import java.util.Locale;

public class EsportsManager extends Game {

    public static final String TITLE = "eSports Manager";
    public static final float VERSION = 0.1f;
    public static final int V_WIDTH = 480;
    public static final int V_HEIGHT = 720;

    public ArrayList<Player> playerList;
    public ArrayList<Team> teamList;


    public OrthographicCamera camera;
    public SpriteBatch batch;
    public AssetManager assets;
    public I18NBundle bundle;

    public TextureRegion[][] facesOptions;

    public BitmapFont font;



    @Override
    public void create() {
        assets = new AssetManager();
        FileHandle baseFileHandle = Gdx.files.internal("languages/languages");
        Locale locale = new Locale("pt", "br");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);

        font = new BitmapFont();
        playerList = new ArrayList<Player>();
        teamList = new ArrayList<Team>();

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

    public Player findPlayerbyId(String playerId) {

        for(int i = 0; i < playerList.size(); i++) {
            if(playerId.equals(playerList.get(i).getId()))
                return playerList.get(i);
        }
        return null;
    }
}