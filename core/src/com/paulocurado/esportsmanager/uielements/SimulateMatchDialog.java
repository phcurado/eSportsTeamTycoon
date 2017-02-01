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

/**
 * Created by phcur on 25/12/2016.
 */

public class SimulateMatchDialog extends GameScreenBox {
    private int actorsAdded = 0;
    public SimulateMatchDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }
    public void setUpDialog(BattleSimulation battleSimulation) {
        Label radiantTeamLabel = new Label(battleSimulation.getRadiantTeam().getName(), skin, "labelDarkGraySimulation");
        Label radiantScoreLabel = new Label(Integer.toString(battleSimulation.getRadiantVictories()), skin, "labelDarkGraySimulation");
        Label direTeamLabel = new Label(battleSimulation.getDireTeam().getName(), skin, "labelDarkGraySimulation");
        Label direScoreLabel = new Label(Integer.toString(battleSimulation.getDireVictories()), skin, "labelDarkGraySimulation");
        Label versusLabel = new Label("-", skin, "labelDarkGraySimulation");


        versusLabel.setPosition(getActor("ResultMatchBox").getX() + getActor("ResultMatchBox").getWidth() / 2 - versusLabel.getWidth() / 2,
                getActor("ResultMatchBox").getTop() - 140);

        radiantScoreLabel.setPosition(versusLabel.getX() - radiantScoreLabel.getWidth(), versusLabel.getY());

        radiantTeamLabel.setWidth(radiantScoreLabel.getX() - getActor("ResultMatchBox").getX() - 23);
        radiantTeamLabel.setPosition(getActor("ResultMatchBox").getX() + 20, versusLabel.getY());
        radiantTeamLabel.setAlignment(Align.right);

        direScoreLabel.setPosition(versusLabel.getRight(), versusLabel.getY());
        direTeamLabel.setWidth(getActor("ResultMatchBox").getRight() - direScoreLabel.getRight() - 23);
        direTeamLabel.setPosition(direScoreLabel.getRight() + 3, versusLabel.getY());

        Label congratz = new Label(mainApp.bundle.format("Final_Match_Congratz", battleSimulation.winner().getName(),
                battleSimulation.winner().getTier()), skin, "default");
        congratz.setWidth(getActor("ResultMatchBox").getWidth() - getActor("ResultMatchBox").getX() - 20);
        congratz.setPosition(getActor("ResultMatchBox").getX() + getActor("ResultMatchBox").getWidth() / 2 - congratz.getWidth() / 2, versusLabel.getY() - 3 * congratz.getHeight());

        congratz.setWrap(true);
        congratz.setAlignment(Align.center);

        stage.addActor(radiantTeamLabel);
        stage.addActor(radiantScoreLabel);
        stage.addActor(versusLabel);
        stage.addActor(direTeamLabel);
        stage.addActor(direScoreLabel);
        stage.addActor(congratz);

        actorsAdded = actorsAdded + 5;
    }

    /*public void setUpDialog(BattleSimulation battleSimulation) {
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
        System.out.println("hello");

    }*/
    public void buttonsClick() {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                for(int i = 0; i < actorsAdded; i++) {
                    stage.getActors().get(stage.getActors().size - 1).remove();
                }
                actorsAdded = 0;
                Gdx.input.setInputProcessor(((GameScreen)root).getStage());
                setVisibility(false);

                if(mainApp.championship.isGroupStage()) {
                    ((GameScreen) root).getResultMatchDialog().setVisibility(true);

                }
                else if(mainApp.championship.getWinnerOfChampionship().equals(mainApp.user.getTeam())) {
                    if(mainApp.user.getTeam().getTier() != 1) {
                        ((GameScreen) root).getConfirmationDialog().setVisibility(true);
                    }
                }
                else if (mainApp.championship.getFinalChampionshipPositions().get(
                        mainApp.championship.getFinalChampionshipPositions().size() - 1).equals(mainApp.user.getTeam())) {
                    if(mainApp.user.getTeam().getTier() != 4) {
                        ((GameScreen) root).getConfirmationDialog().setVisibility(true);
                    }
                    else {
                        mainApp.setScreen(mainApp.championshipScreen);
                    }
                }
                else {
                    mainApp.setScreen(mainApp.championshipScreen);
                    //((GameScreen)root).continueTime();
                }
            }
        });
    }
}
