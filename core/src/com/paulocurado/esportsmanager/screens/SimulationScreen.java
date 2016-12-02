package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;

    public SimulationScreen(EsportsManager mainApp) {
        this.mainApp = mainApp;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(mainApp.V_WIDTH, mainApp.V_HEIGHT, gameCam);

        stage = new Stage(gamePort, mainApp.batch);
        Gdx.input.setInputProcessor(stage);

        TextureAtlas atlas = mainApp.assets.get("ui/ui.atlas", TextureAtlas.class);
        Skin skin = new Skin();
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button_03");
        textButtonStyle.down = skin.getDrawable("button_01");
        textButtonStyle.font = mainApp.labelFont;
        button = new TextButton("Start", textButtonStyle);

//        Texture text = skin.get("button_01", Texture.class);
        stage.addActor(button);

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


        for(int i = 0; i < mainApp.playerList.size(); i++) {
            if(i < 15) {
                mainApp.labelFont.draw(mainApp.batch, mainApp.playerList.get(i).getNickName(), 80, 45 + 30 * i);
                mainApp.playerList.get(i).setPosition(50, 20 + 30 * i);
                mainApp.playerList.get(i).render(mainApp.facesOptions, mainApp.batch);
            }
            else {
                mainApp.labelFont.draw(mainApp.batch, mainApp.playerList.get(i).getNickName(), 230, 45 + 30 * i - 300);
                mainApp.playerList.get(i).setPosition(200, 20 + 30 * i  - 300);
                mainApp.playerList.get(i).render(mainApp.facesOptions, mainApp.batch);
            }
        }
        mainApp.labelFont.draw(mainApp.batch, "fps:"+Gdx.graphics.getFramesPerSecond(), gamePort.getWorldWidth() - 80, gamePort.getWorldHeight() - 20);
        mainApp.batch.end();

        stage.draw();

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
