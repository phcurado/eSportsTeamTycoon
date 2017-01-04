package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Position;
import com.paulocurado.esportsmanager.model.UsefulFunctions;
import com.paulocurado.esportsmanager.uielements.ReaderElements;

/**
 * Created by phcur on 31/12/2016.
 */

public class SellPlayersScreen implements Screen {
    private final EsportsManager mainApp;
    private final Screen parent;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private ReaderElements sellPlayersScreenLayout;

    private int playerSelected = 0;


    public SellPlayersScreen(final EsportsManager mainApp, final Screen parent) {
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
        this.skin.load(Gdx.files.internal("ui/ui.json"));
    }

    @Override
    public void show() {
        System.out.println("Positions Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        sellPlayersScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/SellPlayersScreen.json");

        setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(0));
        createSelectedButtons();


        backButtonClick();
        dismissButtonClick(this);

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
    private void createSelectedButtons() {
        for(int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i++) {
            final int j = i;
            TextButton playerButton = new TextButton(mainApp.user.getTeam().getPlayers().get(i).getNickName(), skin, "default");
            playerButton.setSize(190, 80);
            playerButton.setPosition(0, 650 - 80*i);

            playerButton.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(j));
                    playerSelected = j;
                }
            });
            stage.addActor(playerButton);

        }
    }

    private void setUpPlayerInformation(Player player) {
        ((Label) stage.getRoot().findActor("NickNameLabel")).setWrap(false);
        ((Label) stage.getRoot().findActor("NickNameLabel")).setText(player.getNickName());
        ((Image) stage.getRoot().findActor("faceImage")).setDrawable(player.createPlayerFace(((GameScreen)((LineupScreen)parent).getParent()).facesOptions, gamePort).getDrawable() );


        ((Label) stage.getRoot().findActor("FarmNumberLabel")).setText(Integer.toString(player.getFarm() ));
        ((Label) stage.getRoot().findActor("FightNumberLabel")).setText(Integer.toString(player.getFighting() ));
        ((Label) stage.getRoot().findActor("IndependenceNumberLabel")).setText(Integer.toString(player.getIndependency() ));
        ((Label) stage.getRoot().findActor("RotationNumberLabel")).setText(Integer.toString(player.getRotation() ));
        ((Label) stage.getRoot().findActor("SupportNumberLabel")).setText(Integer.toString(player.getSupport() ));


        ((Label) stage.getRoot().findActor("CarryAbilityLabel")).setText(player.hability(Position.CARRY) );
        ((Label) stage.getRoot().findActor("MidAbilityLabel")).setText(player.hability(Position.MID) );
        ((Label) stage.getRoot().findActor("OfflaneAbilityLabel")).setText(player.hability(Position.OFFLANE) );
        ((Label) stage.getRoot().findActor("Supp4AbilityLabel")).setText(player.hability(Position.SUPP4) );
        ((Label) stage.getRoot().findActor("Supp5AbilityLabel")).setText(player.hability(Position.SUPP5) );

        ((Label) stage.getRoot().findActor("CostNumberLabel")).setText(Integer.toString(player.getCost(mainApp.contractList)) );
        ((Label) stage.getRoot().findActor("SalaryNumberLabel")).setText(Integer.toString(player.getSalary(mainApp.contractList)) );


        ((Label) stage.getRoot().findActor("CostNumberLabel")).setAlignment(Align.right);
        ((Label) stage.getRoot().findActor("SalaryNumberLabel")).setAlignment(Align.right);
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
        skin.dispose();

    }

    private void dismissButtonClick(final Screen sellPlayersScreen) {
        stage.getRoot().findActor("DismissButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.user.getTeam().getPlayers().get(playerSelected).setTeamId("TEAM_0000");
                UsefulFunctions usefulFunctions = new UsefulFunctions(mainApp);
                usefulFunctions.removePlayerContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(playerSelected));
                mainApp.user.getTeam().getPlayers().remove(playerSelected);
                mainApp.user.getTeam().organizeIdPlayers();

                if(mainApp.user.getTeam().getPlayers().size() != 0) {
                    mainApp.setScreen(sellPlayersScreen);
                }
                else {
                    mainApp.setScreen(parent);
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
