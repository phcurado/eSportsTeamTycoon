package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.model.simulation.PlayerInBattle;
import com.paulocurado.esportsmanager.model.simulation.SimulationBattle;


/**
 * Created by Paulo on 25/10/2016.
 */

public class SimulationScreen implements Screen {
    private final EsportsManager mainApp;
    private final Screen parent;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private TextButton battleButton;

    private SimulationBattle simulationBattle;

    private BattleSimulation battleSimulation;

    private int matchesToPlay;
    private boolean isBattleOver;
    private boolean isMatchReady;

    public SimulationScreen(final EsportsManager mainApp, final Screen parent, BattleSimulation battleSimulation, int matchesToPlay) {
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

        this.battleSimulation = battleSimulation;

        simulationBattle = new SimulationBattle(battleSimulation);
        this.matchesToPlay = matchesToPlay;

    }
    @Override
    public void show() {
        System.out.println("Simulation Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Image background = new Image(new TextureRegion(new TextureRegion(mainApp.assets.get(
                "img/images.atlas", TextureAtlas.class)
                .findRegion("startmenubackground"))) );
        background.setSize(stage.getWidth(), stage.getHeight());

        stage.addActor(background);

        battleButton = new TextButton(mainApp.bundle.get("Battle") + "!", skin, "default");

        battleButtonClick();


        Table interfaceTable = new Table();
        Table teamTable = new Table();

        Label timerLabel = new Label("0 " + mainApp.bundle.get("minutes"), skin, "default");
        timerLabel.setName("timerLabel");

        Label towersRadiantLabel = new Label(mainApp.bundle.get("Towers") + ": " +
                Integer.toString(simulationBattle.getAmbientInBattle().getRadiantTowersInBattle().size()), skin, "labelRedGameScreen");
        towersRadiantLabel.setName("towersRadiantLabel");

        Label towersDireLabel = new Label(mainApp.bundle.get("Towers") + ": " +
                Integer.toString(simulationBattle.getAmbientInBattle().getDireTowersInBattle().size()), skin, "labelRedGameScreen");
        towersDireLabel.setName("towersDireLabel");

        interfaceTable.add(timerLabel).expandX().center().row();

        teamTable.add(new Label(battleSimulation.getRadiantTeam().getName(), skin, "default") ).padLeft(3).fillX().expandX().row();
        teamTable.add(towersRadiantLabel).padLeft(3).padBottom(20).fillX().expandX().row();
        teamTable.add(new Label(mainApp.bundle.get("Nickname"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();
        teamTable.add(new Label(mainApp.bundle.get("Farm"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();
        teamTable.add(new Label(mainApp.bundle.get("KDA"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();

        for (int i = 0; i < simulationBattle.getRadiantPlayersInBattle().size(); i++ ) {
            Label radiantPlayerNameLabel = new Label(simulationBattle.getRadiantPlayersInBattle().get(i).getNickName(), skin, "labelDarkGraySimulation");

            Label radiantPlayerLastHitLabel = new Label(Integer.toString((int)simulationBattle.getRadiantPlayersInBattle().get(i).getLastHit()), skin, "labelDarkGraySimulation");
            radiantPlayerLastHitLabel.setName("radiantPlayerLastHitLabel_" + i);

            Label radiantPlayerKDALabel = new Label(Integer.toString(simulationBattle.getRadiantPlayersInBattle().get(i).getKills()), skin, "labelDarkGraySimulation");
            radiantPlayerKDALabel.setName("radiantPlayerKDALabel_" + i);

            teamTable.row().fillX();
            teamTable.add(radiantPlayerNameLabel).padLeft(3).padBottom(8).fillX().expandX();
            teamTable.add(radiantPlayerLastHitLabel).padLeft(3).padBottom(8).fillX().expandX();
            teamTable.add(radiantPlayerKDALabel).padLeft(3).padBottom(8).fillX().expandX();
        }
        teamTable.row();

        teamTable.add(new Label(battleSimulation.getDireTeam().getName(), skin, "default") ).padLeft(3).fillX().expandX().row();
        teamTable.add(towersDireLabel).padLeft(3).padBottom(20).fillX().expandX().row();
        teamTable.add(new Label(mainApp.bundle.get("Nickname"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();
        teamTable.add(new Label(mainApp.bundle.get("Farm"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();
        teamTable.add(new Label(mainApp.bundle.get("KDA"), skin, "labelTitleGraySimulation") ).padLeft(3).fillX().expandX();

        for (int i = 0; i < simulationBattle.getDirePlayersInBattle().size(); i++ ) {
            Label direPlayerNameLabel = new Label(simulationBattle.getDirePlayersInBattle().get(i).getNickName(), skin, "labelDarkGraySimulation");

            Label direPlayerLastHitLabel = new Label(Integer.toString((int)simulationBattle.getDirePlayersInBattle().get(i).getLastHit()), skin, "labelDarkGraySimulation");
            direPlayerLastHitLabel.setName("direPlayerLastHitLabel_" + i);

            Label direPlayerKDALabel = new Label(Integer.toString(simulationBattle.getDirePlayersInBattle().get(i).getKills()), skin, "labelDarkGraySimulation");
            direPlayerKDALabel.setName("direPlayerKDALabel_" + i);

            teamTable.row().fillX();
            teamTable.add(direPlayerNameLabel).padLeft(3).padBottom(8).fillX().expandX();
            teamTable.add(direPlayerLastHitLabel).padLeft(3).padBottom(8).fillX().expandX();
            teamTable.add(direPlayerKDALabel).padLeft(3).padBottom(8).fillX().expandX();
        }

        Table container = new Table();
        container.setSize(stage.getWidth() - 20, stage.getHeight());
        container.top();
        container.add(interfaceTable).fillX().expandX().row();
        //container.add(radiantTable).fill().expandX().row();
        container.add(teamTable).fillX().expandX().row();

        container.add(battleButton).fillX();

        stage.addActor(container);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getViewport().apply();


        if (simulationBattle.getAmbientInBattle().getRadiantTowersInBattle().size() !=0 &&
                simulationBattle.getAmbientInBattle().getDireTowersInBattle().size() != 0) {
            simulationBattle.getAmbientInBattle().setTimePass(true);

        }
        else {
            simulationBattle.getAmbientInBattle().setTimePass(false);

            if (battleSimulation.getRadiantVictories() < matchesToPlay / 2 + 1 &&
                    battleSimulation.getDireVictories() < matchesToPlay / 2 + 1) {
                isBattleOver = false;

            }
            else if (isBattleOver == false) {
                for(int i = 0; i < mainApp.championship.getBattles().size(); i++) {
                    if(mainApp.championship.getBattles().get(i).equals(battleSimulation)) {

                        if(battleSimulation.winner().equals(mainApp.championship.getBattles().get(i).getRadiantTeam())) {
                            mainApp.championship.getBattles().get(i).getRadiantTeam().setVictoriesChampionship(
                                    mainApp.championship.getBattles().get(i).getRadiantTeam().getVictoriesChampionship() + 1
                            );
                        }
                        else if (battleSimulation.winner().equals(mainApp.championship.getBattles().get(i).getDireTeam())) {
                            mainApp.championship.getBattles().get(i).getDireTeam().setVictoriesChampionship(
                                    mainApp.championship.getBattles().get(i).getDireTeam().getVictoriesChampionship() + 1
                            );
                        }

                    }
                }
                isBattleOver = true;
            }
        }

        if (!isBattleOver && isMatchReady) {
            battleButton.getStyle().checked = battleButton.getStyle().down;
            battleButton.setDisabled(true);

            simulationBattle.getAmbientInBattle().passTime();

            if (simulationBattle.getAmbientInBattle().minutePassed()) {
                simulationBattle.gameSimulation();

                ((Label) stage.getRoot().findActor("timerLabel")).setText(Integer.toString(simulationBattle.getAmbientInBattle().getMinute())
                        + " " + mainApp.bundle.get("minutes"));
                ((Label) stage.getRoot().findActor("towersRadiantLabel")).setText(
                        mainApp.bundle.get("Towers") + ": " + Integer.toString(simulationBattle.getAmbientInBattle().getRadiantTowersInBattle().size()) );
                ((Label) stage.getRoot().findActor("towersDireLabel")).setText(
                        mainApp.bundle.get("Towers") + ": " + Integer.toString(simulationBattle.getAmbientInBattle().getDireTowersInBattle().size()) );

                for (int i = 0; i < simulationBattle.getRadiantPlayersInBattle().size(); i++) {
                    ((Label) stage.getRoot().findActor("radiantPlayerLastHitLabel_" + i)).setText(
                            Integer.toString((int) simulationBattle.getRadiantPlayersInBattle().get(i).getLastHit())
                    );
                    ((Label) stage.getRoot().findActor("radiantPlayerKDALabel_" + i)).setText(
                            Integer.toString(simulationBattle.getRadiantPlayersInBattle().get(i).getKills()) + "/" +
                                    Integer.toString(simulationBattle.getRadiantPlayersInBattle().get(i).getDeaths())
                    );

                }
                for (int i = 0; i < simulationBattle.getDirePlayersInBattle().size(); i++) {
                    ((Label) stage.getRoot().findActor("direPlayerLastHitLabel_" + i)).setText(
                            Integer.toString((int) simulationBattle.getDirePlayersInBattle().get(i).getLastHit())
                    );
                    ((Label) stage.getRoot().findActor("direPlayerKDALabel_" + i)).setText(
                            Integer.toString(simulationBattle.getDirePlayersInBattle().get(i).getKills()) + "/" +
                                    Integer.toString(simulationBattle.getDirePlayersInBattle().get(i).getDeaths())
                    );
                }


                if (simulationBattle.getAmbientInBattle().getDireTowersInBattle().size() == 0) {
                    battleSimulation.setRadiantVictories(battleSimulation.getRadiantVictories() + 1);
                    isMatchReady = false;
                    battleButton.getStyle().checked = battleButton.getStyle().up;
                    battleButton.setDisabled(false);
                    battleButton.setText(mainApp.bundle.get("Battle") + "!");

                } else if (simulationBattle.getAmbientInBattle().getRadiantTowersInBattle().size() == 0) {
                    battleSimulation.setDireVictories(battleSimulation.getDireVictories() + 1);
                    isMatchReady = false;
                    battleButton.getStyle().checked = battleButton.getStyle().up;
                    battleButton.setDisabled(false);
                    battleButton.setText(mainApp.bundle.get("Battle") + "!");

                }
            }


        }
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
        skin.dispose();
    }

    private void battleButtonClick() {
        battleButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(!isMatchReady && !isBattleOver) {
                    simulationBattle.reset();
                    isMatchReady = true;
                    battleButton.setText(mainApp.bundle.get("Playing") + "...");
                }
                else if (isBattleOver) {
                    mainApp.setScreen(parent);
                    mainApp.championship.setUserMatchShowed(true);
                }

            }
        });
    }
}
