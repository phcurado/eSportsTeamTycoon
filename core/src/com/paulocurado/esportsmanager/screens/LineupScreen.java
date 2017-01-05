package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.uielements.ButtonPlayer;
import com.paulocurado.esportsmanager.uielements.ReaderElements;
import com.paulocurado.esportsmanager.uielements.TipsDialog;

import java.util.ArrayList;


/**
 * Created by phcur on 29/12/2016.
 */

public class LineupScreen implements Screen {
    private final EsportsManager mainApp;
    private final Screen parent;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private ReaderElements lineupScreenLayout;

    private TipsDialog tipsDialog;

    public Screen getParent() {
        return parent;
    }

    public LineupScreen(final EsportsManager mainApp, final Screen parent) {
        this.mainApp = mainApp;
        this.parent = parent;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);

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
    }

    @Override
    public void show() {
        System.out.println("Lineup Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        lineupScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/LineupScreen.json");
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);

        positionsButtonClick(this);
        sellPlayersButtonClick(this);
        tipsDialog.defaultButtonClick(stage);
        backButtonClick();
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
        tipsDialog.draw();
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
        tipsDialog.dispose();
        skin.dispose();
    }

    private void positionsButtonClick(final Screen lineupParent) {
        stage.getRoot().findActor("PositionsButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(mainApp.user.getTeam().getPlayers().size() >= 5) {
                    mainApp.setScreen(new PositionsScreen(mainApp, lineupParent));
                }
                else {
                    tipsDialog.setTip(mainApp.bundle.get("Tip_Minimum_5_Players"));
                    tipsDialog.setVisibility(true);
                }
            }
        });
    }

    private void sellPlayersButtonClick(final Screen lineupParent) {
        stage.getRoot().findActor("SellButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(mainApp.user.getTeam().getPlayers().size() != 0) {
                    mainApp.setScreen(new SellPlayersScreen(mainApp, lineupParent));
                }
                else {
                    tipsDialog.setTip(mainApp.bundle.get("Tip_No_Players"));
                    tipsDialog.setVisibility(true);
                }
            }
        });
    }

    private void backButtonClick() {
        stage.getRoot().findActor("BackButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(parent);
            }
        });
    }

}
