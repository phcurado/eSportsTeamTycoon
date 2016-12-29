package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by phcur on 20/12/2016.
 */

public class ResultMatchDialog extends GameScreenBox {
    private int actorsAdded = 0;
    public ResultMatchDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }

    public void showRoundMatches(ArrayList<BattleSimulation> battles) {
        String battlesResult = "";
        for(int i = 0; i < battles.size(); i++) {
            battlesResult = battlesResult + battles.get(i).getRadiantTeam().getName() + " " + battles.get(i).getRadiantVictories() + " X " +
                    battles.get(i).getDireVictories() + " " + battles.get(i).getDireTeam().getName() + "\n";
            Label result = new Label(battles.get(i).getRadiantVictories() + " X " + battles.get(i).getDireVictories(), skin, "default");
            result.setPosition(getActor("ResultMatchBox").getX() + getActor("ResultMatchBox").getWidth() / 2 - result.getWidth() / 2,
                    getActor("ResultMatchBox").getY() + getActor("ResultMatchBox").getHeight() / 2 - result.getHeight() / 2 +
            i*2*result.getHeight() - 50);

            Label teamRadiant = new Label(battles.get(i).getRadiantTeam().getName(), skin, "default");
            teamRadiant.setPosition(result.getX() - teamRadiant.getWidth() - 10, result.getY());

            Label teamDire = new Label(battles.get(i).getDireTeam().getName(), skin, "default");
            teamDire.setPosition(result.getRight() + 10, result.getY());

            stage.addActor(result);
            stage.addActor(teamRadiant);
            stage.addActor(teamDire);
            actorsAdded = actorsAdded + 3;
        }
    }

    public void buttonsClick() {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                for(int i = 0; i < actorsAdded; i++) {
                    stage.getActors().get(stage.getActors().size - 1).remove();
                }
                Gdx.input.setInputProcessor(((GameScreen)root).getStage());
                if(mainApp.championship.isFinalsUp()) {
                   mainApp.championship.setGroupStage(false);
                }
                setVisibility(false);
                ((GameScreen)root).continueTime();

            }
        });
    }
}
