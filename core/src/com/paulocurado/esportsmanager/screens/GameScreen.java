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
import com.paulocurado.esportsmanager.uielements.TipsDialog;
import com.paulocurado.esportsmanager.uielements.ReaderElements;

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

    public TextureRegion[][] facesOptions;


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

        this.skin.load(Gdx.files.internal("ui/ui.json"));

        facesOptions = TextureRegion.split(mainApp.assets.get("img/facetextures.png", Texture.class), 32, 32);

        mainApp.teamList.add(mainApp.user.getTeam());
        mainApp.user.getTeam().setBudget(2000000);


    }

    @Override
    public void show() {
        System.out.println("Game Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        gameMenuLayout = new ReaderElements(mainApp, stage, skin, "ui/GameScreen.json");
        tipsDialog = new TipsDialog(mainApp, skin, "ui/informationBox.json", this);



        tipsLogic(this);

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


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        mainApp.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.getViewport().apply();

        stage.draw();
        tipsDialog.draw();

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
    }

    private void tipsLogic(final Screen parent) {
        if(!tipsDialog.isTeamPlayerRequirement()) {
            tipsDialog.setVisibility(true);
            Gdx.input.setInputProcessor(tipsDialog.getStage());

            tipsDialog.getActor("OkButton").addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    mainApp.setScreen(new HireScreen(mainApp, parent));
                    tipsDialog.setVisibility(false);
                }
            });

        }

        else {
            tipsDialog.setVisibility(false);
        }

    }

    public EsportsManager getMainApp() {
        return mainApp;
    }
}
