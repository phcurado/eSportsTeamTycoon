package com.paulocurado.esportsmanager.uielements;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Paulo on 12/11/2016.
 */

public class NewGameDialog extends Dialog {
    String inputText;
    TextField.TextFieldStyle style;
    TextField textField;

    public NewGameDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }
}
