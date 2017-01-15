package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.uielements.TipsDialog;
import com.paulocurado.esportsmanager.uielements.scout.ConfirmContractScoutDialog;
import com.paulocurado.esportsmanager.uielements.scout.ErrorScoutDialog;
import com.paulocurado.esportsmanager.uielements.scout.HireScoutedPlayerDialog;

import java.util.ArrayList;

/**
 * Created by phcur on 07/01/2017.
 */

public class ScoutReportScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private TextButton carryButton;
    private TextButton midButton;
    private TextButton offlaneButton;
    private TextButton roamerButton;
    private TextButton supportButton;

    private TextButton backButton;

    private HireScoutedPlayerDialog hireDialog;
    private ErrorScoutDialog errorScoutDialog;
    private ConfirmContractScoutDialog confirmContractScoutDialog;
    private TipsDialog tipsDialog;

    int position = 1;


    private Image background;
    public ScoutReportScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);


    }

    @Override
    public void show() {
        System.out.println("Scout Report Screen");
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



        mainApp.user.getScout().scoutAllPlayers(mainApp.playerList);

        background = new Image(new TextureRegion(new TextureRegion(
                mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("defaultbackground"))) );
        background.setFillParent(true);

        carryButton = new TextButton(mainApp.bundle.get("Carry"), skin, "toggle");
        midButton = new TextButton(mainApp.bundle.get("Mid"), skin, "toggle");
        offlaneButton = new TextButton(mainApp.bundle.get("Offlane"), skin, "toggle");
        roamerButton = new TextButton(mainApp.bundle.get("Supp4"), skin, "toggle");
        supportButton = new TextButton(mainApp.bundle.get("Supp5"), skin, "toggle");


        ButtonGroup buttonGroup = new ButtonGroup(carryButton, midButton, offlaneButton, roamerButton, supportButton);

        if (position == 1) {
            carryButton.setChecked(true);
        }
        else if (position == 2) {
            midButton.setChecked(true);
        }
        else if (position == 3) {
            offlaneButton.setChecked(true);
        }
        else if (position == 4) {
            roamerButton.setChecked(true);
        }
        else if (position == 5) {
            supportButton.setChecked(true);
        }

        Table interfaceTable = new Table();
        interfaceTable.top();
        interfaceTable.setFillParent(true);
        interfaceTable.add(carryButton).minWidth(30).expandX();
        interfaceTable.add(midButton).minWidth(30).expandX();
        interfaceTable.add(offlaneButton).minWidth(30).expandX();
        interfaceTable.add(roamerButton).minWidth(30).expandX();
        interfaceTable.add(supportButton).minWidth(30).expandX();


        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");
        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.bottom();
        backButtonTable.add(backButton).fillX().expandX();

        positionsButtonClick();
        backButtonClick();


        Table container = new Table();
        container.setFillParent(true);

        container.add(setUpScouts(position)).fillX().expandX();
        container.top();
        container.padTop(carryButton.getHeight()).padBottom(backButton.getHeight());

        stage.addActor(background);
        stage.addActor(interfaceTable);
        stage.addActor(backButtonTable);
        //stage.addActor(setUpScouts(1));
        stage.addActor(container);



        hireDialog = new HireScoutedPlayerDialog(mainApp, skin, "ui/HirePlayerBox.json", this);
        errorScoutDialog = new ErrorScoutDialog(mainApp, skin, "ui/ErrorBox.json", this);
        confirmContractScoutDialog = new ConfirmContractScoutDialog(mainApp, skin, "ui/ConfirmationBox.json", this);
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);

        screenFirstTime();


        errorDialogButtonsClick();

    }

    public Table setUpScouts(int position) {
        mainApp.user.getScout().scoutAllPlayers(mainApp.playerList);

        ArrayList<Player> players = new ArrayList<Player>();


        players.addAll(mainApp.user.getScout().getPlayersPerPosition(position));

        Table labelTable = new Table();
        Table playersContainer = new Table();

        Label nickNameTitleLabel = new Label(mainApp.bundle.get("Nickname"), skin, "tableLabelTitle2");
        Label teamTitleLabel = new Label(mainApp.bundle.get("Team"), skin, "tableLabelTitle2");
        Label positionTitleLabel = new Label(mainApp.bundle.get("Position"), skin, "tableLabelTitle2");
        Label abilityTitleLabel = new Label(mainApp.bundle.get("Ability_Short"), skin, "tableLabelTitle2");
        Label potentialTitleLabel = new Label(mainApp.bundle.get("Potential_Short"), skin, "tableLabelTitle2");


        nickNameTitleLabel.setWidth(165);
        teamTitleLabel.setWidth(165);
        positionTitleLabel.setWidth(140);
        abilityTitleLabel.setWidth(90);


        Table simpleTable = new Table();
        simpleTable.add(nickNameTitleLabel).size(nickNameTitleLabel.getWidth(), nickNameTitleLabel.getHeight());
        simpleTable.add(teamTitleLabel).size(teamTitleLabel.getWidth(), teamTitleLabel.getHeight());
        simpleTable.add(positionTitleLabel).size(positionTitleLabel.getWidth(), positionTitleLabel.getHeight());
        simpleTable.add(abilityTitleLabel).size(abilityTitleLabel.getWidth(), abilityTitleLabel.getHeight());
        simpleTable.add(potentialTitleLabel);

        playersContainer.add(simpleTable).expandX().fillX().row();


        for (int i = 0; i < players.size(); i++) {
            final Label nickNameLabel;
            Label teamLabel;
            Label positionLabel;
            Label abilityLabel;
            Label potentialLabel;
            if(i % 2 == 0) {
                nickNameLabel = new Label(players.get(i).getNickName(), skin, "tableLabel_Light");
                if(players.get(i).hasTeam(mainApp.teamList)) {
                    teamLabel = new Label(players.get(i).playerTeam(mainApp.teamList).getName(), skin, "tableLabel_Light");
                }
                else {
                    teamLabel = new Label("", skin, "tableLabel_Light");
                }
                positionLabel = new Label(players.get(i).bestPosition(), skin, "tableLabel_Light");
                abilityLabel = new Label(Integer.toString(players.get(i).getOverall()) + "%", skin, "tableLabel_Light");
                potentialLabel = new Label(Integer.toString(players.get(i).getOverall() +
                        players.get(i).getPotential()) + "%", skin, "tableLabel_Light");
            }
            else {
                nickNameLabel = new Label(players.get(i).getNickName(), skin, "tableLabel_Dark");
                if(players.get(i).hasTeam(mainApp.teamList)) {
                    teamLabel = new Label(players.get(i).playerTeam(mainApp.teamList).getName(), skin, "tableLabel_Dark");
                }
                else {
                    teamLabel = new Label("", skin, "tableLabel_Dark");
                }
                positionLabel = new Label(players.get(i).bestPosition(), skin, "tableLabel_Dark");
                abilityLabel = new Label(Integer.toString(players.get(i).getOverall()) + "%", skin, "tableLabel_Dark");
                potentialLabel = new Label(Integer.toString(players.get(i).getOverall() +
                        players.get(i).getPotential()) + "%", skin, "tableLabel_Dark");
            }

            labelTable.row().fillX();
            labelTable.add(nickNameLabel).width(nickNameTitleLabel.getWidth());
            labelTable.add(teamLabel).width(teamTitleLabel.getWidth());

            labelTable.add(positionLabel).width(positionTitleLabel.getWidth());
            labelTable.add(abilityLabel).width(abilityTitleLabel.getWidth());
            labelTable.add(potentialLabel).width(potentialTitleLabel.getWidth());

            final Player playerClicked = players.get(i);

            nickNameLabel.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    hireDialog.setUpDialog(playerClicked);
                    hireDialog.setVisibility(true);
                }
            });

        }
        ScrollPane scrollPlayers = new ScrollPane(labelTable, skin);
        scrollPlayers.setupOverscroll(0, 0, 0);

        playersContainer.add(scrollPlayers).fillX().expandX();

        return playersContainer;
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
        hireDialog.draw();
        errorScoutDialog.draw();
        confirmContractScoutDialog.draw();
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
    }

    private void positionsButtonClick() {
        carryButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(position != 1) {
                    position = 1;
                    mainApp.setScreen(mainApp.scoutReportScreen);
                }
            }
        });
        midButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(position != 2) {
                    position = 2;
                    mainApp.setScreen(mainApp.scoutReportScreen);                }
            }
        });
        offlaneButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(position != 3) {
                    position = 3;
                    mainApp.setScreen(mainApp.scoutReportScreen);                }
            }
        });
        roamerButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(position != 4) {
                    position = 4;
                    mainApp.setScreen(mainApp.scoutReportScreen);                }
            }
        });
        supportButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(position != 5) {
                    position = 5;
                    mainApp.setScreen(mainApp.scoutReportScreen);                }
            }
        });

    }
    private void backButtonClick() {
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.scoutScreen);
            }
        });
    }

    public Stage getStage() {
        return stage;
    }


    public HireScoutedPlayerDialog getHireDialog() {
        return hireDialog;
    }

    public ErrorScoutDialog getErrorScoutDialog() {
        return errorScoutDialog;
    }

    public ConfirmContractScoutDialog getConfirmContractScoutDialog() {
        return confirmContractScoutDialog;
    }

    public void errorDialogButtonsClick() {
        errorScoutDialog.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(hireDialog.getStage());
                errorScoutDialog.setVisibility(false);

            }
        });

    }

    public TipsDialog getTipsDialog() {
        return tipsDialog;
    }


    private void screenFirstTime() {
        if (mainApp.user.scoutReportScreenFirstTime == true) {
            tipsDialog.setTip(mainApp.bundle.get("ScoutReportScreen_FirstTime"));
            tipsDialog.setVisibility(true);
            tipsDialog.defaultButtonClick(stage);
            mainApp.user.scoutReportScreenFirstTime = false;
        }
    }
}
