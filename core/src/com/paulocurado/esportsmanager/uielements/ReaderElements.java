package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import com.badlogic.gdx.utils.Align;
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

    private JsonParser json;
    private JsonObject jsonObject;

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
            actorStyle(i);
        }

    }

    private void windowStyle(int position) {
        Image background = new Image(skin.getPatch(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ));


        Vector2 windowSize = new Vector2(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("x").getAsFloat(),
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("y").getAsFloat());

        Vector2 windowPosition = new Vector2(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("x").getAsFloat(),
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("y").getAsFloat());

        if(windowSize.y == 0) {
            background.setSize(windowSize.x, windowSize.x * background.getHeight() / background.getWidth());

        } else {
            background.setSize(windowSize.x, windowSize.y);
        }

        if (jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("orientation").getAsString().equals("center")) {
            background.setPosition(stage.getViewport().getWorldWidth() / 2 - background.getWidth() / 2 + windowPosition.x,
                    stage.getViewport().getWorldHeight() / 2 - background.getHeight() / 2 + windowPosition.y);

        } else {
            background.setPosition(windowPosition.x, windowPosition.y);
        }


        Label titleLabel = new Label(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("title").getAsString()), skin,
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString() );

        titleLabel.setPosition(background.getX() + background.getWidth() / 2 - titleLabel.getWidth() / 2, background.getY() + background.getHeight() - titleLabel.getHeight() - 10);


        stage.addActor(background);
        stage.addActor(titleLabel);
    }




    private void actorStyle(int position) {
        Actor actor;
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("window")) {
            actor = new Image(skin.getPatch(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ));
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("textfield")) {
            actor = new TextField("", skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
            actor = new Label(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("text").getAsString()), skin,
                    jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("textbutton")) {
            actor = new TextButton(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("text").getAsString()),
                    skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("panel")) {
            actor = new Image(skin.getPatch(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ));
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("image")) {
            actor = new Image(new TextureRegion(new TextureRegion(mainApp.assets.get(
                    jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("path").getAsString(), TextureAtlas.class)
                    .findRegion(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("image").getAsString())) ) );
        }
        else {
            actor = new Actor();
            System.out.println("Tipo de ator não encontrado");
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("uniqueName"))
            actor.setName(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("uniqueName").getAsString());


        Vector2 actorSize = new Vector2(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("x").getAsFloat(),
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("y").getAsFloat());

        Vector2 actorPosition = new Vector2(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("x").getAsFloat(),
                jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("position").get("y").getAsFloat());

        if(actorSize.x == 0 && actorSize.y == 0) {
            if(!jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label"))
                actor.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        }

        else if (actorSize.y == 0) {
            if(!jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label"))
                actor.setSize(actorSize.x, actorSize.x * actor.getHeight() / actor.getWidth());
        }

        else {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
                ((Label) actor).setWrap(true);
                ((Label) actor).setAlignment(Align.topLeft);

            }
                actor.setSize(actorSize.x, actorSize.y);


        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("orientation").getAsString().equals("center")) {
            actor.setPosition(stage.getViewport().getWorldWidth() / 2 - actor.getWidth() / 2 + actorPosition.x,
                    stage.getViewport().getWorldHeight() / 2 - actor.getHeight() / 2 + actorPosition.y);

        } else {
            actor.setPosition(actorPosition.x, actorPosition.y);
        }

        stage.addActor(actor);

    }

    public void dispose() {
        stage.dispose();
    }

}
