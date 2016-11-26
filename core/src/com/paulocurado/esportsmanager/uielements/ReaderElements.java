package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by Paulo on 25/11/2016.
 */

public class ReaderElements {
    private String path;
    private Stage stage;
    private Skin skin;

    JsonParser json;
    JsonObject jsonObject;

    EsportsManager mainApp;

    public ReaderElements(EsportsManager mainApp, Stage stage, Skin skin, String path) {
        this.mainApp = mainApp;
        this.path = path;

        this.stage = stage;
        this.skin = skin;


        reader();
    }

    private void reader() {
        json = new JsonParser();
        jsonObject = (JsonObject)json.parse(Gdx.files.internal(path).reader());

        for(int i = 0; i < jsonObject.get("ui").getAsJsonArray().size(); i++) {
            if(jsonObject.get("ui").getAsJsonArray().get(i).getAsJsonObject().get("type").getAsString().equals("window"))
                windowStyle(i);
            if(jsonObject.get("ui").getAsJsonArray().get(i).getAsJsonObject().get("type").getAsString().equals("text_field"))
                text_fieldStyle(i);
        }

    }

    private void windowStyle(int position) {
        Image background = new Image(new TextureRegion(skin.getRegion(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ) ) );

        float windowSize = jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("size").getAsFloat();
        background.setSize(windowSize, windowSize * background.getHeight() / background.getWidth() );

    if (jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("orientation").getAsString().equals("center")) {
            background.setPosition(stage.getViewport().getWorldWidth() / 2 - background.getWidth() / 2, stage.getViewport().getWorldHeight() / 2 - background.getHeight() / 2);

        } else {
            background.setPosition(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("x").getAsInt(),
                    jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("y").getAsInt() );
        }


        Label titleLabel = new Label(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("title").getAsString()), skin);
        titleLabel.setPosition(background.getX() + background.getWidth() / 2 - titleLabel.getWidth() / 2, background.getY() + background.getHeight() - titleLabel.getHeight());

        stage.addActor(background);
        stage.addActor(titleLabel);
    }

    private void text_fieldStyle(int position) {
        TextField textField = new TextField("", skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        Vector2 text_fieldSize = new Vector2(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("x").getAsFloat(),
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("y").getAsFloat());

        




    }

        public void render(float delta) {
            stage.draw();
    }
}
