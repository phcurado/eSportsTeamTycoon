package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.model.BattleSimulation;
import com.paulocurado.esportsmanager.screens.GameScreen;

/**
 * Created by phcur on 20/12/2016.
 */

public class ResultMatchDialog extends GameScreenBox {
    public ResultMatchDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }

    public void setUpDialog(BattleSimulation battleSimulation) {
        ((Label)getActor("ResultLabel")).setText(battleSimulation.getRadiantTeam().getName() + " " + battleSimulation.getRadiantVictories() + " X " +
                battleSimulation.getDireVictories() + " " + battleSimulation.getDireTeam().getName() );
    }

    public void buttonsClick() {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(((GameScreen)root).getStage());
                setVisibility(false);
                ((GameScreen)root).continueTime();
            }
        });
    }
}
