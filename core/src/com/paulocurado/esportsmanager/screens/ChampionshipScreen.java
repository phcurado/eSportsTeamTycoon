package com.paulocurado.esportsmanager.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

/**
 * Created by phcur on 26/12/2016.
 */

public class ChampionshipScreen implements Screen {
    private final EsportsManager mainApp;
    private final Screen parent;

    private Viewport gamePort;

    private Stage stage;
    private Skin skin;

    private TextButton backButton;


    public ChampionshipScreen(final EsportsManager mainApp, final Screen parent) {
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
        System.out.println("Championship Screen");
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        Image background = new Image(mainApp.assets.get("img/images.atlas", TextureAtlas.class).findRegion("inGameFloor") );
        background.setSize(gamePort.getWorldWidth(), gamePort.getWorldHeight());

        stage.addActor(background);

        setBackButton();

        Table table = new Table();

        for(int i = 0; i < mainApp.championship.getTeamsInOrderOfVictory().size(); i++) {
            if(!(2*i >= mainApp.championship.getTeamsInOrderOfVictory().size())) {
                Label teamName_Light = new Label(mainApp.championship.getTeamsInOrderOfVictory().get(2 * i).getName(), skin, "Label_Gray_Light");
                teamName_Light.setAlignment(Align.center);

                Label scoreLabel_Light = new Label(Integer.toString(mainApp.championship.getTeamsInOrderOfVictory().get(2 * i).getVictoriesChampionship()),
                        skin, "Label_Gray_Light");
                scoreLabel_Light.setAlignment(Align.center);

                table.add(teamName_Light).padBottom(2).fillX();
                table.add(scoreLabel_Light).padLeft(2).padBottom(2).fillX();
                table.row();
            }
            if(!(2*i + 1 >= mainApp.championship.getTeamsInOrderOfVictory().size())) {
                Label teamName_Dark = new Label(mainApp.championship.getTeamsInOrderOfVictory().get(2 * i + 1).getName(), skin, "Label_Gray_Dark");
                teamName_Dark.setAlignment(Align.center);

                Label scoreLabel_Dark = new Label(Integer.toString(mainApp.championship.getTeamsInOrderOfVictory().get(2 * i + 1).getVictoriesChampionship()),
                        skin, "Label_Gray_Dark");
                scoreLabel_Dark.setAlignment(Align.center);

                table.add(teamName_Dark).padBottom(2).fillX();
                table.add(scoreLabel_Dark).padLeft(2).padBottom(2).fillX();
                table.row();
            }

        }



        if (mainApp.championship.isOrganizeTeamsFinal()) {
            Table finalTable = new Table();
            Label teamRadiantLabel = new Label(mainApp.championship.getFinalBattle().getRadiantTeam().getName(), skin, "Label_Gray_Light");
            Label scoreRadiantLabel = new Label(Integer.toString(mainApp.championship.getFinalBattle().getRadiantVictories()), skin, "Label_Gray_Light");

            Label teamDireLabel = new Label(mainApp.championship.getFinalBattle().getDireTeam().getName(), skin, "Label_Gray_Light");
            Label scoreDireLabel = new Label(Integer.toString(mainApp.championship.getFinalBattle().getDireVictories()), skin, "Label_Gray_Light");

            Label versus = new Label(mainApp.bundle.get("VS"), skin, "default");
            finalTable.add(teamRadiantLabel).fillX();
            finalTable.add(scoreRadiantLabel).padLeft(2).fillX();
            finalTable.add(versus).padLeft(2).fillX();
            finalTable.add(scoreDireLabel).padLeft(2).fillX();
            finalTable.add(teamDireLabel).padLeft(2).fillX();

            finalTable.setPosition(mainApp.V_WIDTH / 2 - finalTable.getWidth() / 2, 900);
            stage.addActor(finalTable);
        }

        table.setPosition(150,500);



        stage.addActor(table);

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

    public void setBackButton() {
        backButton = new TextButton(mainApp.bundle.get("Back"), skin, "default");

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                mainApp.setScreen(parent); //TODO analisar se é melhor dar new Screen msm ou pegar o parent screen
            }
        });

    }
}
