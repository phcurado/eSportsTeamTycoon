package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Paulo on 29/11/2016.
 */

public class GameScreenBox {
    protected Stage stage;
    protected Viewport gamePort;
    protected Skin skin;

    protected boolean visibility;

    ReaderElements reader;

    String[] text = new String[2];

    protected com.paulocurado.esportsmanager.EsportsManager mainApp;
    protected final Screen root;


    public GameScreenBox(final com.paulocurado.esportsmanager.EsportsManager mainApp, Skin skin, String path, final Screen root) {
        this.mainApp = mainApp;
        this.root = root;
        gamePort = new FitViewport(mainApp.V_WIDTH, mainApp.V_HEIGHT , mainApp.camera);
        stage = new Stage(gamePort, mainApp.batch);
        this.skin = skin;
        visibility = false;


        reader = new ReaderElements(mainApp, stage, skin, path);
    }

    public void draw() {
        if (visibility) {
            stage.draw();
        }
    }

    public Actor getActor(String name) {
        return stage.getRoot().findActor(name);
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
        if(visibility == true)
            Gdx.input.setInputProcessor(stage);
    }

    public String cutText(Label label, float yMax) {
        int textSize = label.getText().length();
        text[1] = label.getText().toString();
            if (label.getText().length() >= yMax) {
                label.setText(label.getText().substring(0, (int) yMax));

                for (int i = label.getText().length - 1; i > 0; i--) {
                    if (label.getText().charAt(i) == ' ') {
                        text[0] = label.getText().substring(0, i);
                        text[1] = text[1].substring(i, textSize);
                        label.setText(label.getText().substring(0, i));
                        return label.getText().toString();
                    }
                }
            }

        return label.getText().toString();
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public void dispose() {
        stage.dispose();
    }
}
