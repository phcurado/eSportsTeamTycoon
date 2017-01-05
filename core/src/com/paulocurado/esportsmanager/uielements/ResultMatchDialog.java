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
        for (int i = 0; i < battles.size(); i++) {
            Label radiantTeamLabel = new Label(battles.get(i).getRadiantTeam().getName(), skin, "labelDarkGraySimulation");
            Label radiantScoreLabel = new Label(Integer.toString(battles.get(i).getRadiantVictories()), skin, "labelDarkGraySimulation");
            Label direTeamLabel = new Label(battles.get(i).getDireTeam().getName(), skin, "labelDarkGraySimulation");
            Label direScoreLabel = new Label(Integer.toString(battles.get(i).getDireVictories()), skin, "labelDarkGraySimulation");
            Label versusLabel = new Label("-", skin, "labelDarkGraySimulation");


            versusLabel.setPosition(getActor("ResultMatchBox").getX() + getActor("ResultMatchBox").getWidth() / 2 - versusLabel.getWidth() / 2,
                    getActor("ResultMatchBox").getTop() - 140 - 70 * i);

            radiantScoreLabel.setPosition(versusLabel.getX() - radiantScoreLabel.getWidth(), versusLabel.getY());

            radiantTeamLabel.setWidth(radiantScoreLabel.getX() - getActor("ResultMatchBox").getX() - 23);
            radiantTeamLabel.setPosition(getActor("ResultMatchBox").getX() + 20, versusLabel.getY());
            radiantTeamLabel.setAlignment(Align.right);

            direScoreLabel.setPosition(versusLabel.getRight(), versusLabel.getY());
            direTeamLabel.setWidth(getActor("ResultMatchBox").getRight() - direScoreLabel.getRight() - 23);
            direTeamLabel.setPosition(direScoreLabel.getRight() + 3, versusLabel.getY());

            stage.addActor(radiantTeamLabel);
            stage.addActor(radiantScoreLabel);
            stage.addActor(versusLabel);
            stage.addActor(direTeamLabel);
            stage.addActor(direScoreLabel);

            actorsAdded = actorsAdded + 5;

        }
    }

    public void buttonsClick() {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                for(int i = 0; i < actorsAdded; i++) {
                    stage.getActors().get(stage.getActors().size - 1).remove();
                }
                actorsAdded = 0;
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
