package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by phcur on 06/01/2017.
 */

public class ScoutScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Image background;
    private TextButton scoutReportButton;
    private TextButton buyScoutButton;
    private TextButton backButton;

    public ScoutScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);


    }

    @Override
    public void show() {
        System.out.println("Scout Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(mainApp.assets.get("ui/ui.atlas", TextureAtlas.class));
        this.skin.add("button-font", mainApp.buttonFont);
        this.skin.add("label-font", mainApp.labelFont);
        this.skin.add("label-small-font", mainApp.labelFontSmall);
        this.skin.add("position-font", mainApp.positionFont);
        this.skin.add("position-small-font", mainApp.positionSmallFont);
        this.skin.add("label-medium-font", mainApp.labelFontMedium);
        this.skin.add("label-clean-font", mainApp.cleanFont);
        this.skin.add("playerName-font", mainApp.playerNameFont);
        this.skin.load(Gdx.files.internal("ui/ui.json"));

        background = new Image(new TextureRegion(new TextureRegion(
                mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("defaultbackground"))) );
        background.setFillParent(true);

        scoutReportButton = new TextButton(mainApp.bundle.get("Scout_Report"), skin, "default");
        buyScoutButton = new TextButton(mainApp.bundle.get("Buy_Scout"), skin, "default");
        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");

        scoutReportButtonClick();
        buyScoutButtonClick();
        backButtonClick();

        Table interfaceTable = new Table();
        interfaceTable.center();
        interfaceTable.setFillParent(true);
        interfaceTable.add(scoutReportButton).expandX().fillX().padBottom(20).row();
        interfaceTable.add(buyScoutButton).expandX().fillX().padBottom(20).row();
        interfaceTable.add(backButton).expandX().fillX();


        stage.addActor(background);
        stage.addActor(interfaceTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getViewport().apply();



        stage.draw();

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

    private void scoutReportButtonClick() {
        scoutReportButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.scoutReportScreen);
            }
        });
    }

    private void buyScoutButtonClick() {
        buyScoutButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.buyScoutScreen);
            }
        });
    }

    private void backButtonClick() {
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.gameScreen);
            }
        });
    }

}
