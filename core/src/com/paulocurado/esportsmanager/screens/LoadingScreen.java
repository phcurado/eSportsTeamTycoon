package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.I18NBundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.EsportsManager;

import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by Paulo on 03/11/2016.
 */

public class LoadingScreen implements Screen {

    private final EsportsManager mainApp;
    private ShapeRenderer shapeRenderer;
    private float progress;

    private String language;

    public LoadingScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        this.shapeRenderer = new ShapeRenderer();

        JsonParser jsonParser = new JsonParser();
        JsonObject config = (JsonObject)jsonParser.parse(Gdx.files.internal("database/Configuration.json").reader());

        language = config.get("language").getAsString();

        if (Gdx.files.local("Configuration.json").exists()) {
            config = (JsonObject) jsonParser.parse(Gdx.files.local("Configuration.json").reader());
        }
        language = config.get("language").getAsString();


    }

    @Override
    public void show() {
        System.out.println("Loading Screen");
        this.progress = 0f;
        queueAssets();

    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, mainApp.assets.getProgress(), .1f);
        if(mainApp.assets.update() && progress >= mainApp.assets.getProgress() - .1f) {

            mainApp.bundle =  mainApp.assets.get("languages/languages" + language, I18NBundle.class);

            mainApp.setScreen(mainApp.initScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.setProjectionMatrix(mainApp.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, mainApp.camera.viewportHeight / 2 - 8,mainApp.camera.viewportWidth - 64, 16);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, mainApp.camera.viewportHeight / 2 - 8, progress*(mainApp.camera.viewportWidth - 64), 16);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void queueAssets() {
        Reader reader = Gdx.files.internal("database/Players.json").reader();
        Gson gson = new GsonBuilder().create();
        mainApp.playerList.addAll((ArrayList<com.paulocurado.esportsmanager.model.Player>) gson.fromJson(reader,
                new TypeToken<ArrayList<com.paulocurado.esportsmanager.model.Player>>() {
                }.getType()));

        Reader readerTeam = Gdx.files.internal("database/Teams.json").reader();
        mainApp.teamList.addAll((ArrayList<Team>) gson.fromJson(readerTeam,
                new TypeToken<ArrayList<Team>>() {
                }.getType()));

        Reader readerContract = Gdx.files.internal("database/Contracts.json").reader();
        mainApp.contractList.addAll((ArrayList<com.paulocurado.esportsmanager.model.Contract>) gson.fromJson(readerContract,
                new TypeToken<ArrayList<com.paulocurado.esportsmanager.model.Contract>>() {
                }.getType()));


        JsonParser json = new JsonParser();
        Object arrayTeams = json.parse(Gdx.files.internal("database/Teams.json").reader());
        JsonArray jsonArray = (JsonArray) arrayTeams;

        for(int i = 0; i < mainApp.teamList.size(); i++) {
            JsonObject jsonObject = (JsonObject)jsonArray.get(i);
            if(jsonObject.get("playersId").getAsJsonArray().size() != 0) {
                for(int j = 0; j < jsonObject.get("playersId").getAsJsonArray().size(); j++) {
                    if(!jsonObject.get("playersId").getAsJsonArray().get(j).isJsonNull()) {
                        mainApp.teamList.get(i).getPlayers().add(mainApp.findPlayerbyId(jsonObject.get("playersId").getAsJsonArray().get(j).getAsString()));
                    }
                }
            }
        }


        mainApp.assets.load("img/simulationMap.png", Texture.class);
        mainApp.assets.load("img/facetextures.png", Texture.class);
        mainApp.assets.load("ui/ui.atlas", TextureAtlas.class);
        mainApp.assets.load("img/images.atlas", TextureAtlas.class);
        mainApp.assets.load("languages/languages" + language, I18NBundle.class);


    }
}
