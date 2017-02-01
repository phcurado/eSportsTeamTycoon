package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.paulocurado.esportsmanager.EsportsManager;

/**
 * Created by phcur on 26/01/2017.
 */

public class WinGameDialog extends GameScreenBox {
    private String text;
    public WinGameDialog(EsportsManager mainApp, Skin skin, String path, Screen root) {
        super(mainApp, skin, path, root);
    }

    public void setText(String text) {
        ((Label) this.getActor("TextLabel")).setText(text);

        this.text = text;
    }

    public WinGameDialog getDialog() {
        return this;
    }

    public void defaultButtonClick(final Stage newStage) {
        this.getActor("OkButton").addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(newStage);
                getDialog().setVisibility(false);
            }
        });
    }
}
