package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by Paulo on 12/11/2016.
 */

public class NewGameDialog{
    public static float DIALOG_SIZE = 350f;
    public static float TEXT_FIELD_SIZE = DIALOG_SIZE / 2;

    private Label titleLabel;
    private TextField teamNameField;
    private TextButton but;

    public Stage stage;
    private Viewport gamePort;

    private Image background;
    private Skin skin;

    public boolean visible = false;


    ReaderElements reader;


    private EsportsManager mainApp;

    public NewGameDialog(EsportsManager mainApp, Skin skin) {
        this.mainApp = mainApp;
        gamePort = new FitViewport(mainApp.V_WIDTH , mainApp.V_HEIGHT , mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);
        this.skin = skin;

        reader = new ReaderElements(mainApp, stage, skin, "ui/newGameDialog.json");

        //setDialog();
    }

    public void render(float delta) {
        if(visible) {
            Gdx.input.setInputProcessor(stage);
            stage.draw();
            //reader.render(delta);
        }

    }

    public void setDialog() {
        TextureRegion region = new TextureRegion(skin.getRegion("window"));
        background = new Image(region);
        background.setSize(DIALOG_SIZE, DIALOG_SIZE * background.getHeight() / background.getWidth() );
        background.setPosition(gamePort.getWorldWidth() / 2 - background.getWidth() / 2, gamePort.getWorldHeight() / 2 - background.getHeight() / 2);


        titleLabel = new Label(mainApp.bundle.get("startButton"), skin);
        titleLabel.setPosition(background.getX() + background.getWidth() / 2 - titleLabel.getWidth() / 2, background.getY() + background.getHeight() - titleLabel.getHeight());


        teamNameField = new TextField("", skin, "default");
        teamNameField.setSize(TEXT_FIELD_SIZE, TEXT_FIELD_SIZE * teamNameField.getHeight() / teamNameField.getWidth() );
        teamNameField.setPosition(background.getX() + background.getWidth() / 2 - teamNameField.getWidth() / 2, background.getY() + background.getHeight() - 2*teamNameField.getHeight());


        stage.addActor(background);
        stage.addActor(titleLabel);
        stage.addActor(teamNameField);

    }



}
