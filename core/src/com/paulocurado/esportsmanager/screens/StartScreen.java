package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by Paulo on 09/11/2016.
 */

public class StartScreen implements Screen {
    private EsportsManager mainApp;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public StartScreen(EsportsManager mainApp) {
        this.mainApp = mainApp;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(500,500, gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
