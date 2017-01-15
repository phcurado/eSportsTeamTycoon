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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.uielements.TipsDialog;

/**
 * Created by phcur on 08/01/2017.
 */

public class BuyScoutScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Image background;
    private Label scoutLevelLabel;

    private TextButton upgradeScoutButton;
    private TextButton backButton;
    private GameScreenBox yesNoDialog;
    TipsDialog tipsDialog;

    public BuyScoutScreen(final EsportsManager mainApp) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT, mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);

    }

    @Override
    public void show() {
        System.out.println("Buy Scout Screen");
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

        stage.addActor(background);

        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        yesNoDialog = new TipsDialog(mainApp, skin, "ui/YesNoBox.json", this);

        screenFirstTime();



        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");

        Table interfaceTable = new Table();
        interfaceTable.center();
        interfaceTable.setFillParent(true);
        //interfaceTable.add(upgradeScoutButton).expandX().fillX().padBottom(20).row();


        final ButtonGroup buttonGroup = new ButtonGroup();

        for(int i = 0; i < mainApp.user.getScout().MAX_LEVEL; i++) {
            final TextButton scoutLevelButton = new TextButton(mainApp.bundle.format("Scout_Level", i + 1) +
                    "       $ " + String.format("%,d", (int)(1.5 * i * mainApp.user.getScout().BASE_PRICE)), skin, "sectionBlack");
            scoutLevelButton.getLabel().setAlignment(Align.left);
            buttonGroup.add(scoutLevelButton);

            if (i + 1 == mainApp.user.getScout().getLevel()) {
                scoutLevelButton.setChecked(true);
            }

            interfaceTable.add(scoutLevelButton).expandX().fillX().row();

            final int interation = i;
            scoutLevelButton.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    if (interation + 1 < mainApp.user.getScout().getLevel()) {
                        tipsDialog.setTip(mainApp.bundle.get("Scout_Exist"));

                        tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                            public void clicked(InputEvent e, float x, float y) {
                                mainApp.setScreen(mainApp.buyScoutScreen);

                            }
                        });
                        tipsDialog.setVisibility(true);
                    }
                    else if (interation + 1 != mainApp.user.getScout().getLevel()) {
                        if (mainApp.user.getTeam().getBudget() <= 1.5 * interation * mainApp.user.getScout().BASE_PRICE) {
                            tipsDialog.setTip(mainApp.bundle.format("No_Money_Scout", String.format("%,d",
                                    mainApp.user.getTeam().getBudget())));

                            tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    mainApp.setScreen(mainApp.buyScoutScreen);

                                }
                            });
                            tipsDialog.setVisibility(true);
                        } else {
                            ((Label)yesNoDialog.getActor("informationLabel")).setText(
                                    mainApp.bundle.format("Confirmation_Scout",
                                            String.format("%,d", (int)(1.5 * interation * mainApp.user.getScout().BASE_PRICE)),
                                            String.format("%,d", mainApp.user.getTeam().getBudget() - (int)(1.5 * interation * mainApp.user.getScout().BASE_PRICE)))
                            );
                            ((TextButton)yesNoDialog.getActor("YesButton")).setText(mainApp.bundle.get("Confirm"));
                            ((TextButton)yesNoDialog.getActor("NoButton")).setText(mainApp.bundle.get("Cancel"));

                            yesNoDialog.getActor("YesButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() - (long)(1.5*interation * mainApp.user.getScout().BASE_PRICE));
                                    mainApp.user.getScout().setLevel(interation + 1);
                                    mainApp.setScreen(mainApp.buyScoutScreen);
                                }
                            });
                            yesNoDialog.getActor("NoButton").addListener(new ClickListener() {
                                public void clicked(InputEvent e, float x, float y) {
                                    mainApp.setScreen(mainApp.buyScoutScreen);

                                }
                            });
                            yesNoDialog.setVisibility(true);

                        }
                    }
                }
            });
        }

        interfaceTable.add(backButton).bottom().expandX().fillX();


        backButtonClick();


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
        tipsDialog.draw();
        yesNoDialog.draw();

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

    private void backButtonClick() {
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.scoutScreen);
            }
        });
    }

    private void screenFirstTime() {
        if (mainApp.user.buyScoutScreenFirstTime == true) {
            tipsDialog.setTip(mainApp.bundle.get("BuyScoutScreen_FirstTime"));
            tipsDialog.setVisibility(true);
            tipsDialog.defaultButtonClick(stage);
            mainApp.user.buyScoutScreenFirstTime = false;
        }
    }
}
