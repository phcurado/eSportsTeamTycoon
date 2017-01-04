package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.model.Championship;
import com.paulocurado.esportsmanager.model.HandleSaveGame;
import com.paulocurado.esportsmanager.model.UsefulFunctions;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.uielements.ResultMatchDialog;
import com.paulocurado.esportsmanager.uielements.SimulateMatchDialog;
import com.paulocurado.esportsmanager.uielements.TipsDialog;
import com.paulocurado.esportsmanager.uielements.ReaderElements;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Paulo on 03/12/2016.
 */

public class GameScreen implements Screen {
    private final EsportsManager mainApp;

    public Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private ReaderElements gameMenuLayout;

    private TipsDialog tipsDialog;
    private ResultMatchDialog resultMatchDialog;
    private SimulateMatchDialog simulateMatchDialog;
    private GameScreenBox confirmationDialog;



    public TextureRegion[][] facesOptions;

    TextureRegion floorBackground;

    Label debitsLabel;

    public GameScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(EsportsManager.V_WIDTH, EsportsManager.V_HEIGHT, mainApp.camera);
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

        facesOptions = TextureRegion.split(mainApp.assets.get("img/facetextures.png", Texture.class), 32, 32);

        floorBackground = mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("floorBackground");


    }

    @Override
    public void show() {
        System.out.println("Game Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();



        gameMenuLayout = new ReaderElements(mainApp, stage, skin, "ui/GameScreen.json");
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        resultMatchDialog = new ResultMatchDialog(mainApp, skin, "ui/ResultMatch.json", this);
        simulateMatchDialog = new SimulateMatchDialog(mainApp, skin, "ui/SimulateMatch.json", this);
        confirmationDialog = new GameScreenBox(mainApp, skin, "ui/YesNoBox.json", this);

        tipsLogic(this);


        //buttons in screen logic
        saveButtonLogic();
        exitButtonLogic();
        championshipButtonLogic(this);
        leaderBoardsButtonLogic(this);
        lineupButtonLogic(this);


        if(mainApp.user.getTeam().getPlayers().size() >= 5) {
            for(int i = 1; i <= 5; i++) {
                ((Image)stage.getRoot().findActor("playerImage_" + Integer.toString(i))).setDrawable(mainApp.user.getTeam().getPlayers().get(i - 1).createPlayerFace(facesOptions, gamePort).getDrawable());
                ((Label)stage.getRoot().findActor("playerLabel_" + Integer.toString(i))).setText(mainApp.user.getTeam().getPlayers().get(i - 1).getNickName());
                ((Label)stage.getRoot().findActor("playerLabel_" + Integer.toString(i))).setAlignment(Align.center);
                stage.getRoot().findActor("playerLabel_" + Integer.toString(i)).setPosition(stage.getRoot().findActor("playerImage_" + Integer.toString(i)).getX() + stage.getRoot().findActor("playerImage_" + Integer.toString(i)).getWidth() / 2 -
                                stage.getRoot().findActor("playerLabel_" + Integer.toString(i)).getWidth() / 2,
                        stage.getRoot().findActor("playerImage_" + Integer.toString(i)).getTop() );
            }
        }



        continueTime();

        ((Label) stage.getRoot().findActor("timeLabel")).setText(Integer.toString(mainApp.schedule.getDay()) + "D" + " " +
                Integer.toString(mainApp.schedule.getWeek()) + "W" + " " +
                Integer.toString(mainApp.schedule.getMonth()) + "M" + " " +
                Integer.toString(mainApp.schedule.getYear()) + "Y");

        ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + Long.toString(mainApp.user.getTeam().getBudget()));

        ((Label) stage.getRoot().findActor("FanLabel")).setText(mainApp.bundle.get("Fans") + ": " +
                Integer.toString(mainApp.user.getFansNumber()));

        ((Label)stage.getRoot().findActor("TeamStrLabel")).setText(mainApp.bundle.get("Team_STR") + ": " +
                Integer.toString(mainApp.user.getTeam().getStrength()));

        debitsLabel = new Label("", skin, "labelRedGameScreen");
        debitsLabel.setPosition(stage.getRoot().findActor("budgetLabel").getX(), stage.getRoot().findActor("budgetLabel").getY() -
                stage.getRoot().findActor("budgetLabel").getHeight());
        stage.addActor(debitsLabel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getViewport().apply();

        drawBackground();

        stage.draw();
        tipsDialog.draw();
        tipsLogic(this);
        resultMatchDialog.draw();
        confirmationDialog.draw();
        simulateMatchDialog.draw();


        setChampionship();
        paySalaries();
        passTime();

    }

    private void drawBackground() {
        mainApp.batch.begin();
        for (int i = 0; i < mainApp.V_WIDTH / floorBackground.getRegionWidth() + 1; i++) {
            for (int j = 0; j < (mainApp.V_HEIGHT / floorBackground.getRegionHeight()) / 2 + 1; j++) {
                mainApp.batch.draw(floorBackground, i * floorBackground.getRegionWidth(), j * floorBackground.getRegionHeight());
            }
        }
        mainApp.batch.end();
    }

    private void passTime() {
        mainApp.schedule.passTime();
        if (mainApp.schedule.dayPassed()) {
            ((Label) stage.getRoot().findActor("timeLabel")).setText(Integer.toString(mainApp.schedule.getDay()) + "D" + " " +
                    Integer.toString(mainApp.schedule.getWeek()) + "W" + " " +
                    Integer.toString(mainApp.schedule.getMonth()) + "M" + " " +
                    Integer.toString(mainApp.schedule.getYear()) + "Y");

            ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + Long.toString(mainApp.user.getTeam().getBudget()));

            ((Label) stage.getRoot().findActor("FanLabel")).setText(mainApp.bundle.get("Fans") + ": " +
                    Integer.toString(mainApp.user.getFansNumber()));
        }
    }

    private void paySalaries() {
        if (mainApp.schedule.monthPassed()) {
            int debits = 0;
            for (int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i++) {
                debits += mainApp.user.getTeam().getPlayers().get(i).getSalary(mainApp.contractList);
            }
            debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("red")));
            mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() - debits);
            debitsLabel.setText("$ -" + Integer.toString(debits));
            debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
        }
    }

    private void setChampionship() {
        if (!mainApp.championship.isChampionshipIsUp()) {
            mainApp.championship.startChampionship();
        } else {
            mainApp.championship.defineMatchesDate();

            if (!mainApp.championship.isOrganizeTeams()) {
                mainApp.championship.updateTeamsMatches();
                mainApp.championship.setOrganizeTeams(true);
            } else if (mainApp.championship.isGroupStage()) {

                if (mainApp.championship.isMatchReady() && !mainApp.championship.isFinalsUp()) {


                    pauseTime();
                    if (!mainApp.championship.isUserMatchShowed()) {
                        mainApp.championship.groupMatches();
                        mainApp.setScreen(new SimulationScreen(mainApp, this, mainApp.championship.findBattleByTeam(mainApp.user.getTeam(),
                                mainApp.championship.getRoundsPlayed() ), 3));

                    }
                    if (mainApp.championship.isUserMatchShowed()) {
                        simulateMatchDialog.setUpDialog(mainApp.championship.findBattleByTeam(mainApp.user.getTeam(),
                                mainApp.championship.getRoundsPlayed()));

                        simulateMatchDialog.setVisibility(true);
                        resultMatchDialog.showRoundMatches(mainApp.championship.getMatchesPerRound(mainApp.championship.getRoundsPlayed()));
                        mainApp.championship.setMatchReady(false);
                        mainApp.championship.setUserMatchShowed(false);
                    }

                }
                else if (mainApp.championship.getGamesPlayed() >= mainApp.championship.getBattles().size()) {
                    mainApp.championship.setFinalsUp(true);
                }
            } else if (mainApp.championship.isFinalsUp()) {
                if(!mainApp.championship.isOrganizeTeamsFinal()) {
                    mainApp.championship.organizeFinalMatch();
                    mainApp.championship.setOrganizeTeamsFinal(true);
                }
                if (mainApp.championship.isMatchReady() && mainApp.championship.isOrganizeTeamsFinal()) {
                    pauseTime();
                    //ver e a partida foi mostrada e verificar se user ta na final
                    if(mainApp.championship.getBattles().get(
                            mainApp.championship.getBattles().size() - 1).getRadiantTeam().equals(mainApp.user.getTeam()) ||
                            (mainApp.championship.getBattles().get(
                                    mainApp.championship.getBattles().size() - 1).getDireTeam().equals(mainApp.user.getTeam()))) {
                        if (!mainApp.championship.isUserMatchShowed()) {
                            mainApp.setScreen(new SimulationScreen(mainApp, this, mainApp.championship.getBattles().get(
                                    mainApp.championship.getBattles().size() - 1), 5));
                        }
                        else {
                            mainApp.championship.setUserMatchShowed(false);
                         //   simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                           // simulateMatchDialog.setVisibility(true);
                            mainApp.championship.finishChampionship();
                        }
                    }
                    else {
                        mainApp.championship.setUserMatchShowed(false);
                        mainApp.championship.finalMatch();
                        simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                        simulateMatchDialog.setVisibility(true);
                        mainApp.championship.finishChampionship();
                    }

                }
                if(mainApp.championship.getWinnerOfChampionship() != null) {


                    if(mainApp.championship.getWinnerOfChampionship().equals(mainApp.user.getTeam())) {
                        if(mainApp.user.getTeam().getTier() != 1) {

                            ((Label)confirmationDialog.getActor("informationLabel")).setText(mainApp.bundle.format("Advance_Tier",
                                    mainApp.user.getTeam().getTier(), mainApp.user.getTeam().getTier() - 1));
                            confirmationDialog.getActor("YesButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    mainApp.user.getTeam().setTier(mainApp.user.getTeam().getTier() - 1);
                                    confirmationDialog.setVisibility(false);
                                    Gdx.input.setInputProcessor(stage);
                                    continueTime();
                                    debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("green")));
                                    debitsLabel.setText("$ +" + Integer.toString((int)mainApp.championship.payPrizeToUser()
                                    ));
                                    debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
                                }
                            });
                            confirmationDialog.getActor("NoButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    confirmationDialog.setVisibility(false);
                                    Gdx.input.setInputProcessor(stage);
                                    continueTime();
                                    debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("green")));
                                    debitsLabel.setText("$ +" + Integer.toString((int)mainApp.championship.payPrizeToUser()
                                    ));
                                    debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
                                }
                            });


                            simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                            simulateMatchDialog.setVisibility(true);

                        }
                    }

                }
            }
        }
    }

    public void continueTime() {
        mainApp.schedule.setTimePass(true);
    }

    public void pauseTime() {
        mainApp.schedule.setTimePass(false);
    }

    private void update(float delta) {
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
        skin.dispose();
        tipsDialog.dispose();
        resultMatchDialog.dispose();
        simulateMatchDialog.dispose();
        confirmationDialog.dispose();
        floorBackground.getTexture().dispose();
    }

    private void tipsLogic(final Screen parent) {
        if(!tipsDialog.isTeamPlayerRequirement()) {
            pauseTime();
            if(!tipsDialog.isVisible()) {
                tipsDialog.setVisibility(true);
                Gdx.input.setInputProcessor(tipsDialog.getStage());

                tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        mainApp.setScreen(new HireScreen(mainApp, parent));
                        tipsDialog.setVisibility(false);
                        continueTime();
                    }
                });
            }
        }

    }

    private void saveButtonLogic() {
        stage.getRoot().findActor("SaveButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                pauseTime();
                HandleSaveGame handler = new HandleSaveGame();
                handler.saveGame(mainApp);
                tipsDialog.setTip(mainApp.bundle.get("Game_Saved"));
                tipsDialog.setVisibility(true);


                tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        tipsDialog.setVisibility(false);
                        Gdx.input.setInputProcessor(stage);
                        continueTime();
                    }
                });
            }
        });
    }
    private void exitButtonLogic() {
        stage.getRoot().findActor("ExitButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    private void championshipButtonLogic(final Screen parent) {
        stage.getRoot().findActor("ChampionshipButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                pauseTime();
                mainApp.setScreen(new ChampionshipScreen(mainApp, parent));

            }
        });
    }

    private void leaderBoardsButtonLogic(final Screen parent) {
        stage.getRoot().findActor("LeaderBoardsButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                pauseTime();
                mainApp.setScreen(new HireScreen(mainApp, parent));
            }
        });
    }

    private void lineupButtonLogic(final Screen parent) {
        stage.getRoot().findActor("LineupButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                pauseTime();
                mainApp.setScreen(new LineupScreen(mainApp, parent));
            }
        });

    }


    public EsportsManager getMainApp() {
        return mainApp;
    }

    public Stage getStage() {
        return stage;
    }

    public TipsDialog getTipsDialog() {
        return tipsDialog;
    }

    public ResultMatchDialog getResultMatchDialog() {
        return resultMatchDialog;
    }

    public SimulateMatchDialog getSimulateMatchDialog() {
        return simulateMatchDialog;
    }

    public GameScreenBox getConfirmationDialog() {
        return confirmationDialog;
    }
}
