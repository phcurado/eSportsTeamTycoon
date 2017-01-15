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
import com.paulocurado.esportsmanager.model.Player;
import com.paulocurado.esportsmanager.model.Position;
import com.paulocurado.esportsmanager.uielements.GameScreenBox;
import com.paulocurado.esportsmanager.uielements.ReaderElements;
import com.paulocurado.esportsmanager.uielements.TipsDialog;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


/**
 * Created by phcur on 10/01/2017.
 */

public class TrainScreen implements Screen {
    private final EsportsManager mainApp;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private Image background;
    private ReaderElements playersScreenLayout;

    private TextButton backButton;

    private TipsDialog tipsDialog;
    private GameScreenBox yesNoDialog;

    private static int MONEY_TRAIN = 40000;
    int playerSelectedPosition = 0;

    public TrainScreen(EsportsManager mainApp) {
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

        background = new Image(new TextureRegion(new TextureRegion(
                mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("defaultbackground"))) );
        background.setFillParent(true);
        stage.addActor(background);

        playersScreenLayout = new ReaderElements(mainApp, stage, skin, "ui/showPlayerCicle.json");

        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");
        backButton.setWidth(stage.getWidth());

        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);
        yesNoDialog = new TipsDialog(mainApp, skin, "ui/YesNoBox.json", this);


        screenFirstTime();

        setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(playerSelectedPosition));

        initPlusLabels();

        trainButtonLogic();
        backButtonClick();
        clickLeftLogic();
        clickRightLogic();

        stage.addActor(backButton);
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

    private void setUpPlayerInformation(Player player) {
        ((Label) stage.getRoot().findActor("NickNameLabel")).setWrap(false);
        ((Label) stage.getRoot().findActor("NickNameLabel")).setText(player.getNickName());
        ((Image) stage.getRoot().findActor("faceImage")).setDrawable(player.createPlayerFace(mainApp.facesOptions, gamePort).getDrawable() );


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

        ((Label) stage.getRoot().findActor("OverallNumberLabel")).setText(Integer.toString(player.getOverall()));
        ((Label) stage.getRoot().findActor("PotentialNumberLabel")).setText(Integer.toString(player.getOverall() + player.getPotential()) );

        ((Label) stage.getRoot().findActor("OverallNumberLabel")).setAlignment(Align.right);
        ((Label) stage.getRoot().findActor("PotentialNumberLabel")).setAlignment(Align.right);
    }

    private void backButtonClick() {
        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(mainApp.gameScreen);
            }
        });
    }

    private void screenFirstTime() {
        if (mainApp.user.trainScreenFirstTime == true) {
            tipsDialog.setTip(mainApp.bundle.get("TrainScreen_FirstTime"));
            tipsDialog.setVisibility(true);
            tipsDialog.defaultButtonClick(stage);
            mainApp.user.trainScreenFirstTime = false;
        }
    }

    private void clickLeftLogic() {
        stage.getRoot().findActor("LeftButton").setOrigin(stage.getRoot().findActor("LeftButton").getWidth() / 2,
                stage.getRoot().findActor("LeftButton").getHeight() / 2);
        stage.getRoot().findActor("LeftButton").addAction(forever(sequence(sizeTo(55, 55, 0.5f), sizeTo(45, 45, 0.5f))) );
        stage.getRoot().findActor("LeftButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (playerSelectedPosition == 0) {
                    playerSelectedPosition = mainApp.user.getTeam().getPlayers().size() - 1;
                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(playerSelectedPosition));
                }
                else {
                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(--playerSelectedPosition));
                }
            }
        });
    }
    private void clickRightLogic() {
        stage.getRoot().findActor("RightButton").setOrigin(stage.getRoot().findActor("RightButton").getWidth() / 2,
                stage.getRoot().findActor("RightButton").getHeight() / 2);
        stage.getRoot().findActor("RightButton").addAction(forever(sequence(sizeTo(55, 55, 0.5f), sizeTo(45, 45, 0.5f))) );
        stage.getRoot().findActor("RightButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (playerSelectedPosition == mainApp.user.getTeam().getPlayers().size() - 1) {
                    playerSelectedPosition = 0;
                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(playerSelectedPosition));
                }
                else {
                    setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(++playerSelectedPosition));
                }
            }
        });
    }

    private void trainButtonLogic() {
        stage.getRoot().findActor("TrainButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getPotential() > 0) {
                    if (mainApp.user.getTeam().getBudget() > MONEY_TRAIN) {

                        ((Label)yesNoDialog.getActor("informationLabel")).setText(mainApp.bundle.format("Confirmation_Train",
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getNickName(), MONEY_TRAIN));
                        yesNoDialog.setVisibility(true);

                        yesNoDialog.getActor("YesButton").addListener(new ClickListener() {
                            public void clicked(InputEvent e, float x, float y) {
                                yesNoDialog.getActor("YesButton").getListeners().removeIndex(1);
                                yesNoDialog.setVisibility(false);
                                Gdx.input.setInputProcessor(stage);
                                mainApp.user.getTeam().setBudget(mainApp.user.getTeam().getBudget() - MONEY_TRAIN);

                                System.out.println(playerSelectedPosition);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setPotential(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getPotential() - 1);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setFarm(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getFarm() + 1);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setFighting(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getFighting() + 1);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setIndependency(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getIndependency() + 1);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setRotation(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getRotation() + 1);
                                mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).setSupport(
                                        mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getSupport() + 1);


                                stage.getRoot().findActor("PlusFarmNumberLabel").setVisible(true);
                                stage.getRoot().findActor("PlusFarmNumberLabel").addAction(sequence(alpha(0f), fadeIn(0.5f), delay(1.2f), fadeOut(1f)));

                                stage.getRoot().findActor("PlusFightNumberLabel").setVisible(true);
                                stage.getRoot().findActor("PlusFightNumberLabel").addAction(sequence(alpha(0f), fadeIn(0.5f), delay(1.2f), fadeOut(1f)));

                                stage.getRoot().findActor("PlusIndependenceNumberLabel").setVisible(true);
                                stage.getRoot().findActor("PlusIndependenceNumberLabel").addAction(sequence(alpha(0f), fadeIn(0.5f), delay(1.2f), fadeOut(1f)));
                                //stage.getRoot().findActor("IndependenceNumberLabel").setVisible(false);

                                stage.getRoot().findActor("PlusRotationNumberLabel").setVisible(true);
                                stage.getRoot().findActor("PlusRotationNumberLabel").addAction(sequence(alpha(0f), fadeIn(0.5f), delay(1.2f), fadeOut(1f)));

                                stage.getRoot().findActor("PlusSupportNumberLabel").setVisible(true);
                                stage.getRoot().findActor("PlusSupportNumberLabel").addAction(sequence(alpha(0f), fadeIn(0.5f), delay(1.2f), fadeOut(1f)));

                                setUpPlayerInformation(mainApp.user.getTeam().getPlayers().get(playerSelectedPosition));
                            }
                        });
                        yesNoDialog.getActor("NoButton").addListener(new ClickListener() {
                            public void clicked(InputEvent e, float x, float y) {
                                yesNoDialog.setVisibility(false);
                                Gdx.input.setInputProcessor(stage);

                            }
                        });


                    }
                    else {
                        tipsDialog.setTip(mainApp.bundle.format("No_Money_Train", MONEY_TRAIN, mainApp.user.getTeam().getPlayers().get(playerSelectedPosition).getNickName()));
                        tipsDialog.setVisibility(true);
                        tipsDialog.defaultButtonClick(stage);
                    }
                }

                else {
                    tipsDialog.setTip(mainApp.bundle.get("Tip_Max_Potential"));
                    tipsDialog.setVisibility(true);
                    tipsDialog.defaultButtonClick(stage);
                }

            }
        });
    }

    private void initPlusLabels() {
        ((Label)stage.getRoot().findActor("PlusFarmNumberLabel")).setText("+1");
        ((Label)stage.getRoot().findActor("PlusFightNumberLabel")).setText("+1");
        ((Label)stage.getRoot().findActor("PlusIndependenceNumberLabel")).setText("+1");
        ((Label)stage.getRoot().findActor("PlusRotationNumberLabel")).setText("+1");
        ((Label)stage.getRoot().findActor("PlusSupportNumberLabel")).setText("+1");


        stage.getRoot().findActor("PlusFarmNumberLabel").setVisible(false);
        stage.getRoot().findActor("PlusFightNumberLabel").setVisible(false);
        stage.getRoot().findActor("PlusIndependenceNumberLabel").setVisible(false);
        stage.getRoot().findActor("PlusRotationNumberLabel").setVisible(false);
        stage.getRoot().findActor("PlusSupportNumberLabel").setVisible(false);

        ((TextButton)yesNoDialog.getActor("YesButton")).setText(mainApp.bundle.get("Confirm"));
        ((TextButton)yesNoDialog.getActor("NoButton")).setText(mainApp.bundle.get("Cancel"));
    }
}
