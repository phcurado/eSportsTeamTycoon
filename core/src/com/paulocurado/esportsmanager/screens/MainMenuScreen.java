package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulocurado.esportsmanager.model.Championship;
import com.paulocurado.esportsmanager.model.GameSchedule;
import com.paulocurado.esportsmanager.model.UsefulFunctions;
import com.paulocurado.esportsmanager.model.User;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.uielements.NewGameDialog;
import com.paulocurado.esportsmanager.uielements.ReaderElements;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.HandleSaveGame;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by Paulo on 09/11/2016.
 */

public class MainMenuScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private NewGameDialog newGameDialog;
    private GameScreenBox errorDialog;
    private GameScreenBox confirmDataDialog;
    private GameScreenBox aboutDialog;
    private GameScreenBox languageDialog;
    private com.paulocurado.esportsmanager.uielements.TipsDialog tipsDialog;

    ReaderElements mainMenuLayout;



    public MainMenuScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT , mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);



    }


    @Override
    public void show() {
        System.out.println("Start Screen");
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

        mainMenuLayout = new ReaderElements(mainApp, stage, skin, "ui/MainMenuScreen.json");
        newGameDialog = new NewGameDialog(mainApp, skin, "ui/NewGameBox.json", this);
        errorDialog = new GameScreenBox(mainApp, skin, "ui/ErrorBox.json", this);
        confirmDataDialog = new GameScreenBox(mainApp, skin, "ui/ConfirmData.json", this);
        aboutDialog = new GameScreenBox(mainApp, skin, "ui/HirePlayerBox.json", this);
        tipsDialog =  new com.paulocurado.esportsmanager.uielements.TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        languageDialog = new GameScreenBox(mainApp, skin, "ui/LanguageBox.json", this);



        mainMenuScreenButtonsClick();
        newGameDialogButtonsClick();
        errorDialogButtonsClick();
        confirmDataDialogButtonsClick();
        languageButtonClick();
        tipsDialog.defaultButtonClick(stage);

        languageDialogLogic();


        Actions actions = new Actions();

        stage.getRoot().findActor("logo").addAction(actions.forever(actions.sequence(actions.moveBy(0, 10, 2f), actions.moveBy(0, -10, 2f))) );
        stage.getRoot().findActor("cloud1").addAction(actions.forever(actions.sequence(actions.moveBy(30, 0, 7f), actions.moveBy(-30, 0, 7f))) );
        stage.getRoot().findActor("cloud2").addAction(actions.forever(actions.sequence(actions.moveBy(40, 0, 10f), actions.moveBy(-40, 0, 10f))) );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getCamera().update();
        stage.getViewport().apply();

        stage.draw();
        newGameDialog.draw();
        errorDialog.draw();
        confirmDataDialog.draw();
        aboutDialog.draw();
        tipsDialog.draw();
        languageDialog.draw();

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


    private void mainMenuScreenButtonsClick() {
        stage.getRoot().findActor("startButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                newGameDialog.setVisibility(true);
                Gdx.input.setInputProcessor(newGameDialog.getStage());
            }
        });

        stage.getRoot().findActor("loadButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(Gdx.files.local("Teams.json").exists() && Gdx.files.local("Players.json").exists() &&
                        Gdx.files.local("Contracts.json").exists() && Gdx.files.local("User.json").exists() &&
                        Gdx.files.local("Schedule.json").exists() && Gdx.files.local("Championship.json").exists()) {
                    HandleSaveGame handler = new HandleSaveGame();
                    handler.loadGame(mainApp);
                    mainApp.setScreen(mainApp.gameScreen);
                }
                else {
                    tipsDialog.setTip(mainApp.bundle.get("Error_Load_Game"));
                    tipsDialog.setVisibility(true);
                }

            }
        });

        stage.getRoot().findActor("exitButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });

    }

    private void newGameDialogButtonsClick() {
        newGameDialog.getActor("confirmButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {

                if(!newGameDialog.getError().equals("")) {
                    ((Label) errorDialog.getActor("errorLabel")).setText(newGameDialog.getError());
                    ((Label)errorDialog.getActor("errorLabel")).setText(errorDialog.cutText(((Label)errorDialog.getActor("errorLabel")), errorDialog.getActor("panel").getHeight()) );

                    errorDialog.setVisibility(true);
                    Gdx.input.setInputProcessor(errorDialog.getStage());
                }

                else {
                    Gdx.input.setInputProcessor(confirmDataDialog.getStage());
                    ((Label)confirmDataDialog.getActor("informationLabel")).setText( mainApp.bundle.get("SelectPlayerName") + ": " + ((TextField)newGameDialog.getActor("PlayerName")).getText() +
                            "\n" + mainApp.bundle.get("SelectTeamName") + ": " + ((TextField)newGameDialog.getActor("teamName")).getText() +
                            "\n" + mainApp.bundle.get("SelectShortName") + ": " + ((TextField)newGameDialog.getActor("teamShortName")).getText());


                    newGameDialog.setVisibility(false);
                    confirmDataDialog.setVisibility(true);
                }
            }
        });

        newGameDialog.getActor("cancelButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(stage);
                newGameDialog.setVisibility(false);
            }
        });
    }

    private void errorDialogButtonsClick() {
        errorDialog.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(newGameDialog.getStage());
                errorDialog.setVisibility(false);

            }
        });
        errorDialog.getActor("errorLabel").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                    ((Label) errorDialog.getActor("errorLabel")).setText(errorDialog.getText()[1]);

            }
        });
    }

    private void confirmDataDialogButtonsClick() {
        confirmDataDialog.getActor("confirmButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.user = new User(
                        ((TextField)newGameDialog.getActor("PlayerName")).getText(),
                        ((TextField)newGameDialog.getActor("teamName")).getText(),
                        ((TextField)newGameDialog.getActor("teamShortName")).getText());
                //informações básicas preenchidas para dar newGame
                UsefulFunctions usefulFunctions = new UsefulFunctions(mainApp);
                mainApp.user.getTeam().setId("TEAM_USER");
                mainApp.user.getTeam().setBudget(150000);
                mainApp.user.getTeam().setTier(4);
                mainApp.teamList.add(mainApp.user.getTeam());
                mainApp.schedule = new GameSchedule();
                mainApp.championship = new Championship(usefulFunctions.getTeamsByTier(mainApp.user.getTeam().getTier()),
                        mainApp);
                mainApp.facesOptions = TextureRegion.split(mainApp.assets.get("img/facetextures.png", Texture.class), 32, 32);

                mainApp.user.getTeam().getPlayers().add(usefulFunctions.findPlayerbyNickName("XBOCT"));
                usefulFunctions.createNewContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(0),
                        mainApp.user.getTeam().getPlayers().get(0).getRecomendedCost(), mainApp.user.getTeam().getPlayers().get(0).getRecomendedSalary());
                mainApp.user.getTeam().getPlayers().get(0).setTeamId(mainApp.user.getTeam().getId());

                mainApp.user.getTeam().getPlayers().add(usefulFunctions.findPlayerbyNickName("Wagamama"));
                usefulFunctions.createNewContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(1),
                        mainApp.user.getTeam().getPlayers().get(1).getRecomendedCost(), mainApp.user.getTeam().getPlayers().get(1).getRecomendedSalary());
                mainApp.user.getTeam().getPlayers().get(1).setTeamId(mainApp.user.getTeam().getId());

                mainApp.user.getTeam().getPlayers().add(usefulFunctions.findPlayerbyNickName("Bulldog"));
                usefulFunctions.createNewContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(2),
                        mainApp.user.getTeam().getPlayers().get(2).getRecomendedCost(), mainApp.user.getTeam().getPlayers().get(2).getRecomendedSalary());
                mainApp.user.getTeam().getPlayers().get(2).setTeamId(mainApp.user.getTeam().getId());

                mainApp.user.getTeam().getPlayers().add(usefulFunctions.findPlayerbyNickName("Fear"));
                usefulFunctions.createNewContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(3),
                        mainApp.user.getTeam().getPlayers().get(3).getRecomendedCost(), mainApp.user.getTeam().getPlayers().get(3).getRecomendedSalary());
                mainApp.user.getTeam().getPlayers().get(3).setTeamId(mainApp.user.getTeam().getId());

                mainApp.user.getTeam().getPlayers().add(usefulFunctions.findPlayerbyNickName("ArtStyle"));
                usefulFunctions.createNewContract(mainApp.user.getTeam(), mainApp.user.getTeam().getPlayers().get(4),
                        mainApp.user.getTeam().getPlayers().get(4).getRecomendedCost(), mainApp.user.getTeam().getPlayers().get(4).getRecomendedSalary());
                mainApp.user.getTeam().getPlayers().get(4).setTeamId(mainApp.user.getTeam().getId());

                mainApp.user.getTeam().organizeIdPlayers();


                mainApp.setScreen(mainApp.gameScreen);
            }
        });

        confirmDataDialog.getActor("cancelButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(newGameDialog.getStage());
                confirmDataDialog.setVisibility(false);
                newGameDialog.setVisibility(true);
            }
        });
    }

    private void languageButtonClick() {
        stage.getRoot().findActor("LanguageButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
            languageDialog.setVisibility(true);
            }
        });
    }

    private void languageDialogLogic() {
        languageDialog.getActor("EnglishButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {

                JsonParser jsonParser = new JsonParser();
                JsonObject config = (JsonObject)jsonParser.parse(Gdx.files.internal("database/Configuration.json").reader());
                Writer writerConfig = Gdx.files.local("Configuration.json").writer(false);
                config.addProperty("language", "");

                try {
                    writerConfig.write(config.toString());
                    writerConfig.close();
                } catch (IOException f) {
                    f.printStackTrace();
                }
                languageDialog.setVisibility(false);
                tipsDialog.setVisibility(true);
                tipsDialog.setTip(mainApp.bundle.get("Restart_App"));
                tipsDialog.defaultButtonClick(stage);
            }
        });

        languageDialog.getActor("PortugueseButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {

                JsonParser jsonParser = new JsonParser();
                JsonObject config = (JsonObject)jsonParser.parse(Gdx.files.internal("database/Configuration.json").reader());
                Writer writerConfig = Gdx.files.local("Configuration.json").writer(false);
                config.addProperty("language", "_pt_br");
                try {
                    writerConfig.write(config.toString());
                    writerConfig.close();
                } catch (IOException f) {
                    f.printStackTrace();
                }

                languageDialog.setVisibility(false);
                tipsDialog.setVisibility(true);
                tipsDialog.setTip(mainApp.bundle.get("Restart_App"));
                tipsDialog.defaultButtonClick(stage);
            }
        });

        languageDialog.getActor("BackButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                languageDialog.setVisibility(false);
                Gdx.input.setInputProcessor(stage);
            }
        });


    }


    public Stage getStage(){
        return stage;
    }
}
