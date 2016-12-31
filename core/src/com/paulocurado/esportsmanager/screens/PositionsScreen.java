package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.uielements.ButtonPlayer;
import com.paulocurado.esportsmanager.uielements.ReaderElements;

import java.util.ArrayList;

/**
 * Created by phcur on 30/12/2016.
 */

public class PositionsScreen implements Screen {
    private final EsportsManager mainApp;
    private final Screen parent;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private ReaderElements positionsScreenLayout;


    public PositionsScreen(final EsportsManager mainApp, final Screen parent) {
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

        positionsScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/PositionsScreen.json");


        backButtonClick();

        organizePlayersPositions();

    }

    private void organizePlayersPositions() {
        final ButtonGroup<ButtonPlayer> buttonGroup = new ButtonGroup<ButtonPlayer>();
        final ArrayList<Label> labelGroup = new ArrayList<Label>();
        buttonGroup.setMaxCheckCount(2);
        buttonGroup.setMinCheckCount(0);

        final Actions actions = new Actions();

        for (int i = 0; i < mainApp.user.getTeam().getPlayers().size(); i++) {
            final ButtonPlayer playerFaceButton = new ButtonPlayer(mainApp.user.getTeam().getPlayers().get(i).
                    createPlayerFace(((GameScreen)((LineupScreen)parent).getParent()).facesOptions, gamePort).getDrawable() );
            playerFaceButton.setRolePosition(i + 1);
            playerFaceButton.setSize(100, 100);
            playerFaceButton.setPosition(rolesPositions(i + 1).x, rolesPositions(i + 1).y);
            playerFaceButton.setOrigin(playerFaceButton.getWidth() / 2,
                    playerFaceButton.getY());
            buttonGroup.add(playerFaceButton);

            final Label playerNameLabel = new Label(mainApp.user.getTeam().getPlayers().get(i).getNickName(), skin, "labelVerySmallText");
            playerNameLabel.setPosition(playerFaceButton.getX() + playerFaceButton.getWidth() / 2 - playerNameLabel.getWidth() / 2,
                    playerFaceButton.getTop());
            labelGroup.add(playerNameLabel);

            playerFaceButton.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {

                    if(buttonGroup.getAllChecked().size > 1) {
                        for(ButtonPlayer buttonPlayer : buttonGroup.getButtons()) {
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


                        Label dummyLabel = playerNameLabel;
                        labelGroup.set(positionFaceButton - 1, labelGroup.get(buttonGroup.getChecked().getRolePosition() - 1));
                        labelGroup.set(buttonGroup.getChecked().getRolePosition() - 1, dummyLabel);

                        buttonGroup.getChecked().addAction(actions.moveTo(rolesPositions(positionFaceButton).x,
                                rolesPositions(positionFaceButton).y, 0.25f));
                        playerFaceButton.addAction(actions.moveTo(rolesPositions(buttonGroup.getChecked().getRolePosition()).x,
                                rolesPositions(buttonGroup.getChecked().getRolePosition()).y, 0.25f ));


                        ButtonPlayer buttonPlayer = playerFaceButton;
                        buttonGroup.getButtons().set(playerFaceButton.getRolePosition() - 1,
                                buttonGroup.getChecked());
                        buttonGroup.getButtons().set(buttonGroup.getChecked().getRolePosition() - 1, buttonPlayer);

                        for(int i = 0; i < buttonGroup.getButtons().size; i++) {
                            buttonGroup.getButtons().get(i).setRolePosition(i + 1);
                        }


                        playerFaceButton.setChecked(false);
                        buttonGroup.getChecked().setChecked(false);
                    }
                    else if(buttonGroup.getAllChecked().size == 1) {
                        playerFaceButton.addAction(actions.forever(actions.sequence(
                                actions.sizeBy(10, 10, 0.4f), actions.sizeBy(-10,-10, 0.4f))) );
                    }
                    else {
                        for(ButtonPlayer buttonPlayer : buttonGroup.getButtons()) {
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

    }

    private void backButtonClick() {
        stage.getRoot().findActor("BackButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(parent);
            }
        });
    }

}
