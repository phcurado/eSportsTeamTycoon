package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.model.Team;
import com.paulocurado.esportsmanager.screens.GameScreen;

/**
 * Created by phcur on 04/01/2017.
 */

public class DuelDialog extends GameScreenBox {
    private boolean isLoadInformation = false;
    public DuelDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);
    }

    public void setUpPlayers(BattleSimulation battleSimulation) {
        if (!isLoadInformation()) {
;

            for (int i = 0; i < battleSimulation.getRadiantTeam().getPlayers().size(); i++) {
                Image playerImage = new Image(battleSimulation.getRadiantTeam().getPlayers().get(i).createPlayerFace(
                        ((GameScreen)root).facesOptions, stage.getViewport()).getDrawable() );
                playerImage.setSize(100, 100);
                playerImage.setPosition(getActor("genericWindow").getX() + getActor("genericWindow").getWidth() / 2
                        - 105 * battleSimulation.getRadiantTeam().getPlayers().size() / 2 + 105 * i,
                        getActor("genericWindow").getTop() - 180);

                Label playerLabel = new Label(battleSimulation.getRadiantTeam().getPlayers().get(i).getNickName(), skin, "default_PlayerName");
                playerLabel.setPosition(playerImage.getX() + playerImage.getWidth() / 2 - playerLabel.getWidth() / 2,
                        playerImage.getY() - playerLabel.getHeight());


                stage.addActor(playerImage);
                stage.addActor(playerLabel);
            }

            Label versusLabel = new Label("Vs", skin, "labelWhiteGameScreen");
            versusLabel.setPosition(getActor("genericWindow").getX() + getActor("genericWindow").getWidth() / 2 - versusLabel.getWidth() / 2,
                    getActor("genericWindow").getY() + getActor("genericWindow").getHeight() / 2);

            stage.addActor(versusLabel);

            Label radiantTeamLabel = new Label(battleSimulation.getRadiantTeam().getName(), skin, "default_blue");
            radiantTeamLabel.setPosition(getActor("genericWindow").getX() + getActor("genericWindow").getWidth() / 2 - radiantTeamLabel.getWidth() / 2,
                    versusLabel.getTop());
            Label direTeamLabel = new Label(battleSimulation.getDireTeam().getName(), skin, "default_blue");
            direTeamLabel.setPosition(getActor("genericWindow").getX() + getActor("genericWindow").getWidth() / 2 - direTeamLabel.getWidth() / 2,
                    versusLabel.getY() - direTeamLabel.getHeight());

            stage.addActor(radiantTeamLabel);
            stage.addActor(direTeamLabel);


            for (int i = 0; i < battleSimulation.getDireTeam().getPlayers().size(); i++) {
                Image playerImage = new Image(battleSimulation.getDireTeam().getPlayers().get(i).createPlayerFace(
                        ((GameScreen)root).facesOptions, stage.getViewport()).getDrawable() );
                playerImage.setSize(100, 100);
                playerImage.setPosition(getActor("genericWindow").getX() + getActor("genericWindow").getWidth() / 2
                                - 105 * battleSimulation.getDireTeam().getPlayers().size() / 2 + 105 * i,
                        getActor("genericWindow").getY() + 150);

                Label playerLabel = new Label(battleSimulation.getDireTeam().getPlayers().get(i).getNickName(), skin, "default_PlayerName");
                playerLabel.setPosition(playerImage.getX() + playerImage.getWidth() / 2 - playerLabel.getWidth() / 2,
                        playerImage.getY() - playerLabel.getHeight());


                stage.addActor(playerImage);
                stage.addActor(playerLabel);
            }
        }

    }

    public void buttonClick() {
        getActor("BattleButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                isLoadInformation = true;
                Gdx.input.setInputProcessor(((GameScreen)root).getStage());
            }
        });
    }


    public boolean isLoadInformation() {
        return isLoadInformation;
    }

    public void setLoadInformation(boolean loadInformation) {
        isLoadInformation = loadInformation;
    }
}
