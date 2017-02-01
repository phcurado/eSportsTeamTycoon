package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
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
import com.paulocurado.esportsmanager.uielements.ReaderElements;
import com.paulocurado.esportsmanager.uielements.TipsDialog;


import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sizeBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sizeTo;

/**
 * Created by phcur on 30/12/2016.
 */

public class PositionsScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private ReaderElements positionsScreenLayout;
    private ReaderElements playersScreenLayout;

    private TextButton backButton;

    private TipsDialog tipsDialog;


    public PositionsScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);


    }

    @Override
    public void show() {
        System.out.println("Positions Screen");
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

        playersScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/positionPlayerInformationBox.json");
        positionsScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/PositionsScreen.json");

        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");
        backButton.setWidth(stage.getWidth());

        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);

        screenFirstTime();

        setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(0));

        backButtonClick();

        organizePlayersPositions();

        stage.addActor(backButton);

    }

    private void organizePlayersPositions() {
        final ButtonGroup<com.paulocurado.esportsmanager.uielements.ButtonPlayer> buttonGroup = new ButtonGroup<com.paulocurado.esportsmanager.uielements.ButtonPlayer>();
        final ArrayList<Label> labelGroup = new ArrayList<Label>();
        buttonGroup.setMaxCheckCount(2);
        buttonGroup.setMinCheckCount(0);


        for (int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i++) {
            final com.paulocurado.esportsmanager.uielements.ButtonPlayer playerFaceButton = new com.paulocurado.esportsmanager.uielements.ButtonPlayer(mainApp.user.getTeam().getPlayers().get(i).
                    createPlayerFace(mainApp.facesOptions, gamePort).getDrawable() );
            playerFaceButton.setRolePosition(i + 1);
            playerFaceButton.setSize(100, 100);
            playerFaceButton.setPosition(rolesPositions(i + 1).x, rolesPositions(i + 1).y);
            playerFaceButton.setOrigin(playerFaceButton.getWidth() / 2,
                    playerFaceButton.getHeight() / 2);
            buttonGroup.add(playerFaceButton);

            final Label playerNameLabel = new Label(mainApp.user.getTeam().getPlayers().get(i).getNickName(), skin, "default_PlayerName");
            playerNameLabel.setPosition(playerFaceButton.getX() + playerFaceButton.getWidth() / 2 - playerNameLabel.getWidth() / 2,
                    playerFaceButton.getTop());
            labelGroup.add(playerNameLabel);
            //final int j = i;
            playerFaceButton.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {

                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(playerFaceButton.getRolePosition() - 1));

                    if(buttonGroup.getAllChecked().size > 1) {

                        for(com.paulocurado.esportsmanager.uielements.ButtonPlayer buttonPlayer : buttonGroup.getButtons()) {
                            buttonPlayer.clearActions();
                            buttonPlayer.setSize(100, 100);
                            buttonPlayer.setOrigin(buttonPlayer.getWidth() / 2, buttonPlayer.getHeight() / 2);
                        }
                        Player player = mainApp.user.getTeam().getPlayers().get(playerFaceButton.getRolePosition() - 1);
                        mainApp.user.getTeam().getPlayers().set((playerFaceButton.getRolePosition() - 1),
                                mainApp.user.getTeam().getPlayers().get(buttonGroup.getChecked().getRolePosition() - 1));
                        mainApp.user.getTeam().getPlayers().set(buttonGroup.getChecked().getRolePosition() - 1, player);
                        mainApp.user.getTeam().organizeIdPlayers();


                        int positionFaceButton  = playerFaceButton.getRolePosition();


                        labelGroup.get(buttonGroup.getChecked().getRolePosition() - 1).setPosition(playerFaceButton.getX() +
                                        playerFaceButton.getWidth() / 2 - labelGroup.get(buttonGroup.getChecked().getRolePosition() - 1).getWidth() / 2,
                                rolesPositions(positionFaceButton).y + playerFaceButton.getHeight());

                        playerNameLabel.setPosition(buttonGroup.getChecked().getX() +
                                        buttonGroup.getChecked().getWidth() / 2 - playerNameLabel.getWidth() / 2, //width
                                rolesPositions(buttonGroup.getChecked().getRolePosition()).y + buttonGroup.getChecked().getHeight());


                        labelGroup.set(positionFaceButton - 1, labelGroup.get(buttonGroup.getChecked().getRolePosition() - 1));
                        labelGroup.set(buttonGroup.getChecked().getRolePosition() - 1, playerNameLabel);

                        buttonGroup.getChecked().addAction(moveTo(rolesPositions(positionFaceButton).x,
                                rolesPositions(positionFaceButton).y, 0.25f));
                        playerFaceButton.addAction(moveTo(rolesPositions(buttonGroup.getChecked().getRolePosition()).x,
                                rolesPositions(buttonGroup.getChecked().getRolePosition()).y, 0.25f ));


                        //ButtonPlayer buttonPlayer = playerFaceButton;
                        buttonGroup.getButtons().set(playerFaceButton.getRolePosition() - 1,
                                buttonGroup.getChecked());
                        buttonGroup.getButtons().set(buttonGroup.getChecked().getRolePosition() - 1, playerFaceButton);

                        for(int i = 0; i < buttonGroup.getButtons().size; i++) {
                            buttonGroup.getButtons().get(i).setRolePosition(i + 1);
                        }


                        playerFaceButton.setChecked(false);
                        buttonGroup.getChecked().setChecked(false);
                    }
                    else if(buttonGroup.getAllChecked().size == 1) {
                        playerFaceButton.setTransform(true);
                        playerFaceButton.addAction(forever(sequence(sizeBy(10f, 10f, 0.4f), sizeBy(-10f, -10f, 0.4f)) ));

                    }
                    else {
                        for(com.paulocurado.esportsmanager.uielements.ButtonPlayer buttonPlayer : buttonGroup.getButtons()) {
                            //playerFaceButton.setTransform(false);
                            buttonPlayer.clearActions();
                            buttonPlayer.setSize(100, 100);
                            buttonPlayer.setOrigin(buttonPlayer.getWidth() / 2, buttonPlayer.getHeight() / 2);
                        }
                    }

                }
            });

            stage.addActor(playerFaceButton);
            stage.addActor(playerNameLabel);
        }
    }

    public Vector2 rolesPositions(int position) {
        Vector2 vector2 = new Vector2(0, 0);

        for(int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i++) {
            if (position - 1 == i) {
                Actor playerBox = stage.getRoot().findActor("PlayerBox_" + Integer.toString(i + 1));
                vector2.x = playerBox.getX() + playerBox.getWidth() / 2 - 50;
                vector2.y = playerBox.getY() + 20;
                return vector2;
            }


        }

        return vector2;
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
    }
    private void setUpPlayerInformation(Player player) {
        ((Label) stage.getRoot().findActor("NickNameLabel")).setWrap(false);
        ((Label) stage.getRoot().findActor("NickNameLabel")).setText(player.getNickName());
        ((Image) stage.getRoot().findActor("faceImage")).setDrawable(player.createPlayerFace(mainApp.facesOptions, gamePort).getDrawable() );


        ((Label) stage.getRoot().findActor("FarmNumberLabel")).setText(Integer.toString(player.getFarm() ));
        ((Label) stage.getRoot().findActor("FightNumberLabel")).setText(Integer.toString(player.getFighting() ));
        ((Label) stage.getRoot().findActor("IndependenceNumberLabel")).setText(Integer.toString(player.getIndependency() ));
        ((Label) stage.getRoot().findActor("RotationNumberLabel")).setText(Integer.toString(player.getRotation() ));
        ((Label) stage.getRoot().findActor("SupportNumberLabel")).setText(Integer.toString(player.getSupport() ));


        ((Label) stage.getRoot().findActor("CarryAbilityLabel")).setText(player.hability(com.paulocurado.esportsmanager.model.Position.CARRY) );
        ((Label) stage.getRoot().findActor("MidAbilityLabel")).setText(player.hability(com.paulocurado.esportsmanager.model.Position.MID) );
        ((Label) stage.getRoot().findActor("OfflaneAbilityLabel")).setText(player.hability(com.paulocurado.esportsmanager.model.Position.OFFLANE) );
        ((Label) stage.getRoot().findActor("Supp4AbilityLabel")).setText(player.hability(com.paulocurado.esportsmanager.model.Position.SUPP4) );
        ((Label) stage.getRoot().findActor("Supp5AbilityLabel")).setText(player.hability(com.paulocurado.esportsmanager.model.Position.SUPP5) );

        ((Label) stage.getRoot().findActor("CostNumberLabel")).setText(Integer.toString(player.getCost(mainApp.contractList)) );
        ((Label) stage.getRoot().findActor("SalaryNumberLabel")).setText(Integer.toString(player.getSalary(mainApp.contractList)) );


        ((Label) stage.getRoot().findActor("CostNumberLabel")).setAlignment(Align.right);
        ((Label) stage.getRoot().findActor("SalaryNumberLabel")).setAlignment(Align.right);
    }

    private void backButtonClick() {
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.lineupScreen);
            }
        });
    }

    private void screenFirstTime() {
        if (mainApp.user.positionScreenFirstTime == true) {
            tipsDialog.setTip(mainApp.bundle.get("PositionsScreen_FirstTime"));
            tipsDialog.setVisibility(true);
            tipsDialog.defaultButtonClick(stage);
            mainApp.user.positionScreenFirstTime = false;
        }
    }

}
