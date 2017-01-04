package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.screens.GameScreen;

/**
 * Created by phcur on 25/12/2016.
 */

public class SimulateMatchDialog extends GameScreenBox {
    public SimulateMatchDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }

    public void setUpDialog(BattleSimulation battleSimulation) {
        Label radiantLabel = new Label(battleSimulation.getRadiantTeam().getName(), skin, "default");
        Label direLabel = new Label(battleSimulation.getDireTeam().getName(), skin, "default");
        Label scoreRadiantLabel = new Label(Integer.toString(battleSimulation.getRadiantVictories()), skin, "default");
        Label scoreDireLabel = new Label(Integer.toString(battleSimulation.getDireVictories()), skin, "default");

        radiantLabel.setPosition(getActor("vsLabel").getX() - radiantLabel.getWidth() - 20, getActor("vsLabel").getY());
        direLabel.setPosition(getActor("vsLabel").getX() + getActor("vsLabel").getWidth() + 20, getActor("vsLabel").getY());

        scoreRadiantLabel.setPosition(getActor("-Label").getX() - scoreRadiantLabel.getWidth() - 20, getActor("-Label").getY());
        scoreDireLabel.setPosition(getActor("-Label").getX() + getActor("-Label").getWidth() + 20, getActor("-Label").getY());

        stage.addActor(radiantLabel);
        stage.addActor(direLabel);
        stage.addActor(scoreRadiantLabel);
        stage.addActor(scoreDireLabel);

        for(int i = 0; i < battleSimulation.getRadiantTeam().getPlayers().size(); i++) {
            Image image = new Image(battleSimulation.getRadiantTeam().getPlayers().get(i).createPlayerFace(
                    ((GameScreen)root).facesOptions, ((GameScreen)root).getStage().getViewport()).getDrawable() );
            image.setSize(64, 64);
            image.setPosition(50, 250 + 100*i);

            Label label = new Label(battleSimulation.getRadiantTeam().getPlayers().get(i).getNickName(), skin, "small");
            label.setAlignment(Align.right);
            label.setPosition(image.getRight(), image.getY() + image.getHeight() / 2 - label.getHeight() / 2);

            stage.addActor(image);
            stage.addActor(label);
        }

        for(int i = 0; i < battleSimulation.getDireTeam().getPlayers().size(); i++) {
            Image image = new Image(battleSimulation.getDireTeam().getPlayers().get(i).createPlayerFace(
                    ((GameScreen)root).facesOptions, ((GameScreen)root).getStage().getViewport()).getDrawable() );
            image.setSize(64, 64);
            image.setPosition(550, 250 + 100*i);

            Label label = new Label(battleSimulation.getDireTeam().getPlayers().get(i).getNickName(), skin, "small");
            label.setAlignment(Align.left);
            label.setPosition(image.getX() - label.getWidth(), image.getY() + image.getHeight() / 2 - label.getHeight() / 2);

            stage.addActor(image);
            stage.addActor(label);
        }

    }
    public void buttonsClick() {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                for(int i = 0; i < 24; i++) {
                    stage.getActors().get(stage.getActors().size - 1).remove();
                }
                Gdx.input.setInputProcessor(((GameScreen)root).getStage());
                setVisibility(false);

                if(mainApp.championship.isGroupStage()) {
                    ((GameScreen) root).getResultMatchDialog().setVisibility(true);

                }
                else if(mainApp.championship.getWinnerOfChampionship().equals(mainApp.user.getTeam())) {
                    ((GameScreen) root).getConfirmationDialog().setVisibility(true);
                }
                else {
                    ((GameScreen)root).continueTime();
                }
            }
        });
    }
}
