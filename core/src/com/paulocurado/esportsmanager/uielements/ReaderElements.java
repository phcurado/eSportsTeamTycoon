package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

    private void actorStyle(int position) {
        Actor actor;
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("window")) {
            actor = new Image(skin.getPatch(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ));
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("textfield")) {
            actor = new TextField("", skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
            if (jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("text")) {
                actor = new Label(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("text").getAsString()), skin,
                        jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());

            }
            else {
                actor = new Label("", skin,
                        jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
            }

        }

        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("textbutton")) {
            actor = new TextButton(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("text").getAsString()),
                    skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }

        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("button")) {
            actor = new Button(skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());
        }

        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("imagetextbutton")) {
            actor = new TextButton(mainApp.bundle.get(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("text").getAsString()),
                    skin, jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("id").getAsString());

            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("labelOrientation")) {
                if (jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("labelOrientation").getAsString().equals("center")) {
                    ((TextButton) actor).getLabel().setAlignment(Align.center);
                }
            }
            else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("labelOrientation")) {
                if (jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("labelOrientation").getAsString().equals("right")) {
                    ((TextButton) actor).getLabel().setAlignment(Align.right);
                }
            }

            else {
                ((TextButton) actor).getLabel().setAlignment(Align.center);
            }



        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("panel")) {
            actor = new Image(skin.getPatch(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("background").getAsString() ));
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("image")) {
            actor = new Image(new TextureRegion(new TextureRegion(mainApp.assets.get(
                    jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("path").getAsString(), TextureAtlas.class)
                    .findRegion(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("image").getAsString())) ) );
        }
        else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("imageBlank")) {
            actor = new Image();
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
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
                ((Label) actor).setWrap(true);
                ((Label) actor).setAlignment(Align.topLeft);
                actor.setSize(((Label) actor).getWidth(), ((Label) actor).getHeight() );
            }
            else
                actor.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        }

        else if (actorSize.y == 0) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
                if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").has("orientation")) {
                    if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("orientation").getAsString().equals("center")) {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.center);
                        actor.setSize(actorSize.x, ((Label) actor).getHeight());
                    }
                    else if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("orientation").getAsString().equals("right")) {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.right);
                        actor.setSize(actorSize.x, ((Label) actor).getHeight());
                    }
                    else {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.topLeft);
                        actor.setSize(actorSize.x, ((Label) actor).getHeight());
                    }
                }
                else {
                    ((Label) actor).setWrap(true);
                    ((Label) actor).setAlignment(Align.topLeft);
                    actor.setSize(actorSize.x, ((Label) actor).getHeight());
                }

            }
            else
                actor.setSize(actorSize.x, actorSize.x * actor.getHeight() / actor.getWidth());
        }

        else {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("type").getAsString().equals("label")) {
                if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").has("orientation")) {
                    if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("orientation").getAsString().equals("center")) {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.center);
                        actor.setSize(actorSize.x, actorSize.y);
                    }
                    if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().getAsJsonObject("size").get("orientation").getAsString().equals("right")) {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.right);
                        actor.setSize(actorSize.x, ((Label) actor).getHeight());
                    }
                    else {
                        ((Label) actor).setWrap(true);
                        ((Label) actor).setAlignment(Align.topLeft);
                        actor.setSize(actorSize.x, actorSize.y);
                    }
                }
                else {
                    ((Label) actor).setWrap(true);
                    ((Label) actor).setAlignment(Align.topLeft);
                    actor.setSize(actorSize.x, actorSize.y);
                }
            }
                actor.setSize(actorSize.x, actorSize.y);
        }
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("sizeOf_X")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("sizeOf_X").getAsString().equals("Screen")) {
                actor.setWidth(stage.getWidth() );
            }
            else {

            }
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("below")) {
            actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("below").getAsString()).getX();
            actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("below").getAsString()).getY() -
                    actor.getHeight();
        }
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("alignLeft")) {
            actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignLeft").getAsString()).getX() +
                    stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignLeft").getAsString()).getWidth();
            actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignLeft").getAsString()).getY() - actor.getHeight() +
                    stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignLeft").getAsString()).getHeight();
        }



        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("alignX")) {
            actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignX").getAsString()).getX();
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("alignY")) {
            actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("alignY").getAsString()).getY();
        }



        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("inside_Top")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Top").getAsString().equals("Screen")) {
                actorPosition.y = stage.getHeight() - actor.getHeight();
            }
            else {
                actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Top").getAsString()).getTop() -
                        actor.getHeight();
            }
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("inside_Bottom")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Bottom").getAsString().equals("Screen")) {
                actorPosition.y = 0;
            }
            else {
                actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Bottom").getAsString()).getY();
            }
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("inside_Left")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Left").getAsString().equals("Screen")) {
                actorPosition.x = 0;
            }
            else {
                actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Left").getAsString()).getX();
            }
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("inside_Right")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Right").getAsString().equals("Screen")) {
                actorPosition.x = stage.getWidth() - actor.getWidth();
            }
            else {
                actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("inside_Right").getAsString()).getRight() -
                        actor.getWidth();
            }
        }


        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("centerX")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerX").getAsString().equals("Screen")) {
                actorPosition.x = stage.getWidth() / 2 - actor.getWidth() / 2;
            }
            else {
                actorPosition.x = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerX").getAsString()).getWidth() / 2 +
                        stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerX").getAsString()).getX() -
                        actor.getWidth() / 2;
            }
        }

        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("centerY")) {
            if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerY").getAsString().equals("Screen")) {
                actorPosition.y = stage.getHeight() / 2 - actor.getHeight() / 2;
            }
            else {
                actorPosition.y = stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerY").getAsString()).getY() +
                        stage.getRoot().findActor(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("centerY").getAsString()).getHeight() / 2 -
                        actor.getHeight() / 2;
            }
        }


        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("marginTop")) {
            actorPosition.y = actorPosition.y - jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("marginTop").getAsFloat();
        }
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("marginBottom")) {
            actorPosition.y = actorPosition.y + jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("marginBottom").getAsFloat();
        }
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("marginLeft")) {
            actorPosition.x = actorPosition.x + jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("marginLeft").getAsFloat();
        }
        if(jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().has("marginRight")) {
            actorPosition.x = actorPosition.x - jsonObject.get("ui").getAsJsonArray().get(position).getAsJsonObject().get("marginRight").getAsFloat();
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
        skin.dispose();
    }

}
