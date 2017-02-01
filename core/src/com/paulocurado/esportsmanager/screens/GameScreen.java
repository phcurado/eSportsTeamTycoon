package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.paulocurado.esportsmanager.uielements.DuelDialog;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.uielements.ReaderElements;
import com.paulocurado.esportsmanager.uielements.ResultMatchDialog;
import com.paulocurado.esportsmanager.uielements.SimulateMatchDialog;
import com.paulocurado.esportsmanager.uielements.TipsDialog;
import com.paulocurado.esportsmanager.uielements.WinGameDialog;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
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
    private GameScreenBox yesNoDialog;
    private WinGameDialog winGameDialog;
    private DuelDialog duelDialog;
    private TextureRegion floorBackground;
    private Label debitsLabel;

    public boolean advanceTime = false;



    public GameScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(EsportsManager.V_WIDTH, EsportsManager.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);
    }

    @Override
    public void show() {
        System.out.println("Game Screen");
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

        floorBackground = mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("floorBackground");

        mainApp.adHandler.showAds(true);
        gameMenuLayout = new ReaderElements(mainApp, stage, skin, "ui/GameScreen.json");
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        resultMatchDialog = new ResultMatchDialog(mainApp, skin, "ui/ResultMatch.json", this);
        simulateMatchDialog = new SimulateMatchDialog(mainApp, skin, "ui/SimulateMatch.json", this);
        confirmationDialog = new GameScreenBox(mainApp, skin, "ui/ConfirmationTierBox.json", this);
        duelDialog = new DuelDialog(mainApp, skin, "ui/genericBox.json", this);
        yesNoDialog = new GameScreenBox(mainApp, skin, "ui/YesNoBox.json", this);
        winGameDialog = new WinGameDialog(mainApp, skin, "ui/WinGameBox.json", this);

        screenFirstTime();

        advanceTime = false; //define se avança ou não o tempo

        //buttons in screen logic
        saveButtonLogic();
        exitButtonLogic();
        advanceButtonLogic();
        championshipButtonLogic();
        leaderBoardsButtonLogic();
        lineupButtonLogic();
        scoutButtonLogic();
        trainButtonLogic();


        for (int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i ++) {
            Image playerImage = mainApp.user.getTeam().getPlayers().get(i).createPlayerFace(mainApp.facesOptions, gamePort);
            playerImage.setSize(100, 100);
            playerImage.setPosition(stage.getRoot().findActor("Computers").getX() + 12 + 122*i, stage.getRoot().findActor("Computers").getTop());

            Label playerNameLabel = new Label(mainApp.user.getTeam().getPlayers().get(i).getNickName(), skin, "default_PlayerName");
            playerNameLabel.setPosition(playerImage.getX() + playerImage.getWidth() / 2 - playerNameLabel.getWidth() / 2,
                    playerImage.getTop());

            stage.addActor(playerNameLabel);
            stage.addActor(playerImage);
        }

        ((Label) stage.getRoot().findActor("timeLabel")).setText(Integer.toString(mainApp.schedule.getDay()) + "D" + " " +
                Integer.toString(mainApp.schedule.getWeek()) + "W" + " " +
                Integer.toString(mainApp.schedule.getMonth()) + "M" + " " +
                Integer.toString(mainApp.schedule.getYear()) + "Y");

        ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

        ((Label) stage.getRoot().findActor("FanLabel")).setText(mainApp.bundle.get("Tier") + ": " +
                Integer.toString(mainApp.user.getTeam().getTier()));

        ((Label)stage.getRoot().findActor("TeamStrLabel")).setText(mainApp.bundle.get("Team_STR") + ": " +
                Integer.toString(mainApp.user.getTeam().getStrength()));

        debitsLabel = new Label("", skin, "labelRedGameScreen");
        debitsLabel.setSize(260, 10);
        debitsLabel.setPosition(stage.getRoot().findActor("budgetLabel").getX(), stage.getRoot().findActor("budgetLabel").getY() -
                stage.getRoot().findActor("budgetLabel").getHeight());
        debitsLabel.setAlignment(Align.right);
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

        resultMatchDialog.draw();
        confirmationDialog.draw();
        simulateMatchDialog.draw();
        duelDialog.draw();
        yesNoDialog.draw();
        tipsDialog.draw();
        winGameDialog.draw();


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
        continueTime();
        gameOverLogic();
        if (mainApp.schedule.dayPassed()) {
            ((Label) stage.getRoot().findActor("timeLabel")).setText(Integer.toString(mainApp.schedule.getDay()) + "D" + " " +
                    Integer.toString(mainApp.schedule.getWeek()) + "W" + " " +
                    Integer.toString(mainApp.schedule.getMonth()) + "M" + " " +
                    Integer.toString(mainApp.schedule.getYear()) + "Y");

            ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

            ((Label) stage.getRoot().findActor("FanLabel")).setText(mainApp.bundle.get("Tier") + ": " +
                    Integer.toString(mainApp.user.getTeam().getTier()));
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
            debitsLabel.setText("$ -" + String.format("%,d", debits));
            debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
            ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

        }
    }

    private void setChampionship() {
        if (!mainApp.championship.isChampionshipIsCloseStart) {
            mainApp.championship.closeChampionship();
            if(mainApp.championship.isChampionshipIsCloseStart) {
                advanceTime = false;
                tipsDialog.setTip(mainApp.bundle.format("Championship_Will_Start", mainApp.user.getTeam().getTier()));
                tipsDialog.setVisibility(true);
                tipsDialog.defaultButtonClick(stage);
            }
        }
        if (!mainApp.championship.isChampionshipIsUp()) {
            mainApp.championship.startChampionship();
        } else {
            mainApp.championship.defineMatchesDate();

            if (!mainApp.championship.isOrganizeTeams()) {
                mainApp.championship.updateTeamsMatches();
                mainApp.championship.setOrganizeTeams(true);
            } else if (mainApp.championship.isGroupStage()) {

                if (mainApp.championship.isMatchReady() && !mainApp.championship.isFinalsUp()) {
                    advanceTime = false;

                    if (!mainApp.championship.isUserMatchShowed()) {
                        if (!duelDialog.isLoadInformation() && !mainApp.championship.isDuelShowed()) {
                            mainApp.championship.groupMatches();
                            duelDialog.setUpPlayers(mainApp.championship.findBattleByTeam(mainApp.user.getTeam(),
                                    mainApp.championship.getRoundsPlayed()) );

                            System.out.println("o radiant team aqui: " + mainApp.championship.findBattleByTeam(mainApp.user.getTeam(),
                                    mainApp.championship.getRoundsPlayed()).getRadiantTeam().getName());
                            duelDialog.setVisibility(true);
                            duelDialog.buttonClick();
                            mainApp.championship.setDuelShowed(true);
                        }
                        else if (duelDialog.isLoadInformation() && mainApp.championship.isDuelShowed()) {
                            duelDialog.setLoadInformation(false);
                            mainApp.championship.setDuelShowed(false);
                            mainApp.adHandler.showAds(false);
                            mainApp.setScreen(new SimulationScreen(mainApp, this, mainApp.championship.findBattleByTeam(mainApp.user.getTeam(),
                                    mainApp.championship.getRoundsPlayed()), 3));
                        }
                    }
                    if (mainApp.championship.isUserMatchShowed()) {
                        resultMatchDialog.showRoundMatches(mainApp.championship.getMatchesPerRound(mainApp.championship.getRoundsPlayed()));
                        resultMatchDialog.setVisibility(true);
                        mainApp.championship.setMatchReady(false);
                        mainApp.championship.setUserMatchShowed(false);
                        mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (int)mainApp.championship.payPrizePerMatchToUser());
                        debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("green")));
                        debitsLabel.setText("$ +" + Integer.toString((int)mainApp.championship.payPrizePerMatchToUser()));
                        debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
                        ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

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
                    advanceTime = false;
                    //ver e a partida foi mostrada e verificar se user ta na final
                    if(mainApp.championship.getBattles().get(
                            mainApp.championship.getBattles().size() - 1).getRadiantTeam().equals(mainApp.user.getTeam()) ||
                            (mainApp.championship.getBattles().get(
                                    mainApp.championship.getBattles().size() - 1).getDireTeam().equals(mainApp.user.getTeam()))) {

                        if (!mainApp.championship.isUserMatchShowed()) {
                            if (!duelDialog.isLoadInformation() && !mainApp.championship.isDuelShowed()) {
                                duelDialog.setUpPlayers(mainApp.championship.getBattles().get(
                                        mainApp.championship.getBattles().size() - 1) );
                                duelDialog.setVisibility(true);
                                duelDialog.buttonClick();
                                mainApp.championship.setDuelShowed(true);
                            }
                            else if (duelDialog.isLoadInformation() && mainApp.championship.isDuelShowed()) {
                                duelDialog.setLoadInformation(false);
                                mainApp.championship.setDuelShowed(false);
                                mainApp.adHandler.showAds(false);
                                mainApp.setScreen(new SimulationScreen(mainApp, this, mainApp.championship.getBattles().get(
                                        mainApp.championship.getBattles().size() - 1), 5));
                            }
                        }
                        else {
                            mainApp.championship.setUserMatchShowed(false);
                            mainApp.championship.finalMatch();
                            simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                            simulateMatchDialog.setVisibility(true);
                            mainApp.championship.finishChampionship();
                            //mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (int)mainApp.championship.payPrizeToUser());
                            debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("green")));
                            debitsLabel.setText("$ +" + Integer.toString((int)mainApp.championship.payPrizeToUser()));
                            debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
                            ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

                        }
                    }
                    else {
                        mainApp.championship.setUserMatchShowed(false);
                        mainApp.championship.finalMatch();
                        simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                        simulateMatchDialog.setVisibility(true);
                        mainApp.championship.finishChampionship();
                        //mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() + (int)mainApp.championship.payPrizeToUser());
                        debitsLabel.setStyle(new Label.LabelStyle(skin.getFont("label-medium-font"), skin.getColor("green")));
                        debitsLabel.setText("$ +" + Integer.toString((int)mainApp.championship.payPrizeToUser()));
                        debitsLabel.addAction(sequence(alpha(0f), fadeIn(0.8f), delay(1f), fadeOut(1f)));
                        ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

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
                                  //  confirmationDialog.setVisibility(false);
                                  //  Gdx.input.setInputProcessor(stage);
                                    mainApp.setScreen(mainApp.championshipScreen);

                                }
                            });
                            confirmationDialog.getActor("NoButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                   // confirmationDialog.setVisibility(false);
                                    //Gdx.input.setInputProcessor(stage);
                                    mainApp.setScreen(mainApp.championshipScreen);

                                }
                            });

                        }
                        else {
                            System.out.println("HI");
                            winGameDialog.setText(mainApp.bundle.format("Win_Tier_1", mainApp.user.getTeam().getName()));
                            winGameDialog.setVisibility(true);
                            winGameDialog.defaultButtonClick(simulateMatchDialog.getStage());
                        }
                        if (!winGameDialog.isVisible()) {
                            simulateMatchDialog.setUpDialog(mainApp.championship.getFinalBattle());
                            simulateMatchDialog.setVisibility(true);
                        }
                    }
                    if (mainApp.championship.getFinalChampionshipPositions().get(
                            mainApp.championship.getFinalChampionshipPositions().size() - 1).equals(mainApp.user.getTeam())) {
                        if(mainApp.user.getTeam().getTier() != 4) {

                            ((Label)confirmationDialog.getActor("informationLabel")).setText(mainApp.bundle.format("Regress_Tier",
                                    mainApp.user.getTeam().getTier(), mainApp.user.getTeam().getTier() + 1));
                            confirmationDialog.getActor("YesButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    mainApp.user.getTeam().setTier(mainApp.user.getTeam().getTier() + 1);
                                    //confirmationDialog.setVisibility(false);
                                    //Gdx.input.setInputProcessor(stage);
                                    mainApp.setScreen(mainApp.championshipScreen);
                                }
                            });
                            confirmationDialog.getActor("NoButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                 //   confirmationDialog.setVisibility(false);
                                 //   Gdx.input.setInputProcessor(stage);
                                    mainApp.setScreen(mainApp.championshipScreen);

                                }
                            });

                        }

                    }
                    ((Label) stage.getRoot().findActor("budgetLabel")).setText("$ " + String.format("%,d", mainApp.user.getTeam().getBudget()));

                }
            }
        }
    }

    public void continueTime() {
        if(advanceTime) {
            if (!((TextButton)stage.getRoot().findActor("AdvanceButton")).getText().equals(mainApp.bundle.get("Stop"))) {
                ((TextButton) stage.getRoot().findActor("AdvanceButton")).setText(mainApp.bundle.get("Stop"));
            }
            mainApp.schedule.setTimePass(true);
        }
        else {
            if (!((TextButton)stage.getRoot().findActor("AdvanceButton")).getText().equals(mainApp.bundle.get("Advance"))) {
                ((TextButton) stage.getRoot().findActor("AdvanceButton")).setText(mainApp.bundle.get("Advance"));

            }
            mainApp.schedule.setTimePass(false);
        }
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
    }


    private void saveButtonLogic() {
        stage.getRoot().findActor("SaveButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                com.paulocurado.esportsmanager.model.HandleSaveGame handler = new com.paulocurado.esportsmanager.model.HandleSaveGame();
                handler.saveGame(mainApp);
                tipsDialog.setTip(mainApp.bundle.get("Game_Saved"));
                tipsDialog.setVisibility(true);


                tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        tipsDialog.setVisibility(false);
                        Gdx.input.setInputProcessor(stage);
                    }
                });
            }
        });
    }
    private void exitButtonLogic() {
        stage.getRoot().findActor("ExitButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                System.out.println(advanceTime);
                ((Label)yesNoDialog.getActor("informationLabel")).setText(mainApp.bundle.get("Exit_Confirmation"));
                yesNoDialog.setVisibility(true);
                yesNoDialog.getActor("YesButton").addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        Gdx.app.exit();
                    }
                });
                yesNoDialog.getActor("NoButton").addListener(new ClickListener() {
                    public void clicked(InputEvent e, float x, float y) {
                        yesNoDialog.setVisibility(false);
                        Gdx.input.setInputProcessor(stage);
                    }
                });
            }
        });

    }
    private void advanceButtonLogic() {
        stage.getRoot().findActor("AdvanceButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(mainApp.user.getTeam().getPlayers().size() < 5) {
                    tipsDialog.setTip(mainApp.bundle.get("Team_Missing_Player"));
                    tipsDialog.setVisibility(true);
                    tipsDialog.defaultButtonClick(stage);
                }
                else {
                    advanceTime = !advanceTime;
                    if (advanceTime) {
                        ((TextButton) stage.getRoot().findActor("AdvanceButton")).setText(mainApp.bundle.get("Stop"));
                    } else {
                        ((TextButton) stage.getRoot().findActor("AdvanceButton")).setText(mainApp.bundle.get("Advance"));
                    }
                }

            }
        });
    }

    private void championshipButtonLogic() {
        stage.getRoot().findActor("ChampionshipButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                mainApp.adHandler.showAds(false);
                mainApp.setScreen(mainApp.championshipScreen);

            }
        });
    }

    private void leaderBoardsButtonLogic() {
        stage.getRoot().findActor("LeaderBoardsButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                mainApp.adHandler.showAds(false);
                mainApp.setScreen(mainApp.hireScreen);
            }
        });
    }

    private void lineupButtonLogic() {
        stage.getRoot().findActor("LineupButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                mainApp.adHandler.showAds(false);
                mainApp.setScreen(mainApp.lineupScreen);
            }
        });
    }
    private void scoutButtonLogic() {
        stage.getRoot().findActor("ScoutButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                advanceTime = false;
                mainApp.adHandler.showAds(false);
                mainApp.setScreen(mainApp.scoutScreen);
            }
        });
    }

    private void trainButtonLogic() {
        stage.getRoot().findActor("TrainButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (mainApp.user.getTeam().getPlayers().size() < 5) {
                 advanceTime = false;
                    tipsDialog.setTip(mainApp.bundle.get("Team_Missing_Player"));
                    tipsDialog.setVisibility(true);
                }
                else {
                    advanceTime = false;
                    mainApp.adHandler.showAds(false);
                    mainApp.setScreen(mainApp.trainScreen);
                }

            }
        });
    }

    private void screenFirstTime() {
        if (mainApp.user.gameScreenFristTime == true) {
            tipsDialog.setTip(mainApp.bundle.format("GameScreen_FirstTime", mainApp.user.getTeam().getName()));
            tipsDialog.setVisibility(true);
            tipsDialog.defaultButtonClick(stage);
            mainApp.user.gameScreenFristTime = false;
        }
    }

    private void gameOverLogic() {
        if (mainApp.user.getTeam().getBudget() < 0) {
            advanceTime = false;
            tipsDialog.setTip(mainApp.bundle.get("Game_Over"));
            tipsDialog.setVisibility(true);
            System.out.println(tipsDialog.getActor("OkButton").getListeners().size);
            tipsDialog.getActor("OkButton").clearListeners();
            tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    mainApp.setScreen(mainApp.mainMenuScreen);
                }
            });
        }
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
