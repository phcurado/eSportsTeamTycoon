package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Paulo on 14/11/2016.
 */

public class InitScreen implements Screen {
    private final EsportsManager mainApp;
    private Viewport gamePort;

    private Image logoCompany;
    private Stage stage;

    private static float LOGO_SIZE = 150f;

    public InitScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT , mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);

    }

    @Override
    public void show() {
        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                mainApp.setScreen(new MainMenuScreen(mainApp));
            }
        };

        logoCompany = new Image(new TextureRegion(mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("logoCompany")) );
        logoCompany.setSize(LOGO_SIZE, LOGO_SIZE * logoCompany.getHeight() / logoCompany.getWidth());
        logoCompany.setPosition(stage.getWidth() / 2 - logoCompany.getWidth() / 2, stage.getHeight() / 2 - logoCompany.getHeight() / 2 );
        logoCompany.addAction(sequence(alpha(0f), fadeIn(1.3f), delay(1.5f), fadeOut(1f), run(transitionRunnable)) );
        stage.addActor(logoCompany);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);

        stage.draw();

    }

    public void update(float delta) {
        stage.act(delta);
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
        stage.dispose();

    }
}
