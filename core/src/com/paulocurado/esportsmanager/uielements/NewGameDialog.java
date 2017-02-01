package com.paulocurado.esportsmanager.uielements;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by Paulo on 12/11/2016.
 */

public class NewGameDialog extends GameScreenBox {

    private String error;

    public NewGameDialog(EsportsManager mainApp, Skin skin, String path, Screen parent) {
        super(mainApp, skin, path, parent);

        error = "";
    }

    public Actor getActor(String name) {
        return stage.getRoot().findActor(name);
    }

    public String getError() {
        error = "";
        if (((TextField) stage.getRoot().findActor("PlayerName")).getText().equals("") || ((TextField) stage.getRoot().findActor("teamName")).getText() == null) {
            error += mainApp.bundle.get("Error_PlayerName_Caracter_Zero") + "\n";
        }
        if (((TextField) stage.getRoot().findActor("PlayerName")).getText().length() > 14) {
            error += mainApp.bundle.get("Error_PlayerName_Caracter_Bigger") + "\n";
        }
        if (((TextField) stage.getRoot().findActor("teamName")).getText().equals("") || ((TextField) stage.getRoot().findActor("teamName")).getText() == null) {
            error += mainApp.bundle.get("Error_TeamName_Caracter_Zero") + "\n";
        }
        if (((TextField) stage.getRoot().findActor("teamName")).getText().length() > 14) {
            error += mainApp.bundle.get("Error_TeamName_Caracter_Bigger") + "\n";
        }
        if (((TextField) stage.getRoot().findActor("teamShortName")).getText().equals("") || ((TextField) stage.getRoot().findActor("teamShortName")).getText() == null) {
            error += mainApp.bundle.get("Error_TeamShortName_Caracter_Zero") + "\n";
        }
        if (((TextField) stage.getRoot().findActor("teamShortName")).getText().length() > 7) {
            error += mainApp.bundle.get("Error_TeamShortName_Caracter_Bigger") + "\n";
        }

        return error;
    }

}
