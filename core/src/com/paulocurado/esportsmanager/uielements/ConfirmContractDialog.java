package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;
import com.paulocurado.esportsmanager.screens.HireScreen;

/**
 * Created by Paulo on 07/12/2016.
 */

public class ConfirmContractDialog extends GameScreenBox {
    public ConfirmContractDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);

        buttonsClick();
    }

    private void buttonsClick() {
        this.getActor("YesButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(((HireScreen)root).getHireDialog().getStage());
                setVisibility(false);
                ((HireScreen)root).getHireDialog().setVisibility(false);
                //TODO fazer a transferencia e pensar em fazer uma classe que tenha funções de find
            }
        });

        this.getActor("NoButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(((HireScreen)root).getHireDialog().getStage());
                setVisibility(false);
            }
        });
    }
}
