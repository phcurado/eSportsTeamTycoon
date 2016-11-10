package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;


/**
 * Created by Paulo on 25/10/2016.
 */

public class SimulationScreen implements Screen {

    private EsportsManager mainApp;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private Stage stage;
    BattleSimulation battleSimulation;
    Texture background;
    Texture faces;

    public SimulationScreen(EsportsManager mainApp) {
        this.mainApp = mainApp;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(500,500, gameCam);

        stage = new Stage(gamePort, mainApp.batch);
    }
    @Override
    public void show() {
        System.out.println("Entrou na Simulation Screen");

        background = mainApp.assets.get("simulationMap.png", Texture.class);
        faces = mainApp.assets.get("facetextures.png", Texture.class);

        battleSimulation = new BattleSimulation(mainApp.teamList.get(0), mainApp.teamList.get(1));
        mainApp.facesOptions = TextureRegion.split(faces, 32, 32);

    }
    private void update(float delta) {
        battleSimulation.setPositions();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        mainApp.batch.setProjectionMatrix(gameCam.combined);
        mainApp.batch.begin();
        mainApp.batch.setColor(Color.WHITE);
        stage.getBatch().draw(background, 0, 0, gamePort.getWorldWidth(), gamePort.getWorldHeight());

        //for(int i = 0; i < 5; i++) {
       //     mainApp.teamList.get(0).getPlayers().get(i).render(mainApp.facesOptions, mainApp.batch);
       // }

        for(int i = 0; i < mainApp.playerList.size(); i++) {
            if(i < 15) {
                mainApp.font.draw(mainApp.batch, mainApp.playerList.get(i).getNickName(), 80, 45 + 30 * i);
                mainApp.playerList.get(i).setPosition(50, 20 + 30 * i);
                mainApp.playerList.get(i).render(mainApp.facesOptions, mainApp.batch);
            }
            else {
                mainApp.font.draw(mainApp.batch, mainApp.playerList.get(i).getNickName(), 230, 45 + 30 * i - 300);
                mainApp.playerList.get(i).setPosition(200, 20 + 30 * i  - 300);
                mainApp.playerList.get(i).render(mainApp.facesOptions, mainApp.batch);
            }
        }

        mainApp.font.draw(mainApp.batch, "fps:"+Gdx.graphics.getFramesPerSecond(), 50, 50);
        mainApp.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

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
        background.dispose();
        faces.dispose();

    }
}
